// https://youtrack.jetbrains.com/issue/WEB-51079
[''].filter((value) => {
    if (value.length) {
        return 'empty'
    }
    return 'content'
})
