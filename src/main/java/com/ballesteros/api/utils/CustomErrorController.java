package com.ballesteros.api.utils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador personalizado para manejar errores.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Maneja los errores de la aplicación.
     *
     * @param request la solicitud HTTP
     * @param model el modelo para pasar datos a la vista
     * @return el nombre de la vista de error
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorMessage", "You do not have permission to access this page.");
            } else {
                model.addAttribute("errorMessage", "An unexpected error occurred. Error code: " + statusCode);
            }
        }
        return "error";
    }

    /**
     * Obtiene la ruta de error.
     *
     * @return la ruta de error
     */
    public String getErrorPath() {
        return "/error";
    }
}
