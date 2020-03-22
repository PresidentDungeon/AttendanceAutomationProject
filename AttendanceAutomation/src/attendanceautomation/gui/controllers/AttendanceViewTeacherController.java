/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.be.Classroom;
import attendanceautomation.gui.AppModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Peter
 */
public class AttendanceViewTeacherController implements Initializable {

    private Teacher loggedTeacher;
    private ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Classroom> comboSort;
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
    @FXML
    private Label teacherName;
    @FXML
    private Label teacherEmail;
    @FXML
    private Label lastAccess;
    @FXML
    private TextField studentSearch;

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

        absentColumn.setComparator((absent1, absent2)->{

            absent1 = absent1.replace(",", ".");
            absent2 = absent2.replace(",", ".");

            double absentPercentage1 = Double.valueOf(absent1.substring(0, absent1.length()-1));
            double absentPercentage2 = Double.valueOf(absent2.substring(0, absent2.length()-1));
            return Double.compare(absentPercentage1, absentPercentage2);  
        });

        studentTable.setItems(students);
    }

    public void setTeacher(Teacher teacher) {
        loggedTeacher = teacher;
        teacherName.setText(teacher.getName());
        teacherEmail.setText(teacher.getEmail());
        lastAccess.setText(teacher.getLastAccess().toString());
        comboSort.setItems(teacher.getClassrooms());
        comboSort.getItems().add(0, new Classroom("All classrooms"));
        comboSort.getSelectionModel().select(0);
        setStudents(teacher);

    }

    public void setStudents(Teacher teacher) {
        for (Classroom c : teacher.getClassrooms()) {

            for (Student student : c.getStudents()) {
                students.add(student);
            }
        }
    }

    @FXML
    private void classroomSearch(ActionEvent event) {
        search2(loggedTeacher, studentSearch.getText(), comboSort.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void studentSearch(KeyEvent event) {
        search2(loggedTeacher, studentSearch.getText(), comboSort.getSelectionModel().getSelectedItem());
    }

    public void search(Teacher teacher, String studentName, Classroom classroom) {
        students.clear();
        students.addAll(appModel.searchStudent(teacher, studentName, classroom));
    }
    
    public void search2(Teacher teacher, String studentName, Classroom classroom)
    {
        if (studentName.equalsIgnoreCase("") && classroom.getId() == 0){
            studentTable.setItems(students);
        } 
        else
        {
            studentTable.setItems(appModel.searchStudent2(teacher, studentName, classroom, students));
        }
    }


    @FXML
    private void openStudentStatisticsView(MouseEvent event) throws IOException {

        if (!studentTable.getSelectionModel().isEmpty()) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            AttendanceController controller = fxmlLoader.getController();
            controller.setStudent(studentTable.getSelectionModel().getSelectedItem(), studentTable);

            stage.setScene(scene);
            stage.show();

        }
    }

    @FXML
    private void handleLogOut(ActionEvent event){

        try{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage appStage = (Stage) menubar.getScene().getWindow();
        appStage.setScene(scene);   
        }
        catch(Exception ex){}
    }

}
