package org.example.weather.session;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static String createSession(int userId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(sessionId, userId));
        return sessionId;
    }

    public static Session getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public static String getSessionIdFromRequest(HttpServletRequest request) {
        String sessionId = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("SESSIONID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }
}
