package fy;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AppTest {


    String path = "/Users/fy/Documents/cc2vec/test/spring-security-oauth";

    @Test
    void run_single() throws IOException, XmlPullParserException {
        App app = new App();
        app.process(path);
    }
}