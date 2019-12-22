package com.waflo;

import com.waflo.model.Book;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/books")
public class BookResource {

    private final Logger logger;
    private DBService dbService;

    public BookResource() {
        logger = LoggerFactory.getLogger(BookResource.class);
        dbService = DBService.getInstance();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertBook(@Valid Book book) {

        logger.warn(String.valueOf(book));

        book.setPath_to_book(dbService.dbPath() + "/books/" + book.getAuthor().getName() + "/" + book.getTitle() + "/");
        if (!new File(book.getPath_to_book()).mkdirs()) return Response.serverError().build();

        if (book.getRating() < -1 || book.getRating() > 5)
            return Response.status(Response.Status.BAD_REQUEST).entity("The Rating has to be between 0 and 5").build();

        Session sess = dbService.openSession();
        sess.beginTransaction();
        Serializable id = sess.save(book);

        sess.getTransaction().commit();

        try {
            return Response.created(new URI("/books/" + id)).build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return Response.serverError().build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Book[] books() {
        Session sess = dbService.openSession();
        CriteriaQuery<Book> query = sess.getCriteriaBuilder().createQuery(Book.class);

        return sess.createQuery(query.select(query.from(Book.class))).getResultList().toArray(new Book[0]);

    }

    @GET
    @Path("/{id}")
    public Response findBook(@PathParam(value = "id") int id) {
        Session sess = dbService.openSession();

        Book b = sess.get(Book.class, id);
        if (b == null)
            return Response.noContent().build();
        return Response.ok(b).build();

    }

}
