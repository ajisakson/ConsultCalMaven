/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static austinisakson.consultcalmaven.DBConnection.conn;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class ClientScreenController implements Initializable {

    private int clientID = 0;
    
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
    Button adminButton = new Button();
    @FXML
    Button logoutButton = new Button();
    @FXML
    Button searchButton = new Button();
    
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField address2Field;
    @FXML
    private ChoiceBox cityField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField phoneField;
    @FXML
    private ObservableList<Client> clients = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Location> addresses = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> options = FXCollections.observableArrayList();
    @FXML
    private TableView clientView;
    @FXML
    private TableColumn<Client, String> column1 = new TableColumn<>("Contact Name");
    @FXML
    private TableColumn<Client, String> column2 = new TableColumn<>("Email");
    @FXML
    private TableColumn<Client, String> column3 = new TableColumn<>("Phone");
    @FXML
    private TableColumn<Client, String> column4 = new TableColumn<>("Location");
           
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
    private void handleSearch(ActionEvent event) throws IOException {
        
    }
    
    /*
    @FXML
    private void handleAddSave(ActionEvent event) throws SQLException {
        // if cust ID is not null, then use SQL update query with info
        if (nameField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating client:");
            alert.setContentText("Name cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Name cannot be blank!");
        }
        else if (addressField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating client:");
            alert.setContentText("Address cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Address cannot be blank!");
        }
        else if (cityField.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating client:");
            alert.setContentText("Please select a city!");
            alert.showAndWait();
            throw new IllegalArgumentException("City cannot be blank!");
        }
        else if (postalCodeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating client:");
            alert.setContentText("Postal code cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code cannot be blank!");
        }
        else if (phoneField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating client:");
            alert.setContentText("Please enter a phone number!");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone cannot be blank!");
        }
        else{
            
            if (clientID > 0){
                //update row for where custid = sql id on cust table
                String query = "UPDATE client SET clientName = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE clientid = ?";
                PreparedStatement updateRow = conn.prepareStatement(query);
                updateRow.setString(1, nameField.getText());
                updateRow.setString(2, LoginScreenController.currentUser);
                updateRow.setInt(3, clientID);

                String query2 = "UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE addressid = ?";
                PreparedStatement updateRow2 = conn.prepareStatement(query2);
                updateRow2.setString(1, addressField.getText());
                updateRow2.setString(2, address2Field.getText());

                int cityID = 0;
                switch (cityField.getValue().toString()){
                    case "Pheonix":
                        cityID = 1;
                        break;
                    case "New York":
                        cityID = 2;
                        break;
                    case "London":
                        cityID = 3;
                        break;    
                }
                updateRow2.setInt(3, cityID);

                updateRow2.setString(4, postalCodeField.getText());
                updateRow2.setString(5, phoneField.getText());
                updateRow2.setString(6, LoginScreenController.currentUser);
                updateRow2.setInt(7, addressID);
                updateRow.execute();
                updateRow2.execute();
            }

            // if cust ID is null, create new one with given info
            else {
                //insert row
                String query = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";

                PreparedStatement insertAddress = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                insertAddress.setString(1, addressField.getText());
                insertAddress.setString(2, address2Field.getText());
                int cityID = 0;
                switch (cityField.getValue().toString()){
                    case "Pheonix":
                        cityID = 1;
                        break;
                    case "New York":
                        cityID = 2;
                        break;
                    case "London":
                        cityID = 3;
                        break;    
                }
                insertAddress.setInt(3, cityID);
                insertAddress.setString(4, postalCodeField.getText());
                insertAddress.setString(5, phoneField.getText());
                insertAddress.setString(6, LoginScreenController.currentUser);
                insertAddress.setString(7, LoginScreenController.currentUser);
                insertAddress.execute();

                ResultSet rs = insertAddress.getGeneratedKeys();
                int generatedKey = 0;
                if(rs.next()) {
                    generatedKey = rs.getInt(1);
                }

                query = "INSERT INTO client (clientName, addressID, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, NOW(), ?, NOW(), ?)";
                PreparedStatement insertclient = conn.prepareStatement(query);
                // FIX THIS SQL QUERY AND STUFF
                insertclient.setString(1, nameField.getText());
                insertclient.setInt(2, generatedKey);
                insertclient.setString(3, LoginScreenController.currentUser);
                insertclient.setString(4, LoginScreenController.currentUser);
                insertclient.execute();
            }

            clientID = 0;
            addressID = 0;
            nameField.clear();
            addressField.clear();
            address2Field.clear();
            cityField.setValue(null);
            postalCodeField.clear();
            phoneField.clear();

            // refresh tableview
            clientView.getItems().clear();
            clients.clear();
            addresses.clear();

            ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM client");
            while (custTable.next()){

                Client newclient = new Client();
                newclient.setID(custTable.getInt("clientid"));
                newclient.setclient(custTable.getString("clientName"));
                newclient.setAddressID(custTable.getInt("addressID"));
                newclient.setActive(custTable.getBoolean("active"));
                newclient.setCreatedDate(custTable.getDate("createDate"));
                newclient.setCreatedBy(custTable.getString("createdBy"));
                newclient.setLastUpdate(custTable.getTimestamp("lastUpdate"));
                newclient.setLastUpdateBy(custTable.getString("lastUpdateBy"));
                clients.add(newclient);
            }

            ResultSet addressSet = conn.createStatement().executeQuery("SELECT * FROM address");
            while (addressSet.next()){

                Location newAddress = new Location();
                newAddress.setID(addressSet.getInt("addressid"));
                newAddress.setAddress(addressSet.getString("address"));
                newAddress.setAddress2(addressSet.getString("address2"));
                newAddress.setCityID(addressSet.getInt("cityId"));
                newAddress.setPostalCode(addressSet.getString("postalCode"));
                newAddress.setPhone(addressSet.getString("phone"));
                newAddress.setCreatedDate(addressSet.getDate("createDate"));
                newAddress.setCreatedBy(addressSet.getString("createdBy"));
                newAddress.setLastUpdate(addressSet.getTimestamp("lastUpdate"));
                newAddress.setLastUpdateBy(addressSet.getString("lastUpdateBy"));
                addresses.add(newAddress);
            }

            clientView.setItems(clients);
        }
    }
    
    @FXML
    private void handleUpdate(ActionEvent event) {
        
        // take selected value from tableview and populate the left side text fields with the info to be editable
        Client selectedclient = (Client) clientView.getSelectionModel().getSelectedItem();
        clientID = selectedclient.getID();
        addressID = selectedclient.getAddressID();
        nameField.setText(selectedclient.getclientName());
        Location selectedAddress = new Location();
        for (Location address : addresses){
            if (addressID == address.getID()){
                selectedAddress = address;
            }
        }
        addressField.setText(selectedAddress.getAddress());
        address2Field.setText(selectedAddress.getAddress2());
        switch(selectedAddress.getCityID()){
            case 1:
                cityField.setValue("Pheonix");
                break;
            case 2:
                cityField.setValue("New York");
                break;
            case 3:
                cityField.setValue("London");
                break;
        }
        postalCodeField.setText(selectedAddress.getPostalCode());
        phoneField.setText(selectedAddress.getPhone());
        
    }
    
    @FXML
    private void handleDelete(ActionEvent event) throws SQLException {
        // delete selected client from the SQL table
        
        Client selectedclient = (Client) clientView.getSelectionModel().getSelectedItem();
        int deleteclientID = selectedclient.getID();
        String query = "DELETE FROM client WHERE clientid = ?";
        PreparedStatement deleteRow = conn.prepareStatement(query);
        deleteRow.setInt(1, deleteclientID);
        deleteRow.execute();
        clients.remove(clientView.getSelectionModel().getSelectedItem());
        
        
        // refresh table view
        clientID = 0;
        addressID = 0;
        nameField.clear();
        addressField.clear();
        address2Field.clear();
        cityField.setValue(null);
        postalCodeField.clear();
        phoneField.clear();
        clientView.getItems().clear();
            
        ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM client");
        while (custTable.next()){
            Client newclient = new Client();
            newclient.setID(custTable.getInt("clientid"));
            newclient.setclient(custTable.getString("clientName"));
            newclient.setAddressID(custTable.getInt("addressID"));
            newclient.setActive(custTable.getBoolean("active"));
            newclient.setCreatedDate(custTable.getDate("createDate"));
            newclient.setCreatedBy(custTable.getString("createdBy"));
            newclient.setLastUpdate(custTable.getTimestamp("lastUpdate"));
            newclient.setLastUpdateBy(custTable.getString("lastUpdateBy"));
            clients.add(newclient);
            System.out.println("New client added from database!");
        }

        clientView.setItems(clients);
    }
    
    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    */
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            /*
            // Query result set for Address Table, load each address as an object of the Address class
            int addressCount = 0;
            ResultSet addressSet = conn.createStatement().executeQuery("SELECT * FROM address");
            while (addressSet.next()){
                Location newAddress = new Location();
                newAddress.setID(addressSet.getInt("addressid"));
                newAddress.setAddress(addressSet.getString("address"));
                newAddress.setAddress2(addressSet.getString("address2"));
                newAddress.setCityID(addressSet.getInt("cityId"));
                newAddress.setPostalCode(addressSet.getString("postalCode"));
                newAddress.setPhone(addressSet.getString("phone"));
                newAddress.setCreatedDate(addressSet.getDate("createDate"));
                newAddress.setCreatedBy(addressSet.getString("createdBy"));
                newAddress.setLastUpdate(addressSet.getTimestamp("lastUpdate"));
                newAddress.setLastUpdateBy(addressSet.getString("lastUpdateBy"));
                addresses.add(newAddress);
                addressCount++;
            }
            System.out.println(addressCount + " addresses added from the database.");
            */
            
            
            /*
            // Query result set for City Table, load each city as an object of the City class
            int cityCount = 0;
            ResultSet citySet = conn.createStatement().executeQuery("SELECT * FROM city");            
            while (citySet.next()){
                City newCity = new City();
                newCity.setID(citySet.getInt("cityid"));
                newCity.setCity(citySet.getString("city"));
                newCity.setCountryID(citySet.getInt("countryId"));
                newCity.setCreatedDate(citySet.getDate("createDate"));
                newCity.setCreatedBy(citySet.getString("createdBy"));
                newCity.setLastUpdate(citySet.getTimestamp("lastUpdate"));
                newCity.setLastUpdateBy(citySet.getString("lastUpdateBy"));
                cityCount++;
            }
            System.out.println(cityCount + " cities added from the database.");
            
            int countryCount = 0;
            ResultSet countrySet = conn.createStatement().executeQuery("SELECT * FROM country");            
            while (countrySet.next()){
                Country newCountry = new Country();
                newCountry.setID(countrySet.getInt("countryid"));
                newCountry.setCountry(countrySet.getString("country"));
                newCountry.setCreatedDate(countrySet.getDate("createDate"));
                newCountry.setCreatedBy(countrySet.getString("createdBy"));
                newCountry.setLastUpdate(countrySet.getTimestamp("lastUpdate"));
                newCountry.setLastUpdateBy(countrySet.getString("lastUpdateBy"));
                countryCount++;
            }
            System.out.println(countryCount + " countries added from the database.");
            */
            
            
            /*
            options.add("Pheonix");
            options.add("New York");
            options.add("London");
            cityField.setItems(options);
            */
            
            // populate Client table
            column1.setCellValueFactory(new PropertyValueFactory<>("contact_name"));
            column2.setCellValueFactory(new PropertyValueFactory<>("contact_email"));
            column3.setCellValueFactory(new PropertyValueFactory<>("contact_phone"));
            column4.setCellValueFactory(new PropertyValueFactory<>("location"));
            
            // Query result set for client Table, load each client as an object of the client class, display as a TableView
            clientView.getItems().clear();
            
            int clientCount = 0;
            ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM client");
            while (custTable.next()){
                Client newClient = new Client();
                newClient.setID(custTable.getInt("clientID"));
                newClient.setClient(custTable.getString("clientName"));
                newClient.setAddressID(custTable.getInt("addressID"));
                newClient.setActive(custTable.getBoolean("active"));
                newClient.setCreatedDate(custTable.getDate("createDate"));
                newClient.setCreatedBy(custTable.getString("createdBy"));
                newClient.setLastUpdate(custTable.getTimestamp("lastUpdate"));
                newClient.setLastUpdateBy(custTable.getString("lastUpdateBy"));
                clients.add(newClient);
                clientCount++;
            }
            System.out.println(clientCount + " clients added from the database.");
            
            clientView.setItems(clients);
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
