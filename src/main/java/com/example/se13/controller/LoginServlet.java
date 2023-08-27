package com.example.se13.controller;

import com.example.se13.model.bl.UserBl;
import com.example.se13.model.entity.Role;
import com.example.se13.model.entity.User;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.MDC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
@WebServlet(name = "login.do", urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("LOGIN POST");
            // validation
            User user = UserBl.getUserBl().login(req.getParameter("username"), req.getParameter("password"));
            System.out.println("user");
            System.out.println(user);
            if (user == null) {
                MDC.put("data", "USER NOT FOUND");
                MDC.put("action", "Login");
                MDC.put("userId", -1);

                log.error("LOGIN FAILED");
                req.getRequestDispatcher("/login-error.jsp").forward(req, resp);
            } else {
                MDC.put("data", user.toString());
                MDC.put("action", "Login");
                MDC.put("userId", user.getId());

                log.info("LOGIN SUCCESSFUL");
                if(user.getRole().equals(Role.admin)) {
                    req.getRequestDispatcher("/WEB-INF/admin-panel.jsp").forward(req, resp);
                }else if (user.getRole().equals(Role.customer)){
                    req.getRequestDispatcher("/WEB-INF/panel.jsp").forward(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
