// https://www.typescriptlang.org/docs/handbook/declaration-merging.html
declare namespace jest {
  interface Matchers<R> {
    toEndWith(value: string): R;
  }
}
