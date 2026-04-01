package com.SmartPark.smartpark_api.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SmartPark.smartpark_api.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil = new JwtUtil();

    private final String USERNAME = "admin";
    private final String PASSWORD = "password";

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {

            String token = jwtUtil.generateToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return response;
        }

        throw new RuntimeException("Invalid username or password");
    }
}
