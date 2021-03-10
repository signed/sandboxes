type FluentBuilder<T> = {
    [Property in keyof T as `with${Capitalize<string & Property>}`]: (value: T[Property]) => FluentBuilder<T>
}

interface Data {
    one: string
    two: number
}

// @ts-expect-error
class Builder implements FluentBuilder<Data> {

}
