import * as actionTypes from './actionTypes'

export function replaceItemsWith(items) {
  return {
    type: actionTypes.REPLACE_ITEMS,
    payload: {
      items,
    }
  };
}

