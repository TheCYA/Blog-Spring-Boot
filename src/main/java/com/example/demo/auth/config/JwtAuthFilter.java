package com.example.demo.auth.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.auth.TokenBlackList;
import com.example.demo.auth.services.JwtService;
import com.example.demo.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenBlackList tokenBlackList;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService, TokenBlackList tokenBlackList){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenBlackList = tokenBlackList;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String token = getToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Token: " + token);

        try {
            final String username = jwtService.getUsername(token);
            System.out.println("Username: " + username);
            System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
            if(username != null && !isAuthenticated() && !tokenBlackList.containsToken(token)){
                User user = (User)userDetailsService.loadUserByUsername(username);
                if(jwtService.isTokenValid(token, user)){
                    System.out.println("Autoridad: " + user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                    );
                }
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido");
        }
    }

    private String getToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")){ return null; }

        return authHeader.substring(7);
    }

    private boolean isAuthenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
            && !(authentication instanceof AnonymousAuthenticationToken);
    }
}
