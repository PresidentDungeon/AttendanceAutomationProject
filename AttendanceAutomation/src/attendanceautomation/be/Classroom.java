/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ander
 */
public class Classroom {
    
    private int id;
    private List<Student> students = new ArrayList<>();
    private String className;

    public Classroom(String className) {
        this.className = className;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getClassroomName() {
        return className;
    }

    public void setClassroomName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return this.className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        
        Classroom classroom = (Classroom) obj;

        if (classroom.getId() == this.getId()) {
            return true;
        }
        return false;
    }   
}