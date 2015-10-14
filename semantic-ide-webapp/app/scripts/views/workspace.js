/*global define*/

define([
    'jquery',
    'underscore',
    'views/codemirror',
    'backbone',
    'views/base',
    'templates',
    'collections/workspaces',
    'models/workspace'
], function($, _, codemirror, Backbone, BaseView, JST, WorkspaceCollection, WorkspaceModel) {
    'use strict';

    var EditorView = BaseView.extend({
        template: JST['app/scripts/templates/workspace.ejs'],
        text: '',

        events:{
            'click #workspace_create': 'workspace_create',
            'click #workspace_remote_create': 'workspace_remote_create',
            'click .delete': 'workspace_delete',
            'click .copy': 'copy',
            'click .rename': 'rename'
        },

        initialize: function(options) {
            this.collection = window.workspaces;
            this.listenTo(this.collection, 'sync change remove add', this.render);
            BaseView.prototype.initialize.call(this);
        },

        workspace_create: function(e){
            e.preventDefault();
            $(e.target).button('loading');
            var model = new WorkspaceModel();
            model.set('name', this.$el.find('#workspace_name').val());
            model.save({},{
                success: function(){
                    window.workspaces.add(model);
                    window.location.hash = "#"+model.get('id');
                },
                error: function(){
                    $(e.target).button('reset');
                }
            })
        },

        workspace_remote_create: function(e){
            e.preventDefault();
            $(e.target).button('loading');
            var model = new WorkspaceModel();
            model.set('name', this.$el.find('#workspace_remote_name').val());
            model.set('url', this.$el.find('#workspace_remote_url').val());
            model.set('type', 'remote');
            var self = this;
            model.save({},{
                success: function(){
                    window.workspaces.add(model);
                    window.location.hash = "#"+model.get('id');
                },
                error: function(){
                    $(e.target).button('reset');
                    self.$el.find('#workspace_remote_url').closest('.form-group').addClass('has-error has-feedback');
                }
            })
        },


        copy: function(e) {
            e.preventDefault();
            var name = prompt("New workspace name");
            if (name && name.length > 0) {
                var self = this;
                var id = $(e.target).closest('li.list-group-item').data('id');
                $.ajax({
                    type: "POST",
                    url: window.base_url() + '/' + id + '/copy',
                    contentType : 'application/json',
                    dataType: "json",
                    data: JSON.stringify({'name':name}),
                    success: function(data) {
                        window.workspaces.add(new WorkspaceModel(data));
                        window.location.hash = "#"+data.id;
                        window.location.reload();
                    },
                    failure: function(errMsg) {
                        alert(errMsg);
                    }
                });
            }
        },

        rename: function(e) {
            e.preventDefault();
            var name = prompt("New workspace name");
            if (name && name.length > 0) {
                var self = this;
                var id = $(e.target).closest('li.list-group-item').data('id');
                var workspace = window.workspaces.get(id);
                workspace.set('name', name);
                workspace.save();
            }
        },

        workspace_delete: function(e){
            e.preventDefault();
            var id = $(e.target).closest('li.list-group-item').data('id');
            if(confirm("Do you want to delete this workspace?")){
                window.workspaces.get(id).destroy();
            }
            return; 
        },
 

        get_data: function() {
            return {
                workspaces: this.collection.toJSON()
            };
        },
    });

    return EditorView;
});
