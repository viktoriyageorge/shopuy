package com.soa.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // Login form
    @RequestMapping("/index.html")
    public String home() {
        return "index.html";
    }
}
