package edu.jsu.mcis.cs310.tas_sp26.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import edu.jsu.mcis.cs310.tas_sp26.EventType;
import edu.jsu.mcis.cs310.tas_sp26.Punch;
import edu.jsu.mcis.cs310.tas_sp26.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_sp26.Shift;

public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> punchlist, Shift shift) {
        int totalMinutes = 0;
        for (int i = 0; i + 1 < punchlist.size(); i++) {
            Punch current = punchlist.get(i);
            Punch next    = punchlist.get(i + 1);
            if (current.getPunchtype() == EventType.CLOCK_IN &&
                next.getPunchtype() == EventType.CLOCK_OUT) {
                LocalDateTime start = current.getAdjustedtimestamp();
                LocalDateTime stop  = next.getAdjustedtimestamp();
                if (start != null && stop != null) {
                    int minutes = (int) ChronoUnit.MINUTES.between(start, stop);
                    DayOfWeek day = start.getDayOfWeek();
                    boolean isWeekday = (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY);
                    boolean isShiftDock = current.getAjustmenttype() == PunchAdjustmentType.SHIFT_DOCK;
                    if (isWeekday && !isShiftDock) {
                        LocalDateTime lunchStart = LocalDateTime.of(start.toLocalDate(), shift.getLunchstart());
                        LocalDateTime lunchStop  = LocalDateTime.of(start.toLocalDate(), shift.getLunchstop());
                        long totalWorked = ChronoUnit.MINUTES.between(start, stop);
                        if (start.isBefore(lunchStart) && stop.isAfter(lunchStop) && totalWorked >= shift.getLunchthreshold()) {
                            minutes -= shift.getLunchduration();
                        }
                    }
                    totalMinutes += minutes;
                }
                i++;
            }
        }
        return totalMinutes;
    }

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift shift) {
        int scheduledMinutes = 5 * (shift.getShiftduration() - shift.getLunchduration());
        if (scheduledMinutes == 0) return BigDecimal.ZERO;
        int workedMinutes = calculateTotalMinutes(punchlist, shift);
        BigDecimal scheduled = new BigDecimal(scheduledMinutes);
        BigDecimal worked    = new BigDecimal(workedMinutes);
        return scheduled.subtract(worked)
                        .divide(scheduled, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .setScale(2, RoundingMode.HALF_UP);
    }
}