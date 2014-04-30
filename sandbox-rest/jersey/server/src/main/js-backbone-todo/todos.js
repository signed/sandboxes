// An example Backbone application contributed by
// [Jérôme Gravel-Niquet](http://jgn.me/). This demo uses a simple
// [LocalStorage adapter](backbone-localstorage.html)
// to persist Backbone models within your browser.

// Load the application once the DOM is ready, using `jQuery.ready`:
_thomas={};

function PadDigits(n, totalDigits){
    n = n.toString();
    var pd = '';
    if (totalDigits > n.length)
    {
        for (i=0; i < (totalDigits-n.length); i++)
        {
            pd += '0';
        }
    }
    return pd + n.toString();
}

var toDate = function(str){
  var year = str.substring(0,4);
  var month = str.substring(4,6);
  var dayOfMonth = str.substring(6,8);
  var hourOfDay = str.substring(8,10);
  var minutesOfHour = str.substring(10,12);
  var secondsOfMinute = str.substring(12,14);
  var shiftedMonth = parseInt(month, 10)-1;
  return new Date(year, shiftedMonth , dayOfMonth, hourOfDay, minutesOfHour, secondsOfMinute).toString();
}

var fromDateToString = function(date){
  var year = PadDigits(date.getFullYear(), 4);
  var month = PadDigits((date.getMonth+1),2);
  var dayOfMonth = PadDigits(date.getDate(),2);
  var hourOfDay = PadDigits(date.getHours(), 2);
  var minutesOfHour = PadDigits(date.getMinutes(),2);
  var secondsOfMinutes = PadDigits(date.getSeconds(), 2);
  return year+''+month+''+dayOfMonth+''+hourOfDay+''+minutesOfHour+''+secondsOfMinute;
}

$(function(){

  // Todo Model
  // ----------
  _thomas.Todo = Backbone.Model.extend({

    urlRoot : 'http://localhost:8182/buggy/todos',

    // Default attributes for a todo item.
    defaults: function() {
      return {
        done:  false,
      };
    },

    // Toggle the `done` state of this todo item.
    toggle: function() {
      this.save({done: !this.get("done")});
    }

  });

  // Todo Collection
  // ---------------
  _thomas.TodoList = Backbone.Collection.extend({

    // Reference to this collection's model.
    model: _thomas.Todo,

    initialize: function(options) {
        options || (options = {});
        this.appendix = options.appendix || '';
        this.sortProperty = 'id';
        this.sortByName();
    },

    url: function () {
        var createdUrl = 'http://localhost:8182/buggy/todos/' + this.appendix;
        return createdUrl;
    },

    forYear: function(){
        this.appendix = 'year/';
        this.fetch();
    },

    // Filter down the list of all todo items that are finished.
    done: function() {
      return this.filter(function(todo){ return todo.get('done'); });
    },

    // Filter down the list to only todo items that are still not finished.
    remaining: function() {
      return this.without.apply(this, this.done());
    },

    sortByName: function(){
      this.comparator = function(todo){
        return todo.get('text');
      };
      this.sort();
    },

    sortByReception: function(){
          this.comparator = function(todo){
            return todo.get('received');
          };
          this.sort();
    },

    sortById: function(){
       this.comparator = function(todo){
        return todo.get('id');
       };
      this.sort();
    }
  });

  // Create our global collection of **Todos**.
  _thomas.Todos = new _thomas.TodoList;

  // Todo Item View
  // --------------

  // The DOM element for a todo item...
  _thomas.TodoView = Backbone.View.extend({

    //... is a list tag.
    tagName:  "li",

    // Cache the template function for a single item.
    template: _.template($('#item-template').html()),

    // The DOM events specific to an item.
    events: {
      "click .check"              : "toggleDone",
      "dblclick div.todo-text"    : "edit",
      "click span.todo-destroy"   : "clear",
      "keypress .todo-input"      : "updateOnEnter"
    },

    // The TodoView listens for changes to its model, re-rendering.
    initialize: function() {
      this.model.bind('change', this.render, this);
      this.model.bind('destroy', this.remove, this);
    },

    // Re-render the contents of the todo item.
    render: function() {
      $(this.el).html(this.template(this.model.toJSON()));
      this.setText();
      this.setReceptionDate();
      return this;
    },

    // To avoid XSS (not that it would be harmful in this particular app),
    // we use `jQuery.text` to set the contents of the todo item.
    setText: function() {
      var text = this.model.get('text');
      this.$('.todo-text').text(text);
      this.input = this.$('.todo-input');
      this.input.bind('blur', _.bind(this.close, this)).val(text);
    },

    setReceptionDate: function() {
      var text = this.model.get('received');
      this.$('.reception-date').text(text);
    },

    // Toggle the `"done"` state of the model.
    toggleDone: function() {
      this.model.toggle();
    },

    // Switch this view into `"editing"` mode, displaying the input field.
    edit: function() {
      $(this.el).addClass("editing");
      this.input.focus();
    },

    // Close the `"editing"` mode, saving changes to the todo.
    close: function() {
      this.model.save({text: this.input.val()});
      $(this.el).removeClass("editing");
    },

    // If you hit `enter`, we're through editing the item.
    updateOnEnter: function(e) {
      if (e.keyCode == 13) this.close();
    },

    // Remove this view from the DOM.
    remove: function() {
      $(this.el).remove();
    },

    // Remove the item, destroy the model.
    clear: function() {
      this.model.destroy();
    }

  });

  // The Application
  // ---------------

  // Our overall **AppView** is the top-level piece of UI.
  _thomas.AppView = Backbone.View.extend({

    // Instead of generating a new element, bind to the existing skeleton of
    // the App already present in the HTML.
    el: $("#todoapp"),

    // Our template for the line of statistics at the bottom of the app.
    statsTemplate: _.template($('#stats-template').html()),

    // Delegated events for creating new items, and clearing completed ones.
    events: {
      "keypress #new-todo":  "createOnEnter",
      "keyup #new-todo":     "showTooltip",
      "click .todo-clear a": "clearCompleted"
    },

    // At initialization we bind to the relevant events on the `Todos`
    // collection, when items are added or changed. Kick things off by
    // loading any preexisting todos that might be saved in *localStorage*.
    initialize: function() {
      this.input    = this.$("#new-todo");

      _thomas.Todos.bind('add',   this.addOne, this);
      _thomas.Todos.bind('reset', this.addAll, this);
      _thomas.Todos.bind('all',   this.render, this);

      _thomas.Todos.fetch();
    },

    // Re-rendering the App just means refreshing the statistics -- the rest
    // of the app doesn't change.
    render: function() {
      this.$('#todo-stats').html(this.statsTemplate({
        total:      _thomas.Todos.length,
        done:       _thomas.Todos.done().length,
        remaining:  _thomas.Todos.remaining().length
      }));
    },

    // Add a single todo item to the list by creating a view for it, and
    // appending its element to the `<ul>`.
    addOne: function(todo) {
      var view = new _thomas.TodoView({model: todo});
      $("#todo-list").append(view.render().el);
    },

    // Add all items in the **Todos** collection at once.
    addAll: function() {
       this.clearList();
      _thomas.Todos.each(this.addOne);
    },

    clearList: function() {
        $("#todo-list").html('');
    },

    // If you hit return in the main input field, and there is text to save,
    // create new **Todo** model persisting it to *localStorage*.
    createOnEnter: function(e) {
      var text = this.input.val();
      if (!text || e.keyCode != 13) return;
      _thomas.Todos.create({text: text});
      this.input.val('');
    },

    // Clear all done todo items, destroying their models.
    clearCompleted: function() {
      _.each(_thomas.Todos.done(), function(todo){ todo.destroy(); });
      return false;
    },

    // Lazily show the tooltip that tells you to press `enter` to save
    // a new todo item, after one second.
    showTooltip: function(e) {
      var tooltip = this.$(".ui-tooltip-top");
      var val = this.input.val();
      tooltip.fadeOut();
      if (this.tooltipTimeout) clearTimeout(this.tooltipTimeout);
      if (val == '' || val == this.input.attr('placeholder')) return;
      var show = function(){ tooltip.show().fadeIn(); };
      this.tooltipTimeout = _.delay(show, 1000);
    }

  });

  // Finally, we kick things off by creating the **App**.
  _thomas.App = new _thomas.AppView;
});
