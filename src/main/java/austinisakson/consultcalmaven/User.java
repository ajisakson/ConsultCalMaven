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
    
    public User(){
        
    }
    
    // setter methods
    public void setID(int userID){
        this.userID = userID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
       this.email = email; 
    }
    public void setPassword(String password){
       this.password = password; 
    }
    public void setActive(boolean active){
        this.active = active;
    }
    public void setAdmin(boolean admin){
        this.admin = admin;
    }
    public void setPosition(String position){
        this.position = position;
    }
    public void setLocation(String location){
       this.location = location; 
    }
     public void setCreatedDate(Timestamp createdDate){
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
        return userID;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public boolean getActive(){
        return active;
    }
    public boolean getAdmin(){
        return admin;
    }
    public String getPosition(){
        return position;
    }
    public String getLocation(){
        return location;
    }
    public Timestamp getCreatedDate(){
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

