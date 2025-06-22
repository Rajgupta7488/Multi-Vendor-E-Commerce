package ecommerce.service.impl;

import ecommerce.dto.LoginRequest;
import ecommerce.dto.LoginResponse;
import ecommerce.entity.User;
import ecommerce.exception.InvalidCredentialsException;
import ecommerce.service.AuthService;
import ecommerce.service.UserService;
import ecommerce.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userService.findUserEntityByUsernameOrEmail(request.getUsernameOrEmail());
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }


        String token = generateSimpleToken(user);
        
        return new LoginResponse(token, userMapper.toResponse(user));
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public boolean validateToken(String token) {

        return token != null && !token.isEmpty();
    }

    @Override
    public String getUsernameFromToken(String token) {

        return "user";
    }

    @Override
    public Long getUserIdFromToken(String token) {

        return 1L;
    }

    @Override
    public boolean isTokenExpired(String token) {

        return false;
    }

    private String generateSimpleToken(User user) {

        return "simple_token_" + user.getId() + "_" + System.currentTimeMillis();
    }
}