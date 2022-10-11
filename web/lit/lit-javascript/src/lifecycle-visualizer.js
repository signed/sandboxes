import {LitElement, html} from 'lit';


/**
 * There is an overview of the lifecycle in the middle of this page
 * https://codelabs.developers.google.com/codelabs/lit-2-for-react-devs#11
 */
class LifecycleVisualizer extends LitElement {
    static get properties() {
        return {
            _calls: {state: true}
        }
    }



    constructor() {
        super();
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

    shouldUpdate(_changedProperties) {
        const shouldUpdate = super.shouldUpdate(_changedProperties);
        this._calls.push('shouldUpdate')
        return shouldUpdate;
    }

    /**
     * Changes to reactive properties in `willUpdate` do not re-trigger the update cycle
     * This method is called on the server in SSR, so accessing the DOM is not advised here
     * @param changedProperties
     */
    willUpdate(changedProperties) {
        super.willUpdate(changedProperties);
        this._calls = [...this._calls, 'willUpdate']
    }

    /**
     * Changes to reactive properties in update do not re-trigger the update cycle if changed before calling super.update
     * Good place to capture information from the DOM surrounding the component before the rendered output is committed to the DOM
     * @param changedProperties
     */
    update(changedProperties) {
        this._calls = [...this._calls, 'update']
        super.update(changedProperties);
        // doing this here will trigger a re-render
        //this._calls = [...this._calls, 'update']
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
