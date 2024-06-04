package com.ballesteros.api.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Utilidad para validaciones de archivos.
 */
public class Validations {

    /**
     * Verifica si un archivo de imagen tiene una extensión válida.
     *
     * @param fileName el nombre del archivo a verificar
     * @return true si el archivo tiene una extensión válida, false en caso contrario
     */
    public boolean isValidImage(String fileName) {
        List<String> validExtensions = Arrays.asList("png", "jpeg", "jpg", "webp");
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return validExtensions.contains(fileExtension);
    }
}
