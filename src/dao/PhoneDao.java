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
import model.Phone;

/**
 *
 * @author Marius
 */
public class PhoneDao {
    private Connection con;
    private PreparedStatement stmt1;
    private PreparedStatement stmt2;
    private PreparedStatement stmt3;
    private PreparedStatement stmt4;
    private PreparedStatement stmt5;
    private PreparedStatement stmt6;
    private PreparedStatement stmt7;
    
    public PhoneDao (Connection con){
        try {
            this.con = con;
            stmt1 = con.prepareStatement("INSERT INTO availablephones VALUES (NULL,?,?,?)");
            stmt2 = con.prepareStatement("SELECT * FROM availablephones");
            stmt3 = con.prepareStatement("SELECT * FROM availablephones WHERE imei = ?");
            stmt4 = con.prepareStatement("SELECT * FROM availablephones WHERE maker = ? AND model = ?");
            stmt5 = con.prepareStatement("SELECT * FROM availablephones WHERE maker = ?");
            stmt6 = con.prepareStatement("SELECT * FROM availablephones WHERE model = ?");
            stmt7 = con.prepareStatement("DELETE FROM availablephones WHERE imei = ?");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addPhone (Phone phone) throws SQLException{
        stmt1.setString(1, phone.getMaker());
        stmt1.setString(2, phone.getModel());
        stmt1.setString(3, phone.getImei());
        
        stmt1.executeUpdate();
        
    }
    
    public void deletePhone (Phone phone) throws SQLException{
        stmt7.setString(1, phone.getImei());
        
        stmt7.executeUpdate();
    }
    
    public List<Phone> getAllPhones() throws SQLException{
        List list = new ArrayList();
        ResultSet rs = stmt2.executeQuery();
        while(rs.next()){
            String maker = rs.getString("maker");
            String model = rs.getString("model");
            String imei = rs.getString("imei");
            
            list.add(new Phone (maker, model, imei));
        }
        
        return list;
    }
    
   public List<Phone> searchPhones(Phone phone) throws SQLException{
        List list = new ArrayList();
        Phone phone1 = null;
        
        if(phone.getImei() != null){
            stmt3.setString(1, phone.getImei());
            
            try (
                ResultSet rs = stmt3.executeQuery();
                ) {
                while(rs.next()){
                    phone1 = new Phone();
                    phone1.setMaker(rs.getString("maker"));
                    phone1.setModel(rs.getString("model"));
                    phone1.setImei(rs.getString("imei"));
                
                    list.add(phone1);
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
            
            
        }else if(phone.getMaker()!= null && phone.getModel()!= null ){
            stmt4.setString(1, phone.getMaker());
            stmt4.setString(2, phone.getModel());
            
            try (
                ResultSet rs = stmt4.executeQuery();
                ) {
                while(rs.next()){
                    phone1 = new Phone();
                    phone1.setMaker(rs.getString("maker"));
                    phone1.setModel(rs.getString("model"));
                    phone1.setImei(rs.getString("imei"));

                    list.add(phone1);
            
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
            
            }else if (phone.getMaker()!= null){
                stmt5.setString(1, phone.getMaker());
                
                try (
                    ResultSet rs = stmt5.executeQuery();
                    ) {
                    while(rs.next()){
                       phone1 = new Phone();
                        phone1.setMaker(rs.getString("maker"));
                        phone1.setModel(rs.getString("model"));
                        phone1.setImei(rs.getString("imei"));
                
                        list.add(phone1);
                }
            
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }else if(phone.getModel()!= null){
                stmt6.setString(1, phone.getModel());
                
                try (
                    ResultSet rs = stmt6.executeQuery();
                    ) {
                    while(rs.next()){
                        phone1 = new Phone();
                        phone1.setMaker(rs.getString("maker"));
                        phone1.setModel(rs.getString("model"));
                        phone1.setImei(rs.getString("imei"));

                        list.add(phone1);
                        }
            
                }catch (SQLException ex) {
                    ex.printStackTrace();
                    }
                
            }
        
        return list;
    }
    
    public Optional<Phone> findPhone (String imei) throws SQLException{
        stmt3.setString(1, imei);
        Phone phone = null;
        
        try (
            ResultSet rs = stmt3.executeQuery();
            ) {
            if(rs.next()){
                phone = new Phone();
                phone.setMaker(rs.getString("maker"));
                phone.setModel(rs.getString("model"));
                phone.setImei(rs.getString("imei"));
                
                }
            
            }catch (SQLException ex) {
                ex.printStackTrace();
                }
        
        return Optional.ofNullable(phone);
        
    }
}
