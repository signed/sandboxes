"use strict";
import * as d3 from 'd3';
const dataFile = require('./data.tsv');
import css from 'style-loader!css-loader!./negativebar.css';


function translate(x, y) {
    return "translate(" + x + "," + y + ")"
}

let data = d3.tsv(dataFile, type)
    .catch((error) => alert('error loading '+ dataFile + '\n' + JSON.stringify(error)));

const svgTag = {
    "group": "g",
    "rectangle": "rect"
};
const margin = {top: 20, right: 30, bottom: 40, left: 30};
const width = 960 - margin.left - margin.right;
const height = 500 - margin.top - margin.bottom;
const x = d3.scaleLinear().range([0, width]);
const y = d3.scaleBand().rangeRound([0, height]).padding(0.1);
const xAxis = d3.axisBottom(x);
const yAxis = d3.axisLeft(y).tickSize(0).tickPadding(6);
const svgWidth = width + margin.left + margin.right;
const svgHeight = height + margin.top + margin.bottom;
const svg = d3.select("body").append("svg")
    .attr("width", svgWidth)
    .attr("height", svgHeight)
    .append(svgTag.group)
    .attr("transform", translate(margin.left, margin.top));

data.then((data) => {
    x.domain(d3.extent(data, (d) => d.value))
        .nice();
    y.domain(data.map((d) => d.name));

    svg.selectAll(".bar")
        .data(data)
        .enter().append(svgTag.rectangle)
        .attr("class", (d) => "bar bar--" + (d.value < 0 ? "negative" : "positive"))
        .attr("x", (d) => x(Math.min(0, d.value)))
        .attr("y", (d) => y(d.name))
        .attr("width", (d) => Math.abs(x(d.value) - x(0)))
        .attr("height", y.bandwidth());

    svg.append(svgTag.group)
        .attr("class", "x axis")
        .attr("transform", translate(0, height))
        .call(xAxis);

    svg.append(svgTag.group)
        .attr("class", "y axis")
        .attr("transform", translate(x(0), 0))
        .call(yAxis);
});

function type(d) {
    d.value = +d.value;
    return d;
}

