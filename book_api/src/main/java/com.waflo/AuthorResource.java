package com.waflo;


import com.waflo.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/authors")
@ApplicationScoped
public class AuthorResource {

    private final Logger logger;
    @Inject
    private DBService dbService;

    public AuthorResource() {
        logger = LoggerFactory.getLogger(AuthorResource.class);

    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)

    public Author[] authors() {
        return dbService.getAuthors();


    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertAuthor(@Valid Author author) {
        dbService.insertAuthor(author);


        dbService.flush();
        try {
            return Response.created(new URI("/authors/" + author.getId())).build();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/{id}")
    public Response authorByID(@PathParam("id") int id) {


        Author auth = dbService.get(Author.class, id);
        if (auth == null)
            return Response.noContent().entity("Author does not exist").build();
        return Response.ok(auth).build();
    }


}

