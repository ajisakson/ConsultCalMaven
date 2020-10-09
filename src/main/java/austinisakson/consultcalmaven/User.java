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
    private String userName;
    private String password;
    private boolean active;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public User(){
        
    }
    
    // setter methods
    public void setID(int userID){
        this.userID = userID;
    }
    public void setUserName(String userName){
       this.userName = userName; 
    }
    public void setPassword(String password){
       this.password = password; 
    }
    public void setActive(boolean active){
        this.active = active;
    }

    
    // getter methods
    public int getID(){
        return userID;
    }
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
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
    
    
}

