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

    private ObservableList<Classroom> classes = FXCollections.observableArrayList();

    public Teacher(String name, String email) {
        super(name, email, Roles.TEACHER);
    }

    public Teacher() {
        super(Roles.TEACHER);
    }

    public ObservableList<Classroom> getClasses() {
        return classes;
    }

    public void setClasses(ObservableList<Classroom> classes) {
        this.classes = classes;
    }

}