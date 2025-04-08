package org.example.weather.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.weather.model.User;
import org.example.weather.session.SessionManager;
import org.example.weather.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        try {
            boolean success = userService.registerUser(username, password);
            if (!success) {
                model.addAttribute("error", "Имя пользователя уже занято");
                return "register";
            }
            return "redirect:/login"; // после успешной регистрации отправляем на логин
        } catch (Exception e) {
            e.printStackTrace(); // Выводим стек ошибки в консоль
            model.addAttribute("error", "Произошла ошибка при регистрации");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            Model model) {
        User user = userService.authenticateUser(username, password);
        if (user == null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "login";
        }

        // создаём сессию
        String sessionId = SessionManager.createSession(user.getId());

        // добавляем cookie
        Cookie cookie = new Cookie("SESSIONID", sessionId);
        cookie.setPath("/weather_app"); // важно! чтобы кука работала во всём приложении
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24); // 1 день
        response.addCookie(cookie);

        return "redirect:/"; // после логина на главную
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = SessionManager.getSessionIdFromRequest(request);
        if (sessionId != null) {
            SessionManager.removeSession(sessionId);

            // Удаляем cookie
            Cookie cookie = new Cookie("SESSIONID", "");
            cookie.setPath("/weather_app"); // должен совпадать с путём, где она была установлена
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/login";
    }



}
