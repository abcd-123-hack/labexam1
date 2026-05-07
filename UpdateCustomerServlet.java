package com.mvc.controller;


import com.mvc.dao.CustomerDAO;
import com.mvc.model.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update")
public class UpdateCustomerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Customer c = new Customer();
            c.setCustomerId(Integer.parseInt(req.getParameter("id")));
            c.setFirstname(req.getParameter("firstname"));
            c.setEmail(req.getParameter("email"));
            c.setPhoneNumber(req.getParameter("phone"));

            CustomerDAO dao = new CustomerDAO();
            dao.updateCustomer(c);

            res.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}