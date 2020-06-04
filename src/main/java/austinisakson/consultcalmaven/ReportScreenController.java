/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import static austinisakson.consultcalmaven.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author ajisa
 */



public class ReportScreenController implements Initializable {
    @FXML
    private ChoiceBox reportDropdown;

    private void generateApptByType() throws SQLException, IOException{
        ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT type, MONTHNAME(start) as 'Month', COUNT(*) as 'Total' FROM appointment GROUP BY type, MONTH(start)");
        File tmpdir = new File("./appts-by-type.csv");
        if (tmpdir.exists())
            tmpdir.delete();
        FileWriter write = new FileWriter("appts-by-type.csv", true);
        PrintWriter print = new PrintWriter(write);
        print.println("Appointment Type, Month, Quantity");
        while (appointmentTable.next())            
            print.println(appointmentTable.getString("type") + ", " + appointmentTable.getString("Month") + ", " + appointmentTable.getString("Total"));
        print.close();
    }
        
    
    private void generateUserSchedule() throws SQLException, IOException{
        ResultSet contactSchedules = conn.createStatement().executeQuery("SELECT appointment.contact, appointment.title, customer.customerName, start, end FROM appointment JOIN customer ON customer.customerId = appointment.customerId GROUP BY appointment.contact, MONTH(start), start");
        File tmpdir = new File("./sched-by-contact.csv");
        if (tmpdir.exists())
            tmpdir.delete();
        FileWriter write = new FileWriter("sched-by-contact.csv", true);
        PrintWriter print = new PrintWriter(write);
        print.println("Contact, Title, Customer, Start Time, End Time");
        while (contactSchedules.next())            
            print.println(contactSchedules.getString("appointment.contact") + ", " + contactSchedules.getString("appointment.title") + ", " + contactSchedules.getString("customer.customerName") + ", " + contactSchedules.getString("start") + ", " + contactSchedules.getString("end"));
        print.close();
    }
    
    private void generateCustomerApptsTotal() throws SQLException, IOException{
        ResultSet customerList = conn.createStatement().executeQuery("SELECT customer.customerid, customer.customerName, COUNT(*) as 'Total' FROM appointment JOIN customer ON customer.customerId = appointment.customerId GROUP BY customerId");
        File tmpdir = new File("./customer-list.csv");
        if (tmpdir.exists())
            tmpdir.delete();
        FileWriter write = new FileWriter("customer-list.csv", true);
        PrintWriter print = new PrintWriter(write);
        print.println("Customer ID, Name, Total Appointments");
        while (customerList.next())            
            print.println(customerList.getString("customer.customerid") + ", " + customerList.getString("customer.customerName") + ", " + customerList.getString("Total"));
        print.close();
    }
    
    @FXML
    private void handleGenerate(ActionEvent event) throws IOException, SQLException {
        if (reportDropdown.getValue() == "Number of Appointments"){
            generateApptByType();
        }
        else if (reportDropdown.getValue() == "Schedule By Consultant"){
            generateUserSchedule();
        }
        else if (reportDropdown.getValue() == "All Customers List"){
            generateCustomerApptsTotal();
        }
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
        // TODO
        ObservableList<String> data = FXCollections.observableArrayList("Number of Appointments", "Schedule By Consultant", "All Customers List");
        reportDropdown.setItems(data);
    }    
    
}
