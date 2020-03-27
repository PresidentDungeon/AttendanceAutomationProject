/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.utilities;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;

/**
 *
 * @author ander
 */
public class DateIndexer {
    
     public static int[] indexAbsenceDays(Student student) {
        int[] absentDay = {0, 0, 0, 0, 0};

        for (Date d : student.getDays()) {
            if (!d.isAttendance()) {
                absentDay[d.getDate().getDayOfWeek().getValue() - 1] = absentDay[d.getDate().getDayOfWeek().getValue() - 1] += 1;
            }
        }
        return absentDay;
    }
    
}
