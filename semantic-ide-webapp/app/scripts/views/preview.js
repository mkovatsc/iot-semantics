/*global define*/

define([
    'jquery',
    'underscore',
    'views/codemirror',
    'backbone',
    'views/base',
    'templates',
    'views/menu'
], function($, _, codemirror, Backbone, BaseView, JST, MenuView) {
    'use strict';

    var EditorView = BaseView.extend({
        template: JST['app/scripts/templates/preview.ejs'],
        text: '',

        set_attr: function(attributes) {
            this.model = attributes.model;
            this.render();
        },

        after_render: function() {
            this.editor = codemirror.fromTextArea(this.$("textarea")[0], {
                mode: "text/turtle",
                matchBrackets: true,
                lineNumbers: true,
                readOnly: true
            });
            if (this.model) {
                this.editor.setValue(this.model.get('processed'));
            }
            var cm = this.$(".CodeMirror");
            var self = this;
            $(window).resize(function() {
                var windowHeight = $(window).height() - 190;
                // cm.css('height',windowHeight + 'px');
                self.editor.setSize(null, windowHeight);

            }).resize();
        },

        get_data: function() {
            var text = "";
            if (this.model !== undefined) {
                return {
                    device: this.model.toJSON(),
                    isNew: this.model.isNew()
                };
            }
            return {
                device: {
                    name: "",
                    semantics: ""
                },
                isNew: true
            };
        }

    });

    return EditorView;
});
