/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Peter
 */
public class StatisticsTeacherViewController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private ComboBox<?> comboSort;
    @FXML
    private TableView<Date> attendanceTable;
    @FXML
    private TableColumn<Date, String> dateColumn;
    @FXML
    private TableColumn<?, ?> statusColumn;
    @FXML
    private TableColumn<?, ?> descColumn;
    @FXML
    private Label lectureLabel;
    @FXML
    private Label absentLabel;
    @FXML
    private Label percentageLabel;
    @FXML
    private HBox paneArea;
    @FXML
    private ComboBox<?> comboSort1;
    @FXML
    private HBox paneArea1;
    @FXML
    private MenuItem moodleMenu;
    @FXML
    private MenuItem studentViewMenu;
    @FXML
    private MenuItem teacherViewMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goToMoodle(ActionEvent event) {
    }

    @FXML
    private void goToStudentView(ActionEvent event) {
    }

    @FXML
    private void goToTeacherView(ActionEvent event) {
    }
    
}
