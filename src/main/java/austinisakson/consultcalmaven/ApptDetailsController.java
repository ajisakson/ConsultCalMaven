package austinisakson.consultcalmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private DatePicker apptDate;
    @FXML
    private ChoiceBox<String> startTime = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> endTime = new ChoiceBox<String>();
    @FXML
    private TextField location;
    @FXML
    private ComboBox client;
    @FXML
    private TextArea details;
    @FXML
    private TextField contact;
    @FXML
    private CheckBox completed;

    private ObservableList<String> times = FXCollections.observableArrayList(
        "09:00", "09:15", "09:30", "09:45", 
        "10:00", "10:15", "10:30", "10:45",
        "11:00", "11:15", "11:30", "11:45", 
        "12:00", "12:15", "12:30", "12:45", 
        "13:00", "13:15", "13:30", "13:45", 
        "14:00", "14:15", "14:30", "14:45", 
        "15:00", "15:15", "15:30", "15:45", 
        "16:00", "16:15", "16:30", "16:45", 
        "17:00");
    
    
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
    private Appointment selectedAppt;
    
    SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
    DateTimeFormatter timeOnly = DateTimeFormatter.ofPattern("HH:mm");
    
    public void transferAppt(Appointment selectedAppt){
        this.selectedAppt = selectedAppt;
        apptDate.setValue(selectedAppt.getStart().toZonedDateTime().toLocalDate());
        startTime.setValue(timeOnly.format(selectedAppt.getStart().toZonedDateTime()));
        endTime.setValue(timeOnly.format(selectedAppt.getEnd().toZonedDateTime()));
        location.setText(selectedAppt.getLocation());
        client.setValue(selectedAppt.getClientID());
        details.setText(selectedAppt.getDetails());
        contact.setText(selectedAppt.getContact());
        completed.setSelected(selectedAppt.getCompleted());
        
        createdDateLabel.setText("Created Date: " + selectedAppt.getCreatedDate());
        createdByLabel.setText("Created By: " + selectedAppt.getCreatedBy());
        lastUpdateLabel.setText("Last Updated: " + selectedAppt.getLastUpdate());
        updatedByLabel.setText("Updated By: " + selectedAppt.getLastUpdateBy());
    }
    
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
   
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime.setItems(times);
        endTime.setItems(times);
    }    
    
}
