/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Customer;
import model.Phone;

/**
 *
 * @author Marius
 */
public class CustomerDao {
    private Connection con;
    private PreparedStatement stmt1;
    private PreparedStatement stmt2;
    private PreparedStatement stmt3;
    private PreparedStatement stmt4;

    public CustomerDao(Connection con) {
        try {
            this.con = con;
            stmt1 = con.prepareStatement("INSERT INTO customers VALUES (NULL,?,?)");
            stmt2 = con.prepareStatement("SELECT * FROM customers");
            stmt3 = con.prepareStatement("SELECT * FROM customers WHERE idSerial = ?");
            stmt4 = con.prepareStatement("SELECT * FROM customers WHERE name = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Customer> getAllCustomers() throws SQLException{
        List list = new ArrayList();
        ResultSet rs = stmt2.executeQuery();
        while(rs.next()){
            String name = rs.getString("name");
            String idSerial = rs.getString("idSerial");
                        
            list.add(new Customer (name, idSerial));
        }
        
        return list;
    }
    
    public void addCustomer(Customer customer) throws SQLException{
        stmt1.setString(1, customer.getName());
        stmt1.setString(2, customer.getIdSerial());
        
        stmt1.executeUpdate();
    }
    
    public Optional<Customer> findCustomer(String idSerial) throws SQLException{
        stmt3.setString(1, idSerial);
        Customer customer = null;
        
        try (
            ResultSet rs = stmt3.executeQuery();
            ) {
            if(rs.next()){
                customer = new Customer();
                customer.setName(rs.getString("name"));
                customer.setIdSerial(rs.getString("idSerial"));
                                
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
        
        return Optional.ofNullable(customer);
    }
    
    public List<Customer> searchCustomers(Customer customer) throws SQLException{
        List list = new ArrayList();
        Customer customer1 = null;
        
        if(customer.getIdSerial() != null){
            stmt3.setString(1, customer.getIdSerial());
            
            try (
                ResultSet rs = stmt3.executeQuery();
                ) {
                while(rs.next()){
                    customer1 = new Customer();
                    customer1.setName(rs.getString("name"));
                    customer1.setIdSerial(rs.getString("idSerial"));
                                    
                    list.add(customer1);
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
            
            
        }else if (customer.getName()!= null){
                stmt4.setString(1, customer.getName());
                
                try (
                    ResultSet rs = stmt4.executeQuery();
                    ) {
                    while(rs.next()){
                        customer1 = new Customer();
                        customer1.setName(rs.getString("name"));
                        customer1.setIdSerial(rs.getString("idSerial"));
                                        
                        list.add(customer1);
                }
            
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }
                
            
        
        return list;
    }
    
    public List<Customer> searchByName(String name) throws SQLException{
        stmt4.setString(1, name);
        List<Customer> list = new ArrayList<>();
        ResultSet rs = stmt4.executeQuery();
        
        while(rs.next()){
            String name1 = rs.getString("name");
            String idSerial = rs.getString("idSerial");
            
            list.add(new Customer(name1, idSerial));
        }
        
        return list;
    }
    
    public Customer searchByIdSerial(String idSerial) throws SQLException{
        stmt3.setString(1, idSerial);
        Customer customer = new Customer();
        ResultSet rs = stmt3.executeQuery();
        
        if(rs.next()){
            String name = rs.getString("name");
            String idSerial1 = rs.getString("idSerial");
            
            customer.setIdSerial(idSerial1);
            customer.setName(name);
        }
        
        return customer;
    }
    
}
