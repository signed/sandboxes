function component() {
    const element = document.createElement('div');
    element.innerHTML = ['Hello', 'webpack', 'my', 'pleasure'].join(' ');
    return element;
}

document.body.appendChild(component());