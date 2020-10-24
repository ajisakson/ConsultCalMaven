package austinisakson.consultcalmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static austinisakson.consultcalmaven.DBConnection.conn;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class ReportsController implements Initializable {

    @FXML
    Button clientsButton = new Button();
    @FXML
    Button apptsButton = new Button();
    @FXML
    Button exitButton = new Button();
    
    @FXML
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    @FXML
    private void handleClientsReport() throws IOException, SQLException {
        ResultSet allClients = conn.createStatement().executeQuery("SELECT * FROM client;");
        File tmpdir = new File("./all-clients-" + LocalDate.now() + ".csv");
        if (tmpdir.exists())
            tmpdir.delete();
        FileWriter write = new FileWriter("./all-clients-" + LocalDate.now() + ".csv", true);
        PrintWriter print = new PrintWriter(write);
        print.println("id,contact_name,contact_email,contact_phone,location,active,create_time,created_by,last_update,updated_by");
        while (allClients.next())            
            print.println(allClients.getString("id") + "," +
                          allClients.getString("contact_name") + "," +
                          allClients.getString("contact_email") + "," +
                          allClients.getString("contact_phone") + "," +
                          allClients.getString("location") + "," +
                          allClients.getString("active") + "," +
                          allClients.getString("create_time") + "," +
                          allClients.getString("created_by") + "," +
                          allClients.getString("last_update") + "," +
                          allClients.getString("updated_by"));       
        print.close();
    }
    
    @FXML
    private void handleApptsReport() throws IOException, SQLException { 
        ResultSet allClients = conn.createStatement().executeQuery("SELECT * FROM appointment;");
        File tmpdir = new File("./all-appts-" + LocalDate.now() + ".csv");
        if (tmpdir.exists())
            tmpdir.delete();
        FileWriter write = new FileWriter("./all-appts-" + LocalDate.now() + ".csv", true);
        PrintWriter print = new PrintWriter(write);
        print.println("id,starttime,endtime,location,client,scheduler,details,completed,contact,createddate,createdby,updatedby,lastupdate");
        while (allClients.next())            
            print.println(allClients.getString("id") + "," +
                          allClients.getString("starttime") + "," +
                          allClients.getString("endtime") + "," +
                          allClients.getString("location") + "," +
                          allClients.getString("client") + "," +
                          allClients.getString("scheduler") + "," +
                          allClients.getString("details") + "," +
                          allClients.getString("completed") + "," +
                          allClients.getString("contact") + "," +
                          allClients.getString("createddate") + "," +
                          allClients.getString("createdby") + "," +
                          allClients.getString("updatedby") + "," +
                          allClients.getString("lastupdate"));       
        print.close();
    }
    
    @FXML
    private void handleExit() throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {  
            this.clients.clear();
            this.clients = Scheduler.loadClients(clients);
        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {  
            this.appointments.clear();
            this.appointments = Scheduler.loadAppts(appointments);
        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
