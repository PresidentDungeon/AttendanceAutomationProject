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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ander
 */
public class EditViewController {

    private AppModel appModel = new AppModel();
    private Student activeStudent;
    private TableView<Date> dates;
    private Thread thread;

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
     * Updates the selected date and the students list of dates with the newly entered information. 
     * Then updates the students statistics by running the thread sent with the setStudent method.
     *
     * @param event
     */
    @FXML
    private void handleSaveButton(MouseEvent event) {


        if (dates != null && !dates.getSelectionModel().isEmpty()) {

                    Date selectedDate = dates.getSelectionModel().getSelectedItem();
            
            if (attend.isSelected()) {
                selectedDate.setAttendance(true);
                selectedDate.setDescription("");
            } else {
                selectedDate.setAttendance(false);
                selectedDate.setDescription(absenceText.getText());
            }

            appModel.updateAttendance(selectedDate);
            activeStudent.setDays(appModel.getStudentDays(activeStudent.getId()));
            dates.getItems().clear();
            dates.getItems().addAll(activeStudent.getDays());
            thread.start();
        }
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Event handler for the close button. Closes the EditView without saving.
     *
     * @param event
     */
    @FXML
    private void handleCloseButton(MouseEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Select or unselects the absence radiobutton based on what state the attend radiobutton is in.
     *
     * @param event
     */
    @FXML
    private void doAttendance(ActionEvent event) {

        if (attend.isSelected()) {
            absence.setSelected(false);
        } else {
            absence.setSelected(true);
        }

    }

    /**
     * Select or unselects the attend radiobutton based on what state the absence radiobutton is in.
     *
     * @param event
     */
    @FXML
    private void doAbsence(ActionEvent event) {
        if (absence.isSelected()) {
            attend.setSelected(false);
        } else {
            attend.setSelected(true);
        }
    }

    /**
     * This methods runs when the EditView FXML is opened by the "edit" button. It takes the active student, 
     * the students tableview of dates and the update statistics thread and stores as instance variables.
     * @param student the active student in the AttendanceController
     * @param dates the tableview of dates active in the AttendanceController
     * @param thread the Thread returned by method updateStatisticsThread in the AttendanceController
     */
    public void setStudent(Student student, TableView<Date> dates, Thread thread) {
        activeStudent = student;
        this.dates = dates;
        this.thread = thread;
        autoFill();
    }

    /**
     * Autofills the textfield and radiobuttons with the information stored in the selected
     * date object
     */
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
