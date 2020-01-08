package com.waflo;

import com.waflo.model.Author;
import com.waflo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
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


        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Book.class);

        return em.createQuery(query.select(query.from(Book.class))).getResultList().toArray(new Book[0]);
    }

    @Transactional()
    public void insertBook(Book book) {
        var authorObj = em.createQuery("select a from Author a where a.name like :authName", Author.class).setParameter("authName", book.getAuthor().getName()).getResultStream().findFirst().orElse(null);
        if (authorObj != null)
            book.setAuthor(authorObj);

        var b = em.createQuery("select b from Book b where b.title like :title", Book.class).setParameter("title", book.getTitle()).getResultStream().findFirst().orElse(null);
        if (b != null)
            throw new EntityExistsException("Book with title " + book.getTitle() + " already exists.");

        try {
            em.persist(book);
        } catch (Throwable t) {
            logger.warn(t.getMessage());
            throw t;
        }

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
