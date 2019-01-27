import * as React from 'react';
import ListItem from "./list-item";

export default class List extends React.Component {
  public  render() {
    return (
      <ul>
        <ListItem content={'one'}/>
        <ListItem content={'two'}/>
      </ul>
    );
  }
}
