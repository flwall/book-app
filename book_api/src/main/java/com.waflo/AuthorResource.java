package com.waflo;


import com.waflo.model.Author;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/authors")
public class AuthorResource {

    private final Logger logger;
    private DBService dbService;

    public AuthorResource() {
        logger = LoggerFactory.getLogger(AuthorResource.class);
        dbService = DBService.getInstance();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)

    public Author[] authors() {

        Session sess = dbService.openSession();
        CriteriaQuery<Author> query = sess.getCriteriaBuilder().createQuery(Author.class);

        return sess.createQuery(query.select(query.from(Author.class))).getResultList().toArray(new Author[0]);


    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertAuthor(@Valid Author author) {
        Session sess = dbService.openSession();
        sess.beginTransaction();
        var id = sess.save(author);
        sess.getTransaction().commit();

        try {
            return Response.created(new URI("/authors/" + id.toString())).build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response authorByID(@PathParam("id") int id) {

        Session sess = dbService.openSession();
        Author auth = sess.get(Author.class, id);
        if (auth == null)
            return Response.noContent().entity("Author does not exist").build();
        return Response.ok(auth).build();
    }


}

