package com.waflo;

import com.waflo.model.Timestamp;
import io.quarkus.runtime.ShutdownEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("/")
@ApplicationScoped
public class UploadResource {

    private static final String UPLOADED_FILE_PATH = "../uploaded/";
    private final Logger logger;

    public UploadResource() {
        this.logger = LoggerFactory.getLogger(UploadResource.class);

        new File(UPLOADED_FILE_PATH).mkdirs();
    }

    void onStop(@Observes ShutdownEvent ev) {
        logger.info("The application is stopping...");
        try {
            FileUtils.deleteDirectory(new File(UPLOADED_FILE_PATH));
        } catch (IOException e) {
            logger.error("Failed to remove uploaded files: " + e.getMessage());
        }

    }

    @POST
    @Path("/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response cancelBookUpload(Timestamp timestamp) {

        File f = timestampFileMap.get(timestamp.timestamp);
        if (f == null)
            return Response.notModified().entity("No such Timestamp").build();
        f.delete();
        return Response.accepted().build();

    }


    private static Map<String, File> timestampFileMap = new HashMap<>();

    public static Map<String, File> getTimestampFileMap(){
        return timestampFileMap;
    }

    @POST
    @Path("/uploadCover")
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
    @Path("/upload")
    public Response uploadBook(MultipartFormDataInput input) {

        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();


        List<InputPart> inputParts = formDataMap.get("bookFile");//name of the file uploaded has to be like this


        String ts;
        try {
            ts = formDataMap.get("timestamp").get(0).getBodyAsString();
        } catch (IOException e) {
            logger.warn("Missing timestamp");
            return Response.status(400).entity("Missing timestamp").build();
        }
        for (InputPart part : inputParts) {

            String fn = getFilename(part.getHeaders());
            try {
                File f = new File(UPLOADED_FILE_PATH + fn);
                timestampFileMap.put(ts, f);

                FileOutputStream fos = new FileOutputStream(f);
                IOUtils.write(IOUtils.toByteArray(part.getBody(InputStream.class, null)), fos);
                fos.close();
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
