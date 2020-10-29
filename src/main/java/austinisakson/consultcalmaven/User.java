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
public class User {
    private int userID;
    private String name;
    private String email;
    private String password;
    private String location;
    private boolean active;
    private boolean admin;
    private String position;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     *
     */
    public User(){
        
    }
    
    // setter methods

    /**
     *
     * @param userID
     */
    public void setID(int userID){
        this.userID = userID;
    }

    /**
     *
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email){
       this.email = email; 
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password){
       this.password = password; 
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
     * @param admin
     */
    public void setAdmin(boolean admin){
        this.admin = admin;
    }

    /**
     *
     * @param position
     */
    public void setPosition(String position){
        this.position = position;
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
     * @param createdDate
     */
    public void setCreatedDate(Timestamp createdDate){
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
        return userID;
    }

    /**
     *
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     *
     * @return
     */
    public String getPassword(){
        return password;
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
    public boolean getAdmin(){
        return admin;
    }

    /**
     *
     * @return
     */
    public String getPosition(){
        return position;
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
    public Timestamp getCreatedDate(){
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
    
    
}

