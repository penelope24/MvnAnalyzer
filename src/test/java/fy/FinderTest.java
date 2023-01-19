package fy;

import fy.data.ProjectPomInfo;
import org.junit.jupiter.api.Test;

public class FinderTest {

    String base = "/Users/fy/Documents/cc2vec/test";
    String project1 = "/Users/fy/Documents/cc2vec/test/spring-security-oauth";
    String project2 = "/Users/fy/Documents/cc2vec/test/spring-hadoop";
    String project3 = "/Users/fy/Documents/cc2vec/test/spring-data-cassandra";

    @Test
    void test () {
        PomFinder finder = new PomFinder(project1);
        ProjectPomInfo projectPomInfo = finder.find();
        projectPomInfo.allPomFiles.forEach(pomFile -> {
            System.out.println(pomFile.path);
        });
    }
}
