import {setup, teardown} from 'mocha';

export class Rule {
  constructor() {
    this.state = null;
  }

  setupRule() {
    const _this = this;
    setup(function () {
      _this.state = "initialized in setup";
      return _this.state;
    });

    teardown(function () {
      return _this.state = "cleared in teardown";
    });
  };
}
