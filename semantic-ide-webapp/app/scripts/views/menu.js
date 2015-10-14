/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    'views/base',
    'templates',
    'models/workspace'
], function($, _, Backbone, BaseView, JST, Workspace) {
    'use strict';

    var MenuView = BaseView.extend({
        template: JST['app/scripts/templates/menu.ejs'],

        className: 'container-fluid',

        events: {
            'change .fileUpload input': 'upload',
            'click #load': 'load',
            'click #copy': 'copy'
        },

        initialize: function() {
            this.listenTo(window.workspaces, 'sync change add remove', this.render);
            this.listenTo(window.questions, 'sync change', this.render);
            Router.on('route', this.update_active, this);
        },

        load: function(e) {
            e.preventDefault();
            var self = this;
            this.$("#load").button("loading");
            $.ajax({
                type: "GET",
                url: window.base_url() + '/usecases',
                dataType: "json",
                success: function(data) {
                    var modal_template = JST['app/scripts/templates/load.ejs'];
                    var modal = $(modal_template({
                        usecases: data
                    }));
                    $('body').append(modal);
                    self.$("#load").button("reset");
                    modal.modal();
                    modal.on('click', 'button', function(e) {
                        $(e.target).button("loading");
                        $.ajax({
                            type: "GET",
                            url: window.workspace_url() + '/load/' + $(e.target).data('file'),
                            dataType: "json",
                            success: function() {
                                location.reload();
                            },
                            failure: function(errMsg) {
                                alert(errMsg);
                            }
                        });
                    });
                },
                failure: function(errMsg) {
                    alert(errMsg);
                }
            });
            $('.modal').remove();
        },

        copy: function(e) {
            e.preventDefault();
            var name = prompt("New workspace name");
            if (name && name.length > 0) {
                var self = this;
                this.$("#copy").button("loading");
                $.ajax({
                    type: "POST",
                    url: window.workspace_url() + '/copy',
                    contentType : 'application/json',
                    dataType: "json",
                    data: JSON.stringify({'name':name}),
                    success: function(data) {
                        window.workspaces.add(new Workspace(data));
                        window.location.hash = "#"+data.id;
                        window.location.reload();
                    },
                    failure: function(errMsg) {
                        alert(errMsg);
                    }
                });
            }
        },

        after_render: function() {
            this.update_active();
            this.$el.find('.nav a').on('click', function() {
                if ($('.navbar-toggle').css('display') != 'none') {
                    $(".navbar-toggle").trigger("click");
                }
            });
            this.$el.find('[data-toggle="tooltip"]').tooltip()
        },

        update_active: function(argument) {
            var item = null;
            var matchlength = -1;
            var active = window.location.hash + "";
            if (active === "") {
                active = "#";
            }
            var menu_items = this.$el.find('li');
            menu_items.each(function() {
                var url = $(this).find('a').data('match');
                if (url == undefined) {
                    url = $(this).find('a').attr('href');
                }
                if (active.startsWith(url) && url.length > matchlength) {
                    item = $(this);
                    matchlength = url.length;
                }
            });
            item && menu_items.removeClass('active');
            item && item.addClass('active');
        },

        dispose: function() {
            Router.off(null, null, this);
            BaseView.prototype.dispose.call(this);
        },


        get_data: function() {
            var current_workspace = window.workspaces.get(window.workspace);
            return {
                'count': window.questions.length,
                'workspace': current_workspace!=null?current_workspace.get('name'):''
            }
        },

        upload: function(evt) {
            var files = this.$(".fileUpload input")[0].files;
            if (!files.length) {
                alert('Please select a file!');
                return;
            }

            var file = files[0];
            var start = 0;
            var stop = file.size - 1;

            var reader = new FileReader();
            var self = this;
            // If we use onloadend, we need to check the readyState.
            reader.onloadend = function(evt) {
                if (evt.target.readyState == FileReader.DONE) { // DONE == 2
                    $.ajax({
                        type: "POST",
                        url: window.workspace_url() + '/all',
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: evt.target.result,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        success: function(data) {
                            location.reload();
                        },
                        failure: function(errMsg) {
                            alert(errMsg);
                        }
                    });
                }
            };

            var blob = file.slice(start, stop + 1);
            reader.readAsBinaryString(blob);
        }

    });

    return MenuView;
});
