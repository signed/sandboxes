define(["backbone", "bookview"], function(Backbone, BookView) {

    return Backbone.View.extend({
        el: $("#booklist"),

        initialize: function(options){
            options || (options={});
            this.books = options.books;
            this.books.bind('all',   this.render, this);
        },

        render:function(){
            this.$el.empty();
            this.books.each(this.renderSingleBook);
        },

        renderSingleBook:function(book){
            var view = new BookView({model: book});
            $("#booklist").append(view.render().el);
        }
    });
});