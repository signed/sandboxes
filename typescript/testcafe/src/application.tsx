import * as React from 'react';
import List from './list';

export default class Application extends React.Component {
  public render(): React.ReactNode {
    return [
      <div>Hello World</div>,
      <List/>,
    ];
  }
}
