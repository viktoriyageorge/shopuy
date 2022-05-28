package com.soa.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    // Login form
    @RequestMapping({ "/index.html","/home", "/" })
    public String home() {
        return "index.html";
    }

    // Login form
    @RequestMapping("/portfolio-details.html")
    public String details() {
        return "portfolio-details.html";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "shopping-cart.html";
    }
}
