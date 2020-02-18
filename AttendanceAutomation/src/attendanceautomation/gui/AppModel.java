/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui;

import attendanceautomation.be.Student;
import attendanceautomation.bll.StudentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class AppModel {

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private final StudentManager studentManager;

    public AppModel() {
        studentManager = new StudentManager();
        fetchStudents();
    }

    /**
     * Fetches the students from the database and adds them to the
     * ObservableList.
     */
    public void fetchStudents() {
        students.clear();
        students.addAll(studentManager.getAllStudents());
    }

    /**
     * Returns the Student observablelist.
     *
     * @return the Student observablelist
     */
    public ObservableList<Student> getStudentList() {
        return students;
    }

    public Student getSpecificStudent(int number) {
        return studentManager.getSpecificStudent(number);
    }
}
