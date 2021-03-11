// https://youtrack.jetbrains.com/issue/IDEA-264126
export const someFunction = () => {

}

const usage = () => {
    someFunction()
    someFunction()
    someFunction()
    someFunction()
    someFunction()
    someFunction()
}
const anotherUsage = () => {
    someFunction()
}

someFunction()


