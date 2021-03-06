/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;


import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class Student extends Person {
    
    private ObservableList<Date> days = FXCollections.observableArrayList();
    private List<Classroom> classRoom = new ArrayList<>();
    
    public Student(String name, String email) {        
        super(name, email, Roles.STUDENT);    
    }
    
    public Student() {
        super(Roles.STUDENT);
    }
    
    public ObservableList<Date> getDays() {
        return days;
    }
    
    public void setDays(ObservableList<Date> days) {
        this.days = days;
    }

    public List<Classroom> getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(List<Classroom> classRoom) {
        this.classRoom = classRoom;
    }
    
}