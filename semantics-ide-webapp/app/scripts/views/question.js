/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'collections/questions'
], function($, _, Backbone, BaseView, JST) {
    'use strict';

    var QuestionView = BaseView.extend({
        className: 'well',

        template: JST['app/scripts/templates/question.ejs'],


        events: {
            'click #save': 'save',
            'click .select_option': 'select_option'
        },

        save: function(e) {
            e.preventDefault();
            if (this.model != undefined) {
                this.model.set('answer', this.$("input").val());
                this.model.set('answerId', null);
                this.model.save({}, {
                    success: function(model) {
                        window.forceUpdateHints();
                        model.collection.fetch({
                            reset: true
                        });
                    }
                });
            }
        },

        select_option: function(e) {
            e.preventDefault();
            if (this.model != undefined) {
                this.model.set('answerId', $(e.target).closest('li').data('id'));
                this.model.set('answer', null);
                this.model.save({}, {
                    success: function(model) {
                        window.forceUpdateHints();
                        model.collection.fetch({
                            reset: true
                        });
                    }
                });
            }
        },

        get_data: function() {
            return {
                question: this.model.toJSON()
            };
        },

    });

    return QuestionView;
});
