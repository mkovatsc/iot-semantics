/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/codemirror',
    'views/base',
    'views/querylist',
    'collections/queries',
    'templates',
    'views/menu'
], function($, _, Backbone, codemirror, BaseView, QueryListView, QueryCollection, JST, MenuView) {
    'use strict';

    var QueryView = BaseView.extend({
        template: JST['app/scripts/templates/query.ejs'],

        events: {
            'click #executeQuery': 'execute_query',
            'click #reasonerOutput': 'reasoner_output',

            'click .update': 'update',
            'click .store': 'store',
            'click #delete': 'deleteQuery',
            'click .run': 'run'
        },

        collection_type: QueryCollection,
        base_url: 'query',

        initialize: function() {
            this.hints = [];
            this.collection = new this.collection_type();
            this.subviews = {
                '#querylist': new QueryListView({
                    base_url: this.base_url,
                    collection: this.collection
                })
            };
            this.current = 1;
            this.listenTo(this.collection, 'reset', this.render);
            this.collection.fetch({
                reset: true
            });
        },

        after_render: function() {
            var self = this;
            this.query = codemirror.fromTextArea(this.$(".query")[0], {
                mode: "text/turtle",
                matchBrackets: true,
                lineNumbers: true,
                viewportMargin: Infinity,
                lint: true,
                autoCloseBrackets: true,
                extraKeys: {
                    "Ctrl-Space": "autocomplete",
                    'Cmd-/': 'toggleComment',
                    'Ctrl-/': 'toggleComment'
                },
                gutters: ["CodeMirror-lint-markers"],
                errors: function() {
                    return self.query_errors;
                }
            });
            this.input = codemirror.fromTextArea(this.$(".input")[0], {
                mode: "text/turtle",
                matchBrackets: true,
                lineNumbers: true,
                viewportMargin: Infinity,
                lint: true,
                autoCloseBrackets: true,
                extraKeys: {
                    "Ctrl-Space": "autocomplete",
                    'Cmd-/': 'toggleComment',
                    'Ctrl-/': 'toggleComment'
                },
                gutters: ["CodeMirror-lint-markers"],
                errors: function() {
                    return self.input_errors;
                }
            });
            this.render_results();

            var model = this.collection.get(this.current);
            if (model != undefined) {
                this.input_errors = [];
                this.query_errors = [];
                try {
                    this.input.setValue(model.get('input'));
                    this.query.setValue(model.get('query'));

                    if (this.result != undefined && this.result.setValue != undefined) {
                        this.result.setValue("");
                    }
                } catch (err) {

                }
                this.execute_query();
            }
            this.input_text = this.input.getValue();
            this.query_text = this.query.getValue();
            window.updateHints();
            this.delegateEvents();
        },

        render_results: function() {
            this.result = codemirror.fromTextArea(this.$(".result")[0], {
                mode: "text/turtle",
                matchBrackets: true,
                lineNumbers: true,
                readOnly: true,
                autoCloseBrackets: true,
                viewportMargin: Infinity,
                lint: true
            });
        },

        reasoner_output: function() {
            var self = this;
            var button = this.$('#reasonerOutput');
            button.button('loading');
            $.ajax({
                type: "POST",
                url: window.workspace_url() + '/query/true',
                data: JSON.stringify({
                    input: this.input.getValue(),
                    query: this.query.getValue()
                }),
                contentType: "application/json; charset=utf-8"
            }).done(function(data) {
                self.result.setValue(data.result);
                self.input_errors = data.errinput;
                self.query_errors = data.errquery;
                self.query.setValue(self.query.getValue());
                self.input.setValue(self.input.getValue());
            }).complete(function() {
                button.button('reset');
            });
        },

        execute_query: function() {
            var self = this;
            var button = this.$('#executeQuery');
            button.button('loading');
            $.ajax({
                type: "POST",
                url: window.workspace_url() + '/query/false',
                data: JSON.stringify({
                    input: this.input.getValue(),
                    query: this.query.getValue()
                }),
                contentType: "application/json; charset=utf-8"
            }).done(function(data) {
                self.result.setValue(data.result);
                self.input_errors = data.errinput;
                self.query_errors = data.errquery;
                self.query.setValue(self.query.getValue());
                self.input.setValue(self.input.getValue());
            }).complete(function() {
                button.button('reset');
            });
        },

        has_unsaved: function() {
            return this.query_text != this.query.getValue() || this.input_text != this.input.getValue();
        },

        store: function(e) {
            e.preventDefault();
            var name = prompt("Please enter query name");
            if (name && name.length > 0) {
                var button = this.$('#executeQuery');
                button.button('loading');
                var query = this.query.getValue();
                var input = this.input.getValue();
                var model = new this.collection.model();
                var self = this;
                model.save({
                    name: name,
                    query: query,
                    input: input
                }, {
                    success: function(model) {
                        self.input_text = self.input.getValue();
                        self.query_text = self.query.getValue();
                        window.location.hash = "#" + window.workspace + "/" + self.base_url + "/" + model.id;
                        button.button('reset');
                    }
                });
                this.collection.add(model);
            }
        },

        update: function(e) {
            e.preventDefault();
            var button = this.$('#executeQuery');
            button.button('loading');
            var query = this.query.getValue();
            var input = this.input.getValue();
            var model = this.collection.get(this.current);
            var self = this;
            model.save({
                query: query,
                input: input
            }, {
                success: function() {
                    button.button('reset');
                    self.input_text = self.input.getValue();
                    self.query_text = self.query.getValue();
                }
            });
            this.collection.add(model);
        },

        deleteQuery: function() {
            var self = this;
            var model = this.collection.get(this.current);
            this.$("#delete").button("loading");
            var self = this;
            model.destroy({
                success: function(model) {
                    self.collection.remove(model);
                    self.current = null;
                    window.location.hash = "#" + window.workspace + "/" + self.base_url;
                }
            });
        },

        set_attr: function(attributes) {
            this.result = "";
            this.subviews['#querylist'].set_attr(attributes);
            this.current = parseInt(attributes[0]);
            if (isNaN(this.current)) {
                this.current = "new";
            }
            this.render();
        },

        get_data: function() {


            var model = this.collection.get(this.current);
            if (model != undefined) {
                return {
                    query: model.toJSON()
                };
            }
            return {
                query: {
                    id: null,
                    name: ""
                }
            }
        },

        run: function(){

        }

    });

    return QueryView;
});
