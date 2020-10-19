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
    private int clientID;
    private int scheduler;
    private String details;
    private String location;
    private String contact;
    private boolean completed;
    private GregorianCalendar date;
    private GregorianCalendar start;
    private GregorianCalendar end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    public Appointment(){
        
    }
    
    // setter methods
    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }
    public void setClientID(int clientID){
        this.clientID = clientID;
    }
    public void setScheduler(int scheduler){
        this.scheduler = scheduler;
    }
    public void setDetails(String details){
        this.details = details;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public void setDate(GregorianCalendar date){
        this.date = date;
    }
    public void setStart(GregorianCalendar start){
        this.start = start;
    }
    public void setEnd(GregorianCalendar end){
        this.end = end;
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
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    
    // getter methods
    public int getAppointmentID(){
        return appointmentID;
    }
    public int getClientID(){
        return clientID;
    }
    public int getScheduler(){
        return scheduler;
    }
    public String getDetails(){
        return details;
    }
    public String getLocation(){
        return location;
    }
    public String getContact(){
        return contact;
    }
    public GregorianCalendar getDate(){
        return date;
    }
    public GregorianCalendar getStart(){
        return start;
    }
    public GregorianCalendar getEnd(){
        return end;
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
    public Boolean getCompleted(){
        return completed;
    }
}
