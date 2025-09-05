package com.projek.sipatik.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, 
                            @RequestParam(value = "status", required = false) String statusParam) {
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Terjadi kesalahan yang tidak diketahui";
        int statusCode = 500;
        
        // Cek parameter status dari redirect
        if (statusParam != null) {
            try {
                statusCode = Integer.parseInt(statusParam);
            } catch (NumberFormatException e) {
                statusCode = 500;
            }
        } else if (status != null) {
            try {
                statusCode = Integer.valueOf(status.toString());
            } catch (NumberFormatException e) {
                statusCode = 500;
            }
        }
        
        // Debug logging
        System.out.println("Error Controller - Status Code: " + statusCode);
        System.out.println("Error Controller - Status Param: " + statusParam);
        System.out.println("Error Controller - Request URI: " + request.getRequestURI());
        System.out.println("Error Controller - Error Message: " + request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        
        switch (statusCode) {
            case 404:
                errorMessage = "Halaman tidak ditemukan";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("statusCode", 404);
                return "html/error/404";
                
            case 403:
                errorMessage = "Anda tidak memiliki akses ke halaman ini";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("statusCode", 403);
                return "html/error/403";
                
            case 401:
                errorMessage = "Anda perlu login untuk mengakses halaman ini";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("statusCode", 401);
                return "html/error/401";
                
            case 500:
                errorMessage = "Terjadi kesalahan pada server, silakan coba lagi";
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("statusCode", 500);
                return "html/error/500";
                
            default:
                model.addAttribute("errorMessage", errorMessage);
                model.addAttribute("statusCode", statusCode);
                return "html/error/500";
        }
    }
}
