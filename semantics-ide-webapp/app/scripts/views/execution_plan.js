/*global define*/

define([
    'jquery',
    'underscore',
    'backbone',
    '../../bower_components/d3/d3',
    'views/query',
    'collections/queries',
    'templates'
], function($, _, Backbone, d3, QueryView, QueryCollection, JST) {
    'use strict';


    var ExecutionView = QueryView.extend({
        template: JST['app/scripts/templates/execution_plan.ejs'],
        base_url: 'execution',

        collection_type: QueryCollection,


        render_results: function() {
            try {

                var letter = d3.scale.ordinal()
                    .range(['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']);
                var process = function(val) {
                    if (_.isArray(val)) {
                        var str = ""
                        for (var i = 0; i < val.length; i++) {
                            str += process(val[i]);
                        }
                        return str;
                    } else if (_.isString(val)) {
                        return val;
                    } else {
                        return "{$" + letter(val.ref) + "}";
                    }
                };
                var plan = JSON.parse(this.result);
                this.$(".result").html('<div class="row"><div class="col-md-6 chart"></div><div class="col-md-6 requests"></div></div>');
                var links = [];

                var width = this.$('.chart').width(),
                    height = 600;

                plan.requests = _.map(plan.requests, function(x) {
                    var num = x.id.substring(7);
                    x.num = parseInt(num.substr(0, num.length - 1));
                    if (x.level == 0) {
                        x.fixed = true;
                        x.x = width / 4;
                        x.y = 30;
                    }
                    return x;
                });
                var nodes = _.indexBy(plan.requests, function(x) {
                    return x.id
                });

                plan.requests = _.sortBy(plan.requests, function(x) {
                    return x.level;
                });
                console.log(plan.requests);
                plan.letter = letter;
                console.log(plan);
                plan.running = false;
                plan.results = {};
                this.plan = plan;
                this.updatePlan();

                _.each(plan.requests, function(request) {
                    if (request.dependencies != undefined) {
                        _.each(request.dependencies, function(dep) {
                            links.push({
                                target: request,
                                source: nodes[dep]
                            });
                        });
                    }
                });
                var self = this;

                var linkArc = function(d) {
                    var dx = d.target.x - d.source.x,
                        dy = d.target.y - d.source.y,
                        dr = Math.sqrt(dx * dx + dy * dy);
                    return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
                };

                var transform = function(d) {
                    return "translate(" + d.x + "," + d.y + ")";
                };

                var tick = function() {
                    self.path.attr("d", linkArc);
                    self.circle.attr("transform", transform);
                    self.vcircle.attr('class', function(d) {
                            if(d.running){
                                return 'running';
                            }
                            if(d.done){
                                return 'done';
                            }
                            return d.level == 0 ? 'goal' : '';
                        }).attr("transform", transform);
                    self.text.attr("transform", transform);
                };
                this.tick = tick;

                var force = d3.layout.force()
                    .nodes(d3.values(nodes))
                    .links(links)
                    .size([width / 2, height * 2])
                    .gravity(0.01)
                    .linkDistance(90)
                    .charge(-330)
                    .on("tick", tick)
                    .start();
                var svg = d3.select(".chart").append("svg")
                    .attr("width", width)
                    .attr("height", height);
                // Per-type markers, as they don't inherit styles.
                svg.append("defs").selectAll("marker")
                    .data(["dep"])
                    .enter().append("marker")
                    .attr("id", function(d) {
                        return d;
                    })
                    .attr("viewBox", "0 -5 10 10")
                    .attr("refX", 15)
                    .attr("refY", -1.5)
                    .attr("markerWidth", 6)
                    .attr("markerHeight", 6)
                    .attr("orient", "auto")
                    .append("path")
                    .attr("d", "M0,-5L10,0L0,5");

                this.path = svg.append("g").selectAll("path")
                    .data(force.links())
                    .enter().append("path")
                    .attr("class", function(d) {
                        return "link dep";
                    })
                    .attr("marker-end", function(d) {
                        return "url(#dep)";
                    });

                this.vcircle = svg.append("g").selectAll("circle")
                    .data(force.nodes())
                    .enter().append("circle")
                    .attr("r", function(d) {
                        return d.level == 0 ? 8 : 6;
                    })
                    .attr('class', function(d) {
                        return d.level == 0 ? 'goal' : '';
                    });

                this.circle = svg.append("g").selectAll(".hc")
                    .data(force.nodes())
                    .enter().append("circle").attr('class', 'hc')
                    .attr("r", 20)
                    .call(force.drag)
                    .on('mouseover', function(d) {
                        console.log(d);
                        self.$('.request').addClass('inactive').removeClass('active');
                        self.$('.request_' + d.num).removeClass('inactive').addClass('active');
                    }).on('mouseout', function(d) {
                        console.log(d);
                        self.$('.request').removeClass('inactive').removeClass('active');
                    });
                this.text = svg.append("g").selectAll("text")
                    .data(force.nodes())
                    .enter().append("text")
                    .attr("x", 8)
                    .attr("y", ".31em")
                    .text(function(d) {
                        return d.method + " " + process(d.uri);
                    });
                // Use elliptical arc path segments to doubly-encode directionality.
            } catch (err) {
                if (this.result == "") {
                    this.$('.result').html($('<div />').addClass('alert alert-warning').text('No execution plan found.'))
                } else {
                    this.$('.result').text(this.result);
                }
            }
        },

        execute_query: function() {
            var self = this;
            var button = this.$('#executeQuery');
            button.button('loading');
            $.ajax({
                type: "POST",
                url: window.workspace_url() + '/plan',
                data: JSON.stringify({
                    input: this.input.getValue(),
                    query: this.query.getValue()
                }),
                contentType: "application/json; charset=utf-8"
            }).done(function(data) {
                self.result = data.result;
                self.input_errors = data.errinput;
                self.query_errors = data.errquery;
                self.query.setValue(self.query.getValue());
                self.input.setValue(self.input.getValue());
                self.render_results();
            }).complete(function() {
                button.button('reset');
            });
        },

        updatePlan: function(){
            this.$(".requests").html(JST['app/scripts/templates/requests.ejs'](this.plan));
            if(this.tick){
                this.tick();
            }
        },

        run: function(e){
            e.preventDefault();
            if(!this.plan.running){
                this.plan.running = true;
                console.log(this.plan);
                this.runStep();
            }
            
        },

        process: function(data){
            if(_.isObject(data)&&data.ref!=undefined){
                return this.plan.results[data.ref];
            }else if(_.isArray(data)){
                return _.map(data,this.process, this).join("");
            }
            return data;
        },

        buildRequest: function(request){
            return {'method':request.method,'uri':this.process(request.uri),'reqBody':this.process(request.reqBody)};
        },

        runStep: function(){

            for (var i = this.plan.requests.length - 1; i >= 0; i--) {
                var request = this.plan.requests[i];
                if(!request.running&&request.done!=true){
                    request.running = true;
                    console.log(request);
                    var self = this; 
                    $.ajax({
                        type: "POST",
                        url: window.base_url() + '/coap',
                        data: JSON.stringify(this.buildRequest(request)),
                        contentType: "application/json; charset=utf-8"
                    }).done(function(data) {
                        request.done = true;
                        request.failed = !data.success;
                        request.running = false;
                        request.payload = data.payload;
                        request.status = data.status;
                        if(_.isObject(request.resp)&&request.resp.ref!=undefined){
                            self.plan.results[request.resp.ref] = data.payload
                        }
                        if(data.success){
                            self.runStep();
                        }else{
                            self.updatePlan();
                        }
                    });
                    break;
                }
            }
            this.updatePlan();
        }


        
    });

    return ExecutionView;
});
