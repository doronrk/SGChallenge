import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Doron on 1/20/16.
 */
public class AnnotatorTest {


    @Test
    public void testSetOne() {
        Annotator m = new Annotator();
        String name = "alex";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        m.setNameURL(payload);
        String urlRetrieved = m.getUrl(name);
        assertTrue(urlRetrieved.equals(url));
    }

    @Test
    public void testOverwrite() {
        Annotator m = new Annotator();
        String name = "alex";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        m.setNameURL(payload);
        String url2 = "http://alex2.com";
        payload = new NameURLPayload(name, url2);
        m.setNameURL(payload);
        String urlRetrieved = m.getUrl(name);
        assertTrue(urlRetrieved.equals(url2));
    }

    @Test
    public void testTestMissing() {
        Annotator m = new Annotator();
        String urlRetrieved = m.getUrl("alex");
        assertTrue(urlRetrieved == null);
    }

    @Test
    public void testInsertInvalid() {
        Annotator m = new Annotator();
        String name = "alex";
        String url = "asdf://www.example.com/spacehere.html";
        NameURLPayload payload = new NameURLPayload(name, url);
        m.setNameURL(payload);
        String urlRetrieved = m.getUrl("alex");
        assertTrue(urlRetrieved == null);
    }

    @Test
    public void testInsertInvalidAfterValid() {
        Annotator m = new Annotator();
        String name = "alex";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        m.setNameURL(payload);

        // insert invalid
        String urlInvalid = "asdf://www.example.com/spacehere.html";
        NameURLPayload payloadInvalid = new NameURLPayload(name, urlInvalid);
        m.setNameURL(payloadInvalid);

        String urlRetrieved = m.getUrl("alex");
        assertTrue(urlRetrieved.equals(url));
    }

    @Test
    public void testInsertOneHundredThousand() {
        Annotator m = new Annotator();
        for (int i = 0; i < 100000; i++) {
            String name = "alex" + i;
            String url = "http://" + name + ".com";
            NameURLPayload payload = new NameURLPayload(name, url);
            m.setNameURL(payload);
        }

        for (int i = 0; i < 100000; i++) {
            String nameExpected = "alex" + i;
            String urlExpected = "http://" + nameExpected + ".com";
            String urlRetrieved = m.getUrl(nameExpected);
            assertTrue(urlRetrieved.equals(urlExpected));
        }
    }

    @Test
    public void testAnnotate() {
        Annotator m = new Annotator();
        String name = "Alex";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        m.setNameURL(payload);
        //String html = "Alex Alexander <a href=\"http://foo.com\" data-Bo=\"Bo\">Some sentence about Bo</a>";
        String html = "<div data-alex=\"alex\">alex</div>";
        String htmlOut = m.annotate(html);
        System.out.println();
        System.out.println(htmlOut);
        assertTrue(htmlOut != null);
    }
}