// https://youtrack.jetbrains.com/issue/WEB-50680
enum SomeEnum {
  one = 'one',
  two = 'two',
  three = 'three',
}

const someFunction = () => {
  const localVariable = SomeEnum.two
  console.log(localVariable)
}

someFunction()
