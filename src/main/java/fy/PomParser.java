package fy;

import fy.data.PomFile;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.IOException;

public class PomParser {
    public static void read (PomFile pomFile) {
       MavenXpp3Reader reader = new MavenXpp3Reader();
       try {
           Model model = reader.read(new FileReader(pomFile.path));
           pomFile.parse(model);
       } catch (XmlPullParserException | IOException e ) {
           e.printStackTrace();
       }
   }

}
