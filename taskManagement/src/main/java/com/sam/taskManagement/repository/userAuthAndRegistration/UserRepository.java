package com.sam.taskManagement.repository.userAuthAndRegistration;


import com.sam.taskManagement.domain.userAuthAndRegistration.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
