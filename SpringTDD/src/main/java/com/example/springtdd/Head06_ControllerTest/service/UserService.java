package com.example.springtdd.Head06_ControllerTest.service;

import com.example.springtdd.Head06_ControllerTest.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> searchUsers(String name, int minAge, int maxAge);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    UserResponse getUserById(Long id);
}
