package fy;

import fy.data.PomFile;
import fy.data.ProjectPomInfo;
import fy.utils.file.SubFileFinder;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PomFinder {
    String project;
    public Map<Integer, List<PomFile>> levelPomsMap = new LinkedHashMap<>();
    static List<String> specialSymbols = Arrays.asList(
            "tests", "samples", "docs",
            "test", "doc"
    );

    public PomFinder(String project) {
        this.project = project;
    }

    private static class FilterImpl implements SubFileFinder.Filter {
        @Override
        public boolean interested(int level, String path, File file) {
            String[] ss = path.split("/");
            List<String> sList = Arrays.asList(ss);
            String belongingPath = sList.get(sList.size()-2);
//            System.out.println(path);
//            System.out.println(belongingPath);
//            System.out.println("-------------");
            if (belongingPath.startsWith(".") || belongingPath.startsWith("_")) {
                return false;
            }
//            if (sList.stream().anyMatch(s -> s.startsWith(".")
//                    || specialSymbols.contains(s.toLowerCase()))) {
//                return false;
//            }
            return file.getName().equals("pom.xml");
        }
    }

    private class HandlerImpl implements SubFileFinder.FileHandler {
        @Override
        public void handle(int level, String path, File file) {
            levelPomsMap.computeIfAbsent(level, k -> new ArrayList<>()).add(new PomFile(file));
        }
    }

    public ProjectPomInfo find() {
        FilterImpl filter = new FilterImpl();
        HandlerImpl handler = new HandlerImpl();
        new SubFileFinder(filter, handler)
                .explore(new File(project));
        Set<PomFile> allPoms = levelPomsMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        int min = Collections.min(levelPomsMap.keySet());
        List<PomFile> firstLevelPoms = levelPomsMap.getOrDefault(min, null);
        return new ProjectPomInfo(allPoms, firstLevelPoms);
    }

}
