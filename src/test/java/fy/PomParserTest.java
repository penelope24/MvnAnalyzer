package fy;

import org.junit.jupiter.api.Test;

class PomParserTest {
    String path = "/Users/fy/Documents/cc2vec/test/spring-cloud-gcp";

    @Test
    void test () {
        PomFinder finder = new PomFinder(path);
        finder.find();
        System.out.println(finder.levelPomsMap);
    }
}