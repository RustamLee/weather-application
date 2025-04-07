package org.example.weather.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.weather.model.User;
import org.example.weather.service.UserService;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

public class SessionInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public SessionInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        System.out.println("===> SessionInterceptor preHandle: " + path);

        // Удаляем contextPath, чтобы получить "чистый" путь внутри приложения
        String realPath = path.substring(request.getContextPath().length());
        System.out.println("===> Interceptor path: " + realPath);

        // Публичные маршруты
        if (realPath.startsWith("/login") || realPath.startsWith("/register") || realPath.startsWith("/css") || realPath.startsWith("/js")) {
            System.out.println("===> Public path, allow");
            return true;
        }

        String sessionId = SessionManager.getSessionIdFromRequest(request);
        System.out.println("===> Session ID from cookie: " + sessionId);

        if (sessionId != null) {
            Optional<User> userOpt = userService.findBySessionId(sessionId);
            if (userOpt.isPresent()) {
                System.out.println("===> User found by session: " + userOpt.get().getUsername());
                request.setAttribute("username", userOpt.get().getUsername());
            } else {
                System.out.println("===> No user found for session");
            }
        }

        return true; // Всегда пропускаем дальше, но с установленным username
    }


}
