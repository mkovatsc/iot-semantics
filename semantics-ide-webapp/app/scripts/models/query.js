/*global define*/

define([
    'underscore',
    'backbone'
], function (_, Backbone) {
    'use strict';

    var QuestionModel = Backbone.Model.extend({
        url: function () { 
            if(this.id != undefined){
            	return window.workspace_url() + '/queries/' + this.id;
            }else{
                return window.workspace_url() + '/queries';
            }
        } 
    });

    return QuestionModel;
});
