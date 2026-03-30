package edu.jsu.mcis.cs310.tas_sp26;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import com.github.cliftonlabs.json_simple.*;

public class Punch implements Jsonable {

    private Integer id;
    private final int terminalid;
    private final Badge badge;
    private final LocalDateTime originaltimestamp;
    private LocalDateTime adjustedtimestamp;
    private final EventType punchtype;
    private PunchAdjustmentType adjustmenttype = PunchAdjustmentType.NONE;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

    public Punch(int terminalid, Badge badge, EventType punchtype) {
        this.id = null;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originaltimestamp = LocalDateTime.now().withNano(0);
        this.adjustedtimestamp = null;
        this.punchtype = punchtype;
    }

    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originaltimestamp = originaltimestamp;
        this.adjustedtimestamp = null;
        this.punchtype = punchtype;
    }

    public Integer getID() { return id; }
    public int getTerminalid() { return terminalid; }
    public Badge getBadge() { return badge; }
    public LocalDateTime getOriginaltimestamp() { return originaltimestamp; }
    public LocalDateTime getAdjustedtimestamp() { return adjustedtimestamp; }
    public EventType getPunchtype() { return punchtype; }
    public PunchAdjustmentType getAjustmenttype() { return adjustmenttype; }

    public void setAdjustedtimestamp(LocalDateTime adjustedtimestamp) { this.adjustedtimestamp = adjustedtimestamp; }
    public void setAdjustmenttype(PunchAdjustmentType adjustmenttype) { this.adjustmenttype = adjustmenttype; }

    public void adjust(Shift shift) {
        LocalDate punchDate = originaltimestamp.toLocalDate();
        DayOfWeek day = punchDate.getDayOfWeek();
        boolean isWeekday = (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY);

        LocalDateTime shiftStart = LocalDateTime.of(punchDate, shift.getStart());
        LocalDateTime shiftStop  = LocalDateTime.of(punchDate, shift.getStop());
        LocalDateTime lunchStart = LocalDateTime.of(punchDate, shift.getLunchstart());
        LocalDateTime lunchStop  = LocalDateTime.of(punchDate, shift.getLunchstop());

        int interval = shift.getInterval();
        int grace    = shift.getGrace();
        int dock     = shift.getDock();

        if (punchtype == EventType.CLOCK_IN) {
            adjustClockIn(shiftStart, lunchStop, interval, grace, dock, isWeekday);
        } else if (punchtype == EventType.CLOCK_OUT) {
            adjustClockOut(shiftStop, lunchStart, interval, grace, dock, isWeekday);
        }
    }

    private void adjustClockIn(LocalDateTime shiftStart, LocalDateTime lunchStop,
                                int interval, int grace, int dock, boolean isWeekday) {
        long minutesFromShiftStart = ChronoUnit.MINUTES.between(shiftStart, originaltimestamp);

        if (isWeekday) {
            if (minutesFromShiftStart <= 0) {
                adjustedtimestamp = shiftStart;
                adjustmenttype = PunchAdjustmentType.SHIFT_START;
                return;
            }
            if (minutesFromShiftStart <= grace) {
                adjustedtimestamp = shiftStart;
                adjustmenttype = PunchAdjustmentType.SHIFT_START;
                return;
            }
            if (minutesFromShiftStart <= dock) {
                adjustedtimestamp = roundToInterval(originaltimestamp, interval);
                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                return;
            }
        }

        if (isWeekday) {
            long minutesFromLunchStop = ChronoUnit.MINUTES.between(lunchStop, originaltimestamp);
            if (Math.abs(minutesFromLunchStop) <= interval) {
                adjustedtimestamp = lunchStop;
                adjustmenttype = PunchAdjustmentType.LUNCH_STOP;
                return;
            }
        }

        adjustedtimestamp = roundToInterval(originaltimestamp, interval);
        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
    }

    private void adjustClockOut(LocalDateTime shiftStop, LocalDateTime lunchStart,
                                 int interval, int grace, int dock, boolean isWeekday) {
        if (isWeekday) {
            long minutesFromLunchStart = ChronoUnit.MINUTES.between(lunchStart, originaltimestamp);
            if (Math.abs(minutesFromLunchStart) <= interval) {
                adjustedtimestamp = lunchStart;
                adjustmenttype = PunchAdjustmentType.LUNCH_START;
                return;
            }
        }

        long minutesFromShiftStop = ChronoUnit.MINUTES.between(shiftStop, originaltimestamp);

        if (isWeekday) {
            if (minutesFromShiftStop >= -grace && minutesFromShiftStop <= 0) {
                adjustedtimestamp = shiftStop;
                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                return;
            }
            if (minutesFromShiftStop > 0 && minutesFromShiftStop <= dock) {
                LocalDateTime rounded = roundToInterval(originaltimestamp, interval);
                if (rounded.equals(shiftStop)) {
                    adjustedtimestamp = shiftStop;
                    adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                } else {
                    adjustedtimestamp = rounded;
                    adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                }
                return;
            }
            if (minutesFromShiftStop < -grace && minutesFromShiftStop >= -dock) {
                adjustedtimestamp = roundToInterval(originaltimestamp, interval);
                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                return;
            }
            if (minutesFromShiftStop > dock) {
                adjustedtimestamp = roundToInterval(originaltimestamp, interval);
                adjustmenttype = PunchAdjustmentType.NONE;
                return;
            }
        }

        adjustedtimestamp = roundToInterval(originaltimestamp, interval);
        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
    }

    private LocalDateTime roundToInterval(LocalDateTime dt, int interval) {
        int totalMinutes = dt.getHour() * 60 + dt.getMinute();
        int seconds = dt.getSecond();
        if (seconds >= 30) totalMinutes++;
        int remainder = totalMinutes % interval;
        int rounded;
        if (remainder < (interval / 2.0)) {
            rounded = totalMinutes - remainder;
        } else {
            rounded = totalMinutes + (interval - remainder);
        }
        return dt.toLocalDate().atStartOfDay().plusMinutes(rounded);
    }

    public String printOriginal() {
        return "#" + badge.getId() + " " + punchtype.toString() + ": " +
               originaltimestamp.format(FMT).toUpperCase();
    }

    public String printAdjusted() {
        if (adjustedtimestamp == null) return printOriginal();
        return "#" + badge.getId() + " " + punchtype.toString() + ": " +
               adjustedtimestamp.format(FMT).toUpperCase() +
               " (" + adjustmenttype.toString() + ")";
    }

    @Override
    public String toString() { return printOriginal(); }

    @Override
    public String toJson() { return Jsoner.serialize(toJsonObject()); }

    @Override
    public void toJson(Writer writer) throws IOException { toJsonObject().toJson(writer); }

    private JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.put("id", this.id);
        json.put("terminalid", this.terminalid);
        json.put("badge", this.badge);
        json.put("eventtype", this.punchtype.toString());
        json.put("originaltimestamp", originaltimestamp.format(FMT));
        if (adjustedtimestamp != null) {
            json.put("adjustedtimestamp", adjustedtimestamp.format(FMT));
            json.put("adjustmenttype", adjustmenttype.toString());
        } else {
            json.put("adjustedtimestamp", null);
            json.put("adjustmenttype", null);
        }
        return json;
    }
}