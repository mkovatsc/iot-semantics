/*global define*/

define([
    'underscore',
    'backbone',
    'models/query'
], function (_, Backbone, QueryModel) {
    'use strict';

    var QueriesCollection = Backbone.Collection.extend({
        model: QueryModel,

        url: function (argument) {
            return window.workspace_url()+'/queries';
        }
        
    });

    return QueriesCollection;
});
