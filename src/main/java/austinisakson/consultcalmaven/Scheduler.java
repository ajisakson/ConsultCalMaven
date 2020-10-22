/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import static austinisakson.consultcalmaven.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ajisa
 */
public class Scheduler extends Application {
   
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        
        DBConnection.makeConnection();
        launch(args);
        DBConnection.closeConnection();
    }
    
    public static ObservableList<Client> loadClients(ObservableList<Client> clients) throws SQLException{
        ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM client");
        while (custTable.next()){

            Client newClient = new Client();
            newClient.setID(custTable.getInt("id"));
            newClient.setContactName(custTable.getString("contact_name"));
            newClient.setContactEmail(custTable.getString("contact_email"));
            newClient.setContactPhone(custTable.getString("contact_phone"));
            newClient.setLocation(custTable.getString("location"));
            newClient.setActive(custTable.getBoolean("active"));
            newClient.setCreatedDate(custTable.getDate("create_time"));
            newClient.setCreatedBy(custTable.getString("created_by"));
            newClient.setLastUpdate(custTable.getTimestamp("last_update"));
            newClient.setLastUpdateBy(custTable.getString("updated_by"));
            clients.add(newClient);
        }
        return clients;
    }
    
    public static ObservableList<Appointment> loadAppts(ObservableList<Appointment> appointments) throws SQLException {
        ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment");
        while (appointmentTable.next()){

            Appointment newAppointment = new Appointment();
            newAppointment.setAppointmentID(appointmentTable.getInt("id"));
            newAppointment.setClientID(appointmentTable.getInt("client"));
            newAppointment.setScheduler(appointmentTable.getInt("scheduler"));
            newAppointment.setDetails(appointmentTable.getString("details"));
            newAppointment.setLocation(appointmentTable.getString("location"));
            newAppointment.setContact(appointmentTable.getString("contact"));

            Timestamp startStamp = appointmentTable.getTimestamp("starttime");
            GregorianCalendar startCal = (GregorianCalendar) GregorianCalendar.getInstance();
            startCal.setTimeInMillis(startStamp.getTime());
            newAppointment.setStart(startCal);
            Timestamp endStamp = appointmentTable.getTimestamp("endtime");
            GregorianCalendar endCal = (GregorianCalendar) GregorianCalendar.getInstance();
            endCal.setTimeInMillis(endStamp.getTime());
            newAppointment.setEnd(endCal);

            newAppointment.setCreatedDate(appointmentTable.getTimestamp("createdDate"));
            newAppointment.setCreatedBy(appointmentTable.getString("createdBy"));
            newAppointment.setLastUpdate(appointmentTable.getTimestamp("lastUpdate"));
            newAppointment.setLastUpdateBy(appointmentTable.getString("updatedBy"));
            newAppointment.setCompleted(appointmentTable.getBoolean("completed"));
            appointments.add(newAppointment);
        }
        return appointments;
    }
}