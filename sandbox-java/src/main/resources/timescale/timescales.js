(function() {
    var explainer = function(scaleGroup, title, code, dateAssumptions, dateProduction, alert) {
        var titleNum = '<tspan>' + '#' + (i + 1) + '</tspan> ';
        var divisor = 5;
        if (alert) {
            divisor = 6;
            scaleGroup.append('text')
                .attrs({
                    'x': 60,
                    'y': (eachScale * 5/divisor),
                    'class': 'alert-text'
                })
                .text(alert);
        }
        scaleGroup.append('text')
            .attrs({
                'x': 0,
                'y': eachScale/divisor,
                'class': 'title-text'
            })
            .html(titleNum + title);
        scaleGroup.append('text')
            .attrs({
                'x': 30,
                'y': (eachScale * 2/divisor),
                'class': 'code-text'
            })
            .text(code);
        scaleGroup.append('text')
            .attrs({
                'x': 30,
                'y': (eachScale * 3/divisor),
                'class': 'date-text'
            })
            .text(dateAssumptions);
        scaleGroup.append('text')
            .attrs({
                'x': 30,
                'y': (eachScale * 4/divisor),
                'class': 'date-text'
            })
            .text(dateProduction);
    };
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
    var thisScale;
    var svg = d3.select('body')
        .append('svg')
        .attrs({
            'width': width + margin.left + margin.right,
            'height': height + margin.top + margin.bottom
        })
        .append('g')
        .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // legend
    svg.append('rect')
        .attrs({
            'x': 0,
            'y': -70,
            'width': width,
            'height': 50,
            'fill': '#CCC'
        });

    svg.append('circle')
        .attrs({
            'cx': width/4,
            'cy': -45,
            'r': 8,
            'fill': '#CB1414',
            'opacity': 0.5
        });
    svg.append('circle')
        .attrs({
            'cx': width/2,
            'cy': -45,
            'r': 2,
            'fill': '#FFF'
        });
    svg.append('circle')
        .attrs({
            'cx': width * 3/4,
            'cy': -45,
            'r': 2,
            'fill': '#08324C'
        });

    svg.append('text')
        .attrs({
            'x': 60,
            'y': -45,
            'class': 'legend'
        })
        .text('Legend: ');

    svg.append('text')
        .attrs({
            'x': width/4 + 10,
            'y': -45,
            'class': 'legend-label'
        })
        .text('Mar 9, 2014, 3am UTC');
    svg.append('text')
        .attrs({
            'x': width/2 + 5,
            'y': -45,
            'class': 'legend-label'
        })
        .text('Mar 9, 2014, 2:30am UTC');
    svg.append('text')
        .attrs({
            'x': width * (3/4) + 5,
            'y': -45,
            'class': 'legend-label'
        })
        .text('Mar 9, 2014, 1:30am UTC');

    // #1: tideline's current implementation
    var tidelineScale = d3.scaleUtc()
        .domain([new Date('2014-03-08T12:00:00.000Z'), new Date('2014-03-10T00:00:00.000Z')])
        .range([0, width]);
    explainer(drawScale(tidelineScale,
        [ new Date('2014-03-09T00:00:00.000Z'), new Date('2014-03-10T00:00:00.000Z'),
            new Date('2014-03-09T03:00:00.000Z'), new Date('2014-03-09T02:30:00.000Z'),
            new Date('2014-03-09T01:30:00.000Z')
        ]), 'Current Tideline Implementation',
        'd3.time.scale.utc()',
        'assumes application of Watson',
        'uses JavaScript Date constructor');
    i += 1;

    // #2: without UTC
    var noUTCScale = d3.scaleTime()
        .domain([new Date('2014-03-08T12:00:00'), new Date('2014-03-10T00:00:00')])
        .range([0, width]);
    explainer(drawScale(noUTCScale,
        [ new Date('2014-03-09T00:00:00'), new Date('2014-03-10T00:00:00'),
            new Date('2014-03-09T03:00:00'), new Date('2014-03-09T02:30:00'),
            new Date('2014-03-09T01:30:00')
        ]),  'Without UTC behavior varies across browsers, sometimes coerced to browser local time',
        'd3.time.scale()',
        'no application of Watson',
        'uses JavaScript Date constructor');
    i += 1;

    // #3: D3's suggested solution for the JavaScript Date constructor problem
    var timeFormat = d3.time.format('%Y-%m-%dT%H:%M:%S');
    var d3ParseScale = d3.time.scale()
        .domain([timeFormat.parse('2014-03-08T12:00:00'), timeFormat.parse('2014-03-10T00:00:00')])
        .range([0, width]);
    explainer(drawScale(d3ParseScale,
        [ timeFormat.parse('2014-03-09T00:00:00'), timeFormat.parse('2014-03-10T00:00:00'),
            timeFormat.parse('2014-03-09T03:00:00'), timeFormat.parse('2014-03-09T02:30:00'),
            timeFormat.parse('2014-03-09T01:30:00')
        ]), 'D3\'s solution to the JavaScript Date constructor issue, foreshortening around switch to DST',
        'd3.time.scale()',
        'no application of Watson',
        'uses D3 equivalent of strptime');
    i += 1;

    // #4: D3's suggested solution for the JavaScript Date constructor problem + Watson
    var timeFormat2 = d3.time.format('%Y-%m-%dT%H:%M:%S%Z');
    var d3ParseScale2 = d3.time.scale.utc()
        .domain([timeFormat2.parse('2014-03-08T12:00:00+0000'), timeFormat2.parse('2014-03-10T00:00:00+0000')])
        .range([0, width]);
    explainer(drawScale(d3ParseScale2,
        [ timeFormat2.parse('2014-03-09T00:00:00+0000'), timeFormat2.parse('2014-03-10T00:00:00+0000'),
            timeFormat2.parse('2014-03-09T03:00:00+0000'), timeFormat2.parse('2014-03-09T02:30:00+0000'),
            timeFormat2.parse('2014-03-09T01:30:00+0000')
        ]), 'D3\'s solution to the JavaScript Date constructor issue, equivalent to #1',
        'd3.time.scale.utc()',
        'assumes (a variation of) application of Watson',
        'uses D3 equivalent of strptime');
}());