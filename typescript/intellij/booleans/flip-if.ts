
export const hello = (array:Array<string>) => {
    array.filter((value) => {
        if (value.length) {
            return 'empty'
        }
        return 'content'
    })
}
