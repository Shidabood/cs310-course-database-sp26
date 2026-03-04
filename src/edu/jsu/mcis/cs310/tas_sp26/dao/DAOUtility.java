package edu.jsu.mcis.cs310.tas_sp26.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp26.Punch;
import edu.jsu.mcis.cs310.tas_sp26.Shift;

/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

    //start of cole 
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift) {
    
    ArrayList<HashMap<String, String>> punchMaps = new ArrayList<>();
    
    for (int i = 0; i < punchlist.size(); i++) {
        Punch p = punchlist.get(i);
        HashMap<String, String> pMap = new HashMap<>();
        
        // using the getters from Punch
        pMap.put("id", String.valueOf(p.getID()));
        pMap.put("badgeid", p.getBadge().getId()); 
        pMap.put("terminalid", String.valueOf(p.getTerminalid()));
        pMap.put("punchtypeid", String.valueOf(p.getPunchtype().ordinal())); 
        pMap.put("punchstatus", p.getPunchtype().toString());
        
        // formatting the timestamps to strings
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        pMap.put("timestamp", p.getOriginaltimestamp().format(dtf).toUpperCase());
        
        if (p.getAdjustedtimestamp() != null) {
            pMap.put("adjustedtimestamp", p.getAdjustedtimestamp().format(dtf).toUpperCase());
        } else {
            pMap.put("adjustedtimestamp", "null");
        }

        punchMaps.add(pMap);
    }
    
    HashMap<String, Object> jsonMap = new HashMap<>();
    
    // placeholder logic for the missing methods
    int totalMins = 0; 
    double absentPercent = 0.0;
    
    // attempt to call the methods if they exist, otherwise use the 0.0 above
    
    jsonMap.put("punchlist", punchMaps);
    jsonMap.put("totalminutes", String.valueOf(totalMins));
    jsonMap.put("absenteeism", String.format("%.2f", absentPercent));
    
    return Jsoner.serialize(jsonMap);
}
}