package com.soa.shop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soa.shop.model.ProductDto;
import com.soa.shop.model.UserProfile;
import com.soa.shop.service.ProductService;
import com.soa.shop.service.UserCartService;
import com.soa.shop.service.UserProfileService;

@Controller
public class HomeController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private ProductService productService;

    @RequestMapping({ "/index.html", "/home", "/" })
    public String home(Model model, Principal principal) {
        model.addAttribute("isLoggedUser", principal != null && StringUtils.hasText(principal.getName()));
        model.addAttribute("products", ProductService.products);
        return "index.html";
    }

    @RequestMapping("/portfolio-details/{id}")
    public String details(Model model, Principal principal, @PathVariable("id") Integer productId) {
        ProductDto product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/";
        }
        model.addAttribute("isLoggedUser", principal != null && StringUtils.hasText(principal.getName()));
        model.addAttribute("product", product);
        return "portfolio-details.html";
    }

    @RequestMapping("/cart")
    public String cart(Model model, Principal principal) {
        model.addAttribute("products", userCartService.getProducts(principal == null ? "" : principal.getName()));
        model.addAttribute("totalPrice",
                String.format("%.2f", userCartService.getTotalPrice(principal == null ? "" : principal.getName())));
        return "shopping-cart.html";
    }

    @RequestMapping("/buy")
    public String buyProduct(Model model, Principal principal) {
        String response = userCartService.buyProducts(principal == null ? "" : principal.getName());
        if (StringUtils.hasText(response)) {
            model.addAttribute("error", response);
            model.addAttribute("totalPrice",
                    String.format("%.2f", userCartService.getTotalPrice(principal == null ? "" : principal.getName())));
            return "shopping-cart.html";
        }
        return "redirect:/cart";
    }

    @RequestMapping("/remove")
    public String removeProduct(Model model, @RequestParam("id") Integer productId, Principal principal) {
        userCartService.removeProductFromCart(principal == null ? "" : principal.getName(), productId);
        model.addAttribute("products", ProductService.products);
        return "redirect:/cart";
    }

    @RequestMapping("/add")
    public String addProduct(Model model, @RequestParam("id") Integer productId, Principal principal) {
        userCartService.addProductToCart(principal == null ? "" : principal.getName(), productId);
        return "redirect:/cart";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        UserProfile user = userProfileService.getUserByEmail(principal == null ? "" : principal.getName());
        model.addAttribute("user", user);
        return "profile.html";
    }
}
