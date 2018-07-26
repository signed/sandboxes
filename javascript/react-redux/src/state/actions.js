import * as actionTypes from './actionTypes'

export function replaceItemsWith(items) {
  if(!Array.isArray(items)){
    throw 'items argument is not an array';
  }
  return {
    type: actionTypes.REPLACE_ITEMS,
    payload: {
      items,
    }
  };
}

