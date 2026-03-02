
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Shift {
    
    private Integer id;
    private String weekstart;
    private LocalTime start;
    private LocalTime stop;
    private LocalTime lunchstart;
    private LocalTime lunchstop;
    private Integer lunchduration;  
    private Integer shiftduration;  
    private Integer interval;
    private Integer grace;
    private Integer dock;

    
    public Shift(Map<String, String> data) {
        this.id = parseInteger(data.get("id"));
        this.weekstart = data.get("weekstart");
        this.start = parseLocalTime(data.get("start"));
        this.stop = parseLocalTime(data.get("stop"));
        this.lunchstart = parseLocalTime(data.get("lunchstart"));
        this.lunchstop = parseLocalTime(data.get("lunchstop"));
        this.interval = parseInteger(data.get("interval"));
        this.grace = parseInteger(data.get("grace"));
        this.dock = parseInteger(data.get("dock"));

        
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
    public String getWeekstart() { return weekstart; }
    public LocalTime getStart() { return start; }
    public LocalTime getStop() { return stop; }
    public LocalTime getLunchstart() { return lunchstart; }
    public LocalTime getLunchstop() { return lunchstop; }
    public Integer getLunchduration() { return lunchduration; }
    public Integer getShiftduration() { return shiftduration; }
    public Integer getInterval() { return interval; }
    public Integer getGrace() { return grace; }
    public Integer getDock() { return dock; }

    
    @Override
    public String toString() {
        return String.format(
            "Shift{id=%d, weekstart='%s', start=%s, stop=%s, lunchstart=%s, lunchstop=%s, " +
            "lunchduration=%d, shiftduration=%d, interval=%d, grace=%d, dock=%d}",
            id, weekstart, start, stop, lunchstart, lunchstop, lunchduration, shiftduration,
            interval, grace, dock
        );
    }
}
