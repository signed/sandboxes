// https://youtrack.jetbrains.com/issue/IDEA-265719
export class WithGenericMethod<T> {
  public forEach(callback: (value: T) => void): void {}
}

export class WithMethod<T> {
  public method(cache: WithGenericMethod<T>) {}
}

new WithMethod<string>().method(new WithGenericMethod())
