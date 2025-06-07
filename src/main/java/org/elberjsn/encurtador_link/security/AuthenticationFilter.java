package org.elberjsn.encurtador_link.security;

import java.io.IOException;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.mapper.UserMapper;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@EnableWebSecurity
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JWTConfig jwtUtil;

    @Autowired
    UserService service;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (filter(request.getRequestURI()) || authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String subjectToken = JWTConfig.getSubjectFromToken(authHeader.substring(7));

        if (subjectToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(subjectToken);

            if (subjectToken != null) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }

    private boolean filter(String uri) {
        String[] accessNotToken = new String[] { "/", "/register", "/login" };

        for (String access : accessNotToken) {
            if (access.equals(uri)) {
                return true;
            }
        }

        return false;
    }

    // Get credentials, check hashed password and create token
    public UserDTO processLoginToken(String email, String pwd) {

        UserDetails user = service.loadUserByUsername(email);
        if (!encoder.matches(pwd, user.getPassword())) {
            throw new BadCredentialsException("Senha Invalida");
        }
        String token = JWTConfig.generateToken((User) user);

        UserDTO userDto = UserMapper.toDTO((User) user, token);

        return userDto;
    }

}
