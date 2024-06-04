package com.ballesteros.api.security.auth.service;

import com.ballesteros.api.persistence.models.RolModel;
import com.ballesteros.api.persistence.models.UserModel;
import com.ballesteros.api.persistence.repositories.RolRepository;
import com.ballesteros.api.persistence.repositories.UserRepository;
import com.ballesteros.api.security.auth.modeldto.AuthResponseDTO;
import com.ballesteros.api.security.auth.modeldto.LoginRequestDTO;
import com.ballesteros.api.security.auth.modeldto.RegisterRequestDTO;
import com.ballesteros.api.utils.CustomAuthenticationException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request, HttpServletResponse response) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserModel user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtService.getToken(user);

            // Set token in cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            System.out.println("Login successful, token generated: " + token);
            return new AuthResponseDTO(token);
        } catch (BadCredentialsException e) {
            throw new CustomAuthenticationException("Invalid username or password");
        }
    }

    public AuthResponseDTO register(RegisterRequestDTO request, HttpServletResponse response) {
        Optional<RolModel> rol = rolRepository.findById(2L);
        if (rol.isPresent()) {
            UserModel user = new UserModel();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRol(rol.get());
            userRepository.save(user);

            String token = jwtService.getToken(user);

            // Set token in cookie
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);
            System.out.println("User registered successfully, token generated: " + token);
            return new AuthResponseDTO(token);
        } else {
            System.out.println("Role not found");
            return new AuthResponseDTO("");
        }
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void save(UserModel user) {
        userRepository.save(user);
    }
}