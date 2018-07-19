import React from 'react';
import ReactDOM from 'react-dom';

class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name} </h1>;
  }
}

function App() {
  let date = new Date();
  return (
    <div>
      <div>{""+date}</div>
      <Welcome name="Sara"/>
      <Welcome name="Cahal"/>
      <Welcome name="Edite"/>
    </div>
  );
}

ReactDOM.render(
  <App/>,
  document.getElementById('root')
);