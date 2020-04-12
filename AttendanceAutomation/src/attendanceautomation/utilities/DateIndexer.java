/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.utilities;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import java.time.DayOfWeek;

/**
 *
 * @author ander
 */
public class DateIndexer {

    /**
     * Counts the days a student is absent, and indexes
     * into the days of the week, not counting weekends.
     * @param student the active student in the AttendanceController
     * @return integerArray of counted days
     */
    public static int[] indexAbsenceDays(Student student) {
        int[] absentDay =
        {
            0, 0, 0, 0, 0
        };

        if (student.getDays().size() > 0)
        {
            for (Date d : student.getDays())
            {
                if (d.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
                        || d.getDate().getDayOfWeek() == DayOfWeek.SUNDAY)
                {
                    throw new IllegalArgumentException("Chill - it's the weekends");
                }
                if (!d.isAttendance())
                {
                    absentDay[d.getDate().getDayOfWeek().getValue() - 1]
                            = absentDay[d.getDate().getDayOfWeek().getValue() - 1]
                            += 1;
                }
            }
        }
        return absentDay;
    }

}
