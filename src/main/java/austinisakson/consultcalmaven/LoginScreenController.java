
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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import static austinisakson.consultcalmaven.DBConnection.conn;

/**
 *
 * @author ajisa
 */
public class LoginScreenController implements Initializable {
    
    static String currentUser;
    static int currentUserId;
    
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button submitLoginForm;
    
    // CHANGE THE BUTTON ON THE LOGINSCREEN.FXML TO #HANDLELOGINREQUEST
    @FXML
    private void handleLoginRequest(ActionEvent event) throws IOException
    {
        ResultSet queryResult;
        String username = userNameInput.getText();
        String password = passwordInput.getText();
        String query = "SELECT * FROM user WHERE email=? AND password=?";
        
        try {
            
            PreparedStatement sqlQuery = conn.prepareStatement(query);
            
            sqlQuery.setString(1, username);
            sqlQuery.setString(2, password);
            
            queryResult = sqlQuery.executeQuery();
            
            if(queryResult.next()) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println("Username and Password correct. Proceeding to Main Screen...");
                    currentUser = username;
                    currentUserId = queryResult.getInt(1);
                    File tmpdir = new File("./user-log.txt");
                    if (tmpdir.exists()){
                        FileWriter write = new FileWriter("user-log.txt", true);
                        PrintWriter print = new PrintWriter(write);
                        print.println(currentUser + ", " + dtf.format(now));
                        print.close(); 
                    }
                    else {
                        FileWriter write = new FileWriter("user-log.txt", true);
                        PrintWriter print = new PrintWriter(write);
                        print.println("Username, Login Time");
                        print.println(currentUser + ", " + dtf.format(now));
                        print.close(); 
                    }
                    
                    
                    proceedToMainScreen(event);
            }
            else if (!queryResult.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Error");
                alert.setHeaderText("Error logging in:");
                alert.setContentText("Username or password is incorrect.");
                alert.showAndWait();
                System.out.println("Incorrect username or password...");
            }
            
        }
        catch (SQLException ex) {
            System.out.println("SQL Exception thrown... error: " + ex);
        }
    }
    
    @FXML
    private void proceedToMainScreen(ActionEvent event) throws IOException {
        Parent mainParent = FXMLLoader.load(getClass().getResource("/fxml/MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
