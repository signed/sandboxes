require.config({
    packages: ['shelf', 'book', 'librarian'],
    paths: {
        jquery: 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min',
        underscore: 'libs/underscore/1.3.1/underscore',
        backbone: 'libs/backbone/0.9.1/backbone',
        bookmodel: 'book/book-model',
        bookcollection: 'book/book-collection',
        bookview: 'book/book-view',
        booktemplate: 'book/book-template',
        booklistapplication : 'booklist',
    }
});

require(       ["bookmodel", "bookcollection", "booklistapplication","shelf/model", "librarian/model"],
        function(BookModel, BookCollection, BookListApplication, book) {

    var books = new BookCollection();
    var bookListApplication = new BookListApplication({books:books});

    books.add(new BookModel({author:"James", title:"The return to the moon"}));
    books.add({title:'From the trenches'});
});

