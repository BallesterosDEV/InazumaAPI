package com.ballesteros.api.utils;

/**
 * Excepción para tipos de archivos no válidos.
 */
public class InvalidFileTypeException extends RuntimeException{
    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message el mensaje de error
     */
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
