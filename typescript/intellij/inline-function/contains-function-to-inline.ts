

export const tmp = () => {
    return 'stand-in'
}

export const promote = (value: string)=> {
    return `promote(${value})`;
}

export const toInline = () => {
    return promote(tmp())
}

