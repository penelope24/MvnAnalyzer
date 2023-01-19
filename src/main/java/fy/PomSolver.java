package fy;

import fy.data.PomFile;
import fy.data.PomId;
import fy.data.ProjectPomInfo;
import fy.data.TreeNode;
import org.apache.maven.model.Model;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PomSolver {

    Set<PomFile> unresolved;
    List<PomFile> startPoints;
    List<TreeNode<PomFile>> trees = new ArrayList<>();

    public PomSolver(Set<PomFile> unresolved, List<PomFile> startPoints) {
        this.unresolved = unresolved;
        this.startPoints = startPoints;
    }

    public PomSolver(ProjectPomInfo projectPomInfo) {
        this.unresolved = projectPomInfo.unsolvedPomFiles;
        this.startPoints = projectPomInfo.firstLevelPomFiles;
    }

    public void parse() {
        startPoints.forEach(PomParser::read);
        unresolved.forEach(PomParser::read);
    }

    public void solve() throws IOException {
        parse();
        for (PomFile startPoint : startPoints) {
            TreeNode<PomFile> tree = t(startPoint);
            trees.add(tree);
        }
        List<PomFile> worklist = new ArrayList<>(unresolved);
        for (PomFile pomFile : worklist) {
            TreeNode<PomFile> tree = t(pomFile);
            if (tree.size() > 1) {
                trees.add(tree);
            }
        }
        worklist = new ArrayList<>(unresolved);
        for (PomFile pomFile : worklist) {
            trees.add(new TreeNode<>(pomFile));
        }
    }

    private TreeNode<PomFile> t (PomFile startPoint) throws IOException {
        Deque<TreeNode<PomFile>> deque = new ArrayDeque<>();
        TreeNode<PomFile> root = new TreeNode<>(startPoint);
        deque.add(root);
        while (!deque.isEmpty()) {
            TreeNode<PomFile> curr = deque.pop();
            List<PomFile> subModules = unresolved.stream()
                    .filter(pomFile -> curr.data.matchesSubModule(pomFile.pomId))
                    .collect(Collectors.toList());
            subModules.forEach(pomFile -> {
                unresolved.remove(pomFile);
                TreeNode<PomFile> child = curr.addChild(pomFile);
                deque.add(child);
            });
        }
        return root;
    }
}
