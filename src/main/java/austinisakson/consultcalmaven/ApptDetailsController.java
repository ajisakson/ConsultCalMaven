package austinisakson.consultcalmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
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
    private ChoiceBox startTime;
    @FXML
    private ChoiceBox endTime;
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
    
    @FXML
    Button saveButton = new Button();
    @FXML
    Button exitButton = new Button();
    
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
