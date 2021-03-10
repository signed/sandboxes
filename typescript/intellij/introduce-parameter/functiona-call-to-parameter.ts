interface Aggregate {
    a: string
    b: string
}

export const createAggregate = (a: string, b: string): Aggregate => {
    return { a, b };
};

export const someFunction = (a: string, b: string, c: number) => {
    const ag = createAggregate(a, b)

    console.log({ ag, c });
};


someFunction('1', '2', 3)
