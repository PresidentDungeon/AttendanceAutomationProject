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
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author ander
 */
public class LoginViewController implements Initializable {

    AppModel appModel = new AppModel();

    Preferences preferences;

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXCheckBox rememberMe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        preferences = Preferences.userRoot().node("AttendanceAutomation");
        if (preferences != null)
        {
            usernameField.setText(preferences.get("username", null));
            passwordField.setText(preferences.get("password", null));
            rememberMe.setSelected(preferences.getBoolean("rememberActivated", false));
        }
        
        
    }

    @FXML
    private void login(MouseEvent event) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Person personToValidate = appModel.validateUser(username, password);

        if (personToValidate == null) {
            // some kind of error message should show here?

        } else {

            if (rememberMe.isSelected())
            {
                preferences.put("username", usernameField.getText());
                preferences.put("password", passwordField.getText());
                preferences.putBoolean("rememberActivated", true);
            }
            else
            {
                preferences.put("username", "");
                preferences.put("password", "");
                preferences.putBoolean("rememberActivated", false);
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            Scene scene = null;

            if (personToValidate.getRole().equals(Roles.STUDENT)) {
                fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
                scene = new Scene(fxmlLoader.load());
                AttendanceController controller = fxmlLoader.getController();
                controller.setStudent((Student) personToValidate, null);
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
