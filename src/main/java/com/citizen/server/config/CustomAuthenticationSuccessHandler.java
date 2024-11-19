package com.citizen.server.config;

import java.io.IOException;

import com.citizen.server.context.UserContextUtil;
import com.citizen.server.jwt.JwtUtil;
import com.citizen.server.mgr.VUserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        // super.onAuthenticationSuccess(request, response, authentication);

        response.setStatus(HttpServletResponse.SC_OK);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        VUserProfile vUserProfile = UserContextUtil.getUserProfile();
        if (vUserProfile == null) {
            throw new UsernameNotFoundException("User not match");

        }
        String jwtToken = JwtUtil.generateToken(vUserProfile);
        vUserProfile.setJwtToken(jwtToken);
        response.getWriter().write(mapper.writeValueAsString(vUserProfile));
        clearAuthenticationAttributes(request);
    }
}