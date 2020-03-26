/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.utilities;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ander
 */
public class AbsenceCalculator {

    public static double calculateAttendance(Student student) {
        double present = 0.00;
        double absent = 0.00;

        for (Date d : student.getDays()) {
            if (d.isAttendance()) {
                present++;
            } else {
                absent++;
            }
        }
        return (absent / (present + absent) * 100);
    }

    public static int getDaysAbsence(Student student) {
        int absent = 0;

        for (Date d : student.getDays()) {
            if (!d.isAttendance()) {
                absent++;
            }
        }
        return absent;
    }

    public static int getDaysPresent(Student student) {
        int present = 0;

        for (Date d : student.getDays()) {
            if (d.isAttendance()) {
                present++;
            }
        }
        return present;
    }

    public static void main(String[] args) {

        ObservableList<Date> dates = FXCollections.observableArrayList();

        Date date1 = new Date(LocalDate.parse("2020-03-27"), false, "");
        Date date2 = new Date(LocalDate.parse("2020-03-28"), false, "I am sick");
        Date date3 = new Date(LocalDate.parse("2020-03-29"), false, "I am sick");
        Date date4 = new Date(LocalDate.parse("2020-03-30"), true, "");
        Date date5 = new Date(LocalDate.parse("2020-03-30"), true, "");
        Date date6 = new Date(LocalDate.parse("2020-03-30"), true, "");
        Date date7 = new Date(LocalDate.parse("2020-03-30"), true, "");
        Date date8 = new Date(LocalDate.parse("2020-03-30"), true, "");

        dates.add(date1);
        dates.add(date2);
        dates.add(date3);
        dates.add(date4);
        dates.add(date5);
        dates.add(date6);
        dates.add(date7);
        dates.add(date8);

        Student student = new Student("Peter", "easv");
        student.setDays(dates);

        System.out.println("Attendance is: " + AbsenceCalculator.calculateAttendance(student));

    }

}
