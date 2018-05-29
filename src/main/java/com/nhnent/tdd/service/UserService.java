package com.nhnent.tdd.service;

import com.nhnent.tdd.entity.User;
import com.nhnent.tdd.exception.DuplicatedUserException;
import com.nhnent.tdd.exception.NonMatchingPasswordConfirmException;
import com.nhnent.tdd.repository.UserRepository;

import java.util.Optional;

/**
 * @author hanjin lee
 */
class UserService {

    private UserRepository userRepository;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String id, String password, String passwordConfirm) {
        validate(id, password, passwordConfirm);
        assertPasswordMatching(password, passwordConfirm);
        throwExIfDuplicatedUserId(id);
        return createUser(id, password);
    }

    private void validate(String id, String password, String passwordConfirm) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void assertPasswordMatching(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new NonMatchingPasswordConfirmException();
        }
    }

    private void throwExIfDuplicatedUserId(String id) {
        findById(id).ifPresent(s -> {
            throw new DuplicatedUserException();
        });
    }

    private User createUser(String id, String password) {
        User user = new User(id, password);
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    private Optional<User> findById(String id) {

        return userRepository.findById(id);
    }
}
