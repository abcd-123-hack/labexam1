package com.mvc.controller;


import com.mvc.dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteCustomerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));

            CustomerDAO dao = new CustomerDAO();
            dao.deleteCustomer(id);

            res.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}