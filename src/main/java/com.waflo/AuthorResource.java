package com.waflo;


import com.waflo.model.Author;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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


}

