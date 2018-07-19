import React from 'react';
import ReactDOM from 'react-dom';

const SimpleComponent = React.createClass({
  render: function () {
    return (
      <div>{this.props.message}</div>
    );
  }
});

ReactDOM.render(
  <SimpleComponent message="React Demo"/>,
  document.querySelector('.js-app')
);