/*global define*/

define([
    'underscore',
    'backbone',
    'models/question'
], function (_, Backbone, QuestionModel) {
    'use strict';

    var QuestionCollection = Backbone.Collection.extend({
        model: QuestionModel,

        url: function () {
            return window.workspace_url()+'/questions';
        }

    });

    return QuestionCollection;
});
