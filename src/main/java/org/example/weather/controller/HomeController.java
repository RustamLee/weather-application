package org.example.weather.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        String username = (String) request.getAttribute("username");
        System.out.println("===> HomeController username attribute: " + username);

        if (username != null) {
            model.addAttribute("username", username);
        }
        return "home";
    }

}
