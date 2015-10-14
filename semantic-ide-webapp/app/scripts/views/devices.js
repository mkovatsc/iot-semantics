/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'collections/devices',
    'views/editor',
    'views/preview',
    'views/devicelist'
], function($, _, Backbone, BaseView, JST, DevicesCollection, Editor, Preview, DeviceList) {
    'use strict';

    var DevicesView = BaseView.extend({
        className: 'row',

        template: JST['app/scripts/templates/devices.ejs'],

        initialize: function() {
            this.collection = new DevicesCollection();
            this.editor = new Editor();
            this.preview = new Preview();
            this.subviews = {
                '#editor': this.editor,
                '#devicelist': new DeviceList({
                    collection: this.collection
                })
            };
            this.current = 0;
            this.listenTo(this.collection, 'reset', this.render);
            BaseView.prototype.initialize.call(this);
            this.collection.fetch({
                reset: true
            });
            this.subviews['#editor'].collection = this.collection;

        },

        after_render: function() {
            this.$("[data-id=" + this.current + "]").addClass("active");
            var model = this.collection.get(this.current);
            if (this.current == "new") {
                model = new this.collection.model();
            }
            this.subviews['#editor'].set_attr({
                model: model
            });

        },

        has_unsaved: function() {
            if (this.subviews['#editor'] == this.editor) {
                return this.editor.has_unsaved();
            }
            return false;
        },

        set_attr: function(attributes) {
            this.subviews['#devicelist'].set_attr(attributes);
            this.current = parseInt(attributes[0]);
            if (isNaN(this.current)) {
                this.current = "new";
            }
            if (attributes[1] == 'preview') {
                this.subviews['#editor'] = this.preview;

            } else {
                this.subviews['#editor'] = this.editor;
            }
            this.render();
        }

    });

    return DevicesView;
});
