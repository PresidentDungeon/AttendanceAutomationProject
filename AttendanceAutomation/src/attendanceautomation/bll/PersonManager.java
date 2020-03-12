/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Person;
import attendanceautomation.be.Student;
import attendanceautomation.dal.MockStudentManager;
import attendanceautomation.dal.dbmanagers.facades.DalFacade;
import java.util.List;

/**
 *
 * @author ander
 */
public class PersonManager {

    private MockStudentManager mockStudentManager;
    private DalFacade dalFacade;

    public PersonManager() {
        mockStudentManager = new MockStudentManager();
        dalFacade = new DalFacade();
    }

    public Person validateUser(String username, String password)
    {
       return dalFacade.login(username, password);

    }

    public List<Student> getAllStudents() {
        List<Student> sortedList = mockStudentManager.readAllStudents();
        sortedList.sort((c1, c2) -> {
            return c1.getName().compareToIgnoreCase(c2.getName());
        });
        return sortedList;
    }

    public Student getSpecificStudent(int number) {
        return mockStudentManager.readSpecificStudent(number);
    }

}
