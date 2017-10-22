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
public class Phone {
    private String maker;
    private String model;
    private String imei;

    public Phone(String maker, String model, String imei) {
        this.maker = maker;
        this.model = model;
        this.imei = imei;
    }

    public Phone() {
    }

            
    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
    
    @Override
    public String toString(){
        return maker + " " + model + "    IMEI:" + imei;
    }
}
