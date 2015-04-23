/*global define*/

define([
    'underscore',
    'backbone'
], function (_, Backbone) {
    'use strict';

    var DeviceModel = Backbone.Model.extend({
        defaults:{name:"",semantics:"", disabled:false, remote:false},
        url: function () { 
            if(this.id != undefined){
            	return window.workspace_url() + '/devices/' + this.id;
            }else{
                return window.workspace_url() + '/devices';
            }
        } 
    });

    return DeviceModel;
});
