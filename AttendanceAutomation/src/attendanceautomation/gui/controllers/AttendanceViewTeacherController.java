/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.gui.AppModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Peter
 */
public class AttendanceViewTeacherController implements Initializable {

    @FXML
    private ComboBox<?> comboSort;
    @FXML
    private ComboBox<?> comboSort1;
    @FXML
    private MenuBar menubar;

    AppModel appModel = new AppModel();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void goToStudentView(ActionEvent event) throws IOException {

        //Loads the student view from the menubar. This should not be in the final project
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AttendanceController controller = fxmlLoader.getController();
        controller.setStudent(appModel.getSpecificStudent(0));

        Stage appStage = (Stage) menubar.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();

    }

    @FXML
    private void openStudentStatisticsView(MouseEvent event) throws IOException {
        
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/StatisticsTeacherView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            StatisticsTeacherViewController controller = fxmlLoader.getController();
            controller.setStudent(appModel.getSpecificStudent(0));
            
            
            Stage appStage = (Stage) menubar.getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
            
        
    }

}
