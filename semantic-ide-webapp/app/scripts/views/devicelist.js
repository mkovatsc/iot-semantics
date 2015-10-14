/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'collections/devices',
    'views/editor'
], function($, _, Backbone, BaseView, JST, DevicesCollection, Editor) {
    'use strict';

    var DevicesView = BaseView.extend({

        template: JST['app/scripts/templates/devicelist.ejs'],

        events: {
            'click .devicetoggle': 'devicetoggle'
        },

        initialize: function(options) {
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
                devices: this.collection.toJSON()
            };
        },

        devicetoggle: function(e) {
            e.preventDefault();
            var id = $(e.target).closest('li').data('id');
            var model = this.collection.get(parseInt(id));
            var value = !model.get('disabled');
            model.set('disabled', value);
            var icon = $(e.target).closest('li').find('i')
                .removeClass("fa-toggle-on")
                .removeClass("fa-toggle-off");
            icon.addClass("fa-spinner fa-spin");
            model.save({}, {
                success: function() {
                    window.forceUpdateHints();
                }
            });
        }

    });

    return DevicesView;
});
