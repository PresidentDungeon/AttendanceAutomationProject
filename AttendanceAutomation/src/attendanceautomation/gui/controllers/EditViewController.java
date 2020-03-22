/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ander
 */
public class EditViewController implements Initializable {

    AppModel appModel = new AppModel();
    Student activeStudent;
    TableView<Date> dates;
    Thread thread;

    @FXML
    private JFXTextField absenceText;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXRadioButton attend;
    @FXML
    private JFXRadioButton absence;
    @FXML
    private JFXButton cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleSaveButton(MouseEvent event) {
        Date selectedDate = dates.getSelectionModel().getSelectedItem();

        if (dates != null) {

            if (attend.isSelected()) {
                selectedDate.setAttendance(true);
                selectedDate.setDescription("");
            } else {
                selectedDate.setAttendance(false);
                selectedDate.setDescription(absenceText.getText());
            }

            appModel.updateAttendance(selectedDate);
            activeStudent.setDays(appModel.getStudentDays(activeStudent));
            dates.getItems().clear();
            dates.getItems().addAll(activeStudent.getDays());
            thread.start();

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        }

    }

    @FXML
    private void handleCloseButton(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void doAttendance(ActionEvent event) {

        if (attend.isSelected()) {
            absence.setSelected(false);
        } else {
            absence.setSelected(true);
        }

    }

    @FXML
    private void doAbsence(ActionEvent event) {
        if (absence.isSelected()) {
            attend.setSelected(false);
        } else {
            attend.setSelected(true);
        }
    }

    public void setStudent(Student student, TableView<Date> dates, Thread thread) {
        activeStudent = student;
        this.dates = dates;
        this.thread = thread;
        autoFill();
    }

    public void autoFill() {

        Date selectedDate = dates.getSelectionModel().getSelectedItem();

        if (selectedDate != null) {
            absenceText.setText(selectedDate.getDescription());
            attend.setSelected(selectedDate.isAttendance());
            absence.setSelected(!selectedDate.isAttendance());
        } else {
            attend.setSelected(true);
        }
    }

}
