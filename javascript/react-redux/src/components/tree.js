import React from "react";
import * as backend from '../inmemorybackend';
import {connect} from 'react-redux';

export class BackendBoundTree extends React.Component {
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
        {this.state.items.map((c, i) => (
          <li key={i}>{c.name}</li>
        ))}
      </ul>
    );
  }
}

export class TreeContainer extends React.Component {
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
      <Tree items={this.state.items}/>
    );
  }
}

const mapStateToProps = state => {
  return {
    items: state.items
  }
};

const mapDispatchToProps = (dispatch) => {
  return {}
};

export class Tree extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ul>
        {this.props.items.map((c, i) => (
          <li key={i}>{c.name}</li>
        ))}
      </ul>
    );
  }
}

export const TreeContainerStoreBound = connect(mapStateToProps, mapDispatchToProps)(Tree);