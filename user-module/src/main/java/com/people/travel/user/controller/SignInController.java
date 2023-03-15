package com.people.travel.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class SignInController {

    @GetMapping("/login")
    public String login(Model model, Principal principal){

        return "pages/index";
    }
}
