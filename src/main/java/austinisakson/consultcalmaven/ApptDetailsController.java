package austinisakson.consultcalmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static austinisakson.consultcalmaven.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class ApptDetailsController implements Initializable {

    // Declare FXML Elements
    @FXML
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    @FXML
    private DatePicker apptDate;
    @FXML
    private ChoiceBox<String> startTime = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> endTime = new ChoiceBox<String>();
    @FXML
    private TextField location;
    @FXML
    private ComboBox<Client> client;
    @FXML
    private TextArea details;
    @FXML
    private TextField contact;
    @FXML
    private CheckBox completed;

    private ObservableList<String> times = FXCollections.observableArrayList(
        "00:00", "00:15", "00:30", "00:45",
        "01:00", "01:15", "01:30", "01:45",
        "02:00", "02:15", "02:30", "02:45",
        "03:00", "03:15", "03:30", "03:45",
        "04:00", "04:15", "04:30", "04:45",
        "05:00", "05:15", "05:30", "05:45",
        "06:00", "06:15", "06:30", "06:45",
        "07:00", "07:15", "07:30", "07:45",
        "08:00", "08:15", "08:30", "08:45",
        "09:00", "09:15", "09:30", "09:45", 
        "10:00", "10:15", "10:30", "10:45",
        "11:00", "11:15", "11:30", "11:45", 
        "12:00", "12:15", "12:30", "12:45", 
        "13:00", "13:15", "13:30", "13:45", 
        "14:00", "14:15", "14:30", "14:45", 
        "15:00", "15:15", "15:30", "15:45", 
        "16:00", "16:15", "16:30", "16:45", 
        "17:00", "17:15", "17:30", "17:45",
        "18:00", "18:15", "18:30", "18:45",
        "19:00", "19:15", "19:30", "19:45",
        "20:00", "20:15", "20:30", "20:45",
        "21:00", "21:15", "21:30", "21:45",
        "22:00", "22:15", "22:30", "22:45",
        "23:00", "23:15", "23:30", "23:45"
        );
    
    @FXML
    Button saveButton = new Button();
    @FXML
    Button cancelButton = new Button();
    @FXML
    Button deleteButton = new Button();
    
    @FXML
    Label createdDateLabel = new Label();
    @FXML
    Label createdByLabel = new Label();
    @FXML
    Label lastUpdateLabel = new Label();
    @FXML
    Label updatedByLabel = new Label();

    @FXML
    private Appointment selectedAppt = new Appointment();
    
    SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
    DateTimeFormatter timeOnlyDTF = DateTimeFormatter.ofPattern("HH:mm");
    SimpleDateFormat timeOnlySDF =  new SimpleDateFormat("HH:mm");
    DateTimeFormatter sqlTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     *
     * @param selectedAppt
     */
    public void transferAppt(Appointment selectedAppt){
        this.selectedAppt = selectedAppt;
        apptDate.setValue(this.selectedAppt.getStart().toZonedDateTime().toLocalDate());
        startTime.setValue(timeOnlyDTF.format(this.selectedAppt.getStart().toZonedDateTime()));
        endTime.setValue(timeOnlyDTF.format(this.selectedAppt.getEnd().toZonedDateTime()));
        location.setText(this.selectedAppt.getLocation());
        this.selectedAppt.getClientID();
        clients.stream().filter(apptClient ->
            (apptClient.getID() == this.selectedAppt.getClientID())).forEachOrdered(apptClient ->
                {client.setValue(apptClient);});
        details.setText(this.selectedAppt.getDetails());
        contact.setText(this.selectedAppt.getContact());
        completed.setSelected(this.selectedAppt.getCompleted());
        
        createdDateLabel.setText("Created Date: " + this.selectedAppt.getCreatedDate());
        createdByLabel.setText("Created By: " + this.selectedAppt.getCreatedBy());
        lastUpdateLabel.setText("Last Updated: " + this.selectedAppt.getLastUpdate());
        updatedByLabel.setText("Updated By: " + this.selectedAppt.getLastUpdateBy());
    }
    
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
   
    @FXML
    private void handleSave(ActionEvent event) throws IOException, ParseException {
        if(inputValidation()){
            // if new Appointment
            if(selectedAppt.getAppointmentID() <= 0){
                String query = "INSERT INTO appointment(starttime,endtime,location,client,scheduler,details,completed,contact,createddate,createdby,updatedby,lastupdate)"
                        + "VALUES(?,?,?,?,?,?,?,?,NOW(),?,?,NOW());";

                try {
                    PreparedStatement sqlQuery = conn.prepareStatement(query);
                    String dateToString = apptDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    //combine DatePicker w/ Starttime
                    String sqlStartTime = dateToString + " " + startTime.getValue() + ":00";
                    //combine DatePicker w/ Endtime
                    String sqlEndTime = dateToString + " " + endTime.getValue() + ":00";

                    LocalDateTime ldtStart = LocalDateTime.parse(sqlStartTime, sqlTime);
                    ZonedDateTime startZoned = ldtStart.atZone(ZoneId.systemDefault());
                    ZonedDateTime startUtc = startZoned.withZoneSameInstant(ZoneId.of("UTC"));

                    LocalDateTime ldtEnd = LocalDateTime.parse(sqlEndTime, sqlTime);
                    ZonedDateTime endZoned = ldtEnd.atZone(ZoneId.systemDefault());
                    ZonedDateTime endUtc = endZoned.withZoneSameInstant(ZoneId.of("UTC"));

                    //starttime
                    sqlQuery.setString(1, startUtc.format(sqlTime));
                    //endtime
                    sqlQuery.setString(2, endUtc.format(sqlTime));
                    //location
                    sqlQuery.setString(3, location.getText());
                    //client
                    sqlQuery.setInt(4, client.getValue().getID());
                    //scheduler
                    sqlQuery.setInt(5, LoginScreenController.currentUserId);
                    //details
                    sqlQuery.setString(6, details.getText());
                    //completed
                    sqlQuery.setBoolean(7, completed.isSelected());
                    //contact
                    sqlQuery.setString(8, contact.getText());
                    //createdby
                    sqlQuery.setString(9, LoginScreenController.currentUser);
                    //updtatedby
                    sqlQuery.setString(10, LoginScreenController.currentUser);                 

                    sqlQuery.execute();
                }
                catch (SQLException ex) {
                    System.out.println("SQL Exception thrown... error: " + ex);
                }
            }



             // if updating Appointment
            else if(selectedAppt.getAppointmentID() > 0){
                String query = "UPDATE appointment SET starttime=?, endtime=?, location=?, client=?, scheduler=?, details=?, completed=?, contact=?, updatedby=?, lastupdate=NOW() WHERE id=?";

                try {
                    PreparedStatement sqlQuery = conn.prepareStatement(query);
                    String dateToString = apptDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    //combine DatePicker w/ Starttime
                    String sqlStartTime = dateToString + " " + startTime.getValue() + ":00";
                    //combine DatePicker w/ Endtime
                    String sqlEndTime = dateToString + " " + endTime.getValue() + ":00";

                    LocalDateTime ldtStart = LocalDateTime.parse(sqlStartTime, sqlTime);
                    ZonedDateTime startZoned = ldtStart.atZone(ZoneId.systemDefault());
                    ZonedDateTime startUtc = startZoned.withZoneSameInstant(ZoneId.of("UTC"));

                    LocalDateTime ldtEnd = LocalDateTime.parse(sqlEndTime, sqlTime);
                    ZonedDateTime endZoned = ldtEnd.atZone(ZoneId.systemDefault());
                    ZonedDateTime endUtc = endZoned.withZoneSameInstant(ZoneId.of("UTC"));

                    //starttime
                    sqlQuery.setString(1, startUtc.format(sqlTime));
                    //endtime
                    sqlQuery.setString(2, endUtc.format(sqlTime));
                    //location
                    sqlQuery.setString(3, location.getText());
                    //client
                    sqlQuery.setInt(4, client.getValue().getID());
                    //scheduler
                    sqlQuery.setInt(5, LoginScreenController.currentUserId);
                    //details
                    sqlQuery.setString(6, details.getText());
                    //completed
                    sqlQuery.setBoolean(7, completed.isSelected());
                    //contact
                    sqlQuery.setString(8, contact.getText());
                    //updtatedby
                    sqlQuery.setString(9, LoginScreenController.currentUser);
                    sqlQuery.setInt(10, selectedAppt.getAppointmentID());

                    sqlQuery.execute();
                }
                catch (SQLException ex) {
                    System.out.println("SQL Exception thrown... error: " + ex);
                }
            }

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void handleDelete(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.WARNING,
            "This will permanently delete this Appointment.",
            ButtonType.YES,
            ButtonType.NO);
        alert.setTitle("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.YES) {
            String query = "DELETE FROM appointment WHERE id=?;";


            try {
                PreparedStatement sqlQuery = conn.prepareStatement(query);
                sqlQuery.setInt(1, selectedAppt.getAppointmentID());

                sqlQuery.execute();

            }

            catch (SQLException ex) {
                System.out.println("SQL Exception thrown... error: " + ex);
            }
            
            
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
        }
        else {
            
        }
    }
    
    private boolean inputValidation() throws ParseException {
        if(apptDate.getValue() == null || startTime.toString().isBlank() || endTime.toString().isBlank() || location.getText().isBlank() || client.toString().isBlank() || contact.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields must not be left empty:");
            alert.setContentText("Please ensure all fields are filled with the correct appointment information.");
            alert.showAndWait();
            return false;
        }
        else if (apptDate.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Appointments prior to today cannot be created:");
            alert.setContentText("Please ensure all fields are filled with the correct appointment information.");
            alert.showAndWait();
            return false;
        }
        else if (timeOnlySDF.parse(startTime.getValue()).after(timeOnlySDF.parse(endTime.getValue()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Start time cannot be after End time:");
            alert.setContentText("Please ensure all fields are filled with the correct appointment information.");
            alert.showAndWait();
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public boolean isSameDateTime(Calendar cal1, Calendar cal2) {
    // compare if is the same ERA, YEAR, DAY, HOUR, MINUTE and SECOND
    return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
           && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
           && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
           && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
           && cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE)
           && cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND));
}
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime.setItems(times);
        endTime.setItems(times);
        
        try {
            this.clients = Scheduler.loadClients(clients);
        } catch (SQLException ex) {
            Logger.getLogger(ApptDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.getItems().addAll(clients);
        
        
    }    
    
}
