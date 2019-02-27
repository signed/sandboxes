import { handler } from "../src";

describe('lambda', () => {
  it('then', () => {
    let callback = jest.fn();

    handler({}, {}, callback);
    let data = (callback).mock.calls[0][1];
    expect(data).toEqual('{}');
  });
});
