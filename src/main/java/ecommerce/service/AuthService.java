package ecommerce.service;

import ecommerce.dto.LoginRequest;
import ecommerce.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void logout(String token);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    Long getUserIdFromToken(String token);
    boolean isTokenExpired(String token);
}