package com.sam.taskManagement.repository.userAuthAndRegistration;


import com.sam.taskManagement.domain.userAuthAndRegistration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
