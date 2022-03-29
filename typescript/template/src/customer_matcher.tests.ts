describe('ends with matcher', () => {
  it('actually ends with the suffix ', () => {
    expect('Hello Matcher').toEndWith('Matcher');
  });
  it('does not end with the suffix', () => {
    expect('Hello Matcher').not.toEndWith('Bogus');
  });
});
