package com.waflo;

import com.waflo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/books")
@ApplicationScoped
public class BookResource {


    private final Logger logger;
    @Inject
    DBService dbService;

    public BookResource() {
        logger = LoggerFactory.getLogger(BookResource.class);


    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertBook(@Valid Book book) {

        book.setPath_to_book(dbService.dbPath() + "/books/" + book.getAuthor().getName() + "/" + book.getTitle() + "/");
        new File(book.getPath_to_book()).mkdirs();

        if (book.getRating() < -1 || book.getRating() > 5)
            return Response.status(Response.Status.BAD_REQUEST).entity("The Rating has to be between 0 and 5").build();
        try {
            dbService.insertBook(book);

        } catch (EntityExistsException ex) {

            return Response.status(Response.Status.CONFLICT).entity("{\"error\" : \"The Book already exists\"}").build();
        }
        try {
            return Response.created(new URI("/books/" + book.getId())).build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
        return Response.serverError().build();
    }


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Book[] books() {


        return dbService.getBooks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBook(@PathParam(value = "id") int id) {

        Book b = dbService.get(Book.class, id);

        if (b == null)
            return Response.noContent().build();
        return Response.ok(b).build();

    }

}
