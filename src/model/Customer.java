/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Marius
 */
public class Customer {
    private String name;
    private String idSerial;

    public Customer() {
    }

    public Customer(String name, String idSerial) {
        this.name = name;
        this.idSerial = idSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdSerial() {
        return idSerial;
    }

    public void setIdSerial(String idSerial) {
        this.idSerial = idSerial;
    }
    
    @Override
    public String toString(){
        return name + "   | Serie ID " + idSerial;
    }

    /*@Override
    public boolean equals(Customer customer){
        boolean rez = false;
        
        if(this.idSerial.equals(customer.getIdSerial())){
            rez = true;
        }
        
        return rez;
        
        
    }*/
}
