package org.example.bet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.example.bet.models.exceptions.EventNotFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEventNotFound(EventNotFoundException ex, Model model) {
        log.warn("Событие не найдено: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Событие не найдено");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "404");
        return "error/custom-error";
    }
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFound(NoResourceFoundException ex) {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        log.warn("Некорректные данные: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Некорректные данные");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "400");
        return "error/custom-error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Внутренняя ошибка сервера", ex);
        model.addAttribute("errorTitle", "Внутренняя ошибка сервера");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка. Пожалуйста, попробуйте позже.");
        model.addAttribute("errorCode", "500");
        return "error/custom-error";
    }
}
