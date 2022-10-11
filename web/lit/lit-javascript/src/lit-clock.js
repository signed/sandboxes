import {LitElement, html} from 'lit';

class ClockController {
    constructor(host, timout) {
        this.host = host;
        this.timeout = timout
        host.addController(this);
        this._tick()
    }

    _tick() {
        this._now = new Date()
        this.host.requestUpdate()
    }

    hostConnected() {
        this._scheduleTimeUpdate()
    }

    hostDisconnected() {
        globalThis.clearTimeout(this._timeoutIdentifier)
        this._timeoutIdentifier = undefined
    }

    _scheduleTimeUpdate() {
        this._timeoutIdentifier = globalThis.setTimeout(() => {
            this._tick();
            this._scheduleTimeUpdate()
        }, this.timeout)
    }

}

class LitClock extends LitElement {
    _clockController = new ClockController(this)

    render() {
        return html`
            <div>
                <h1>Hello, world!</h1>
                <h2>It is ${this._clockController._now.toLocaleTimeString()}.</h2>
            </div>`
    }
}

customElements.define('lit-clock', LitClock);
