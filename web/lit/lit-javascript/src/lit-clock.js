import {LitElement, html} from 'lit';


class LitClock extends LitElement {


    render() {
        return html`static clock`
    }
}

customElements.define('lit-clock', LitClock);
