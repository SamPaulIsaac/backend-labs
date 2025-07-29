package com.sam.relationshiphub.onetomany.service;


import com.sam.relationshiphub.onetomany.dto.request.ProjectDto;
import com.sam.relationshiphub.onetomany.dto.response.ProjectResponseDto;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto createProject(ProjectDto projectDto);
    ProjectResponseDto updateProject(Long id, ProjectDto projectDto);
    ProjectResponseDto patchProject(Long id, ProjectDto projectDto);
    void deleteProject(Long id);
    List<ProjectResponseDto> getAllProjects();
    ProjectResponseDto findById(Long id);
}
