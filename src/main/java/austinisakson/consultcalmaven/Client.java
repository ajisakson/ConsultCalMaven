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
public class Client {
    private int clientID;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String location;
    private boolean active;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Client(){
        
    }
    
    // setter methods
    public void setID(int clientID){
        this.clientID = clientID;
    }
    public void setContactName(String contactName){
       this.contactName = contactName; 
    }
    public void setContactEmail(String contactEmail){
        this.contactEmail = contactEmail;
    }
    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }
    public void setLocation(String location){
        this.location = location;
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
        return clientID;
    }
    public String getContactName(){
        return contactName;
    }
    public String getContactEmail(){
        return contactEmail;
    }
    public String getContactPhone(){
        return contactPhone;
    }
    public String getLocation(){
        return location;
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
        return contactName;
    }
    
    
}
