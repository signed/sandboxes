// https://youtrack.jetbrains.com/issue/IDEA-265722
interface AnInterface {
  newName(): void
}

class AnImplementation implements AnInterface {
  readonly #toRename: string = 'initial'

  public retrieve() {
    return this.#toRename
  }

  newName(): void {}
}
