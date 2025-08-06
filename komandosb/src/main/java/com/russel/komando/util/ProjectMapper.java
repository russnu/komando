package com.russel.komando.util;

import com.russel.komando.entity.ProjectData;
import com.russel.komando.model.Project;

public class ProjectMapper {
    public static Project toModel(ProjectData projectData) {
        if (projectData == null) return null;
        Project project = new Project();
        project.setProjectId(projectData.getProjectId());
        project.setProjectName(projectData.getProjectName());
        project.setProjectStartDate(projectData.getProjectStartDate());
        project.setProjectDueDate(projectData.getProjectDueDate());
        project.setStatus(StatusMapper.toModel(projectData.getStatus()));
        project.setCreatedAt(projectData.getCreatedAt());
        project.setUpdatedAt(projectData.getUpdatedAt());

        return project;
    }

    public static ProjectData toEntity(Project project) {
        if (project == null) return null;
        ProjectData projectData = new ProjectData();
        projectData.setProjectId(project.getProjectId());
        projectData.setProjectName(project.getProjectName());
        projectData.setProjectStartDate(project.getProjectStartDate());
        projectData.setProjectDueDate(project.getProjectDueDate());
        projectData.setStatus(StatusMapper.toEntity(project.getStatus()));
        projectData.setCreatedAt(project.getCreatedAt());
        projectData.setUpdatedAt(project.getUpdatedAt());

        return projectData;
    }
}
