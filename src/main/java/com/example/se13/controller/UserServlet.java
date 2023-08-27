package com.example.se13.controller;

import com.example.se13.model.bl.UserBl;
import com.example.se13.model.entity.Role;
import com.example.se13.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="user.do", urlPatterns = "/user.do")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            // validation
            User user = User
                    .builder()
                    .userName(req.getParameter("username"))
                    .password(req.getParameter("password"))
                    .role(Role.customer)
                    .active(true)
                    .build();
            System.out.println(UserBl.getUserBl().save(user) + " Saved");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
