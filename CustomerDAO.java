/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mvc.dao;

import com.mvc.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MSIS
 */
public class CustomerDAO {
    
    
    public void insertCustomer(Customer c) throws Exception {
        Connection con = (Connection) DbConnection.getConnection();
        String sql = "INSERT INTO customer (firstname, email, phone_number) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getFirstname());
        ps.setString(2, c.getEmail());
        ps.setString(3, c.getPhoneNumber());
        ps.executeUpdate();
        con.close();
    }

    // DISPLAY
    public List<Customer> getAllCustomers() throws Exception {
        List<Customer> list = new ArrayList<>();
        Connection con = (Connection) DbConnection.getConnection();
        String sql = "SELECT * FROM customer";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Customer c = new Customer();
            c.setCustomerId(rs.getInt("customer_id"));
            c.setFirstname(rs.getString("firstname"));
            c.setEmail(rs.getString("email"));
            c.setPhoneNumber(rs.getString("phone_number"));
            list.add(c);
        }
        con.close();
        return list;
    }

    // DELETE
    public void deleteCustomer(int id) throws Exception {
        Connection con = (Connection) DbConnection.getConnection();
        String sql = "DELETE FROM customer WHERE customer_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }

    // UPDATE
    public void updateCustomer(Customer c) throws Exception {
        Connection con = (Connection) DbConnection.getConnection();
        String sql = "UPDATE customer SET firstname=?, email=?, phone_number=? WHERE customer_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, c.getFirstname());
        ps.setString(2, c.getEmail());
        ps.setString(3, c.getPhoneNumber());
        ps.setInt(4, c.getCustomerId());
        ps.executeUpdate();
        con.close();
    }
    
}
