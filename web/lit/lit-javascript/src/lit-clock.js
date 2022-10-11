import {LitElement, html} from 'lit';


class LitClock extends LitElement {
    static get properties() {
        return {
            _now: {state: true}
        }
    }

    constructor() {
        super();
        this._tick()
    }

    connectedCallback() {
        super.connectedCallback();
        this._scheduleTimeUpdate()
    }

    disconnectedCallback() {
        globalThis.clearTimeout(this._timeoutIdentifier)
        this._timeoutIdentifier = undefined
    }

    _scheduleTimeUpdate () {
        this._timeoutIdentifier = globalThis.setTimeout(() => {
            this._tick();
            this._scheduleTimeUpdate()
        }, 1000)
    }

    _tick() {
        this._now = new Date()
    }

    render() {
        return html`
            <div>
                <h1>Hello, world!</h1>
                <h2>It is ${this._now.toLocaleTimeString()}.</h2>
            </div>`
    }
}

customElements.define('lit-clock', LitClock);
