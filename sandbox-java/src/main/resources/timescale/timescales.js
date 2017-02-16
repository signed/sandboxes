(function() {
    var drawScale = function(scale, dst) {
        var axis = d3.axisTop(scale);
        var scaleGroup = svg.append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(0,' + (eachScale * i) + ')')
            .call(axis);
        scaleGroup.append('rect')
            .attrs({
                'x': scale(dst[0]),
                'width': scale(dst[1]) - scale(dst[0]),
                'y': -5,
                'height': 10,
                'fill': '#0A81CB',
                'opacity': 0.75
            });
        scaleGroup.append('circle')
            .attrs({
                'cx': scale(dst[2]),
                'cy': 0,
                'r': 8,
                'fill': '#CB1414',
                'opacity': 0.5
            })
            .append('title')
            .text(dst[2].toISOString().slice(11,16) + ' UTC');
        scaleGroup.append('circle')
            .attrs({
                'cx': scale(dst[3]),
                'cy': -2,
                'r': 2,
                'fill': '#FFF'
            })
            .append('title')
            .text(dst[3].toISOString().slice(11,16) + ' UTC');
        scaleGroup.append('circle')
            .attrs({
                'cx': scale(dst[4]),
                'cy': 2,
                'r': 2,
                'fill': '#08324C'
            })
            .append('title')
            .text(dst[4].toISOString().slice(11,16) + ' UTC');
        return scaleGroup;
    };
    var margin = {top: 100, right: 30, bottom: 30, left: 30};
    var width = 800 - margin.left - margin.right;
    var height = 960 - margin.top - margin.bottom;
    var eachScale = 180;
    var i = 0;
    var svg = d3.select('body')
        .append('svg')
        .attrs({
            'width': width + margin.left + margin.right,
            'height': height + margin.top + margin.bottom
        })
        .append('g')
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // #1: tideline's current implementation
    var tidelineScale = d3.scaleUtc()
        .domain([new Date('2014-03-08T12:00:00.000Z'), new Date('2014-03-10T00:00:00.000Z')])
        .range([0, width]);

    drawScale(tidelineScale,
        [ new Date('2014-03-09T00:00:00.000Z'), new Date('2014-03-10T00:00:00.000Z'),
            new Date('2014-03-09T03:00:00.000Z'), new Date('2014-03-09T02:30:00.000Z'),
            new Date('2014-03-09T01:30:00.000Z')
        ]);

}());