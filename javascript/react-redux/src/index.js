import React from 'react';
import ReactDOM from 'react-dom';
import Clock from './components/clock';

class Welcome extends React.Component {
  render() {
    return <h1>Hello, {this.props.name}</h1>;
  }
}

function App() {
  return (
    <div>
      <Clock/>
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