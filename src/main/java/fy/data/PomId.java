package fy.data;

import java.util.Objects;

public class PomId {
    public boolean is_valid = false;
    public String groupId;
    public String artifactId;
    public String version;
    public String qualifiedName;

    public PomId(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        if (groupId != null && artifactId != null && version != null) {
            is_valid = true;
            this.qualifiedName = groupId + "/" + artifactId + "/" + version;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final PomId other = (PomId) obj;
        return Objects.equals(this.groupId, other.groupId) && Objects.equals(this.artifactId, other.artifactId)
                && Objects.equals(this.version, other.version);
    }

    public boolean loose_equal(Object obj) {
        if (equals(obj)) {
            return true;
        }
        else {
            final PomId other = (PomId) obj;
            return Objects.equals(this.groupId, other.groupId)
                    && Objects.equals(this.version, other.version);
        }
    }

}
