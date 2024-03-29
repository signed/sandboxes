'use strict';

import * as d3 from 'd3';
import moment from 'moment';
import css from 'style-loader!css-loader!./timescales.css';

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

function generateDataFor(start) {
    const end = start.clone().add(1, 'day');
    const data = [];
    let cu = start.clone();
    const delta = 5;
    while (cu.isBefore(end)) {
        const value = getRandomArbitrary(-delta, delta);
        data.push({'date': cu.toDate(), 'deviation': value});
        cu.add(5, 'minutes');
    }
    return data;
}

function timeScaleFor(day, height) {
    return d3.scaleUtc()
        .domain([day.toDate(), day.clone().add(1, 'day').toDate()])
        .range([0, height]);
}

function printDataForDate(parent, offset, height, data, yScale, day) {
    const xScale = d3.scaleLinear()
        .domain([-10, 10])
        .range([0, 100]);
    const centerOfTheDayGraph = (xScale(0) + offset);

    parent.append('text')
        .text(day.format('dddd'))
        .style("text-anchor", "middle")
        .attr('transform', 'translate(' + centerOfTheDayGraph + ', ' + -25 + ')')

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
        .attr('transform', 'translate(' + centerOfTheDayGraph + ',' + 0 + ')')
        .call(yAxisCenter);

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

const margin = {top: 50, right: 30, bottom: 30, left: 40};
const width = 1200 - margin.left - margin.right;
const height = 800 - margin.top - margin.bottom;
const svg = d3.select('body')
    .append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', `translate(${margin.left},${margin.top})`);

const startDay = moment.utc('2017-02-20', 'YYYY-MM-DD');
const yScaleForHoursOfTheDay = timeScaleFor(startDay, height);

const yAxis = d3.axisLeft(yScaleForHoursOfTheDay).ticks(d3.utcMinute.every(30));

svg.append('g')
    .attr('class', 'x axis')
    .attr('transform', 'translate(0,' + 0 + ')')
    .call(yAxis);

const dayGraphOffsets = [0, 1, 2, 3, 4, 5, 6];

dayGraphOffsets.map((it) => {
    return {
        offset: (it * 120) + 20,
        day: startDay.clone().add(it, 'day')
    }
}).forEach((it) => {
    const data = generateDataFor(it.day);
    const yScale = timeScaleFor(it.day, height);
    printDataForDate(svg, it.offset, height, data, yScale, it.day)
});
