import {handler} from "../src";

describe('lambda', () => {
    it('then', () => {
      return handler({}).then(data => expect(data).toEqual('{}'));
    });
    it('await', async () => {
      expect.assertions(1);
      let data = await handler({});
      expect(data).toEqual('{ }');
    });
});
