package org.elberjsn.encurtador_link.security;

import java.io.IOException;
import java.util.Arrays;

import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.elberjsn.encurtador_link.security.userDatails.UserDetailsImplments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTConfig config;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        if (checkEndpoint(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = config.getSubjectFromToken(token);
                User user = userRepository.findByEmail(subject).get();
                UserDetailsImplments userDetails = new UserDetailsImplments(user);
                var authenticator = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticator);
            }

        }else{
            throw new RuntimeException("O token está ausente.");
        }
        filterChain.doFilter(request, response);

    }

    private String recoveryToken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth != null) {
            return auth.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkEndpoint(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !Arrays.asList(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(uri);
    }

}
