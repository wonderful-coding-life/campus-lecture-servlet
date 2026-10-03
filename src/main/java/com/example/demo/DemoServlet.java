package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("*.do")
@Slf4j
public class DemoServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    // Do not call super.doGet(req, resp), otherwise HttpServlet will return 405 Method Not Allowed.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("uri={}", req.getRequestURI());
        log.info("query={}", req.getQueryString());
        log.info("body={}", req.getReader().lines().collect(Collectors.joining("\n")));

        // Return the current session, or create one if it does not exist.
        log.info("session={}", req.getSession().getId());

        // do business logic here

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("""
            {
                "id": "12",
                "name": "윤서준",
                "email": "SeojunYoon@campus.co.kr",
                "age": "10"
            }
            """);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
