package com.waflo;

import com.waflo.model.Book;
import org.apache.commons.io.IOUtils;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

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

        Map<String, File> map = UploadResource.getTimestampFileMap();
        File uploadedFile = map.get(book.timestamp);
        if (uploadedFile != null) {
            String fn = uploadedFile.getName();
            String dir = dbService.dbPath() + "/books/" + book.getAuthor().getName() + "/";
            new File(dir).mkdirs();
            book.setPath_to_book(dir);
            book.setFormat(fn.substring(fn.lastIndexOf(".") + 1));
            try {
                Files.move(map.get(book.timestamp).toPath(), new File(book.getPath_to_book() + book.getTitle().toLowerCase() + fn.substring(fn.lastIndexOf(".")).toLowerCase()).toPath());
            } catch (IOException e) {
                logger.error("FAILED TO MOVE FILE TO RIGHT LOCATION");
            }
        }
        if (book.getRating() < -1 || book.getRating() > 5)
            return Response.status(Response.Status.BAD_REQUEST).entity("The Rating has to be between 0 and 5").build();

        try {
            dbService.insertBook(book);
        } catch (EntityExistsException ex) {
            Response.status(409).entity(ex.getMessage()).build();
        }

        return Response.status(201).entity(book).build();
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

    @Path("{id}/download")
    @POST
    public Response downloadBook(@QueryParam("id") int id) {
        Book b = dbService.get(Book.class, id);
        if (b == null) return null;
        File f = new File(b.getPath_to_book() + b.getTitle().toLowerCase() + "." + b.getFormat().toLowerCase());
        logger.warn(f.getAbsolutePath());

        try {
            return Response.ok(IOUtils.toByteArray(new FileInputStream(f))).header("Content-Disposition", "attachment; filename=" + b.getTitle().toLowerCase() + "." + b.getFormat().toLowerCase()).build();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }

}
