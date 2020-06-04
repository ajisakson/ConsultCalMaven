/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author ajisa
 */
public class Appointment {
    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private GregorianCalendar start;
    private GregorianCalendar end;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Appointment(){
        
    }
    
    // setter methods
    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setURL(String url){
        this.url = url;
    }
    public void setStart(GregorianCalendar start){
        this.start = start;
    }
    public void setEnd(GregorianCalendar end){
        this.end = end;
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
    public int getAppointmentID(){
        return appointmentID;
    }
    public int getCustomerID(){
        return customerID;
    }
    public int getUserID(){
        return userID;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getLocation(){
        return location;
    }
    public String getContact(){
        return contact;
    }
    public String getType(){
        return type;
    }
    public String getURL(){
        return url;
    }
    public GregorianCalendar getStart(){
        return start;
    }
    public GregorianCalendar getEnd(){
        return end;
    }
    public Date getCreatedDate(){
        return createdDate;
    }
    public String CreatedBy(){
        return createdBy;
    }
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }
    public String getLastUpdateBy(){
        return lastUpdateBy;
    }
}
