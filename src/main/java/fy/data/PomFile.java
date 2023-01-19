package fy.data;

import org.apache.maven.model.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PomFile {
    public File file;
    public String path;
    public String name;
    public Model model;
    public PomId pomId;
    public String parArtifact;
    public List<String> subArtifacts = new ArrayList<>();


    public PomFile(File file) {
        this.file = file;
        this.path = file.getAbsolutePath();
        this.name = file.getName();
    }

    public void parse(Model model) {
        this.model = model;
        String groupId;
        String artifactId;
        String version;
        boolean has_group_id = model.getGroupId() != null;
        groupId = has_group_id ? model.getGroupId() : model.getParent().getGroupId();
        boolean has_version = model.getVersion() != null;
        version = has_version ? model.getVersion() : model.getParent().getVersion();
        artifactId = model.getArtifactId();
        pomId = new PomId(groupId, artifactId, version);
        if (model.getParent() != null) {
            parArtifact = model.getParent().getArtifactId();
        }
        if (!model.getModules().isEmpty()) {
            subArtifacts.addAll(model.getModules());
        }
    }

    public boolean matchesSubModule(PomId other) {
        if (other.loose_equal(this.pomId)) {
            String tgt = other.artifactId;
            for (String subModuleStr : subArtifacts) {
                // demo : spring-xx-xxx-demo
                if (tgt.contains(subModuleStr)) {
                    return true;
                }
                // xx/demo : demo / spring-xx-xxx-demo
                String[] ss = subModuleStr.split("/");
                String subKeyStr = ss[ss.length-1];
                if (tgt.endsWith(subKeyStr)) {
                    return true;
                }
            }
        }
        return false;
    }


}
