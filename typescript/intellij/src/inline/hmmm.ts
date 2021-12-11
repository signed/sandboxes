export type Options = {
    one: string
    two: number
}

const processOne = (_one: string) => {
};
const processTwo = (_two: number) => {
};

const expensive = () => 2;

export const someFunction = (options: Options) => {
    processOne(options.one);
    processTwo(options.two);
};

someFunction({
    one: '1',
    two: expensive()
});


