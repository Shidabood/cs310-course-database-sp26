package edu.jsu.mcis.cs310.tas_sp26;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Shift {

    private Integer id;
    private String description;
    private LocalTime start;
    private LocalTime stop;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private Integer lunchduration;
    private Integer shiftduration;
    private Integer interval;
    private Integer grace;
    private Integer dock;
    private Integer lunchthreshold;

    public Shift(Map<String, String> data) {
        this.id = parseInteger(data.get("id"));
        this.description = data.get("description");
        this.start = parseLocalTime(data.get("start"));
        this.stop = parseLocalTime(data.get("stop"));
        this.lunchstart = parseLocalTime(data.get("lunchstart"));
        this.lunchstop = parseLocalTime(data.get("lunchstop"));
        this.interval = parseInteger(data.get("interval"));
        this.grace = parseInteger(data.get("grace"));
        this.dock = parseInteger(data.get("dock"));
        this.lunchthreshold = parseInteger(data.get("lunchthreshold"));

        this.lunchduration = computeDurationInMinutes(lunchstart, lunchstop);
        this.shiftduration = computeDurationInMinutes(start, stop);
    }

    private LocalTime parseLocalTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        return LocalTime.parse(timeStr);
    }

    private Integer parseInteger(String intStr) {
        if (intStr == null || intStr.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer computeDurationInMinutes(LocalTime startTime, LocalTime stopTime) {
        if (startTime == null || stopTime == null) return null;
        return (int) java.time.Duration.between(startTime, stopTime).toMinutes();
    }

    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public LocalTime getStart() { return start; }
    public LocalTime getStop() { return stop; }
    public LocalTime getLunchstart() { return lunchstart; }
    public LocalTime getLunchstop() { return lunchstop; }
    public Integer getLunchduration() { return lunchduration; }
    public Integer getShiftduration() { return shiftduration; }
    public Integer getInterval() { return interval; }
    public Integer getGrace() { return grace; }
    public Integer getDock() { return dock; }
    public Integer getLunchthreshold() { return lunchthreshold; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        return String.format(
            "%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
            description,
            start.format(fmt),
            stop.format(fmt),
            shiftduration,
            lunchstart.format(fmt),
            lunchstop.format(fmt),
            lunchduration
        );
    }
}