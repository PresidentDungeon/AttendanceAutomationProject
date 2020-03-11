/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.gui.AppModel;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author ander
 */
public class LoginView2Controller {

    AppModel appModel = new AppModel();
    
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXTextField passwordField;
    
    
    
    @FXML
    private void login(MouseEvent event) throws IOException, InterruptedException {
        
        
            //Should later check if username and password is correct.
            String username = usernameField.getText();
            String password = passwordField.getText();
            if(username.equals("1")&&password.equals("2")) {
            
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AttendanceController controller = fxmlLoader.getController();
            controller.setStudent(appModel.getSpecificStudent(2));
            
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
            }else
                System.out.println("WRONG! Username = 1 | Password = 2");
        
        
    }
}