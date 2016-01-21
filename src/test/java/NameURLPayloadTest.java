import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Doron on 1/20/16.
 */
public class NameURLPayloadTest {


    @Test
    public void testInvalidName() {
        String name = "aa.aa";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        assertFalse(payload.isValid());
    }

    @Test
    public void testInvalidURL() {
        String name = "alex";
        String url = "asdf://www.example.com/spacehere.html";
        NameURLPayload payload = new NameURLPayload(name, url);
        assertFalse(payload.isValid());
    }

    @Test
    public void testValidPayload() {
        String name = "alex";
        String url = "http://alex.com";
        NameURLPayload payload = new NameURLPayload(name, url);
        assertTrue(payload.isValid());
    }
}