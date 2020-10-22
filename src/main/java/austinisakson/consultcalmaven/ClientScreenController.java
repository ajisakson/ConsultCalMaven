/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class ClientScreenController implements Initializable {
    
    @FXML
    Button homeButton = new Button();
    @FXML
    Button manageAppts = new Button();
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
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Client, String> column1 = new TableColumn<>("Contact Name");
    @FXML
    private TableColumn<Client, String> column2 = new TableColumn<>("Email");
    @FXML
    private TableColumn<Client, String> column3 = new TableColumn<>("Phone");
    @FXML
    private TableColumn<Client, String> column4 = new TableColumn<>("Location");
    @FXML
    private TableView clientView = new TableView();
    
    private ObservableList<String> searchChoices = FXCollections.observableArrayList(
        "Name", "Email", "Location", "Phone"
    );
    
    FilteredList<Client> searchResults = new FilteredList(clients, c -> true);
    
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
    private void handleReports(ActionEvent event) throws IOException {

        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/ReportScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleClientAdd(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientDetails.fxml"));
        Parent secondParent = loader.load();
        
        Scene secondScene = new Scene(secondParent);
        Stage window = new Stage();
        
        ClientDetailsController controller = loader.getController();
        
        controller.screenTitle.setText("Add Client");
        controller.deleteButton.setDisable(true);
        
        /* to hide window
        (Stage) ((Node) event.getSource()).getScene().getWindow();
        */

        window.setScene(secondScene);
        window.show();
    }
    
    @FXML
    private void handleClientView(ActionEvent event) throws IOException {
        
        if (clientView.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ClientDetails.fxml"));
            Parent secondParent = loader.load();

            Client selectedClient = (Client) clientView.getSelectionModel().getSelectedItem();

            ClientDetailsController controller = loader.getController();
            controller.transferClient(selectedClient);
            controller.screenTitle.setText("Update or View Client");

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
            alert.setTitle("No Client Selected");
            alert.setHeaderText("Please select a client from the table.");
            alert.setContentText("Please select a client from the table prior to clicking the View button. To add a new client please click the Add button.");
            alert.showAndWait();
        }
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
    private void handleSearch(ActionEvent event) throws IOException, SQLException {
        // searchResults
        searchResults.removeAll();
        switch(searchOptions.getValue()){
            case "Name" ->  {
                searchResults.setPredicate(c -> c.getContactName().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                clientView.setItems(searchResults);
            }
            case "Email" ->  {
                searchResults.setPredicate(c -> c.getContactEmail().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                clientView.setItems(searchResults);
            }
            case "Phone" ->  {
                searchResults.setPredicate(c -> c.getContactPhone().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                clientView.setItems(searchResults);
            }
            case "Location" ->  {
                searchResults.setPredicate(c -> c.getLocation().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
                clientView.setItems(searchResults);
            }
               
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        column1.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        column2.setCellValueFactory(new PropertyValueFactory<>("contactEmail"));
        column3.setCellValueFactory(new PropertyValueFactory<>("contactPhone"));
        column4.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        // populate search options
        searchOptions.setItems(searchChoices);
        searchOptions.setValue("Name");
        
        // populate Client table
        try {  
            // Query result set for client Table, load each client as an object of the client class, display as a TableView
            clientView.getItems().clear();
            this.clients.clear();
            this.clients = Scheduler.loadClients(clients);
            clientView.setItems(clients);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
