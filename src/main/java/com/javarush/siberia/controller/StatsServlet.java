package com.javarush.siberia.controller;

import com.javarush.siberia.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet(name="StatsServlet", urlPatterns="/stats")
public class StatsServlet extends HttpServlet {
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,Integer> stats = userService.getUserRepository().getUserStats();
        req.setAttribute("stats", stats);
        req.getRequestDispatcher("/WEB-INF/stats.jsp").forward(req, resp);
    }
}