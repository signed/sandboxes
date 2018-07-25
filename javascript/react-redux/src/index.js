import React from 'react';
import ReactDOM from 'react-dom';
import Clock from './components/clock';
import Welcome from './components/welcome'
import {BackendBoundTree, TreeContainer} from './components/tree'

import {createStore} from 'redux'
import reducers from './state/reducers'
import {replaceItemsWith} from "./state/actions";

const store = createStore(reducers);
store.subscribe(() => {
  console.log("change")
  const state = store.getState();
  console.log(state);
});

store.dispatch(replaceItemsWith({name: "one"}));


function App() {
  return (
    <div>
      <h1>Hello, World!</h1>
      <Clock/>
      <Welcome name="Sara"/>
      <Welcome name="Cahal"/>
      <Welcome name="Edite"/>
      <BackendBoundTree/>
      <TreeContainer/>
    </div>
  );
}

ReactDOM.render(
  <App/>,
  document.getElementById('root')
);