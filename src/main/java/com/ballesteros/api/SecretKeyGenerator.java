package com.ballesteros.api;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Generador de claves secretas.
 */
public class SecretKeyGenerator {
    /**
     * Método principal para generar y mostrar una clave secreta.
     *
     * @param args los argumentos de la línea de comandos
     * @throws Exception si ocurre un error durante la generación de la clave
     */
    public static void main(String[] args) throws Exception {

        SecureRandom secureRandom = new SecureRandom();

        byte[] secretKeyBytes = new byte[32];
        secureRandom.nextBytes(secretKeyBytes);

        String SECRET_KEY = Base64.getEncoder().encodeToString(secretKeyBytes);

        System.out.println(SECRET_KEY);

    }
}
