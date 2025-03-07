package com.javarush.siberia.controller;

import com.javarush.siberia.model.Stats;
import com.javarush.siberia.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.javarush.siberia.util.AppConstants.*;

@WebServlet(WS_STATS_URL)
public class StatsServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Stats> allStats = userService.getAllStats();
        req.setAttribute("allStats", allStats);
        req.getRequestDispatcher(JSP_STATS).forward(req, resp);
    }

}