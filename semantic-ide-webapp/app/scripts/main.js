/*global require*/
'use strict';

require.config({
    shim: {
        bootstrap: {
            deps: ['jquery'],
            exports: 'jquery'
        }
    },
    paths: {
        jquery: '../bower_components/jquery/dist/jquery',
        backbone: '../bower_components/backbone/backbone',
        underscore: '../bower_components/lodash/dist/lodash',
        bootstrap: '../bower_components/bootstrap/dist/js/bootstrap'
    }
});
if (typeof String.prototype.startsWith != 'function') {
    // see below for better implementation!
    String.prototype.startsWith = function(str) {
        return this.indexOf(str) == 0;
    };
}

var url = location.protocol + '//' + location.host;
window.workspace = 0
window.workspace_url = function(){
    return url+"/"+window.workspace;
};

window.base_url = function(){
    return url;
};

// window.workspace_url() = "http://127.0.0.1:5000";
require([
    'jquery', 'backbone', 'bootstrap', 'routers/app', 'collections/workspaces'
], function($, Backbone, bootstrap, App, WorkspaceCollection) {

 
    var onLoading = function () {
        if ($("#progress").length === 0) {
            $("body").append($("<div><dt/><dd/></div>").attr("id", "progress"));
            $("#progress").width((50 + Math.random() * 30) + "%");
        }
    };
    var onComplete = function () {
        $("#progress").width("101%").delay(200).fadeOut(400, function() {
            $(this).remove();
        });
    };

    var pendingAjaxCounter = 0;
    var backboneSync = Backbone.sync;
    Backbone.sync = function (method, model, options) {
        model.once('request', function (model, xhr, options) {
            xhr.always(function () {
                pendingAjaxCounter -= 1;
                if (pendingAjaxCounter === 0) {
                    onComplete();
                }
            });
        });
        pendingAjaxCounter += 1;
        if (pendingAjaxCounter === 1) {
            onLoading();
        }
        return backboneSync.apply(this, arguments);
    };
    window.workspaces = new WorkspaceCollection(); 
    window.workspaces.fetch({
        reset: true,
        success:function(){
            window.Router = new App();
            Backbone.history.start();
        }
    });
    
});
