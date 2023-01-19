package fy;

import org.apache.maven.model.Model;
import org.apache.maven.model.Repository;
import org.apache.maven.model.RepositoryPolicy;

public class MojoGenerator {

    public static void add_spring_snapshots (Model model) {
        Repository repository = new Repository();
        repository.setId("spring-snapshots");
        repository.setName("Spring Snapshots");
        repository.setUrl("https://repo.spring.io/snapshot");
        RepositoryPolicy snapshots = new RepositoryPolicy();
        snapshots.setEnabled(true);
        repository.setSnapshots(snapshots);
    }

    public static void add_spring_milestone(Model model) {
        Repository repository = new Repository();
        repository.setId("spring-milestones");
        repository.setName("Spring Milestones");
        repository.setUrl("https://repo.spring.io/milestone");
        RepositoryPolicy snapshots = new RepositoryPolicy();
        snapshots.setEnabled(false);
    }
}
