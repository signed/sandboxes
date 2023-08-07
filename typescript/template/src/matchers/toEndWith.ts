import {expect} from "vitest";
// https://jestjs.io/docs/en/expect.html#expectextendmatchers
expect.extend({
  toEndWith(received: string, suffix: string) {
    const pass = suffix === received.substr(-suffix.length);
    if (pass) {
      return {
        message: () =>
          `expected ${this.utils.printReceived(received)} not to end with ${this.utils.printExpected(suffix)}`,
        pass: true,
      };
    }
    return {
      message: () =>
        `expected ${this.utils.printReceived(received)} to end with ${this.utils.printExpected(suffix)}`,
      pass: false,
    };
  },
});
