
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
    static boolean english = true;
    
    @FXML
    private Label userLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Button spanishEnglishSwitch;
    @FXML
    private Label locale;
    @FXML
    private TextField userNameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Button submitLoginForm;
    
    @FXML
    private void handleLoginRequest(ActionEvent event) throws IOException
    {
        ResultSet queryResult;
        String username = userNameInput.getText();
        String password = passwordInput.getText();
        String query = "SELECT * FROM user WHERE userName=? AND password=?";
        
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
                if (english) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Error");
                    alert.setHeaderText("Error logging in:");
                    alert.setContentText("Username or password is incorrect.");
                    alert.showAndWait();
                    System.out.println("Incorrect username or password...");
                    
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error de Login");
                    alert.setHeaderText("Hay un error de login:");
                    alert.setContentText("Usuario o contraseña incorrecto.");
                    alert.showAndWait();
                    System.out.println("Usuario o contraseña incorrecto...");
                }
            }
            
        }
        catch (SQLException ex) {
            System.out.println("SQL Exception thrown... error: " + ex);
        }
    }
    
    @FXML
    private void handleLanguageSwitch(ActionEvent event) {
        if (english == true) {
            english = false;
            userLabel.setText("Usuario");
            passwordLabel.setText("Contraseña");
            locationLabel.setText("Ubicacion");
            spanishEnglishSwitch.setText("English");
            userNameInput.setPromptText("Ingrese su usuario");
            passwordInput.setPromptText("Ingrese su contraseña");
            submitLoginForm.setText("Entrar");
        }
        else if (english == false) {
            english = true;
            userLabel.setText("User");
            passwordLabel.setText("Password");
            locationLabel.setText("Location");
            spanishEnglishSwitch.setText("Español");
            userNameInput.setPromptText("Please enter your username here");
            passwordInput.setPromptText("Please enter your password here");
            submitLoginForm.setText("Submit");
        }
    }
    
    @FXML
    private void proceedToMainScreen(ActionEvent event) throws IOException {
        Parent mainParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        Scene mainScene = new Scene(mainParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Locale currentLocale = Locale.getDefault();
        if (currentLocale.getLanguage() == "es"){
            english = false;
            userLabel.setText("Usuario");
            passwordLabel.setText("Contraseña");
            locationLabel.setText("Ubicacion");
            spanishEnglishSwitch.setText("English");
            userNameInput.setPromptText("Ingrese su usuario");
            passwordInput.setPromptText("Ingrese su contraseña");
            submitLoginForm.setText("Entrar");
        }
        
        locale.setText(currentLocale.getDisplayCountry());
        
    }    
    
}
