import cytoscape from 'cytoscape';
import dagre from 'cytoscape-dagre';
import edgehandles from 'cytoscape-edgehandles';
import uuid from 'uuid/v4';

cytoscape.use(dagre);
cytoscape.use(edgehandles);

const node = title => {
    return {
        type: 'node',
        data: {
            id: uuid(),
            title
        },
        classes: ['data']

    }
};

const edge = (from, to) => {
    const fromId = from.data.id;
    const toId = to.data.id;
    const id = fromId + ' ' + toId;
    return {
        type: 'edge',
        data: {
            id,
            source: fromId,
            target: toId
        }
    }
};

const a = node('a');
const b = node('b');
const b1 = node('b1');
const b2 = node('b2');
const c = node('c');
const d = node('d');

const options = {
    container: document.getElementById('graph'),
    elements: {
        nodes: [a, b, b1, b2, c, d],
        edges: [
            edge(a, b),
            edge(a, b1),
            edge(a, b2),
            edge(b1, c)
        ]
    },
    style: [
        {
            selector: 'node',
            style: {
                'background-color': '#666'
            }
        },
        {
            selector: '.data',
            style: {
                'label': 'data(title)'
            }
        },
        {
            selector: '.eh-handle',
            style: {
                'background-color': 'red',
                'width': 12,
                'height': 12,
                'shape': 'ellipse',
                'overlay-opacity': 0,
                'border-width': 12, // makes the handle easier to hit
                'border-opacity': 0
            }
        },
        {
            selector: 'edge',
            style: {
                'width': 3,
                'line-color': '#ccc',
                'target-arrow-color': '#ccc',
                'target-arrow-shape': 'triangle',
                'curve-style': 'taxi'
            }
        }
    ],
    layout: {
        name: 'dagre',
        rankDir: 'BT'
    }
};
const cy = cytoscape(options);
const eh = cy.edgehandles({});
eh.enable();
cy.on('click', e => {
    if (e.target === cy) {
        const newNode = node('new');
        newNode.position = {
            x: e.position.x,
            y: e.position.y
        };
        cy.add(newNode)
    }
});