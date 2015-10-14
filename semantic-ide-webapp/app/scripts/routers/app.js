/*global define*/

define([
    'jquery',
    'backbone',
    'views/layout',
    'views/fullscreen',
    'views/workspace',
    'views/devices',
    'views/questions',
    'views/query',
    'views/execution_plan',
], function($, Backbone, LayoutView, FullscreenView, WorkspaceView, DevicesView, QuestionsView, QueryView, ExecutionView) {
    'use strict';
    var AppRouter = Backbone.Router.extend({
        routes: {
            '': 'workspace',
            ':workspace': 'devices',
            ':workspace/device/:id': 'devices',
            ':workspace/device/:id(/:view)': 'devices',
            ':workspace/questions': 'questions',
            ':workspace/query': 'query',
            ':workspace/query/:id': 'query',
            ':workspace/execution': 'execution',
            ':workspace/execution/:id': 'execution',
        },

        views: {
            'WorkspaceView': WorkspaceView,
            'DevicesView': DevicesView,
            'QuestionsView': QuestionsView,
            'QueryView': QueryView,
            'ExecutionView': ExecutionView
        },

        current_layout: null,

        last_hash: null,

        modal: null,

        current_view: null,

        layouts: {
            'main': function() {
                return new LayoutView();
            },
            'fullscreen': function() {
                return new FullscreenView();
            }
        },

        workspace: function(argument) {
            this.loadView('fullscreen', 'WorkspaceView', arguments);
        },

        devices: function(argument) {
            this.loadView('main', 'DevicesView', arguments);
        },

        questions: function(argument) {
            this.loadView('main', 'QuestionsView', arguments);
        },

        query: function(argument) {
            this.loadView('main', 'QueryView', arguments);
        },

        execution: function(argument) {
            this.loadView('main', 'ExecutionView', arguments);
        },

        loadView: function(layout, view, attr) {
            var workspace = parseInt(attr[0]);
            if(workspace!=window.workspace){
                window.workspace = workspace;
                if(!isNaN(workspace)){
                    window.forceUpdateHints();
                    window.questions.fetch({
                        reset: true
                    });
                }
            }
            attr = _.toArray(attr).slice(1);
            if (this.modal !== null) {
                this.modal.$el.modal('hide');
                this.modal.dispose();
                this.modal = null;
            }
            if (this.last_hash == window.location.hash) {
                return;
            }
            if(this.view && this.view.has_unsaved && this.view.has_unsaved() && !confirm("Do you want to discard all unsaved changes?")){
                window.location.hash = this.last_hash;
                return;
            }
            this.last_hash = window.location.hash;
            if (this.current_view == view) {
                this.view.set_attr(attr);
                return;
            }
            this.view && this.view.dispose();
            if (this.current_layout !== layout) {
                this.current_layout = layout;
                this.layout && this.layout.dispose();
                this.layout = this.layouts[layout]();
                $("#app").html(this.layout.$el);
                this.layout.render();
            }
            this.view = new this.views[view]();
            this.view.set_attr(attr);
            this.current_view = view;
            this.layout.$el.find("#content").html(this.view.$el);
            this.view.render();
        }

    });

    return AppRouter;
});
