import * as React from 'react';

export default class RemoteCall extends React.Component<any, { status: number }> {

  constructor(props: any) {
    super(props);
    this.state = {
      status: -1,
    };
  }

  public componentDidMount(): void {
    fetch('https://httpbin.org/user-agent')
      .then(result => this.setState({ status: result.status }))
      .catch(error => this.setState({ status: error.status }));
  }

  public render(): React.ReactNode {
    const { status } = this.state;
    return (
      <div>
        Status: <span key="status" data-automation-id="remote-call__status">{status}</span>
      </div>
    );
  }
}
