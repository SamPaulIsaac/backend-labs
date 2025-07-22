package com.sam.taskManagement.utils;

import com.sam.taskManagement.domain.taskManagement.TaskStatus;
import com.sam.taskManagement.domain.userAuthAndRegistration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskStatusValidator {

    public boolean isTaskStatusValidTransition(TaskStatus currentStatus,
                                               TaskStatus statusToBeUpdated,
                                               Collection<GrantedAuthority> grantedAuthorities) {
        Set<String> roles = fetchRolesFromAuthorities(grantedAuthorities);
        return isValidTransition(currentStatus, statusToBeUpdated, roles);
    }

    private boolean isValidTransition(TaskStatus currentStatus, TaskStatus statusToBeUpdated, Set<String> roles) {
        switch (currentStatus) {
            case OPEN -> {
                if (roles.contains(Role.PRODUCT_OWNER.name()) || roles.contains(Role.MANAGER.name()))
                    return Objects.equals(TaskStatus.IN_PROGRESS, statusToBeUpdated);
            }
            case IN_PROGRESS -> {
                if (roles.contains(Role.DEVELOPER.name()))
                    return Objects.equals(TaskStatus.WAITING_FOR_VALIDATION, statusToBeUpdated);
            }
            case WAITING_FOR_VALIDATION -> {
                if (roles.contains(Role.QA.name()))
                    return Objects.equals(TaskStatus.VALIDATION, statusToBeUpdated);
            }
            case VALIDATION -> {
                if (roles.contains(Role.QA.name()))
                    return Objects.equals(TaskStatus.REVIEW, statusToBeUpdated);
            }
            case REVIEW -> {
                if (roles.contains(Role.PRODUCT_OWNER.name()))
                    return Objects.equals(TaskStatus.DONE, statusToBeUpdated);
            }
            case DONE -> {
                if (roles.contains(Role.PRODUCT_OWNER.name()))
                    return true;
            }
        }
        return false;
    }

    private Set<String> fetchRolesFromAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        return grantedAuthorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .peek(System.out::println)
                .map(role -> role.replace("ROLE_", ""))
                .collect(Collectors.toSet());
    }
}
