import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux'
import Clock from './components/clock';
import Welcome from './components/welcome'
import {TreeContainerStoreBound} from './components/tree'

import {createStore} from 'redux'
import reducers from './state/reducers'
import {FetchItemsButtonFunctionContainer} from "./components/FetchItemsButton";

const store = createStore(reducers);
store.subscribe(() => {
  console.log("change");
  const state = store.getState();
  console.log(state);
});

function App() {
  return (
    <div>
      <h1>Hello, World!</h1>
      <Clock/>
      <Welcome name="Sara"/>
      <FetchItemsButtonFunctionContainer/>
      <TreeContainerStoreBound/>
    </div>
  );
}

ReactDOM.render(
  <Provider store={store}>
    <App/>
  </Provider>,
  document.getElementById('root')
);