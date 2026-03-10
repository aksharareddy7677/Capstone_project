package com.smartclassroom.Smart_Classroom.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String role=authentication.getAuthorities()
                .iterator()
                .next().getAuthority();

        if(role.equals("ROLE_ADMIN")){
            response.sendRedirect("/api/admin/dashboard");
        } else if (role.equals("ROLE_TEACHER")) {
            response.sendRedirect("/api/teacher/dashboard");
        }
        else{
            response.sendRedirect("/api/student/dashboard");
        }
    }
}
