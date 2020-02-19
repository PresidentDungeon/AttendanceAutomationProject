/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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
    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> studentColumn;
    @FXML
    private TableColumn<Student, String> absentColumn;

    AppModel appModel = new AppModel();
    @FXML
    private Menu logOutMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        studentColumn.setCellValueFactory((data) -> {
            Student student = data.getValue();
            return new SimpleStringProperty(student.getName());
        });

        //This is bad code, as it takes up a lot of resources and is repetead code
        //In the future, the database should count the amount of days the student is gone
        absentColumn.setCellValueFactory((data) -> {
            double present = 0;
            double absent = 0;

            Student student = data.getValue();
            for (Date d : student.getDays()) {
                if (d.isAttendance()) {
                    present++;
                } else {
                    absent++;
                }
            }
            return new SimpleStringProperty(String.format("%.2f", (absent / (present + absent) * 100)) + "%");
        });

        studentTable.setItems(appModel.getStudentList());

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

        if (!studentTable.getSelectionModel().isEmpty()) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AttendanceController controller = fxmlLoader.getController();
            controller.setStudent(studentTable.getSelectionModel().getSelectedItem());

            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) throws IOException {

    }

}
