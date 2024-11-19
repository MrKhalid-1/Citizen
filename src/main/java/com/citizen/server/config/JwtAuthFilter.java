package com.citizen.server.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.citizen.server.jwt.JwtUtil;
import com.citizen.server.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String JWT_HEADER = "jwt-token";

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public JwtAuthFilter(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader(JWT_HEADER);

        if (StringUtils.isNotBlank(token)) {
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("Got token=" + token);
                }
                DecodedJWT decodedJWT = JwtUtil.validateToken(token);
                if (decodedJWT != null) {
                    String username = JwtUtil.getUsername(decodedJWT);
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    if (userDetails == null) {
                        throw new UsernameNotFoundException("User Not found");
                    }
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.warn("Got null decodedJWT for token=" + token);
                }
            } catch (Exception e) {
                // Log error if token cannot be validated
                logger.error("Failed to auth using jwtToken=" + token, e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
