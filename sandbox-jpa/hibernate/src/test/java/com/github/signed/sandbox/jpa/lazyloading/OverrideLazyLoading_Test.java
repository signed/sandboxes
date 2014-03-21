package com.github.signed.sandbox.jpa.lazyloading;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.github.signed.sandbox.jpa.bookstore.Author;
import com.github.signed.sandbox.jpa.bookstore.Book;
import com.github.signed.sandbox.jpa.bookstore.Bookstore;
import com.github.signed.sandbox.jpa.h2.DatabaseConnector;
import com.github.signed.sandbox.jpa.h2.DatabaseServer;
import com.github.signed.sandbox.jpa.h2.H2JdbcUrlBuilder;
import com.github.signed.sandbox.jpa.h2.JpaDatabase;

public class OverrideLazyLoading_Test {
    private final H2JdbcUrlBuilder jdbcUrlBuilder = new H2JdbcUrlBuilder().database("test").keepDataInMemoryUntilJvmShutdown();
    private final DatabaseConnector connector = new DatabaseConnector(jdbcUrlBuilder);
    private final JpaDatabase jpaDatabase = new JpaDatabase(connector);
    private final DatabaseServer server = new DatabaseServer(9081);

    @Before
    public void setUp() throws Exception {
        connector.createEntityManagerFactory();
        server.start();
    }

    @After
    public void stopServer(){
        connector.close();
        server.stop();
    }

    @Test
    public void testName() throws Exception {

        Author fowler = fowler();
        Author martin = martin();
        jpaDatabase.persist(fowler);
        jpaDatabase.persist(martin);

        Book cleanCode = cleanCode(martin);
        Book refactoring = refactoring(fowler);
        jpaDatabase.persist(cleanCode);
        jpaDatabase.persist(refactoring);


        Bookstore thalia = new Bookstore();
        thalia.setName("Thalia");
        ArrayList<Book> books = new ArrayList<>();
        books.add(cleanCode);
        books.add(refactoring);

        thalia.setBooks(books);
        jpaDatabase.persist(thalia);

        System.out.println("juhu");


    }

    private Book refactoring(Author fowler) {
        Book refactoring = new Book();
        refactoring.setAuthor(fowler);
        refactoring.setTitle("Refactoring");
        return refactoring;
    }

    private Book cleanCode(Author martin) {
        Book cleanCode = new Book();
        cleanCode.setAuthor(martin);
        cleanCode.setTitle("Clean Code");
        return cleanCode;
    }

    private Author martin() {
        Author martin = new Author();
        martin.setFirstName("Robert");
        martin.setLastName("Martin");
        return martin;
    }

    private Author fowler() {
        Author fowler = new Author();
        fowler.setFirstName("Martin");
        fowler.setLastName("Fowler");
        return fowler;
    }
}
