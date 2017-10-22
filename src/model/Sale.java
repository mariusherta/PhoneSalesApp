/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Marius
 */
public class Sale {
    private Customer customer;
    private Phone phone;
    private LocalDate date;

    public Sale(Customer customer, Phone phone, LocalDate date) {
        this.customer = customer;
        this.phone = phone;
        this.date = date;
    }

    public Sale(Customer customer, Phone phone) {
        this.customer = customer;
        this.phone = phone;
    }
    
    

    public Sale() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String toString(){
        return phone.getMaker() + " " + phone.getModel() + " cu imei " + phone.getImei() + " - " + customer.getName()+ " ID " + customer.getIdSerial() + " - in " + date;
    }
    
}
