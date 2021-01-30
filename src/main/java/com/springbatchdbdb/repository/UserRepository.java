package com.springbatchdbdb.repository;

import com.springbatchdbdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
