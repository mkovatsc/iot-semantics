/*global define*/

define([
    'underscore',
    'backbone',
    'models/device'
], function (_, Backbone, DeviceModel) {
    'use strict';

    var DeviceCollection = Backbone.Collection.extend({
        model: DeviceModel,

        url: function () {
            return window.workspace_url()+'/devices';
        }

    });

    return DeviceCollection;
});
