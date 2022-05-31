package com.soa.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.soa.shop.model.UserProfile;
import com.soa.shop.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired
    private UserProfileService userProfileService;

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserProfile());
        return "registration.html";
    }

    @PostMapping(value = "/registration")
    public String register(@ModelAttribute("user") UserProfile user, Model model) {
        log.info("saving user:::" + user.getFirstName());
        if (userProfileService.save(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("error","Incorrect fields");
            return "registration.html";
        }
    }
}
