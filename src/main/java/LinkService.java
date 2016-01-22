/**
 * Created by Doron on 1/20/16.
 */
import static spark.Spark.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;



public class LinkService {
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;

    public static void main(String[] args) {
        Annotator annotator = new Annotator();
        port(3001);
        get("/names/:name", (request, response) -> {
            String name = request.params(":name");
            String url = annotator.getUrl(name);
            if (url == null) {
                response.status(HTTP_NOT_FOUND);
                return "";
            }
            NameURLPayload data = new NameURLPayload(name, url);
            response.status(200);
            response.type("application/json");
            return dataToJson(data);
        });

        put("/names/:name", (request, response) -> {
            String name = request.params(":name");
            ObjectMapper mapper = new ObjectMapper();
            String body = request.body();
            JsonNode rootNode = mapper.readTree(body);
            JsonNode urlNode = rootNode.get("url");
            if (urlNode == null) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            String url = urlNode.asText();
            NameURLPayload data = new NameURLPayload(name, url);
            if (!data.isValid()) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            annotator.setNameURL(data);
            response.status(200);
            return "";
        });

        delete("/names", (request, response) -> {
            annotator.deleteAll();
            response.status(200);
            return "";
        });

        post("/annotate", (request, response) -> {
            String html = request.body();
            if (html == null) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
            String annotatedHtml = annotator.annotate(html);
            response.status(200);
            response.type("text/plain");
            return annotatedHtml;
        });
    }

    private static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            String json = sw.toString();
            return json;
        } catch (IOException e){
            throw new RuntimeException("IOException from a StringWriter");
        }
    }

}



