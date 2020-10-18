/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ajisa
 */
public class ClientDetailsController implements Initializable {
    
       // Declare FXML Elements

    @FXML
    private TextField contactName;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private TextField location;
    @FXML
    private CheckBox active;
    
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
    private Client selectedClient;
    
    public void transferClient(Client selectedClient){
        this.selectedClient = selectedClient;
        contactName.setText(selectedClient.getContactName());
        email.setText(selectedClient.getContactEmail());
        phone.setText(selectedClient.getContactPhone());
        location.setText(selectedClient.getLocation());
        active.setSelected(selectedClient.getActive());
        createdDateLabel.setText("Created Date: " + selectedClient.getCreatedDate());
        createdByLabel.setText("Created By: " + selectedClient.getCreatedBy());
        lastUpdateLabel.setText("Last Updated: " + selectedClient.getLastUpdate());
        updatedByLabel.setText("Updated By: " + selectedClient.getLastUpdateBy());
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


    } 
    
}
