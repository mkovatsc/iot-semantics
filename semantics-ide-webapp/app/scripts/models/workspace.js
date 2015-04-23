/*global define*/

define([
    'underscore',
    'backbone'
], function (_, Backbone) {
    'use strict';

    var WorkspaceModel = Backbone.Model.extend({
        defaults:{name:"",id:null},
        url: function () { 
            if(this.id != undefined){
            	return window.base_url() + '/workspaces/' + this.id;
            }else{
                return window.base_url() + '/workspaces';
            }
        } 
    });

    return WorkspaceModel;
});
