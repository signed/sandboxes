define(["backbone", "underscore", "text!booktemplate.html!strip"], function(Backbone, _, booktemplate) {

    return Backbone.View.extend({
        tagName:'tr',

        template : _.template(booktemplate),

        render: function() {
            $(this.el).html(this.template(this.model.toJSON()));
            return this;
        }
    });
});