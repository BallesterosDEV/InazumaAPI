package com.ballesteros.api.security.auth.controller;

import com.ballesteros.api.security.auth.modeldto.LoginRequestDTO;
import com.ballesteros.api.security.auth.modeldto.RegisterRequestDTO;
import com.ballesteros.api.security.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute("loginRequest") LoginRequestDTO request, HttpServletResponse response, Model model) {
        try {
            authService.login(request, response);
            return "redirect:/";
        } catch (AuthenticationException e) {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequestDTO request, BindingResult bindingResult, HttpServletResponse response, Model model) {
        if (bindingResult.hasErrors()) {
            return "register"; // Retorna a la página de registro si hay errores
        }

        if (authService.emailExists(request.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "This email already exists.");
            return "register"; // Retorna a la página de registro si el email ya existe
        }

        authService.register(request, response);
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "login";
    }
}
