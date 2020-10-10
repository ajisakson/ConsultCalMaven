/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
}
