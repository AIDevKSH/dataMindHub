package com.datamindhub.blog.config.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CsrfLoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Object csrf = request.getAttribute("_csrf");
        CsrfToken csrfToken = (CsrfToken) csrf;

        log.info("Request URI = {}", request.getRequestURI());
        log.info("현재 sessionID = {}", request.getSession().getId());
        log.info("CSRF Token getParam = {}, getHeader = {} , getToken = {}",
                csrfToken.getParameterName(), csrfToken.getHeaderName(), csrfToken.getToken());
        filterChain.doFilter(request, response);
    }
}
