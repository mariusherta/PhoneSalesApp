/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import dao.CustomerDao;
import dao.PhoneDao;
import dao.SaleDao;
import dao.UserDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.Customer;
import model.Phone;
import model.Sale;
import model.User;


/**
 *
 * @author Marius
 */
public class MainService {
    private String user = "root";
    private String pass = "";
    private String url = "jdbc:mysql://localhost/phonesalesapp";
        
    private Connection con; 
    
    public MainService(){
        
        try{
            con = DriverManager.getConnection(url, user, pass);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public boolean inregistrare (User user){
        UserDao userDao = new UserDao(con);
        boolean rez = false;
        try {
            Optional<User> optionalUser = userDao.findUser(user.getUsername());
            
            if(!optionalUser.isPresent()){
                userDao.adaugaUser(user);
                
                rez = true;
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        return rez;
    }
    
    public boolean addPhone(Phone phone){
        PhoneDao phoneDao = new PhoneDao(con);
        boolean rez = false;
        
        try {
            Optional<Phone> optionalPhone = phoneDao.findPhone(phone.getImei());
            
            if(!optionalPhone.isPresent()){
                phoneDao.addPhone(phone);
                
                rez = true;
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        return rez;
    }
    
    public Optional<User> login(String username, String password){
        UserDao userDao = new UserDao(con);
        
        try {
            Optional<User> optionalUser = userDao.findUser(username);
            if(optionalUser.isPresent()){
                if(optionalUser.get().getPassword().equals(password)){
                    return optionalUser;
                }
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    
    public List<Phone> getAllPhones (){
        
        PhoneDao phoneDao = new PhoneDao(con);
        
        try {
            return phoneDao.getAllPhones();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Sale> getAllSales (){
        
        SaleDao saleDao = new SaleDao(con);
        
        try {
            List<Sale> list = new ArrayList<>();
            list = saleDao.getAllSales();
            
            for(Sale sale:list){
                CustomerDao customerDao = new CustomerDao(con);
                
                String searchIdSerial;
                searchIdSerial = sale.getCustomer().getIdSerial();
                
                Optional<Customer> optionalCustomer = customerDao.findCustomer(searchIdSerial);
                
                if(optionalCustomer.isPresent()){
                    sale.getCustomer().setName(optionalCustomer.get().getName());
                    }
                                
                
                }
            
            return list;
            
            
            
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Customer> getAllCustomers(){
        CustomerDao customerDao = new CustomerDao(con);
        
        try {
            return customerDao.getAllCustomers();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return Collections.emptyList();
    }
    
    public List<Phone> searchPhones(Phone phone){
        PhoneDao phoneDao = new PhoneDao(con);
        
        try {
            return phoneDao.searchPhones(phone);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return Collections.emptyList();
        
    }
    
    
    public List<Sale> searchSalesByCustomer(Customer customer){
        SaleDao saleDao = new SaleDao(con);
        CustomerDao customerDao = new CustomerDao(con);
        
        List<Sale> finalList = new ArrayList<>();
        List<Customer>customerList1;
                
        if(customer.getIdSerial()!=null){
            try {
                List<Sale>list = saleDao.searchSalesByCustomer(customer);
                            
                for(Sale sale1:list){
                    Customer tempCustomer = customerDao.searchByIdSerial(customer.getIdSerial());
                    sale1.getCustomer().setName(tempCustomer.getName());
                    finalList.add(sale1);
                    }
                
                return finalList;
                
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }else if (customer.getName()!=null){
            try {
                List<Sale>list;
                customerList1 = customerDao.searchByName(customer.getName());
                            
                for(Customer customer1:customerList1){
                    list = saleDao.searchSalesByCustomer(customer1);
                    
                    for(Sale sale:list){
                        
                        sale.getCustomer().setName(customer1.getName());
                        finalList.add(sale);
                        }
                    }
                
                return finalList;
                
                }catch (SQLException ex) {
                    ex.printStackTrace();
                    } 
                }
                return finalList;
            
        }
    
    public List<Sale> searchSalesByPhone(Phone phone){
        SaleDao saleDao = new SaleDao(con);
        CustomerDao customerDao = new CustomerDao(con);
        
        List<Sale> finalList = new ArrayList<>();
                
        
        if(phone.getImei()!=null){
            try {
                List<Sale>list = saleDao.searchSalesByImei(phone);
                            
                for(Sale sale:list){
                    Customer tempCustomer = customerDao.searchByIdSerial(sale.getCustomer().getIdSerial());
                    sale.getCustomer().setName(tempCustomer.getName());
                    finalList.add(sale);
                    }
                
                return finalList;
                
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
        }else if (phone.getMaker()!=null || phone.getModel()!=null){
            try {
                List<Sale>list = saleDao.searchSalesByMakerOrModel(phone);
                for(Sale sale:list){
                    Customer tempCustomer = customerDao.searchByIdSerial(sale.getCustomer().getIdSerial());
                    sale.getCustomer().setName(tempCustomer.getName());
                    finalList.add(sale);
                    }
                
                return finalList;
                
                }catch (SQLException ex) {
                    ex.printStackTrace();
                    } 
                }
                return finalList;
            
        }
     
    public List<Customer> searchCustomers(Customer customer){
        CustomerDao customerDao = new CustomerDao(con);
        
        try {
            return customerDao.searchCustomers(customer);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return Collections.emptyList();
        
    }
    
    public boolean addCustomer(Customer customer){
        CustomerDao customerDao = new CustomerDao(con);
        
        boolean rez = false;
        
        try {
            Optional<Customer> optionalCustomer = customerDao.findCustomer(customer.getIdSerial());
            
            if(!optionalCustomer.isPresent()){
                customerDao.addCustomer(customer);
                
                rez = true;
            }
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        return rez;
    }
    
    public void sellPhone (Sale sale){
        
        try {
            SaleDao saleDao = new SaleDao(con);
            saleDao.addSale(sale);
            
            PhoneDao phoneDao = new PhoneDao(con);
            phoneDao.deletePhone(sale.getPhone());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}
