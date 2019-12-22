package com.waflo;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/upload")
public class UploadResource {

    private final Logger logger;

    public UploadResource() {
        this.logger = LoggerFactory.getLogger(UploadResource.class);
    }

    @POST
    @Path("/cover")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCover(MultipartFormDataInput input, @Context HttpHeaders headers) {
        return Response.ok().build();


    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    public Response uploadBook(MultipartFormDataInput input, @Context HttpHeaders headers) {

        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();
        List<InputPart> inputParts = formDataMap.get("book-file");//name of the file uploaded has to be like this

        for (var part : inputParts) {

            String fn = getFilename(part.getHeaders());
            try {
                IOUtils.write(IOUtils.toByteArray(part.getBody(InputStream.class, null)), new FileOutputStream("./" + fn));
            } catch (IOException io) {
                logger.error(io.getMessage());
            }


        }

        return Response.ok().entity("Uploaded File").build();


    }

    private String getFilename(MultivaluedMap<String, String> headers) {


        String[] contentDisposition = headers.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                return name[1].trim().replaceAll("\"", "");
            }
        }

        logger.error("File has no Filename");

        return "unknown";

    }


}
