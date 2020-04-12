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

    /**
     * Calculates the percentage of presence/absence from a student.
     * @param student the active student in the AttendanceController
     * @return the calculated percentage of absence
     */
    public static double calculateAttendance(Student student) {

        double present = 0.00;
        double absent = 0.00;
        
        if (student.getDays().size() > 0)
        {
            for (Date d : student.getDays())
            {
                if (d.isAttendance())
                {
                    present++;
                } else
                {
                    absent++;
                }
            }
            return (absent / (present + absent) * 100);
        }
        return 0.00;
    }

    /**
     * Counts days a student is absent as an integer.
     * @param student the active student in the AttendanceController
     * @return integer of counted days absent
     */
    public static int getDaysAbsence(Student student) {
        int absent = 0;

        for (Date d : student.getDays())
        {
            if (!d.isAttendance())
            {
                absent++;
            }
        }
        return absent;
    }

    /**
     * Counts days a student is present as an integer.
     * @param student the active student in the AttendanceController
     * @return integer of counted days present
     */
    public static int getDaysPresent(Student student) {
        int present = 0;

        for (Date d : student.getDays())
        {
            if (d.isAttendance())
            {
                present++;
            }
        }
        return present;
    }

}
