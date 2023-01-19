package fy;

import fy.data.PomFile;
import fy.data.ProjectPomInfo;
import fy.data.TreeExporter;
import fy.data.TreeNode;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, XmlPullParserException {
        String base = "/Users/fy/Documents/cc2vec/test";
        List<String> projects = Arrays.stream(Objects.requireNonNull(
                new File(base).listFiles(file -> file.isDirectory() && !file.isHidden())))
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());

        for (String project : projects) {
            System.out.println(project);
            process(project);
        }
    }

    public static void process(String project) throws IOException, XmlPullParserException {
        PomFinder finder = new PomFinder(project);
        ProjectPomInfo projectPomInfo = finder.find();
        PomSolver solver = new PomSolver(projectPomInfo);
        solver.solve();
        String outputPath = "/Users/fy/Documents/fyJavaProjects/MyPomParser/src/test/java/samples/dots";
        int count = 1;
        for (TreeNode<PomFile> tree : solver.trees) {
            TreeExporter exporter = new TreeExporter(tree);
            exporter.exportDOT(outputPath, "tree_" + count++);
        }
    }
}
