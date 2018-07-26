import * as actionTypes from './actionTypes'

import {combineReducers} from 'redux'

function itemsReducer(state = [], action) {
  switch (action.type) {
    case actionTypes.REPLACE_ITEMS:
      return action.payload.items;
    default:
      return state
  }
}

function connectionsReducer(state = [], action) {
  return state
}

export default combineReducers({
  items: itemsReducer,
  connections: connectionsReducer,
})