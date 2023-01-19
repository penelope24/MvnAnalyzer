package fy.data;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TreeExporter {
    TreeNode<PomFile> root;

    public TreeExporter(TreeNode<PomFile> root) {
        this.root = root;
    }

    public void exportDOT(String outputDir, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        if (outputDir.endsWith(File.separator)) {
            outputDir += File.separator;
        }
        File outputFile = new File(outputDir);
        if (!outputFile.exists()) {
            outputFile.mkdir();
        }
        String pathName = outputDir + filename + ".dot";
        try (PrintWriter dot = new PrintWriter(pathName, "UTF-8")) {
            dot.println("digraph " + filename + "{");
            dot.println("    //tree nodes");
            Iterator<TreeNode<PomFile>> iterator = new TreeNodeIter<>(root);
            int nodeCounter = 1;
            Map<TreeNode<PomFile>, String> nodeStringMap = new HashMap<>();
            while (iterator.hasNext()) {
                TreeNode<PomFile> node = iterator.next();
                String name = "v" + nodeCounter++;
                nodeStringMap.put(node, name);
                StringBuilder label = new StringBuilder("    [label=\"");
                String nodeStr = node.data.pomId.artifactId;
                label.append(nodeStr).append("\"];");
                dot.println("    " + name + label.toString());
            }
            dot.println("    //tree edges");
            Iterator<TreeNode<PomFile>> iterator1 = new TreeNodeIter<>(root);
            while (iterator1.hasNext()) {
                TreeNode<PomFile> node = iterator1.next();
                node.children.forEach(child -> {
                    String src = nodeStringMap.get(node);
                    String tgt = nodeStringMap.get(child);
                    dot.println("    " + src + " -> " + tgt +
                            "  [arrowhead=empty, color=gray, style=dashed];");
                });
            }
            dot.println("    // end-of-graph\n}");
        }
    }
}
