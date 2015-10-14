/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'collections/questions',
    'views/question'
], function($, _, Backbone, BaseView, JST, QuestionsCollection, QuestionView) {
    'use strict';

    window.questions = new QuestionsCollection();


    var DevicesView = BaseView.extend({
        className: 'row',

        template: JST['app/scripts/templates/questions.ejs'],

        events: {
            'click #clear': 'deleteAnswers'
        },

        initialize: function() {
            this.collection = window.questions;
            this.listenTo(this.collection, 'reset', this.render);
            BaseView.prototype.initialize.call(this);
            this.collection.fetch({
                reset: true
            });

        },

        after_render: function() {
            _.each(this.collection.models, function(model) {
                var view = new QuestionView({
                    model: model
                });
                this.$(".questions").append(view.el);
                view.render();
            }, this);
            this.d
        },

        deleteAnswers: function() {
            var self = this;
            $.ajax({
                url: window.workspace_url() + '/questions',
                type: 'DELETE',
                success: function() {
                    window.forceUpdateHints();
                    self.collection.fetch({
                        reset: true
                    });
                }
            });
        },

        get_data: function() {
            return {
                questions: this.collection.toJSON()
            };
        },

    });

    return DevicesView;
});
