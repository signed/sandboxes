export const tmp = () => {
    return 'stand-in'
}

export const promote = (value: string)=> {
    return `promote(${value})`;
}

// https://youtrack.jetbrains.com/issue/WEB-50246
export const toInline = () => {
    return promote(tmp())
}

