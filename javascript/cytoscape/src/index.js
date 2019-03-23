import cytoscape from 'cytoscape';
import dagre from 'cytoscape-dagre';

cytoscape.use(dagre);


const options = {
    container: document.getElementById('graph'),
    elements: {
        nodes: [
            {
                data: {id: 'a'}
            },
            {
                data: {id: 'b'}
            },
            {
                data: {id: 'b1'}
            },
            {
                data: {id: 'b2'}
            },
            {
                data: {id: 'c'}
            },
            {
                data: {id: 'd'}
            }
        ],
        edges: [
            {
                data: {id: 'ab', source: 'a', target: 'b'}
            },
            {
                data: {id: 'ab1', source: 'a', target: 'b1'}
            },
            {
                data: {id: 'ab2', source: 'a', target: 'b2'}
            },
            {
                data: {id: 'b1c', source: 'b1', target: 'c'}
            }
        ]
    },
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
        name: 'dagre',
        rankDir: 'BT'
    },
    headless: false
};
const cy = cytoscape(options);

console.log(cy);
console.log(cytoscape.warnings());