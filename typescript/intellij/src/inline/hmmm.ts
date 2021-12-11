export type Options = {
    one: string
    two: number
}

const expensive = () => 2;

export const someFunction = (options: Options) => {
    console.log(options.one);
    console.log(options.two);
};

someFunction({
    one: '1',
    two: expensive()
});


