package com.iangabrieldev.spring_boot_auth.jwt;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iangabrieldev.spring_boot_auth.config.SecurityConfig;
import com.iangabrieldev.spring_boot_auth.expection.ApiException;
import com.iangabrieldev.spring_boot_auth.user.UserDetailsImpl;
import com.iangabrieldev.spring_boot_auth.user.UserModel;
import com.iangabrieldev.spring_boot_auth.user.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired private JwtService jwtService;
    @Autowired private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(!isEndpointPublic(request)) {
            String token = request.getHeader("Authorization");
            if(isTokenMalformatted(token)) {
                throw new ApiException("Token malformatted", HttpStatus.UNAUTHORIZED);
            }
            token = token.replace("Bearer ", "");
            String tokenSubject = jwtService.getSubjectFromToken(token);
            logger.info(tokenSubject.equals("iangz"));
            UserModel user = userService.findUserByUsername(tokenSubject);
            UserDetails userDetails = new UserDetailsImpl(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isTokenMalformatted(String token) {
        return token == null || !token.startsWith("Bearer ");
    }


    private boolean isEndpointPublic(HttpServletRequest request) {
        return Arrays.asList(SecurityConfig.PUBLIC_ENDPOINTS)
            .stream()
            .anyMatch(endpoint -> AntPathRequestMatcher.antMatcher(endpoint).matches(request));
    }
    
}
