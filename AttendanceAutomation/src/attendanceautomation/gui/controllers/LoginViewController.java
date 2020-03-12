/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Person;
import attendanceautomation.be.Roles;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.gui.AppModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
/**
 *
 * @author ander
 */
public class LoginViewController {

    AppModel appModel = new AppModel();

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private void login(MouseEvent event) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        Person personToValidate = appModel.validateUser(username, password);

        if (personToValidate == null) {
        // some kind of error message should show here?
            
        } else {

            FXMLLoader fxmlLoader = new FXMLLoader();
            Scene scene = null;

            if (personToValidate.getRole().equals(Roles.STUDENT)) {
                fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
                scene = new Scene(fxmlLoader.load());
                AttendanceController controller = fxmlLoader.getController();
                controller.setStudent((Student) personToValidate);
            } else if (personToValidate.getRole().equals(Roles.TEACHER)) {
                fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceViewTeacher.fxml"));
                scene = new Scene(fxmlLoader.load());
                AttendanceViewTeacherController controller = fxmlLoader.getController();
                controller.setTeacher((Teacher) personToValidate);
            }

            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();

        }
    }

}
