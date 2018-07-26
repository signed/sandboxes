import React from "react";
import PropTypes from 'prop-types'
import {replaceItemsWith} from "../state/actions";
import {connect} from 'react-redux';


/**
 * FetchItemsButtonComponent and FetchItemsButtonFunction are interchangeable (except some edge cases)
 */
export class FetchItemsButtonComponent extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <button type="button" onClick={this.props.onClick}>Fetch items</button>
    );
  }
}

export const FetchItemsButtonFunction = ({onClick}) => (
  <button type="button" onClick={onClick}>Fetch items</button>
);

FetchItemsButtonFunction.propTypes = {
  onClick: PropTypes.func.isRequired
};

const mapStateToProps = state => {
  return {}
};

const mapDispatchToProps = (dispatch) => {
  return {
    onClick: () => {
      dispatch(replaceItemsWith([{name: "FetchItemsButtonFunction"}]))
    }
  }
};

export const FetchItemsButtonFunctionContainer = connect(mapStateToProps, mapDispatchToProps)(FetchItemsButtonFunction);
