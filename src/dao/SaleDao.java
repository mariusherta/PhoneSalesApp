/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Customer;
import model.Phone;
import model.Sale;

/**
 *
 * @author Marius
 */
public class SaleDao {
    private Connection con;
    private PreparedStatement stmt1;
    private PreparedStatement stmt2;
    private PreparedStatement stmt3;
    private PreparedStatement stmt4;
    private PreparedStatement stmt5;
    private PreparedStatement stmt6;
    private PreparedStatement stmt7;
        
    public SaleDao(Connection con){
        try {
            this.con = con;
            stmt1 = con.prepareStatement("INSERT INTO sales VALUES (NULL,?,?,?,?,?)");
            stmt2 = con.prepareStatement("SELECT * FROM sales");
            stmt3 = con.prepareStatement("SELECT * FROM sales WHERE imei = ?");
            stmt4 = con.prepareStatement("SELECT * FROM sales WHERE maker = ? AND model =?");
            stmt5 = con.prepareStatement("SELECT * FROM sales WHERE maker = ?");
            stmt6 = con.prepareStatement("SELECT * FROM sales WHERE model = ?");
            stmt7 = con.prepareStatement("SELECT * FROM sales WHERE customerId = ?");
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addSale(Sale sale) throws SQLException{
        stmt1.setString(1, sale.getCustomer().getIdSerial());
        stmt1.setString(2, sale.getPhone().getMaker());
        stmt1.setString(3, sale.getPhone().getModel());
        stmt1.setString(4, sale.getPhone().getImei());
        stmt1.setDate(5, Date.valueOf(sale.getDate()));
        
        stmt1.executeUpdate();
    }
    
    public List<Sale> getAllSales() throws SQLException{
        List<Sale> list = new ArrayList<>();
        ResultSet rs = stmt2.executeQuery();
        while(rs.next()){
            String maker = rs.getString("maker");
            String model = rs.getString("model");
            String imei = rs.getString("imei");
            String idSerial = rs.getString("customerId");
            
            Customer customer = new Customer();
            customer.setIdSerial(idSerial);
            
            Phone phone = new Phone(maker, model, imei);
            
            Date date;
            date = rs.getDate("date");
            LocalDate localDate = date.toLocalDate();
            
            Sale sale = new Sale(customer,phone,localDate); 
            
            list.add(sale);
        }
        
        return list;
    }
    
    public List<Sale> searchSalesByCustomer(Customer customer) throws SQLException{
        List<Sale> list = new ArrayList<>();
        Sale sale1 = null;
        
        if(customer.getIdSerial() != null){
            stmt7.setString(1, customer.getIdSerial());
            
            try (
                ResultSet rs = stmt7.executeQuery();
                ) {
                while(rs.next()){
                    sale1 = new Sale(new Customer(), new Phone());
                    sale1.getCustomer().setIdSerial(rs.getString("customerId"));
                    sale1.getPhone().setImei(rs.getString("imei"));
                    sale1.getPhone().setMaker(rs.getString("maker"));
                    sale1.getPhone().setModel(rs.getString("model"));
                    
                    Date date;
                    date = rs.getDate("date");
                    LocalDate localDate = date.toLocalDate();
                    
                    sale1.setDate(localDate);

                    list.add(sale1);
                    
                    return list;
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
            
         
    }
        return Collections.emptyList();
    
    }
    
    public List<Sale> searchSalesByImei(Phone phone) throws SQLException{
        stmt3.setString(1, phone.getImei());
        
        List<Sale> list = new ArrayList<>();
        Sale sale = new Sale();
        
        try (
                ResultSet rs = stmt3.executeQuery();
                ) {
                while(rs.next()){
                    sale = new Sale(new Customer(), new Phone());
                    sale.getCustomer().setIdSerial(rs.getString("customerId"));
                    sale.getPhone().setImei(rs.getString("imei"));
                    sale.getPhone().setMaker(rs.getString("maker"));
                    sale.getPhone().setModel(rs.getString("model"));
                    
                    Date date;
                    date = rs.getDate("date");
                    LocalDate localDate = date.toLocalDate();
                    
                    sale.setDate(localDate);

                    list.add(sale);
                    
                    return list;
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
            
            return Collections.emptyList();
    }
    
    public List<Sale> searchSalesByMakerOrModel(Phone phone) throws SQLException{
        List<Sale>list = new ArrayList<>();
        if(phone.getMaker()!=null && phone.getModel()!=null){
            stmt4.setString(1, phone.getMaker());
            stmt4.setString(2, phone.getModel());
            
            try (
                ResultSet rs = stmt4.executeQuery();    
                    ){
                while(rs.next()){
                
                    Sale sale = new Sale(new Customer(), new Phone());
                    
                    sale.getCustomer().setIdSerial(rs.getString("customerId"));
                    sale.getPhone().setMaker(rs.getString("maker"));
                    sale.getPhone().setModel(rs.getString("model"));
                    sale.getPhone().setImei(rs.getString("imei"));
                    
                    Date date;
                    date = rs.getDate("date");
                    LocalDate localDate = date.toLocalDate();
                    
                    sale.setDate(localDate);
                    
                    list.add(sale);
                }
                
                return list;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(phone.getMaker()!=null){
            stmt5.setString(1, phone.getMaker());
            
            try (
                ResultSet rs = stmt5.executeQuery();    
                    ){
                while(rs.next()){
                
                    Sale sale = new Sale(new Customer(), new Phone());
                    
                    sale.getCustomer().setIdSerial(rs.getString("customerId"));
                    sale.getPhone().setMaker(rs.getString("maker"));
                    sale.getPhone().setModel(rs.getString("model"));
                    sale.getPhone().setImei(rs.getString("imei"));
                    
                    Date date;
                    date = rs.getDate("date");
                    LocalDate localDate = date.toLocalDate();
                    
                    sale.setDate(localDate);
                    
                    list.add(sale);
                }
                
                return list;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(phone.getModel()!=null){
            stmt6.setString(1, phone.getModel());
            
            try (
                ResultSet rs = stmt6.executeQuery();    
                    ){
                while(rs.next()){
                
                    Sale sale = new Sale(new Customer(), new Phone());
                    
                    sale.getCustomer().setIdSerial(rs.getString("customerId"));
                    sale.getPhone().setMaker(rs.getString("maker"));
                    sale.getPhone().setModel(rs.getString("model"));
                    sale.getPhone().setImei(rs.getString("imei"));
                    
                    Date date;
                    date = rs.getDate("date");
                    LocalDate localDate = date.toLocalDate();
                    
                    sale.setDate(localDate);
                    
                    list.add(sale);
                }
                
                return list;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
                    
            return Collections.emptyList();
    }
}
