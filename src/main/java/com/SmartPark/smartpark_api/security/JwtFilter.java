package com.SmartPark.smartpark_api.security;

import com.SmartPark.smartpark_api.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

    String path = request.getRequestURI();

    if (path.startsWith("/auth/login")) {
        filterChain.doFilter(request, response);
        return;
    }
    String authHeader = request.getHeader("Authorization");

    // Check if header exists and starts with Bearer
    if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7); // remove "Bearer "

            // Validate token
            if (!jwtUtil.validateToken(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
        }
        // Continue request if valid
        filterChain.doFilter(request, response);
    }
}