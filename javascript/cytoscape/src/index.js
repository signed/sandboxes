import cytoscape from 'cytoscape';
import dagre from 'cytoscape-dagre';
cytoscape.use(dagre);


const options = {
    container: document.getElementById('graph'),
    elements: [
        {
            group: 'nodes',
            data: {id: 'a'}
        },
        {
            group: 'nodes',
            data: {id: 'b'}
        },
        {
            group: 'nodes',
            data: {id: 'b1'}
        },
        {
            group: 'nodes',
            data: {id: 'b2'}
        },
        {
            group: 'nodes',
            data: {id: 'c'},
        },
        {
            group: 'nodes',
            data: {id: 'd'},
        },
        {
            group: 'edges',
            data: {id: 'ab', source: 'a', target: 'b'}
        },
        {
            group: 'edges',
            data: {id: 'ab1', source: 'a', target: 'b1'}
        },
        {
            group: 'edges',
            data: {id: 'ab2', source: 'a', target: 'b2'}
        },
        {
            group: 'edges',
            data: {id: 'b1c', source: 'b1', target: 'c'}
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
        name: 'dagre'
    },
    headless: false
};
const cy = cytoscape(options);

console.log(cy);
console.log(cytoscape.warnings());