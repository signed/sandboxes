import React from "react";
import * as backend from '../inmemorybackend'

export default class Tree extends React.Component {
  constructor(props) {
    super(props);
    this.state = {items: []};
  }

  componentDidMount() {
    backend.fetchSomeItems()
      .then(items => this.setState({items}));
  }

  render() {
    return (
      <ul>
        {this.state.items.map(c => (
          <li>{c.name}</li>
        ))}
      </ul>
    );
  }
}