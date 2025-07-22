package com.sam.taskManagement.controller.taskManagement;

import com.sam.taskManagement.domain.taskManagement.TaskManagement;
import com.sam.taskManagement.domain.taskManagement.TaskStatus;
import com.sam.taskManagement.dto.TaskManagementDto;
import com.sam.taskManagement.service.taskManagement.TaskManagementService;
import com.sam.taskManagement.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
@Slf4j
public class TaskManagementController {

    private TaskManagementService taskManagementService;
    private JwtUtil jwtUtil;

    @PostMapping
    public Long createTask(@RequestBody TaskManagementDto taskManagementDto, Authentication authentication) {
        log.info("Request received to created task: {}", taskManagementDto);
        return taskManagementService.createTask(taskManagementDto, authentication);
    }

    @PutMapping("{id}")
    public Long updateTask(@PathVariable Long id, @RequestBody TaskManagementDto taskManagementDto,
                           Authentication authentication) {
        log.info("Request received to update task for id:{} with request body: {}", id, taskManagementDto);
        return taskManagementService.updateTask(id, taskManagementDto, authentication);
    }

    @GetMapping("/getAll")
    public Page<TaskManagement> getAllTasks(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        log.info("Request received to get all tasks.");
        return taskManagementService.getAllTasks(pageable);
    }

    @GetMapping("/filterByStatus")
    public List<TaskManagement> filterByStatus(@RequestParam TaskStatus status) {
        log.info("Request received to filter tasks by status: {}.", status);
        return taskManagementService.filterByStatus(status);
    }

    @GetMapping("/sortByPriority")
    public List<TaskManagement> sortByPriority() {
        log.info("Request received to get all tasks sorted by priority.");
        return taskManagementService.sortByPriority();
    }

    @GetMapping("/sortByDueDate")
    public List<TaskManagement> sortByDueDate() {
        log.info("Request received to get all tasks sorted by due date.");
        return taskManagementService.sortByDueDate();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Authentication authentication) {
        log.info("Request received to delete task id: {}.", id);
        taskManagementService.deleteTask(id, authentication);
        log.info("Task:{} deleted successfully.", id);
        return ResponseEntity.ok("Deleted task id: " + id + " successfully.");
    }

}