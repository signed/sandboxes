import * as React from 'react';
import List from './list';
import RemoteCall from './remote-call';
import LocalStorageDisplay from './local-storage-display';

export default class Application extends React.Component {
  public render(): React.ReactNode {
    return [
      <div>Hello World</div>,
      <List/>,
      <RemoteCall/>,
      <LocalStorageDisplay localStorageKey="key"/>
    ];
  }
}
