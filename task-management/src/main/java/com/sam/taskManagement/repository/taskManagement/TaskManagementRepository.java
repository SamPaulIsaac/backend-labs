package com.sam.taskManagement.repository.taskManagement;

import com.sam.taskManagement.domain.taskManagement.TaskManagement;
import com.sam.taskManagement.domain.taskManagement.TaskStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskManagementRepository extends JpaRepository<TaskManagement, Long> {

    List<TaskManagement> findByStatus(TaskStatus status);

    @Query("SELECT tm FROM TaskManagement tm ORDER BY tm.priority ASC")
    List<TaskManagement> sortByPriority();

    @Query("SELECT tm FROM TaskManagement tm ORDER BY tm.dueDate ASC")
    List<TaskManagement> sortByDueDate();

    @Modifying
    @Transactional
    @Query("UPDATE TaskManagement tm SET isDeleted = true, updatedBy = ?2 WHERE id = ?1")
    int delete(Long id, String user);
}