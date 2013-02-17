/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*global window,jQuery*/
(function (window, $, undefined) {

    var SingleValueView,TableValueView,HtmlView,ChartView;

    HtmlView = function() {
    };

    HtmlView.prototype.transformModel = function(model,item){
        if(!model || !model.current || !model.current.data){
            return {table:[]};
        }
        return model.current.data;
    };

    ChartView = function() {
    };

    ChartView.prototype.transformModel = function(model,item) {

        model.current.data.data.sort(function(a,b){
            return a.value > b.value;
        });

        return model.current.data;
    };

    ChartView.prototype.onRender = function(html,model) {
        $elem = $(html);
        var canvasWidth = $elem.width(), //width
        canvasHeight = $elem.height(), //height
        outerRadius = ($elem.height() * 0.7 ) / 2, //radius
        color = d3.scale.category20(); //builtin range of colors

        var vis = d3.select($elem.find('.chart')[0])
        .append("svg:svg")//create the SVG element inside the <body>
        .data([model.data])//associate our data with the document
        .attr("width", canvasWidth)//set the width of the canvas
        .attr("height", canvasHeight)//set the height of the canvas
        .append("svg:g")//make a group to hold our pie chart
        .attr("transform", "translate(" + 1.5 * outerRadius + "," + 1.5 * outerRadius + ")"); // relocate center of pie to 'outerRadius,outerRadius'

        // This will create <path> elements for us using arc data...
        var arc = d3.svg.arc()
        .innerRadius(outerRadius * 0.6)
        .outerRadius(outerRadius);

        var pie = d3.layout.pie()//this will create arc data for us given a list of values
        .value(function (d) {
            return d.value;
        })// Binding each value to the pie
//        .sort(null);

        // Select all <g> elements with class slice (there aren't any yet)
        var arcs = vis.selectAll("g.slice")
            // Associate the generated pie data (an array of arcs, each having startAngle,
            // endAngle and value properties)
        .data(pie)
            // This will create <g> elements for every "extra" data element that should be associated
            // with a selection. The result is creating a <g> for every object in the data array
        .enter()
            // Create a group to hold each slice (we will have a <path> and a <text>
            // element associated with each slice)
        .append("svg:g")
        .attr("class", "slice");    //allow us to style things in the slices (like text)

        arcs.append("svg:path")
            //set the color for each slice to be chosen from the color function defined above
        .attr("fill", function (d, i) {
            return color(i);
        })
            //this creates the actual SVG path using the associated data (pie) with the arc drawing function
        .attr("d", arc);

        var legendSpacer = 150;
        var legendTextSize = 40;
        var legend = vis.append("svg")
        .attr("class", "legend")
        .attr("width", canvasWidth - outerRadius - legendSpacer)
        .attr("height", canvasHeight)
        .attr("x", outerRadius + legendSpacer)
        .attr("y",-outerRadius)
        .selectAll("g")
        .data(color.domain().slice().reverse())
        .enter().append("g")
        .attr("transform", function(d, i) { return "translate(0," + i * (legendTextSize + 15) + ")"; });

        legend.append("rect")
        .attr("width", legendTextSize + 2)
        .attr("height", legendTextSize + 2)
        .style("fill", color);

        legend.append("text")
        .attr("x", legendTextSize + 10)
        .attr("y", 0)
        .attr("dy", legendTextSize + "px")
        .style("font","normal " + legendTextSize + "px 'Droid Serif'")
        .text(function(d) {
            var val = model.data[d];
            return val.label + " " + val.value+ "%";
        });
    };

	window.infowall = {};
    window.infowall.SingleValueView = SingleValueView;
    window.infowall.TableValueView = TableValueView;
    window.infowall.HtmlView = HtmlView;
    window.infowall.ChartView = ChartView;

}(window, jQuery));
