/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;



/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class AppointmentScreenController implements Initializable {
    
    @FXML
    Button homeButton = new Button();
    @FXML
    Button clientsButton = new Button();
    @FXML
    Button reportsButton = new Button();
    @FXML     
    Button addButton = new Button();
    @FXML
    Button viewButton = new Button();
    @FXML
    Button logoutButton = new Button();
                    
    @FXML
    ChoiceBox<String> searchOptions = new ChoiceBox();
    @FXML
    TextField searchField = new TextField();
    @FXML
    Button searchButton = new Button();
    
    
    @FXML
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, Calendar> column1 = new TableColumn<>("Date");
    @FXML
    private TableColumn<Appointment, Calendar> column2 = new TableColumn<>("Start Time");
    @FXML
    private TableColumn<Appointment, Calendar> column3 = new TableColumn<>("End Time");
    @FXML
    private TableColumn<Appointment, String> column4 = new TableColumn<>("Scheduler");
    @FXML
    private TableColumn<Appointment, String> column5 = new TableColumn<>("Contact");
    @FXML
    private TableColumn<Appointment, String> column6 = new TableColumn<>("Location");
    @FXML
    private TableView appointmentView;
    
    private ObservableList<String> searchChoices = FXCollections.observableArrayList(
        "Date", "Start Time", "End Time", "Contact", "Location"
    );
    
    private SimpleDateFormat dateOnly = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm");
    
    FilteredList<Appointment> searchResults = new FilteredList(appointments, a -> true);
    
    @FXML
    private void handleMainScreen(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
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
    private void handleReports(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Reports.fxml"));
        Parent secondParent = loader.load();
        
        Scene secondScene = new Scene(secondParent);
        Stage window = new Stage();
        
        /* to hide window
        (Stage) ((Node) event.getSource()).getScene().getWindow();
        */

        window.setScene(secondScene);
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
    private void handleApptAdd(ActionEvent event) throws IOException {
        
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
    private void handleApptView(ActionEvent event) throws IOException {
        
        if (appointmentView.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApptDetails.fxml"));
            Parent secondParent = loader.load();

            Appointment selectedAppt = (Appointment) appointmentView.getSelectionModel().getSelectedItem();

            ApptDetailsController controller = loader.getController();
            controller.transferAppt(selectedAppt);

            Scene secondScene = new Scene(secondParent);
            Stage window = new Stage();

            /* to hide window
            (Stage) ((Node) event.getSource()).getScene().getWindow();
            */

            window.setScene(secondScene);
            window.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText("Please select an appointment from the table.");
            alert.setContentText("Please select an appointment from the table prior to clicking the View button. To add a new appointment please click the Add button.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleSearch(ActionEvent event) throws IOException, SQLException {
        // searchResults
        searchResults.removeAll();
        switch(searchOptions.getValue()){
            case "Date":
                searchResults.setPredicate(a -> dateOnly.format(a.getStart().getTime()).contains(searchField.getText().trim()));
                appointmentView.setItems(searchResults);
                break;
            case "Start Time":
                searchResults.setPredicate(a -> timeOnly.format(a.getStart().getTime()).contains(searchField.getText().trim()));
                appointmentView.setItems(searchResults);
                break;
            case "End Time":
                searchResults.setPredicate(a -> timeOnly.format(a.getEnd().getTime()).contains(searchField.getText().trim()));
                appointmentView.setItems(searchResults);
                break;
            case "Contact":
                searchResults.setPredicate(a -> a.getContact().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                appointmentView.setItems(searchResults);
                break;
            case "Location":
                searchResults.setPredicate(a -> a.getLocation().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                appointmentView.setItems(searchResults);
                break;
            default:
                appointmentView.setItems(appointments);
               
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
        
        column1.setCellValueFactory(new PropertyValueFactory<>("start"));
        column1.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(dateOnly.format(item.getTime()));
                }
            }
        });
        column2.setCellValueFactory(new PropertyValueFactory<>("start"));
        column2.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(timeOnly.format(item.getTime()));
                }
            }
        });
        column3.setCellValueFactory(new PropertyValueFactory<>("end"));
        column3.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(timeOnly.format(item.getTime()));
                }
            }
        });
        column4.setCellValueFactory(new PropertyValueFactory<>("scheduler"));
        column5.setCellValueFactory(new PropertyValueFactory<>("contact"));
        column6.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        // populate search options
        searchOptions.setItems(searchChoices);
        searchOptions.setValue("Date");
        
        // populate tableview with appointment table
        try{
            // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
            appointmentView.getItems().clear();
            this.appointments.clear();
            this.appointments = Scheduler.loadAppts(appointments);
            appointmentView.setItems(appointments);


        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          
    
}
