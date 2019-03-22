$(function(){
    'use strict';
      var TheModel = Backbone.Model.extend({
          defaults: {
              "appetizer":  "caesar salad",
              "entree":     "ravioli",
              "dessert":    "cheesecake"
           }
      });

      var MyCollection = Backbone.Model.extend({
        initialize: function(models, options){
            options = options || {};
            this.model = options.model;
            this.pferd = options.pferd;
        },

        model:TheModel

      });

      var myCollection = new MyCollection([], {pferd:'fury', model:TheModel});
      console.log(myCollection);
});