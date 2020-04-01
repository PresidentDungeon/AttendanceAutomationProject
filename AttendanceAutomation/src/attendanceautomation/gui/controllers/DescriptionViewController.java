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
public class DescriptionViewController{

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
    

    @FXML
    private void handleCancelButton(MouseEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
}
    
    public void setStudent(Student student, ObservableList<Date> dates, Thread thread) {

        activeStudent = student;
        this.dates = dates;
        this.updateThread = thread;
    }
    
}



