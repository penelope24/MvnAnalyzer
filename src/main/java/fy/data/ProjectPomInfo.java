package fy.data;

import java.util.List;
import java.util.Set;

public class ProjectPomInfo {
    public Set<PomFile> allPomFiles;
    public List<PomFile> firstLevelPomFiles;
    public Set<PomFile> unsolvedPomFiles;

    public ProjectPomInfo(Set<PomFile> allPomFiles, List<PomFile> firstLevelPomFiles) {
        this.allPomFiles = allPomFiles;
        this.firstLevelPomFiles = firstLevelPomFiles;
        allPomFiles.removeAll(firstLevelPomFiles);
        this.unsolvedPomFiles = allPomFiles;
    }
}
