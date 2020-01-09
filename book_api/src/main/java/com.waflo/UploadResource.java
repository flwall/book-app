package com.waflo;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Path("/upload")
public class UploadResource {

    private static final String UPLOADED_FILE_PATH = "./uploaded/";
    private final Logger logger;

    public UploadResource() {
        this.logger = LoggerFactory.getLogger(UploadResource.class);

        new File(UPLOADED_FILE_PATH).mkdirs();
    }

    @POST
    @Path("/cover")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadCover(MultipartFormDataInput input, @Context HttpHeaders headers) {
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("coverFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFilename(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileName = UPLOADED_FILE_PATH + fileName;

                IOUtils.write(bytes, new FileOutputStream(fileName));

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return Response.accepted().entity("Cover uploaded successfully!").build();
    }

    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @POST
    public Response uploadBook(MultipartFormDataInput input, @Context HttpHeaders headers) {

        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();
        List<InputPart> inputParts = formDataMap.get("bookFile");//name of the file uploaded has to be like this

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
