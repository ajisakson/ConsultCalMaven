/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.sql.Timestamp;
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
    private GregorianCalendar start;
    private GregorianCalendar end;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    
    /**
     *
     */
    public Appointment(){
        
    }
    
    // setter methods

    /**
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID){
        this.appointmentID = appointmentID;
    }

    /**
     *
     * @param clientID
     */
    public void setClientID(int clientID){
        this.clientID = clientID;
    }

    /**
     *
     * @param scheduler
     */
    public void setScheduler(int scheduler){
        this.scheduler = scheduler;
    }

    /**
     *
     * @param details
     */
    public void setDetails(String details){
        this.details = details;
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
     * @param contact
     */
    public void setContact(String contact){
        this.contact = contact;
    }

    /**
     *
     * @param start
     */
    public void setStart(GregorianCalendar start){
        this.start = start;
    }

    /**
     *
     * @param end
     */
    public void setEnd(GregorianCalendar end){
        this.end = end;
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

    /**
     *
     * @param completed
     */
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    
    // getter methods

    /**
     *
     * @return
     */
    public int getAppointmentID(){
        return appointmentID;
    }

    /**
     *
     * @return
     */
    public int getClientID(){
        return clientID;
    }

    /**
     *
     * @return
     */
    public int getScheduler(){
        return scheduler;
    }

    /**
     *
     * @return
     */
    public String getDetails(){
        return details;
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
    public String getContact(){
        return contact;
    }

    /**
     *
     * @return
     */
    public GregorianCalendar getStart(){
        return start;
    }

    /**
     *
     * @return
     */
    public GregorianCalendar getEnd(){
        return end;
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

    /**
     *
     * @return
     */
    public Boolean getCompleted(){
        return completed;
    }
}
