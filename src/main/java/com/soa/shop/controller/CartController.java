package com.soa.shop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soa.shop.service.ProductService;
import com.soa.shop.service.UserCartService;

@Controller
public class CartController {

    @Autowired
    private UserCartService userCartService;

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

}
