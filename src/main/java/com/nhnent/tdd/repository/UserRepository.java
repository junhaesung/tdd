package com.nhnent.tdd.repository;

import com.nhnent.tdd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author hanjin lee
 */
public interface UserRepository extends JpaRepository<User, Serializable> {}
