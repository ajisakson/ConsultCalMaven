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
    
    /**
     *
     */
    public Client(){
        
    }
    
    // setter methods

    /**
     *
     * @param clientID
     */
    public void setID(int clientID){
        this.clientID = clientID;
    }

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName){
       this.contactName = contactName; 
    }

    /**
     *
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail){
        this.contactEmail = contactEmail;
    }

    /**
     *
     * @param contactPhone
     */
    public void setContactPhone(String contactPhone){
        this.contactPhone = contactPhone;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active){
        this.active = active;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @param lastUpdateBy
     */
    public void setLastUpdateBy(String lastUpdateBy){
        this.lastUpdateBy = lastUpdateBy;
    }
    
    // getter methods

    /**
     *
     * @return
     */
    public int getID(){
        return clientID;
    }

    /**
     *
     * @return
     */
    public String getContactName(){
        return contactName;
    }

    /**
     *
     * @return
     */
    public String getContactEmail(){
        return contactEmail;
    }

    /**
     *
     * @return
     */
    public String getContactPhone(){
        return contactPhone;
    }

    /**
     *
     * @return
     */
    public String getLocation(){
        return location;
    }

    /**
     *
     * @return
     */
    public boolean getActive(){
        return active;
    }

    /**
     *
     * @return
     */
    public Date getCreatedDate(){
        return createdDate;
    }

    /**
     *
     * @return
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     *
     * @return
     */
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     *
     * @return
     */
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return contactName;
    }
    
    
}
