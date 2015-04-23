/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'collections/queries',
    'views/editor'
], function($, _, Backbone, BaseView, JST, QueryCollection, Editor) {
    'use strict';

    var QueryListView = BaseView.extend({

        template: JST['app/scripts/templates/querylist.ejs'],

        events: {
            'click .watchtoggle': 'watchtoggle'
        },

        initialize: function(options) {
            this.base_url = options.base_url;
            this.collection = options.collection;
            this.listenTo(this.collection, 'sync change', this.render);
            BaseView.prototype.initialize.call(this);
        },

        after_render: function() {
            this.$("[data-id=" + this.current + "]").addClass("active");
            this.delegateEvents();

        },

        set_attr: function(attributes) {
            this.current = parseInt(attributes[0]);
            if (isNaN(this.current)) {
                this.current = "new";
            }
            this.render();
        },

        get_data: function() {
            return {
                base_url: this.base_url,
                queries: this.collection.toJSON()
            };
        },

        watchtoggle: function(e) {
            e.preventDefault();
            var id = $(e.target).closest('li').data('id');
            var model = this.collection.get(parseInt(id));
            var value = !model.get('watch');
            model.set('watch', value);
            var icon = $(e.target).closest('li').find('i')
                .removeClass("fa-eye-slash")
                .removeClass("fa-eye");
            icon.addClass("fa-spinner fa-spin");
            model.save();
        }
    });

    return QueryListView;
});
