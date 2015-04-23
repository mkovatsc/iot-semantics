/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'views/menu'
], function($, _, Backbone, BaseView, JST, MenuView) {
    'use strict';

    var LayoutView = BaseView.extend({
        template: JST['app/scripts/templates/layout.ejs'],

        initialize: function() {
            this.subviews = {
                '[role=navigation]': new MenuView()
            }
        }
    });

    return LayoutView;
});
