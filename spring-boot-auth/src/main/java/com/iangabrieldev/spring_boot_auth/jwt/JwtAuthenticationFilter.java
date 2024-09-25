package com.iangabrieldev.spring_boot_auth.jwt;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iangabrieldev.spring_boot_auth.config.SecurityConfig;
import com.iangabrieldev.spring_boot_auth.expection.ApiException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired private JwtService jwtService;
    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(!isEndpointPublic(request)) {
            String token = request.getHeader("Authorization");
            if(isTokenMalformatted(token)) {
                throw new ApiException("Token malformatted", HttpStatus.UNAUTHORIZED);
            }
            token.replace("Bearer ", "");
            String tokenSubject = jwtService.getSubjectFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(tokenSubject);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenMalformatted(String token) {
        return token == null || !token.startsWith("Bearer ");
    }


    private boolean isEndpointPublic(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.PUBLIC_ENDPOINTS).contains(requestUri);
    }
    
}
