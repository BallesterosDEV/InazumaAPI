package com.ballesteros.api.utils;

import org.springframework.security.core.AuthenticationException;
/**
 * Excepción personalizada para autenticación.
 */
public class CustomAuthenticationException extends AuthenticationException {
    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message el mensaje de error
     */
    public CustomAuthenticationException(String message) {
        super(message);
    }
}
