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
        template: JST['app/scripts/templates/editor.ejs'],
        text: '',

        events: {
            'click #save': 'save',

            'click #delete': 'deleteDevice'
        },

        save: function() {
            if (this.model != undefined) {
                var isNew = this.model.isNew();
                this.model.set('semantics', this.editor.getValue());
                this.model.set('name', this.$("input").val());
                var self = this;
                this.$("#save").button("loading");
                this.model.save({}, {
                    success: function(model) {
                        window.forceUpdateHints();
                        self.collection.add(model);
                        setTimeout(function() {
                            self.$("#save").button("reset");
                        }, 200);
                        self.editor.setValue(model.get('semantics'));
                        self.content = self.editor.getValue();
                        window.questions.fetch({
                            reset: true
                        });
                        window.location.hash = "#device/" + model.id;
                    }
                });
            }
        },

        deleteDevice: function() {
            var self = this;
            this.$("#delete").button("loading");
            this.model.destroy({
                success: function(model) {
                    window.forceUpdateHints();
                    self.collection.remove(model);
                    window.location.hash = "#";
                    setTimeout(function() {
                        self.$("#save").button("reset");
                    }, 200);
                }
            });
        },

        has_unsaved: function() {
            return this.content != this.editor.getValue() ||
                (this.$("input").val() != this.model.get('name') && !this.model.get('remote'));
        },

        set_attr: function(attributes) {
            this.model = attributes.model;
            this.render();
        },
        after_render: function() {
            var self = this;
            this.editor = codemirror.fromTextArea(this.$("textarea")[0], {
                mode: "text/turtle",
                matchBrackets: true,
                lineNumbers: true,
                autoCloseBrackets: true,
                gutters: ["CodeMirror-lint-markers"],
                lint: true,
                readOnly: self.model != undefined && this.model.get('remote'),
                extraKeys: {
                    "Ctrl-Space": "autocomplete",
                    'Cmd-/': 'toggleComment',
                    'Ctrl-/': 'toggleComment'
                },
                errors: function() {
                    if (self.model != undefined) {
                        return self.model.get('errors');
                    }
                    return undefined;
                }
            });
            if (this.model) {
                this.editor.setValue(this.model.get('semantics'));
                this.content = this.editor.getValue();
                this.name = this.model.get('name');
            }
            var cm = this.$(".CodeMirror");
            var self = this;
            $(window).resize(function() {
                var windowHeight = $(window).height() - 190;
                // cm.css('height',windowHeight + 'px');
                self.editor.setSize(null, windowHeight);

            }).resize();
            window.updateHints();
            this.delegateEvents();
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
