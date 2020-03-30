/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Classroom;
import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.be.Teacher;
import attendanceautomation.dal.dbmanagers.facades.DalFacade;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class StudentManager {

    private DalFacade dalFacade;

    public StudentManager() {
        dalFacade = new DalFacade();
    }

    public ObservableList<Student> searchStudent(String studentName, Classroom classRoom, ObservableList<Student> studentList) {
        
        ObservableList<Student> searchStudentList = FXCollections.observableArrayList();

        if (classRoom.getId() == 0) {
            for (Student student : studentList) {
                if (student.getName().toLowerCase().contains(studentName.toLowerCase())) {
                    searchStudentList.add(student);
                }
            }
        }
        
        else {
            for (Student student : studentList) {

                if (student.getName().toLowerCase().contains(studentName.toLowerCase())) {
                    for (Classroom c : student.getClassRoom()) {
                        if (c.getId() == classRoom.getId()) {
                            searchStudentList.add(student);
                            break;
                        }
                    }
                }
            }
        }
        return searchStudentList;
    }
    
    public void toBeAttending (int studentID, Date date)
    {
        dalFacade.toBeAttending(studentID, date);
    }
    
     public ObservableList<Date> getStudentDays(int studentID)
    {
        return dalFacade.getStudentDays(studentID);
    }

     public void updateAttendance(Date date)
     {
         dalFacade.updateAttendance(date);
     }
}
