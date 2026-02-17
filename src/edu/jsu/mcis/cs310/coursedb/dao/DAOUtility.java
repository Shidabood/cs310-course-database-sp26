package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_SP26 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
        
            while (rs.next()) {
            
                JsonObject obj = new JsonObject();
            
                for (int i = 1; i <= columnCount; i++) {
                    String colName = meta.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    obj.put(colName, value);
                }   // end for
                
                records.add(obj);
            }       // end while

        }           // end if
        
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    
    return Jsoner.serialize(records);
   }
}
