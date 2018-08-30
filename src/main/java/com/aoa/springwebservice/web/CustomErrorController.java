package com.aoa.springwebservice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("code", statusCode);
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/notfound";
            }
            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                return "/forbidden";
            }
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() || statusCode == HttpStatus.NOT_IMPLEMENTED.value()) {
                return "/serverError";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
