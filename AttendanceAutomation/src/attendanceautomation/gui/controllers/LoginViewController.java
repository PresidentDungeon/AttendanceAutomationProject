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
import attendanceautomation.dal.dbaccess.DBSettings;
import attendanceautomation.gui.AppModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Label errorMsg;
    @FXML
    private JFXButton loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        preferences = Preferences.userRoot().node("AttendanceAutomation");
        if (preferences != null) {
            usernameField.setText(preferences.get("username", null));
            passwordField.setText(preferences.get("password", null));
            rememberMe.setSelected(preferences.getBoolean("rememberActivated", false));
        }

    }

    /**
     * Event handler for the login button.
     * Starts the thread returned by the loadPerson method.
     * @param event
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException 
     */
    @FXML
    private void login(MouseEvent event) throws IOException, InterruptedException, ExecutionException {
        loadPerson().start();
        rememberMe.requestFocus();

    }

    /**
     * Displays loading and disables the login button
     */
    public void showLoading() {
        loginButton.setDisable(true);
        progressBar.setVisible(true);
        errorMsg.setVisible(false);
    }

    /**
     * Hides loading and enables the login button
     */
    public void hideLoading() {
        progressBar.setVisible(false);
        errorMsg.setVisible(true);
        loginButton.setDisable(false);
    }

    /**
     * Saves the login username and password entered in preferences
     */
    public void saveLogin() {
        preferences.put("username", usernameField.getText());
        preferences.put("password", passwordField.getText());
        preferences.putBoolean("rememberActivated", true);
    }

    /**
     * Saves an empty string as the username and password in preferences
     */
    public void forgetLogin() {
        preferences.put("username", "");
        preferences.put("password", "");
        preferences.putBoolean("rememberActivated", false);
    }

    /**
     * Creates a new thread that will try to login
     * with the entered username and password.
     * If correct login is entered, the stage will switch
     * to either the teacher- or student view depending on
     * the login information given.
     * @return Returns the login thread to be executed.
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException 
     */
    public Thread loadPerson() throws InterruptedException, ExecutionException, IOException {

        Thread loginThread = new Thread(() -> {

            String username = usernameField.getText();
            String password = passwordField.getText();
            showLoading();

            Person personToValidate = appModel.validateUser(username, password);

            if (personToValidate == null) {
                hideLoading();

            } else {

                if (rememberMe.isSelected()) {
                    saveLogin();
                } else {
                    forgetLogin();
                }

                Platform.runLater(() -> {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    Scene scene = null;

                    if (personToValidate.getRole().equals(Roles.STUDENT)) {
                        fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException ex) {
                            System.out.println("error");
                        }
                        AttendanceController controller = fxmlLoader.getController();
                        controller.setStudent((Student) personToValidate, null);
                    } else if (personToValidate.getRole().equals(Roles.TEACHER)) {
                        fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceViewTeacher.fxml"));
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException ex) {
                            System.out.println("error");
                        }
                        AttendanceViewTeacherController controller = fxmlLoader.getController();
                        controller.setTeacher((Teacher) personToValidate);

                    }
                    Stage appStage = (Stage) (rememberMe.getScene().getWindow());
                    appStage.setScene(scene);
                    appStage.show();
                });

            }

        });

        return loginThread;
    }

}
