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
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Peter
 */
public class DescriptionViewController {

    private AppModel appModel = new AppModel();
    private Student activeStudent;
    private ObservableList<Date> dates;
    private Thread updateThread;

    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField absenceText;

    /**
     * Adds/updates the date for the current day and updates the student list of dates with the newly entered information. 
     * Then updates the students statistics by running the thread sent with the setStudent method.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleSaveButton(MouseEvent event) throws IOException {

        Date date = new Date(LocalDate.now(), false, absenceText.getText());
        appModel.toBeAttending(activeStudent.getId(), date);
        activeStudent.setDays(appModel.getStudentDays(activeStudent.getId()));
        dates.clear();
        dates.addAll(activeStudent.getDays());

        updateThread.start();

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Event handler for the close button. Closes the EditView without saving.
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleCancelButton(MouseEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This methods runs when the DescriptionView FXML is opened by the "absence" button. It takes the active student, 
     * the students list of dates and the update statistics thread and stores as instance variables.
     * @param student the active student in the AttendanceController
     * @param dates the list of dates contained in the active student
     * @param thread the Thread returned by method updateStatisticsThread in the AttendanceController
     */
    public void setStudent(Student student, ObservableList<Date> dates, Thread thread) {

        activeStudent = student;
        this.dates = dates;
        this.updateThread = thread;
    }

}
