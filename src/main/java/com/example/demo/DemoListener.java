package com.example.demo;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
public class DemoListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Web application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Web application stopped");
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("Session created: {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("Session destroyed: {}", se.getSession().getId());
    }
}
