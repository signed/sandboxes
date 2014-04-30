define(["backbone", "bookmodel"], function(Backbone, BookModel) {

    return Backbone.Collection.extend({
        model:BookModel
    });
});