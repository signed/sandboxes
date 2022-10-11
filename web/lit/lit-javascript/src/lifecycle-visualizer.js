import {LitElement, html} from 'lit';

class LifecycleVisualizer extends LitElement {
    static get properties() {
        return {
            name: {type: String},
            _calls: {state: true}
        }
    }



    constructor() {
        super();
        this.name = '';
        this._calls = []
    }

    connectedCallback(){
        // if super call is missing `firstUpdated` will not be called
        super.connectedCallback()
        this._calls = [...this._calls, 'connectedCallback']
    }


    /**
     * Useful for cleaning up event listeners and leaky references so that the browser can garbage collect the component
     */
    disconnectedCallback() {
        super.disconnectedCallback();
        this._calls = [...this._calls, 'disconnectedCallback']
    }

    willUpdate(_changedProperties) {
        super.willUpdate(_changedProperties);
        this._calls = [...this._calls, 'willUpdate']
    }

    /**
     * Called the first time the component's template is rendered into the component's root
     * This is a good place to perform component setup that requires the DOM rendered by the component
     *
     * @param propertyValues
     */
    firstUpdated(propertyValues) {
        super.firstUpdated(propertyValues)
        // todo: https://lit.dev/docs/components/properties/
        //this._calls.push('firstUpdated')
        //this.requestUpdate()
        // changing the reactive property will trigger a re-render
        this._calls = [...this._calls, 'firstUpdated']
    }


    updated(changedProperties) {
        super.updated(changedProperties);
        //only conditionally change reactive properties in here, otherwise it will end up in an endless loop
        //this._calls = [...this._calls, 'updated']
        this._calls.push('updated')
    }

    render() {
        return html`
            <ol>
                ${this._calls.map(call => html`
                    <li>${call}</li>`)}
            </ol>`
    }
}

customElements.define('lifecycle-visualizer', LifecycleVisualizer);
