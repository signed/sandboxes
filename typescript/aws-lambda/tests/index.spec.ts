import {handler} from "../src";

describe('lambda', () => {
    it('should run', () => {
      return handler({}).then(data => expect(data).toEqual('{}'));
    });
});
