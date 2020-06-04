/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package austinisakson.consultcalmaven;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import static java.util.Calendar.MINUTE;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static austinisakson.consultcalmaven.DBConnection.conn;

/**
 * FXML Controller class
 *
 * @author ajisa
 */
public class MainScreenController implements Initializable {

    @FXML
    final ToggleGroup group = new ToggleGroup();
    @FXML
    RadioButton thisWeek = new RadioButton();        
    @FXML
    RadioButton thisMonth = new RadioButton();
    @FXML
    Button manageCustomers = new Button();
    @FXML
    Button manageAppts = new Button();
    @FXML
    Button reports = new Button();
    @FXML
    private ObservableList<Appointment> data = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointment> allUpcoming = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, Integer> column1 = new TableColumn<>("Title");
    @FXML
    private TableColumn<Appointment, Calendar> column2 = new TableColumn<>("Start Time");
    @FXML
    private TableColumn<Appointment, String> column3 = new TableColumn<>("Location");
    @FXML
    private TableColumn<Appointment, String> column4 = new TableColumn<>("Contact");
    @FXML
    private TableView appointmentView;
    
    private static final String timeFromCal(GregorianCalendar cal){
        String time = "";
        time += String.format("%02d", cal.get(GregorianCalendar.HOUR_OF_DAY)) + ":";
        time += String.format("%02d", cal.get(GregorianCalendar.MINUTE));
        
        return time;
    }
    
    private static LocalDate calToLocalDate(GregorianCalendar cal){
        TimeZone tz = cal.getTimeZone();
        ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
        LocalDate localDate = LocalDateTime.ofInstant(cal.toInstant(), zid).toLocalDate();
        return localDate;
    }
   
    private void alert15Min(ObservableList<Appointment> allUpcoming){
        for (Appointment appointment : allUpcoming){
            
            Appointment appointment2 = appointment;
            appointment2.getStart().add(MINUTE, -15);
            
            GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();
            
            if ((calendar.after(appointment2.getStart())) && (appointment.getUserID() == LoginScreenController.currentUserId)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming Appointment");
                alert.setHeaderText("You have an appointment starting soon.");
                alert.setContentText("Your calendar shows an appointment beginning within the next 15 minutes!");
                alert.showAndWait();
                System.out.println("Upcoming appointment alert.");
                
                break;
            }
        }
    }
    
    
    @FXML
    private void handleManageAppointments(ActionEvent event) throws IOException {
        
        Parent mainParent = FXMLLoader.load(getClass().getResource("AppointmentScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleReports(ActionEvent event) throws IOException {

        Parent mainParent = FXMLLoader.load(getClass().getResource("ReportScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
    
    @FXML
    private void handleGenerate(ActionEvent event) throws IOException {
        
        // if radio selected this week, generate all of this week's appointments in the table view
        if (thisWeek.isSelected()){
            try {
                appointmentView.getItems().clear();
                // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
                ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment WHERE start >= NOW() AND start < NOW() + INTERVAL 1 WEEK");
                while (appointmentTable.next()){
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(appointmentTable.getInt("appointmentid"));
                    newAppointment.setCustomerID(appointmentTable.getInt("customerId"));
                    newAppointment.setUserID(appointmentTable.getInt("userID"));
                    newAppointment.setTitle(appointmentTable.getString("title"));
                    newAppointment.setDescription(appointmentTable.getString("description"));
                    newAppointment.setLocation(appointmentTable.getString("location"));
                    newAppointment.setContact(appointmentTable.getString("contact"));
                    newAppointment.setType(appointmentTable.getString("type"));
                    newAppointment.setURL(appointmentTable.getString("url"));
                    
                    Timestamp startStamp = appointmentTable.getTimestamp("start");
                    GregorianCalendar startCal = (GregorianCalendar) GregorianCalendar.getInstance();
                    startCal.setTimeInMillis(startStamp.getTime());
                    newAppointment.setStart(startCal);
                    Timestamp endStamp = appointmentTable.getTimestamp("end");
                    GregorianCalendar endCal = (GregorianCalendar) GregorianCalendar.getInstance();
                    endCal.setTimeInMillis(endStamp.getTime());
                    newAppointment.setEnd(endCal);
                    
                    newAppointment.setCreatedDate(appointmentTable.getDate("createDate"));
                    newAppointment.setCreatedBy(appointmentTable.getString("createdBy"));
                    newAppointment.setLastUpdate(appointmentTable.getTimestamp("lastUpdate"));
                    newAppointment.setLastUpdateBy(appointmentTable.getString("lastUpdateBy"));
                    data.add(newAppointment);
                    System.out.println("New appointment added from database!");
                }
                
                appointmentView.setItems(data);
            
            }
            catch (SQLException ex) {
                Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // if radio selected this month, generate all of this month's appointments in the table view
        else if (thisMonth.isSelected()){
            try {
                appointmentView.getItems().clear();
                // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
                ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment WHERE start >= NOW() AND start < NOW() + INTERVAL 1 MONTH");
                while (appointmentTable.next()){
                    Appointment newAppointment = new Appointment();
                    newAppointment.setAppointmentID(appointmentTable.getInt("appointmentid"));
                    newAppointment.setCustomerID(appointmentTable.getInt("customerId"));
                    newAppointment.setUserID(appointmentTable.getInt("userID"));
                    newAppointment.setTitle(appointmentTable.getString("title"));
                    newAppointment.setDescription(appointmentTable.getString("description"));
                    newAppointment.setLocation(appointmentTable.getString("location"));
                    newAppointment.setContact(appointmentTable.getString("contact"));
                    newAppointment.setType(appointmentTable.getString("type"));
                    newAppointment.setURL(appointmentTable.getString("url"));
                    
                    Timestamp startStamp = appointmentTable.getTimestamp("start");
                    GregorianCalendar startCal = (GregorianCalendar) GregorianCalendar.getInstance();
                    startCal.setTimeInMillis(startStamp.getTime());
                    newAppointment.setStart(startCal);
                    Timestamp endStamp = appointmentTable.getTimestamp("end");
                    GregorianCalendar endCal = (GregorianCalendar) GregorianCalendar.getInstance();
                    endCal.setTimeInMillis(endStamp.getTime());
                    newAppointment.setEnd(endCal);
                    
                    newAppointment.setCreatedDate(appointmentTable.getDate("createDate"));
                    newAppointment.setCreatedBy(appointmentTable.getString("createdBy"));
                    newAppointment.setLastUpdate(appointmentTable.getTimestamp("lastUpdate"));
                    newAppointment.setLastUpdateBy(appointmentTable.getString("lastUpdateBy"));
                    data.add(newAppointment);
                    System.out.println("New appointment added from database!");
                }
                
                appointmentView.setItems(data);
            
            }
            catch (SQLException ex) {
                Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        manageCustomers.setOnAction((event)->  { // lambda expression here is used to remove a separately declared function and cleanly insert it into the initial program, eliminating the need to call a separate function
            try {
                Parent mainParent = FXMLLoader.load(getClass().getResource("CustomerScreen.fxml"));
                Scene mainScene = new Scene(mainParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                window.setScene(mainScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column2.setCellValueFactory(new PropertyValueFactory<Appointment, Calendar>("start"));
        final DateFormat dateFormat = DateFormat.getInstance();
        column2.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(dateFormat.format(item.getTime()));
                }
            }
        });
        column3.setCellValueFactory(new PropertyValueFactory<>("location"));
        column4.setCellValueFactory(new PropertyValueFactory<>("contact"));
        
        // generate and populate current date
        
        // generate and populate local time
        
        thisWeek.setToggleGroup(group);
        thisMonth.setToggleGroup(group);
        
        try {
            // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
            ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment WHERE start >= NOW()");
            while (appointmentTable.next()){
                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentID(appointmentTable.getInt("appointmentid"));
                newAppointment.setCustomerID(appointmentTable.getInt("customerId"));
                newAppointment.setUserID(appointmentTable.getInt("userID"));
                newAppointment.setTitle(appointmentTable.getString("title"));
                newAppointment.setDescription(appointmentTable.getString("description"));
                newAppointment.setLocation(appointmentTable.getString("location"));
                newAppointment.setContact(appointmentTable.getString("contact"));
                newAppointment.setType(appointmentTable.getString("type"));
                newAppointment.setURL(appointmentTable.getString("url"));

                Timestamp startStamp = appointmentTable.getTimestamp("start");
                GregorianCalendar startCal = (GregorianCalendar) GregorianCalendar.getInstance();
                startCal.setTimeInMillis(startStamp.getTime());
                newAppointment.setStart(startCal);
                Timestamp endStamp = appointmentTable.getTimestamp("end");
                GregorianCalendar endCal = (GregorianCalendar) GregorianCalendar.getInstance();
                endCal.setTimeInMillis(endStamp.getTime());
                newAppointment.setEnd(endCal);

                newAppointment.setCreatedDate(appointmentTable.getDate("createDate"));
                newAppointment.setCreatedBy(appointmentTable.getString("createdBy"));
                newAppointment.setLastUpdate(appointmentTable.getTimestamp("lastUpdate"));
                newAppointment.setLastUpdateBy(appointmentTable.getString("lastUpdateBy"));
                allUpcoming.add(newAppointment);
            }


        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        alert15Min(allUpcoming);
}    
    
}
