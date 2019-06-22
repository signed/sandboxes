import * as React from 'react';

export default class RemoteCall extends React.Component<any, { status: number, body: string }> {

  constructor(props: any) {
    super(props);
    this.state = {
      status: -1,
      body: ''
    };
  }

  public render(): React.ReactNode {
    const { body, status } = this.state;
    return (
      <div>
        <button onClick={() => this.executeRemoteCall()} data-automation-id="remote-call__execute">execute remote call</button>
        <p>
          Status: <span key="status" data-automation-id="remote-call__status">{status}</span>
        </p>
        <p>
          Body: <span key="body" data-automation-id="remote-call__body">{body}</span>
        </p>
      </div>
    );
  }

  private executeRemoteCall() {
    fetch('https://httpbin.org/user-agent')
      .then(async result => {
        const status = result.status;
        const body = await result.text();
        return this.setState({ status, body });
      })
      .catch(error => this.setState({ status: error.status }));
  }
}
