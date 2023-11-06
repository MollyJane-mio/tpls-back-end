package com.tpls.depModel;

import java.util.List;

public class DependencyView {
    private String groupId;
    private String artifactId;
    private String version;
    private List<DependencyView> excludes=null;

    public DependencyView() {
    }

    public DependencyView(String groupId, String artifactId, String version, List<DependencyView> excludes) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.excludes = excludes;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DependencyView> getExcludes() {
        return excludes;
    }

    @Override
    public String toString() {
        return "DependencyView{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", excludes=" + excludes +
                '}';
    }

    public void setExcludes(List<DependencyView> excludes) {
        this.excludes = excludes;
    }
}
