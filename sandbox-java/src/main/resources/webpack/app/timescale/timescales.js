'use strict';
import * as d3 from 'd3';
import css from 'style-loader!css-loader!./timescales.css';

function addMinutes(date, minutes) {
    return new Date(date.getTime() + minutes * 60000);
}

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

function printDataForDate(parent, offset) {

    const xScale = d3.scaleLinear()
        .domain([-10, 10])
        .range([0, 100]);

    const xAxisTop = d3.axisTop(xScale).ticks(4);
    parent.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(' + offset + ', ' + 0 + ')')
        .call(xAxisTop);

    const xAxisBottom = d3.axisBottom(xScale).ticks(4);
    parent.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(' + offset + ', ' + height + ')')
        .call(xAxisBottom);

    const yAxisCenter = d3.axisLeft(yScale).ticks(0);
    parent.append('g')
        .attr('class', 'y axis')
        .attr('transform', 'translate(' + (xScale(0) + offset) + ',' + 0 + ')')
        .call(yAxisCenter);

    const data = [];
    let cu = startOfDay;
    while (cu < startOfNextDay) {
        const value = getRandomArbitrary(-10, 10);
        data.push({'date': cu, 'deviation': value});
        cu = addMinutes(cu, 5);
    }

    const bandwidth = yScale(data[1].date) - yScale(data[0].date);

    const cssClasses = ".bar .id-" + offset;
    parent.selectAll(cssClasses)
        .data(data)
        .enter().append('rect')
        .attr("class", (d) => cssClasses + " bar--" + (d.deviation < 0 ? "negative" : "positive"))
        .attr("x", (d) => offset + xScale(Math.min(0, d.deviation)))
        .attr("y", (d) => yScale(d.date))
        .attr("width", (d) => Math.abs(xScale(d.deviation) - xScale(0)))
        .attr("height", bandwidth);
}

const margin = {top: 25, right: 30, bottom: 30, left: 40};
const width = 1200 - margin.left - margin.right;
const height = 800 - margin.top - margin.bottom;
const svg = d3.select('body')
    .append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', `translate(${margin.left},${margin.top})`);

const startOfDay = new Date('2017-02-16T00:00:00.000Z');
const startOfNextDay = new Date('2017-02-17T00:00:00.000Z');
const yScale = d3.scaleUtc()
    .domain([startOfDay, startOfNextDay])
    .range([0, height]);

const yAxis = d3.axisLeft(yScale).ticks(d3.utcMinute.every(30));

svg.append('g')
    .attr('class', 'x axis')
    .attr('transform', 'translate(0,' + 0 + ')')
    .call(yAxis);

const dayGraphOffsets = [0, 1, 2, 3, 4, 5, 6];

dayGraphOffsets.map((it) => it * 120).map((it) => 20 + it).forEach((it) => {
    printDataForDate(svg, it, it)
});