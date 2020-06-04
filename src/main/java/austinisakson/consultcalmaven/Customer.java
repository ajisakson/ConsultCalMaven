/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author ajisa
 */
public class Customer {
    private int customerID;
    private String customerName;
    private int addressID;
    private boolean active;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Customer(){
        
    }
    
    // setter methods
    public void setID(int customerID){
        this.customerID = customerID;
    }
    public void setCustomer(String customerName){
       this.customerName = customerName; 
    }
    public void setAddressID(int addressID){
        this.addressID = addressID;
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }
    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    public void setLastUpdateBy(String lastUpdateBy){
        this.lastUpdateBy = lastUpdateBy;
    }
    
    // getter methods
    public int getID(){
        return customerID;
    }
    public String getCustomerName(){
        return customerName;
    }
    public int getAddressID(){
        return addressID;
    }
    public boolean getActive(){
        return active;
    }
    public Date getCreatedDate(){
        return createdDate;
    }
    public String getCreatedBy(){
        return createdBy;
    }
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }
    public String toString(){
        return customerName;
    }
    
    
}
