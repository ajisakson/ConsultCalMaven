/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static austinisakson.consultcalmaven.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class MainScreenController implements Initializable {

    @FXML
    final ToggleGroup group = new ToggleGroup();
    @FXML
    RadioButton upcoming = new RadioButton();        
    @FXML
    RadioButton previous = new RadioButton();
    @FXML
    Button manageClients = new Button();
    @FXML
    Button manageAppts = new Button();
    @FXML
    Button reports = new Button();
    @FXML
    Button adminPanel = new Button();
    @FXML
    Button logoutButton = new Button();
    @FXML
    Button view = new Button();
    
    @FXML
    private ObservableList<Appointment> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, Calendar> column1 = new TableColumn<>("Start Time");
    @FXML
    private TableColumn<Appointment, Calendar> column2 = new TableColumn<>("End Time");
    @FXML
    private TableColumn<Appointment, String> column3 = new TableColumn<>("Contact");
    @FXML
    private TableColumn<Appointment, String> column4 = new TableColumn<>("Location");
    @FXML
    private TableView appointmentView;
    
    private static final String timeFromCal(GregorianCalendar cal){
        String time = "";
        time += String.format("%02d", cal.get(GregorianCalendar.HOUR_OF_DAY)) + ":";
        time += String.format("%02d", cal.get(GregorianCalendar.MINUTE));
        
        return time;
    }
    
    private static LocalDate calToLocalDate(GregorianCalendar cal){
        TimeZone tz = cal.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        LocalDate localDate = LocalDateTime.ofInstant(cal.toInstant(), zid).toLocalDate();
        return localDate;
    }
    
    @FXML
    private void handleMainScreen(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleManageAppointments(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/AppointmentScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleApptDetails(ActionEvent event) throws IOException {
        
        Parent secondParent = FXMLLoader.load(getClass().getResource("/fxml/ApptDetails.fxml"));
        Scene secondScene = new Scene(secondParent);
        Stage window = new Stage();
        
        /* to hide window
        (Stage) ((Node) event.getSource()).getScene().getWindow();
        */
        
        window.setScene(secondScene);
        window.show();
    }
    
    @FXML
    private void handleManageClients(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/ClientScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleClientDetails(ActionEvent event) throws IOException {
        
        Parent secondParent = FXMLLoader.load(getClass().getResource("/fxml/ClientDetails.fxml"));
        Scene secondScene = new Scene(secondParent);
        Stage window = new Stage();
        
        /* to hide window
        (Stage) ((Node) event.getSource()).getScene().getWindow();
        */

        window.setScene(secondScene);
        window.show();
    }
    
    @FXML
    private void handleReports(ActionEvent event) throws IOException {

        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/ReportScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleAdminPanel(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        
        LoginScreenController.currentUserId = 0;
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleGenerate(ActionEvent event) throws IOException {
        
        // if radio selected this week, generate all of this week's appointments in the table view
        if (upcoming.isSelected()){
            try {
                appointmentView.getItems().clear();
                // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
                ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment WHERE starttime >= NOW();");
                while (appointmentTable.next()){
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(appointmentTable.getInt("id"));
                    newAppointment.setClientID(appointmentTable.getInt("client"));
                    newAppointment.setSchedulerID(appointmentTable.getInt("scheduler"));
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
                    data.add(newAppointment);
                    System.out.println("New appointment added from database!");
                }
                
                appointmentView.setItems(data);
            
            }
            catch (SQLException ex) {
                Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // if radio selected this month, generate all of this month's appointments in the table view
        else if (previous.isSelected()){
            try {
                appointmentView.getItems().clear();
                // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
                ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment WHERE starttime < NOW();");
                while (appointmentTable.next()){
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(appointmentTable.getInt("id"));
                    newAppointment.setClientID(appointmentTable.getInt("client"));
                    newAppointment.setSchedulerID(appointmentTable.getInt("scheduler"));
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
                    data.add(newAppointment);
                    System.out.println("New appointment added from database!");
                }
                
                appointmentView.setItems(data);
            
            }
            catch (SQLException ex) {
                Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // navigate to client screen when clicking Clients button
        manageClients.setOnAction((event)->  { // lambda expression here is used to remove a separately declared function and cleanly insert it into the initial program, eliminating the need to call a separate function
            try {
                Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/ClientScreen.fxml"));
                Scene mainScene = new Scene(mainParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                window.setScene(mainScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        // set up appointment table for display
        final DateFormat dateFormat = DateFormat.getInstance();
        column1.setCellValueFactory(new PropertyValueFactory<>("start"));
        column1.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(dateFormat.format(item.getTime()));
                }
            }
        });
        column2.setCellValueFactory(new PropertyValueFactory<>("end"));
        column2.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(dateFormat.format(item.getTime()));
                }
            }
        });
        column3.setCellValueFactory(new PropertyValueFactory<>("contact"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));

        // set radio toggle options
        upcoming.setToggleGroup(group);
        previous.setToggleGroup(group);
    }
}
