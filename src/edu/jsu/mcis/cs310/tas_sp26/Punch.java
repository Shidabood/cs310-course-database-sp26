/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26;
import edu.jsu.mcis.cs310.tas_sp26.dao.PunchDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author emiel
 */
//this will represent a single punch
    public class Punch {
        private Integer id; //can be null
        private final int terminalid; 
        private final Badge badge;
        private final LocalDateTime originaltimestamp;
        private LocalDateTime adjustedtimestamp;
        private final EventType punchtype;
        private PunchAdjustmentType adjustmenttype = PunchAdjustmentType.NONE;
       
        //format for timestamps
        private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
       
        //constructor for new punches 
        public Punch(int terminalid, Badge badge, EventType punchtype){
            this.id = null;
            this.terminalid = terminalid;
            this.badge = badge;
            this.originaltimestamp = LocalDateTime.now();
            this.adjustedtimestamp = null;
            this.punchtype = punchtype;           
        }
        //constructor for the existing punches 
        public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
            this.id = id;
            this.terminalid = terminalid;
            this.badge = badge;
            this.originaltimestamp = originaltimestamp;
            this.adjustedtimestamp = null;
            this.punchtype = punchtype;
        }
        
        //getters
        public Integer getID(){
            return id;
        }
        public int getTerminalid(){
            return terminalid;
        }
        public Badge getBadge(){    
            return badge;   
        }
        public LocalDateTime getOriginaltimestamp(){
            return originaltimestamp;
        }
        public LocalDateTime getAdjustedtimestamp(){
            return adjustedtimestamp;
        }
        public EventType getPunchtype(){
            return punchtype;
        }
        public PunchAdjustmentType getAjustmenttype(){
            return adjustmenttype;
        }
        
        //setters
        public void setAdjustedtimestamp(LocalDateTime adjustedtimestamp){
            this.adjustedtimestamp = adjustedtimestamp;
        }
        public void setAdjustmenttype(PunchAdjustmentType adjustmenttype){
            this.adjustmenttype = adjustmenttype;
        }
        
        //returns string using the original timestamp using toString() method
        public String printOriginal() {
             String typeStr = punchtype.toString();
             String formattedTime = originaltimestamp.format(FMT).toUpperCase();
             return "#" + badge.getId() + " " + typeStr + ": " + formattedTime;
        }
        //printAdjusted() pretty printing for the adjusted timestamp
       
        
}
  