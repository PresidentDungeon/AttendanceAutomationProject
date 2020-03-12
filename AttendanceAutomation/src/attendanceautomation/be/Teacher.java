/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class Teacher extends Person {

    private ObservableList<Classroom> classrooms = FXCollections.observableArrayList();

    public Teacher(String name, String email) {
        super(name, email, Roles.TEACHER);
    }

    public Teacher() {
        super(Roles.TEACHER);
    }

    public ObservableList<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClasses(ObservableList<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

}