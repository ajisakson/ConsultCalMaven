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
public class Location {
    private int addressID;
    private String address;
    private String address2;
    private int cityID;
    private String postalCode;
    private String phone;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Location(){
        
    }
    
    // setter methods
    public void setID(int addressID){
        this.addressID = addressID;
    }
    public void setAddress(String address){
       this.address = address; 
    }
    public void setAddress2(String address2){
       this.address2 = address2; 
    }
    public void setCityID(int cityID){
        this.cityID = cityID;
    }
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }
    public void setPhone(String phone){
        this.phone = phone;
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
        return addressID;
    }
    public String getAddress(){
        return address;
    }
    public String getAddress2(){
        return address2;
    }
    public int getCityID(){
        return cityID;
    }
    public String getPostalCode(){
        return postalCode;
    }
    public String getPhone(){
        return phone;
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
    
    
}

