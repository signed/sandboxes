const margin = {top: 10, right: 30, bottom: 30, left: 40};
const width = 800 - margin.left - margin.right;
const height = 960 - margin.top - margin.bottom;
const svg = d3.select('body')
    .append('svg')
    .attr('width', width + margin.left + margin.right)
    .attr('height', height + margin.top + margin.bottom)
    .append('g')
    .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

// #1: tideline's current implementation
const tidelineScale = d3.scaleUtc()
    .domain([new Date('2017-02-16T00:00:00.000Z'), new Date('2017-02-17T00:00:00.000Z')])
    .range([0, width]);

const axis = d3.axisLeft(tidelineScale).ticks(d3.utcMinute.every(30));
svg.append('g')
    .attr('class', 'x axis')
    .attr('transform', 'translate(0,' + 0 + ')')
    .call(axis);
