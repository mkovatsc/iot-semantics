/*global define*/

define([
    'underscore',
    'backbone',
    'models/workspace'
], function (_, Backbone, WorkspaceModel) {
    'use strict';

    var WorkspaceCollection = Backbone.Collection.extend({
        model: WorkspaceModel,

        url: function () {
            return window.base_url() + '/workspaces';
        }

    });

    return WorkspaceCollection;
});
