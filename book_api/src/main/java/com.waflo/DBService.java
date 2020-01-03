package com.waflo;

import com.waflo.model.Author;
import com.waflo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class DBService {
    private static final Logger logger = LoggerFactory.getLogger(DBService.class.getSimpleName());


    public String dbPath() {
        return "./";
    }


    @Inject
    EntityManager em;

    @Transactional
    public Book[] getBooks() {
        logger.warn("Entity Manager" + em);

        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Book.class);

        return em.createQuery(query.select(query.from(Book.class))).getResultList().toArray(new Book[0]);
    }

    @Transactional
    public void insertBook(Book book) {


        em.persist(book);

    }

    public <T> T get(Class<T> entityClass, Object primaryKey) {
        return em.find(entityClass, primaryKey);
    }

    @Transactional
    public void flush() {
        em.flush();
    }

    public Author[] getAuthors() {
        CriteriaQuery<Author> query = em.getCriteriaBuilder().createQuery(Author.class);

        return em.createQuery(query.select(query.from(Author.class))).getResultList().toArray(new Author[0]);

    }

    @Transactional
    public void insertAuthor(Author author) {
        em.persist(author);
    }
}
