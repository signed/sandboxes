export class SomeClass {
  aMethod(condition: boolean) {
    const emitter = new EventSource('stand in')
    emitter.addEventListener('on', () => {
      this.asdfasd(condition)
    })
  }

  protected asdfasd(condition: boolean) {
    if (condition) {
      console.log('jup')
    }
  }
}
