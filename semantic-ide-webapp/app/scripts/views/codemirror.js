/*global define*/

define([
    'jquery',
    'underscore',
    '../../bower_components/codemirror/addon/comment/comment',
    '../../bower_components/codemirror/addon/edit/closebrackets',
    '../../bower_components/codemirror/addon/hint/show-hint',
    '../../bower_components/codemirror/addon/lint/lint',
    '../../bower_components/codemirror/mode/turtle/turtle',
    '../../bower_components/codemirror/lib/codemirror'
], function($, _, comment, closebrackets, hint, lint, turtle, codemirror) {
    'use strict';

    codemirror.registerHelper("lint", "turtle", function(text, x, cm) {
        var found = [];
        if (cm.options.errors != undefined) {
            var errors = _.result(cm.options, 'errors');
            if (errors != undefined) {
                for (var i = errors.length - 1; i >= 0; i--) {
                    var error = errors[i];
                    found.push({
                        from: codemirror.Pos(error.line - 1, error.charPositionInLine),
                        to: codemirror.Pos(error.line, 0),
                        message: error.msg
                    });

                }
            }
        }
        return found;
    });

    function match(string, word) {
        var len = string.length;
        var sub = word.substr(0, len);
        return string.toUpperCase() === sub.toUpperCase();
    }

    function addMatches(result, search, wordlist, formatter) {
        for (var word in wordlist) {
            if (!wordlist.hasOwnProperty(word)) continue;
            if (Array.isArray(wordlist)) {
                word = wordlist[word];
            }
            if (match(search, word)) {
                result.push(formatter(word));
            }
        }
    }
    window.hints = [];
    window.forceUpdateHints = function() {
        if(isNaN(window.workspace)){
            return;
        }
        $.ajax({
            type: "GET",
            url: window.workspace_url() + '/hints',
            contentType: "application/json; charset=utf-8"
        }).done(function(data) {
            window.hints = data;
        });
    };
    window.updateHints = _.throttle(window.forceUpdateHints, 30000);
    codemirror.registerHelper("hint", "turtle", function(editor, options) {
        window.updateHints();
        var cur = editor.getCursor();
        var result = [];
        var token = editor.getTokenAt(cur),
            start, end, search;
        if (token.string.match(/^[:.`\w@]\w*$/)) {
            search = token.string;
            start = token.start;
            end = token.end;
            while (start > 0) {
                var lastoken = editor.getTokenAt(codemirror.Pos(cur.line, start));
                if (lastoken.type != null) {
                    console.log(lastoken);
                    start = lastoken.start;
                    search = lastoken.string + search;
                } else {
                    break;
                }
            }
            console.log(search);
        } else {
            start = end = cur.ch;
            search = "";
        }
        addMatches(result, search, window.hints, function(w) {
            return w;
        });
        return {
            list: result.sort(),
            from: codemirror.Pos(cur.line, start),
            to: codemirror.Pos(cur.line, end)
        };
    });


    return codemirror;
});
