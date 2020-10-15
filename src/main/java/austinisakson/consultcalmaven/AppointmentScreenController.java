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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
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
public class AppointmentScreenController implements Initializable {

    private int apptID = 0;
    
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
    Button adminButton = new Button();
    @FXML
    Button logoutButton = new Button();
                    
    @FXML
    private ComboBox searchType;
    @FXML
    private TextField searchField;
    @FXML
    Button searchButton = new Button();
    
    
    @FXML
    private ObservableList<Appointment> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Appointment, Calendar> column1 = new TableColumn<>("Date");
    @FXML
    private TableColumn<Appointment, Calendar> column2 = new TableColumn<>("Start Time");
    @FXML
    private TableColumn<Appointment, Calendar> column3 = new TableColumn<>("End Time");
    @FXML
    private TableColumn<Appointment, String> column4 = new TableColumn<>("Consultant");
    @FXML
    private TableColumn<Appointment, String> column5 = new TableColumn<>("Contact");
    @FXML
    private TableColumn<Appointment, String> column6 = new TableColumn<>("Location");
    @FXML
    private TableView appointmentView;
    
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
    
    public void handlePopulate() {
        
        // populate tableview with appointment table
        try{
            
            // Query result set for Appointment Table, load each appointment as an object of the Appointment class, display as a TableView
            appointmentView.getItems().clear();
            int apptCount = 0;
            ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment;");
            while (appointmentTable.next()){
                
                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentID(appointmentTable.getInt("id"));
                newAppointment.setClientID(appointmentTable.getInt("client"));
                newAppointment.setSchedulerID(appointmentTable.getInt("scheduler"));
                newAppointment.setDetails(appointmentTable.getString("details"));
                newAppointment.setLocation(appointmentTable.getString("location"));
                newAppointment.setContact(appointmentTable.getString("contact"));

                Timestamp startStamp = appointmentTable.getTimestamp("starttime");
                GregorianCalendar startCal = (GregorianCalendar) GregorianCalendar.getInstance();
                startCal.setTimeInMillis(startStamp.getTime());
                newAppointment.setStart(startCal);
                Timestamp endStamp = appointmentTable.getTimestamp("endtime");
                GregorianCalendar endCal = (GregorianCalendar) GregorianCalendar.getInstance();
                endCal.setTimeInMillis(endStamp.getTime());
                newAppointment.setEnd(endCal);

                newAppointment.setCreatedDate(appointmentTable.getTimestamp("createdDate"));
                newAppointment.setCreatedBy(appointmentTable.getString("createdBy"));
                newAppointment.setLastUpdate(appointmentTable.getTimestamp("lastUpdate"));
                newAppointment.setLastUpdateBy(appointmentTable.getString("updatedBy"));
                newAppointment.setCompleted(appointmentTable.getBoolean("completed"));
                data.add(newAppointment);
                System.out.println("New appointment added from database!");
                
                apptCount++;
            }
            
            System.out.println(apptCount + " appointments added from the database.");
            appointmentView.setItems(data);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ClientScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /*
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
    
    private String apptFieldsToUTCString(String time){
        String localTimeString = "";
        
        localTimeString += dateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " ";
        localTimeString += time + ":00";
        
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.parse(localTimeString, format);
        ZonedDateTime zonedLocalTime = localTime.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedUTCTime = zonedLocalTime.withZoneSameInstant(ZoneOffset.UTC);
        
        String sqlString = zonedUTCTime.format(format);
        
        return sqlString;
    }
    
    private GregorianCalendar inputToGregCal(String time) throws ParseException{
        String localTimeString = "";
        
        localTimeString += dateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "T";
        localTimeString += time + ":00.0";
        
        Date date;
        SimpleDateFormat simpleDateFormat;
        GregorianCalendar gregorianCalendar;
        
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        date = simpleDateFormat.parse(localTimeString);
        
        gregorianCalendar =(GregorianCalendar) GregorianCalendar.getInstance();
        
        gregorianCalendar.setTime(date);
        
        return gregorianCalendar;
    }
    
    private boolean checkOverlap() throws ParseException{
        boolean overlappingAppts = false;
        for (Appointment appt : data) {
            if (LoginScreenController.currentUserId == appt.getUserID()){
                GregorianCalendar newApptStartTime = inputToGregCal(startTimeField.getText());
                GregorianCalendar oldApptStartTime = appt.getStart();
                GregorianCalendar newApptEndTime = inputToGregCal(endTimeField.getText());
                GregorianCalendar oldApptEndTime = appt.getEnd();
                
                if (((newApptStartTime.after(oldApptStartTime)) && (newApptStartTime.before(oldApptEndTime))) || 
                    ((newApptEndTime.after(oldApptStartTime)) && (newApptEndTime.before(oldApptEndTime)))) {
                    overlappingAppts = true;
                }
            }

        }
        return overlappingAppts;
    }
    
    private boolean duringBizHours() throws ParseException{
        String sTime = startTimeField.getText();
        String eTime = endTimeField.getText();
        boolean isDuringHours = true;
        String startTime = "08:00";
        String endTime = "17:00";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d1 = sdf.parse(startTime);
        Date d2 = sdf.parse(endTime);
        Date d3 = sdf.parse(sTime);
        Date d4 = sdf.parse(eTime);
        
        if (!d3.after(d1) || !d3.before(d2) ||
           (!d4.after(d1) || !d4.before(d2))){
            isDuringHours = false;
        }
        return isDuringHours;
    }
   
    
    @FXML
    private void handleAddSave(ActionEvent event) throws SQLException, ParseException {
        // if appt ID is not null, then use SQL update query with info
        if (apptID > 0 && !checkOverlap() && duringBizHours()){
            //update row for where apptid = sql id on appt table
            String query = "UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE appointmentid = ? ";
            PreparedStatement updateRow = conn.prepareStatement(query);
            Client selectedCust = (Client) customerField.getValue();
            updateRow.setInt(1, selectedCust.getID());
            updateRow.setString(2, titleField.getText());
            updateRow.setString(3, descriptionField.getText());
            updateRow.setString(4, locationField.getText());
            updateRow.setString(5, contactField.getText());
            updateRow.setString(6, typeField.getText());
            updateRow.setString(7, urlField.getText());
            
            updateRow.setString(8, apptFieldsToUTCString(startTimeField.getText()));
            updateRow.setString(9, apptFieldsToUTCString(endTimeField.getText()));
            
            updateRow.setString(10, LoginScreenController.currentUser);
            Appointment selectedAppointment = (Appointment) appointmentView.getSelectionModel().getSelectedItem();
            updateRow.setInt(11, selectedAppointment.getAppointmentID());
            updateRow.execute();
        }
        
        // if cust ID is null, create new one with given info
        else if (!(apptID > 0) && !checkOverlap() && duringBizHours()) {
            //insert row
            String query = "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)";
            PreparedStatement insertRow = conn.prepareStatement(query);
            Client selectedCust = (Client) customerField.getValue();
            insertRow.setInt(1, selectedCust.getID());
            insertRow.setInt(2, LoginScreenController.currentUserId);
            insertRow.setString(3, titleField.getText());
            insertRow.setString(4, descriptionField.getText());
            insertRow.setString(5, locationField.getText());
            insertRow.setString(6, contactField.getText());
            insertRow.setString(7, typeField.getText());
            insertRow.setString(8, urlField.getText());
            
            insertRow.setString(9, apptFieldsToUTCString(startTimeField.getText()));
            insertRow.setString(10, apptFieldsToUTCString(endTimeField.getText()));
            
            insertRow.setString(11, LoginScreenController.currentUser);
            insertRow.setString(12, LoginScreenController.currentUser);
            insertRow.execute();
        }
        
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add/Save Error");
            alert.setHeaderText("Error adding or updating appointment:");
            alert.setContentText("Appointment times overlap with a pre-existing appointment or are outside of business hours.");
            alert.showAndWait();
            throw new IllegalArgumentException("Appointment times overlap with a pre-existing appointment");
        }
        
        apptID = 0;
        customerField.setValue(null);
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        contactField.clear();
        typeField.clear();
        urlField.clear();
        dateField.setValue(null);
        startTimeField.clear();
        endTimeField.clear();
        
        // refresh tableview
        appointmentView.getItems().clear();
        ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment");
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
    
    @FXML
    private void handleUpdate(ActionEvent event) {
        // take selected value from tableview and populate the left side text fields with the info to be editable
        Appointment selectedAppointment = (Appointment) appointmentView.getSelectionModel().getSelectedItem();
        apptID = selectedAppointment.getAppointmentID();
        for (Client selectedClient : clients) {
            if (selectedClient.getID() == selectedAppointment.getCustomerID()){
                customerField.setValue(selectedClient);
                break;
            }
        }
        titleField.setText(selectedAppointment.getTitle());
        descriptionField.setText(selectedAppointment.getDescription());
        locationField.setText(selectedAppointment.getLocation());
        contactField.setText(selectedAppointment.getContact());
        typeField.setText(selectedAppointment.getType());
        urlField.setText(selectedAppointment.getURL());
        dateField.setValue(calToLocalDate(selectedAppointment.getStart()));
        startTimeField.setText(timeFromCal(selectedAppointment.getStart()));
        endTimeField.setText(timeFromCal(selectedAppointment.getEnd()));
        
    }
    
    @FXML
    private void handleDelete(ActionEvent event) throws SQLException {
        // delete selected appointment from the SQL table
        
        Appointment selectedAppointment = (Appointment) appointmentView.getSelectionModel().getSelectedItem();
        int deleteAppointmentID = selectedAppointment.getAppointmentID();
        String query = "DELETE FROM appointment WHERE appointmentid = ?";
        PreparedStatement deleteRow = conn.prepareStatement(query);
        deleteRow.setInt(1, deleteAppointmentID);
        deleteRow.execute();
        data.remove(appointmentView.getSelectionModel().getSelectedItem());
        
        
        // refresh table view
        customerField.setValue(null);
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        contactField.clear();
        typeField.clear();
        urlField.clear();
        startTimeField.clear();
        endTimeField.clear();
        
        appointmentView.getItems().clear();
        ResultSet appointmentTable = conn.createStatement().executeQuery("SELECT * FROM appointment");
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
    
    
    */
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
        clientsButton.setOnAction((event)->  { // lambda expression here is used to remove a separately declared function and cleanly insert it into the initial program, eliminating the need to call a separate function
            try {
                Parent mainParent = FXMLLoader.load(getClass().getResource("/austinisakson/consultcalmaven/CustomerScreen.fxml"));
                Scene mainScene = new Scene(mainParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
                window.setScene(mainScene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        final DateFormat dateFormat = DateFormat.getInstance();
        column1.setCellValueFactory(new PropertyValueFactory<>("date"));
        column1.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
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
        column2.setCellValueFactory(new PropertyValueFactory<>("starttime"));
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
        column3.setCellValueFactory(new PropertyValueFactory<>("endtime"));
        column3.setCellFactory(col -> new TableCell<Appointment, Calendar>(){ // this lambda expression allows the anonymous function "updateItem" to insert its content as the parameter for the setCellFactory method
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
        column4.setCellValueFactory(new PropertyValueFactory<>("consultant"));
        column5.setCellValueFactory(new PropertyValueFactory<>("contact"));
        column6.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        this.handlePopulate();
    }
          
    
}
