package com.example.demo;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("*.do")
@Slf4j
public class DemoFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("{} starts at {}", httpRequest.getRequestURI(), LocalDateTime.now());
        chain.doFilter(request, response);
        log.info("{} returns at {}", httpRequest.getRequestURI(), LocalDateTime.now());
    }
}
