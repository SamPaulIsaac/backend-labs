package com.sam.relationshiphub.onetomany.service.impl;

import com.sam.relationshiphub.onetomany.dto.request.ProjectDto;
import com.sam.relationshiphub.onetomany.dto.request.TaskDto;
import com.sam.relationshiphub.onetomany.dto.response.ProjectResponseDto;
import com.sam.relationshiphub.onetomany.dto.response.TaskResponseDto;
import com.sam.relationshiphub.onetomany.entity.Project;
import com.sam.relationshiphub.onetomany.entity.Task;
import com.sam.relationshiphub.onetomany.repository.ProjectRepository;
import com.sam.relationshiphub.onetomany.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponseDto createProject(ProjectDto dto) {
        Project project = Project.builder()
                .title(dto.getTitle())
                .deadLine(dto.getDeadLine())
                .tasks(dto.getTasks().stream()
                        .map(this::mapToEntity)
                        .collect(Collectors.toList()))
                .build();

        Project saved = projectRepository.saveAndFlush(project);
        return mapToResponse(saved);
    }

    @Override
    public ProjectResponseDto updateProject(Long id, ProjectDto dto) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDeadLine(dto.getDeadLine());
        existing.setTasks(dto.getTasks().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList()));

        Project updated = projectRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public ProjectResponseDto patchProject(Long id, ProjectDto dto) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));

        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDeadLine() != null) existing.setDeadLine(dto.getDeadLine());
        if (dto.getTasks() != null && !dto.getTasks().isEmpty()) {
            existing.setTasks(dto.getTasks().stream()
                    .map(this::mapToEntity)
                    .collect(Collectors.toList()));
        }

        Project updated = projectRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponseDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Project not found."));
        return mapToResponse(project);

    }

    // Mapper: TaskDto -> Task Entity
    private Task mapToEntity(TaskDto dto) {
        return Task.builder()
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }

    // Mapper: Project Entity -> ProjectResponseDto
    private ProjectResponseDto mapToResponse(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .title(project.getTitle())
                .deadLine(project.getDeadLine())
                .tasks(project.getTasks().stream()
                        .map(task -> TaskResponseDto.builder()
                                .id(task.getId())
                                .description(task.getDescription())
                                .status(task.getStatus().name())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
