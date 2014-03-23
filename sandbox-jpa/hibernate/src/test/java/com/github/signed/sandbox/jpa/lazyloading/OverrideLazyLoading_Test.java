package com.github.signed.sandbox.jpa.lazyloading;

import com.github.signed.sandbox.jpa.bookstore.Author;
import com.github.signed.sandbox.jpa.bookstore.Book;
import com.github.signed.sandbox.jpa.bookstore.Bookstore;
import com.github.signed.sandbox.jpa.h2.DatabaseConnector;
import com.github.signed.sandbox.jpa.h2.DatabaseServer;
import com.github.signed.sandbox.jpa.h2.H2JdbcUrlBuilder;
import com.github.signed.sandbox.jpa.h2.JpaDatabase;
import org.dozer.CustomFieldMapper;
import org.dozer.DozerBeanMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void stopServer() {
        connector.close();
        server.stop();
    }

    @Test
    public void testName() throws Exception {

        putThaliaIntoTheDatabase();

        System.out.println("juhu");
        EntityManager entityManager = connector.entityManagerForLocalHsqlDatabase();


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bookstore> query = criteriaBuilder.createQuery(Bookstore.class);
        Root<Bookstore> root = query.from(Bookstore.class);

        //root.fetch("books", JoinType.LEFT);


        CriteriaQuery<Bookstore> selectAll = query.select(root);
        TypedQuery<Bookstore> allQuery = entityManager.createQuery(selectAll);
        List<Bookstore> resultList = allQuery.getResultList();

        System.out.println("done loading\n\n\n");
        System.out.println(resultList);
    }

    @Test
    public void dozerMappingTest() throws Exception {
        putThaliaIntoTheDatabase();
        EntityManager entityManager = connector.entityManagerForLocalHsqlDatabase();
        Bookstore bookstore = (Bookstore) entityManager.createQuery("select b from Bookstore b").getSingleResult();

        entityManager.detach(bookstore);

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setCustomFieldMapper(new CustomFieldMapper() {
            @Override
            public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
                if (Hibernate.isInitialized(sourceFieldValue)) {
                    return false;
                }
                Class<?> destinationFieldType = fieldMapping.getDestFieldType(destination.getClass());

                if(Collection.class.isAssignableFrom(destinationFieldType)){
                    if(List.class.isAssignableFrom(destinationFieldType)){
                        fieldMapping.writeDestValue(destination, new ArrayList());
                        return true;
                    }
                    throw new RuntimeException("Unhandled collection mapping");
                }

                throw new RuntimeException("Unhandled type mapping");
            }
        });

        Bookstore mapped = mapper.map(bookstore, Bookstore.class);

        assertThat(mapped.getBooks().isEmpty(), is(true));
    }

    private void putThaliaIntoTheDatabase() {
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
