package ecommerce.service;

import ecommerce.dto.UserRegistrationRequest;
import ecommerce.dto.UserResponse;
import ecommerce.entity.User;

import java.util.List;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);
    UserResponse getUserById(Long id);
    UserResponse getUserByUsername(String username);
    UserResponse getUserByEmail(String email);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRegistrationRequest request);
    void deleteUser(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findUserEntityById(Long id);
    User findUserEntityByUsernameOrEmail(String usernameOrEmail);
}