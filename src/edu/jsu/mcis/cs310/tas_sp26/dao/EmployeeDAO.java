/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.Employee;
import edu.jsu.mcis.cs310.tas_sp26.Badge;
import edu.jsu.mcis.cs310.tas_sp26.dao.BadgeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author shsmith
 */
public class EmployeeDAO {
    private static final String QUERY_FIND = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";
    
     private final DAOFactory daoFactory;
     
     private int ID;
     private String fName;
     private String mName;
     private String lName;
     private String badge;
     private String type;
     private String dep;
     private String active;
     public Employee employee;
     
     
    EmployeeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    public Employee find(int id){
        
        
        
        
        
        try {
            
            
            Connection conn = daoFactory.getConnection();
        
            
            PreparedStatement pstmt = conn.prepareStatement(QUERY_FIND);
            pstmt.setInt(1, ID);
            
            boolean hasresults = pstmt.execute();
            
            if ( hasresults ) {
                
                ResultSet resultset = pstmt.getResultSet();
                
                if (resultset.next())
                    //Here we will set up all of our values
                    ID = resultset.getInt("id");
                    badge = resultset.getString("badgeid");
                    fName = resultset.getString("firstname");
                    mName = resultset.getString("middlename");
                    lName = resultset.getString("lastname");
                    type = resultset.getString("employeetypeid");
                    dep = resultset.getString("departmentid");
                    active = resultset.getString("active");
                    
                    employee.setId(ID);
                    employee.setActive(active);
                    employee.setBadgeID(badge);
                    employee.setDepID(dep);
                    employee.setShiftID(active);
                    employee.setTypeID(type);
                    employee.setfName(fName);
                    employee.setlName(lName);
                    employee.setmName(mName);

                    
                    //Employee employee = new Employee(ID, badge, fName, mName, lName, type, dep, active);
                    System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
                    System.out.println(employee);
                   
                            
                
            }
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return employee;
        
    }
    public Employee find(Badge badger){
       
        Employee emp = new Employee(ID, badge, fName, mName, lName, type, dep, active);
        
        
        
        try {
            
            
            Connection conn = daoFactory.getConnection();
        
            
            PreparedStatement pstmt = conn.prepareStatement(QUERY_FIND2);
            pstmt.setString(1, badge);
            
            boolean hasresults = pstmt.execute();
            
            if ( hasresults ) {
                
                ResultSet resultset = pstmt.getResultSet();
                
                if (resultset.next())
                    //Here we will set up all of our values
                    ID = resultset.getInt("id");
                    badge = resultset.getString("badgeid");
                    fName = resultset.getString("firstname");
                    mName = resultset.getString("middlename");
                    lName = resultset.getString("lastname");
                    type = resultset.getString("employeetypeid");
                    dep = resultset.getString("departmentid");
                    active = resultset.getString("active");
                    
                    employee.setId(ID);
                    employee.setActive(active);
                    employee.setBadgeID(badge);
                    employee.setDepID(dep);
                    employee.setShiftID(active);
                    employee.setTypeID(type);
                    employee.setfName(fName);
                    employee.setlName(lName);
                    employee.setmName(mName);
                    
                    //Employee employee = new Employee(ID, badge, fName, mName, lName, type, dep, active);
                    
                    
                            
                
            }
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
       
        return employee;
    }
        
        
    @Override
    public String toString(){
        String emp = "ID #"+employee.getId() + ": " + employee.getlName() + ", " + employee.getfName() + " " + employee.getmName() + " (#" + employee.getBadgeID() + "), Type: " + employee.getTypeID() + ", Department: " + employee.getDepID() + ", Active: " + employee.getActive();
        System.out.println(emp);
        return emp;
    }
    
    
    
}
