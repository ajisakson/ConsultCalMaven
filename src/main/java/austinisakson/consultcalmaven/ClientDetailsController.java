/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import static austinisakson.consultcalmaven.DBConnection.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private ObservableList<Client> clients = FXCollections.observableArrayList();
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
    Label screenTitle = new Label();
    @FXML
    Label createdDateLabel = new Label();
    @FXML
    Label createdByLabel = new Label();
    @FXML
    Label lastUpdateLabel = new Label();
    @FXML
    Label updatedByLabel = new Label();
    
    @FXML
    private Client selectedClient = new Client();
    
    private boolean isValid;
    
    /**
     *
     * @param selectedClient
     */
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
    
    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        if (inputValidation()) {
            // if new Client
            if(selectedClient.getID() <= 0) {
                String query = "INSERT INTO client(contact_name, contact_email, contact_phone, location, active, create_time, created_by, last_update, updated_by) "
                                         + "VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW(), ?);";

                try {
                    PreparedStatement sqlQuery = conn.prepareStatement(query);

                    sqlQuery.setString(1, contactName.getText());
                    sqlQuery.setString(2, email.getText());
                    sqlQuery.setString(3, phone.getText());
                    sqlQuery.setString(4, location.getText());
                    sqlQuery.setBoolean(5, active.isSelected());
                    sqlQuery.setString(6, LoginScreenController.currentUser);
                    sqlQuery.setString(7, LoginScreenController.currentUser);

                    sqlQuery.execute();
                }
                catch (SQLException ex) {
                    System.out.println("SQL Exception thrown... error: " + ex);
                }
            }

            // if updating Client
            else if(selectedClient.getID() > 0){
                String query = "UPDATE client SET contact_name=?, contact_email=?, contact_phone=?, location=?, active=?, last_update=NOW(), updated_by=? WHERE id=?";

                try {
                    PreparedStatement sqlQuery = conn.prepareStatement(query);

                    sqlQuery.setString(1, contactName.getText());
                    sqlQuery.setString(2, email.getText());
                    sqlQuery.setString(3, phone.getText());
                    sqlQuery.setString(4, location.getText());
                    sqlQuery.setBoolean(5, active.isSelected());
                    sqlQuery.setString(6, LoginScreenController.currentUser);
                    sqlQuery.setInt(7, selectedClient.getID());

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
            "This will permanently delete this Client.",
            ButtonType.YES,
            ButtonType.NO);
        alert.setTitle("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.YES) {
            String query = "DELETE FROM client WHERE id=?;";

            try {
                PreparedStatement sqlQuery = conn.prepareStatement(query);
                sqlQuery.setInt(1, selectedClient.getID());

                sqlQuery.execute();
            }

            catch (SQLException ex) {
                System.out.println("SQL Exception thrown... error: " + ex);
            }
            
            Stage stage = (Stage) deleteButton.getScene().getWindow();
            stage.close();
        }
    }
    
    private boolean inputValidation(){
        if(contactName.getText().isBlank() || email.getText().isBlank() || location.getText().isBlank()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Fields must not be left empty:");
            alert.setContentText("Please ensure all fields are filled with the client's information.");
            alert.showAndWait();
            return false;
        }

        else {
            return true;
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            this.clients = Scheduler.loadClients(clients);
        } catch (SQLException ex) {
            Logger.getLogger(ClientDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    
}
