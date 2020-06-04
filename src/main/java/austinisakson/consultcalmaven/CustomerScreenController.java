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

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class CustomerScreenController implements Initializable {

    private int customerID = 0;
    private int addressID = 0;
    
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
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Address> addresses = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> options = FXCollections.observableArrayList();
    @FXML
    private TableView customerView;
    @FXML
    private TableColumn<Customer, String> column1 = new TableColumn<>("Name");
           
   
    
    @FXML
    private void handleAddSave(ActionEvent event) throws SQLException {
        // if cust ID is not null, then use SQL update query with info
        if (nameField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating customer:");
            alert.setContentText("Name cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Name cannot be blank!");
        }
        else if (addressField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating customer:");
            alert.setContentText("Address cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Address cannot be blank!");
        }
        else if (cityField.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating customer:");
            alert.setContentText("Please select a city!");
            alert.showAndWait();
            throw new IllegalArgumentException("City cannot be blank!");
        }
        else if (postalCodeField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating customer:");
            alert.setContentText("Postal code cannot be blank!");
            alert.showAndWait();
            throw new IllegalArgumentException("Postal Code cannot be blank!");
        }
        else if (phoneField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating customer:");
            alert.setContentText("Please enter a phone number!");
            alert.showAndWait();
            throw new IllegalArgumentException("Phone cannot be blank!");
        }
        else{
            
            if (customerID > 0){
                //update row for where custid = sql id on cust table
                String query = "UPDATE customer SET customerName = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE customerid = ?";
                PreparedStatement updateRow = conn.prepareStatement(query);
                updateRow.setString(1, nameField.getText());
                updateRow.setString(2, LoginScreenController.currentUser);
                updateRow.setInt(3, customerID);

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

                query = "INSERT INTO customer (customerName, addressID, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, NOW(), ?, NOW(), ?)";
                PreparedStatement insertCustomer = conn.prepareStatement(query);
                // FIX THIS SQL QUERY AND STUFF
                insertCustomer.setString(1, nameField.getText());
                insertCustomer.setInt(2, generatedKey);
                insertCustomer.setString(3, LoginScreenController.currentUser);
                insertCustomer.setString(4, LoginScreenController.currentUser);
                insertCustomer.execute();
            }

            customerID = 0;
            addressID = 0;
            nameField.clear();
            addressField.clear();
            address2Field.clear();
            cityField.setValue(null);
            postalCodeField.clear();
            phoneField.clear();

            // refresh tableview
            customerView.getItems().clear();
            customers.clear();
            addresses.clear();

            ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM customer");
            while (custTable.next()){

                Customer newCustomer = new Customer();
                newCustomer.setID(custTable.getInt("customerid"));
                newCustomer.setCustomer(custTable.getString("customerName"));
                newCustomer.setAddressID(custTable.getInt("addressID"));
                newCustomer.setActive(custTable.getBoolean("active"));
                newCustomer.setCreatedDate(custTable.getDate("createDate"));
                newCustomer.setCreatedBy(custTable.getString("createdBy"));
                newCustomer.setLastUpdate(custTable.getTimestamp("lastUpdate"));
                newCustomer.setLastUpdateBy(custTable.getString("lastUpdateBy"));
                customers.add(newCustomer);
            }

            ResultSet addressSet = conn.createStatement().executeQuery("SELECT * FROM address");
            while (addressSet.next()){

                Address newAddress = new Address();
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

            customerView.setItems(customers);
        }
    }
    
    @FXML
    private void handleUpdate(ActionEvent event) {
        
        // take selected value from tableview and populate the left side text fields with the info to be editable
        Customer selectedCustomer = (Customer) customerView.getSelectionModel().getSelectedItem();
        customerID = selectedCustomer.getID();
        addressID = selectedCustomer.getAddressID();
        nameField.setText(selectedCustomer.getCustomerName());
        Address selectedAddress = new Address();
        for (Address address : addresses){
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
        // delete selected customer from the SQL table
        
        Customer selectedCustomer = (Customer) customerView.getSelectionModel().getSelectedItem();
        int deleteCustomerID = selectedCustomer.getID();
        String query = "DELETE FROM customer WHERE customerid = ?";
        PreparedStatement deleteRow = conn.prepareStatement(query);
        deleteRow.setInt(1, deleteCustomerID);
        deleteRow.execute();
        customers.remove(customerView.getSelectionModel().getSelectedItem());
        
        
        // refresh table view
        customerID = 0;
        addressID = 0;
        nameField.clear();
        addressField.clear();
        address2Field.clear();
        cityField.setValue(null);
        postalCodeField.clear();
        phoneField.clear();
        customerView.getItems().clear();
            
        ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM customer");
        while (custTable.next()){
            Customer newCustomer = new Customer();
            newCustomer.setID(custTable.getInt("customerid"));
            newCustomer.setCustomer(custTable.getString("customerName"));
            newCustomer.setAddressID(custTable.getInt("addressID"));
            newCustomer.setActive(custTable.getBoolean("active"));
            newCustomer.setCreatedDate(custTable.getDate("createDate"));
            newCustomer.setCreatedBy(custTable.getString("createdBy"));
            newCustomer.setLastUpdate(custTable.getTimestamp("lastUpdate"));
            newCustomer.setLastUpdateBy(custTable.getString("lastUpdateBy"));
            customers.add(newCustomer);
            System.out.println("New customer added from database!");
        }

        customerView.setItems(customers);
    }
    
    @FXML
    private void handleExit(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Query result set for Address Table, load each address as an object of the Address class
            int addressCount = 0;
            ResultSet addressSet = conn.createStatement().executeQuery("SELECT * FROM address");
            while (addressSet.next()){
                Address newAddress = new Address();
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
            
            options.add("Pheonix");
            options.add("New York");
            options.add("London");
            cityField.setItems(options);
            
            // populate customer table
            column1.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            
            // Query result set for Customer Table, load each customer as an object of the Customer class, display as a TableView
            customerView.getItems().clear();
            
            int customerCount = 0;
            ResultSet custTable = conn.createStatement().executeQuery("SELECT * FROM customer");
            while (custTable.next()){
                Customer newCustomer = new Customer();
                newCustomer.setID(custTable.getInt("customerid"));
                newCustomer.setCustomer(custTable.getString("customerName"));
                newCustomer.setAddressID(custTable.getInt("addressID"));
                newCustomer.setActive(custTable.getBoolean("active"));
                newCustomer.setCreatedDate(custTable.getDate("createDate"));
                newCustomer.setCreatedBy(custTable.getString("createdBy"));
                newCustomer.setLastUpdate(custTable.getTimestamp("lastUpdate"));
                newCustomer.setLastUpdateBy(custTable.getString("lastUpdateBy"));
                customers.add(newCustomer);
                customerCount++;
            }
            System.out.println(customerCount + " customers added from the database.");
            
            customerView.setItems(customers);
            
        } catch (SQLException ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}