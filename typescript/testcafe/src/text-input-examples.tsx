import * as React from 'react';

export default class TextInputExample extends React.Component {
  public render() {
    return (
      <div>
        E-Mail: <input id="email-input" type="email" name="emailaddress"/>
      </div>);
  }
}
