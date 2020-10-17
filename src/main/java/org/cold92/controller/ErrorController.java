package org.cold92.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 处理错误页的Servlet
 */
@Controller
public class ErrorController {

    @GetMapping("/error")
    public String error() {
        return "error/error";
    }
}
