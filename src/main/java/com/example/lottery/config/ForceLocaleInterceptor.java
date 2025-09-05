package com.example.lottery.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ForceLocaleInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response,
	                         Object handler) {

		String lang = request.getParameter("lang");
		if (lang != null) {
			Cookie cookie = new Cookie("localeInfo", lang);
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 30);
			cookie.setHttpOnly(false);
			cookie.setSecure(true);
			response.addCookie(cookie);
		}
		return true;
	}
}
