package com.intern.first_task.repository;

import com.intern.first_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}