package com.sam.relationshiphub.onetomany.controller;

import com.sam.relationshiphub.onetomany.dto.request.ProjectDto;
import com.sam.relationshiphub.onetomany.dto.response.ProjectResponseDto;
import com.sam.relationshiphub.onetomany.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDto> create(@RequestBody ProjectDto dto) {
        log.info("Creating new project: {}", dto);
        return ResponseEntity.ok(projectService.createProject(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> update(@PathVariable Long id, @RequestBody ProjectDto dto) {
        log.info("Updating project with id {}: {}", id, dto);
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> patch(@PathVariable Long id, @RequestBody ProjectDto dto) {
        log.info("Patching project with id {}: {}", id, dto);
        return ResponseEntity.ok(projectService.patchProject(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting project with id {}", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponseDto>> getAll() {
        log.info("Fetching all projects.");
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("{id}")
    public ResponseEntity<ProjectResponseDto> findById(@PathVariable Long id) {
        log.info("Fetching the requested project: {}",id);
        return ResponseEntity.ok(projectService.findById(id));
    }
}
