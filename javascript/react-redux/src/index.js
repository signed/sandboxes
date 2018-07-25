import React from 'react';
import ReactDOM from 'react-dom';
import Clock from './components/clock';
import Welcome from './components/welcome'
import Tree from './components/tree'

function App() {
  return (
    <div>
      <h1>Hello, World!</h1>
      <Clock/>
      <Welcome name="Sara"/>
      <Welcome name="Cahal"/>
      <Welcome name="Edite"/>
      <Tree/>
    </div>
  );
}

ReactDOM.render(
  <App/>,
  document.getElementById('root')
);