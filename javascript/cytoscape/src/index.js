import cytoscape from 'cytoscape';

console.log('hello');


const options = {
    container: document.getElementById('cytoscape'),
    elements: [ // list of graph elements to start with
        { // node a
            data: {id: 'a'}
        },
        { // node b
            data: {id: 'b'}
        },
        { // edge ab
            data: {id: 'ab', source: 'a', target: 'b'}
        }
    ],
    style: [ // the stylesheet for the graph
        {
            selector: 'node',
            style: {
                'background-color': '#666',
                'label': 'data(id)'
            }
        },

        {
            selector: 'edge',
            style: {
                'width': 3,
                'line-color': '#ccc',
                'target-arrow-color': '#ccc',
                'target-arrow-shape': 'triangle'
            }
        }
    ],
    layout: {
        name: 'grid',
        rows: 1
    },
    headless: false
};
const cy = cytoscape(options);

console.log(cy);
console.log(cytoscape.warnings());