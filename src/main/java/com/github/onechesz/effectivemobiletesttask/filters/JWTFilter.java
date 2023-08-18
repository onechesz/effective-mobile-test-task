package com.github.onechesz.effectivemobiletesttask.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.github.onechesz.effectivemobiletesttask.secutiry.JWTUtil;
import com.github.onechesz.effectivemobiletesttask.services.UserDetailsService;
import com.github.onechesz.effectivemobiletesttask.utils.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JWTFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);

            if (jwt.isBlank()) {
                ExceptionResponse exceptionResponse = new ExceptionResponse("Пустой JWT", System.currentTimeMillis());

                request.setAttribute("exception", exceptionResponse);
            } else
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.validateTokenAndRetrieveClaim(jwt));
                    SecurityContext securityContext = SecurityContextHolder.getContext();

                    if (securityContext.getAuthentication() == null)
                        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
                } catch (JWTVerificationException jwtVerificationException) {
                    ExceptionResponse exceptionResponse = new ExceptionResponse("Неверный JWT", System.currentTimeMillis());

                    request.setAttribute("exception", exceptionResponse);
                }
        } else {
            ExceptionResponse exceptionResponse = new ExceptionResponse("Аутентификация отсутствует", System.currentTimeMillis());

            request.setAttribute("exception", exceptionResponse);
        }

        filterChain.doFilter(request, response);
    }
}
