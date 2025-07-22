package com.sam.taskManagement.service.taskManagement;

import com.sam.taskManagement.domain.taskManagement.TaskManagement;
import com.sam.taskManagement.domain.taskManagement.TaskStatus;
import com.sam.taskManagement.dto.TaskManagementDto;
import com.sam.taskManagement.exception.InvalidTaskStatusRequestException;
import com.sam.taskManagement.exception.InvalidTaskStatusTransitionException;
import com.sam.taskManagement.exception.TaskNotFoundException;
import com.sam.taskManagement.repository.taskManagement.TaskManagementRepository;
import com.sam.taskManagement.utils.TaskManagementUtils;
import com.sam.taskManagement.utils.TaskStatusValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskManagementService {

    private TaskManagementRepository taskManagementRepository;
    private TaskStatusValidator taskStatusValidator;

    public Page<TaskManagement> getAllTasks(Pageable pageable) {
        return taskManagementRepository.findAll(pageable);
    }

    public List<TaskManagement> sortByPriority() {
        return taskManagementRepository.sortByPriority();
    }

    public List<TaskManagement> sortByDueDate() {
        return taskManagementRepository.sortByDueDate();
    }

    public List<TaskManagement> filterByStatus(TaskStatus status) {
        return taskManagementRepository.findByStatus(status);
    }

    public Long createTask(TaskManagementDto taskManagementDto, Authentication authentication) {
        String currentUser = authentication.getName();
        if (taskManagementDto.getStatus() != TaskStatus.OPEN)
            throw new InvalidTaskStatusRequestException("Task status should be OPEN during the task creation.");

        TaskManagement savedTaskManagement = taskManagementRepository.saveAndFlush(
                TaskManagement.builder()
                        .name(taskManagementDto.getName())
                        .description(taskManagementDto.getDescription())
                        .status(taskManagementDto.getStatus())
                        .comment(taskManagementDto.getComment())
                        .priority(taskManagementDto.getPriority())
                        .dueDate(taskManagementDto.getDate())
                        .createdBy(currentUser)
                        .updatedBy(currentUser)
                        .createdAt(TaskManagementUtils.getCurrentDateTime())
                        .updatedAt(TaskManagementUtils.getCurrentDateTime())
                        .build());

        Long savedTaskId = savedTaskManagement.getId();
        log.info("Created task with ID: {} ", savedTaskId);
        return savedTaskId;
    }

    public Long updateTask(Long id, TaskManagementDto taskManagementDto, Authentication authentication) {
        String currentUser = authentication.getName();
        TaskManagement taskManagement = taskManagementRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Requested Task id: " + id + " not found."));

        if (!taskStatusValidator.isTaskStatusValidTransition(taskManagement.getStatus(), taskManagementDto.getStatus(),
                (Collection<GrantedAuthority>) authentication.getAuthorities())) {
            throw new InvalidTaskStatusTransitionException(
                    String.format("Transition from '%s' to '%s' is not allowed.", taskManagement.getStatus(), taskManagementDto.getStatus()));
        }
        TaskManagement updatedTaskManagement = taskManagementRepository.saveAndFlush(
                TaskManagement.builder()
                        .id(taskManagement.getId())
                        .name(taskManagementDto.getName())
                        .description(taskManagementDto.getDescription())
                        .status(taskManagementDto.getStatus())
                        .comment(taskManagementDto.getComment())
                        .priority(taskManagementDto.getPriority())
                        .dueDate(taskManagementDto.getDate())
                        .createdBy(taskManagement.getCreatedBy())
                        .createdAt(taskManagement.getCreatedAt())
                        .updatedBy(currentUser)
                        .updatedAt(TaskManagementUtils.getCurrentDateTime())
                        .build());
        Long updatedTaskId = updatedTaskManagement.getId();
        log.info("Updated task with ID: {} ", updatedTaskId);
        return updatedTaskId;
    }

    public void deleteTask(Long id, Authentication authentication) {
        TaskManagement taskManagement = taskManagementRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Requested Task id: " + id + " does not exist. "));
        int rowsAffected = taskManagementRepository.delete(taskManagement.getId(), authentication.getName());
        log.info("No of rows affected after deletion is: {}", rowsAffected);
    }
}