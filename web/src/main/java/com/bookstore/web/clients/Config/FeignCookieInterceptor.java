package com.bookstore.web.clients.Config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignCookieInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            // Get cookies from request (sessionId, jwt)
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    // Grab token string from jwt key
                    if ("jwt".equals(cookie.getName())) {
                        String token = cookie.getValue();

                        // Add token to Authorization header to match JwtFilter's expectations
                        template.header("Authorization", "Bearer " + token);
                    }
                }
            }
        }
    }
}
