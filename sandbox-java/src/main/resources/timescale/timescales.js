(function() {
    var margin = {top: 10, right: 30, bottom: 30, left: 40};
    var width = 800 - margin.left - margin.right;
    var height = 960 - margin.top - margin.bottom;
    var svg = d3.select('body')
        .append('svg')
        .attrs({
            'width': width + margin.left + margin.right,
            'height': height + margin.top + margin.bottom
        })
        .append('g')
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // #1: tideline's current implementation
    const tidelineScale = d3.scaleUtc()
        .domain([new Date('2014-03-08T12:00:00.000Z'), new Date('2014-03-10T00:00:00.000Z')])
        .range([0, width]);

    var axis = d3.axisLeft(tidelineScale);
    svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(0,' + 0 + ')')
        .call(axis);

}());