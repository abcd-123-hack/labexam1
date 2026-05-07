package com.mvc.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.mvc.dao.CustomerDAO;
import com.mvc.model.Customer;

@WebServlet("/insert")
public class InsertCustomerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            CustomerDAO dao = new CustomerDAO();

            Customer c = new Customer();
            c.setFirstname(req.getParameter("firstname"));
            c.setEmail(req.getParameter("email"));
            c.setPhoneNumber(req.getParameter("phone"));

            dao.insertCustomer(c);

            res.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}