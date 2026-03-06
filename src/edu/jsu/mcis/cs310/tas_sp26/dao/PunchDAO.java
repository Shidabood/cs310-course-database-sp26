/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.*;
import java.sql.*;
import java.time.LocalDateTime;
import edu.jsu.mcis.cs310.tas_sp26.Punch;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 *
 * @author emiel
 */
public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    public PunchDAO(DAOFactory daoFactory){
   
        this.daoFactory = daoFactory;
    }
    
    
    public Punch find(int id) {

    Punch punch = null;
    Connection conn = daoFactory.getConnection();

    int terminalid;
    String badgeid;
    LocalDateTime ots;
    int eventtypeid;

    try {
        PreparedStatement ps = conn.prepareStatement(QUERY_FIND);
        ps.setInt(1, id);

        try (ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return null;

            terminalid = rs.getInt("terminalid");
            badgeid = rs.getString("badgeid");
            ots = rs.getTimestamp("timestamp").toLocalDateTime();
            eventtypeid = rs.getInt("eventtypeid");
        }

    } catch (SQLException e) {
        throw new DAOException(e.getMessage());
    }



    BadgeDAO bdao = daoFactory.getBadgeDAO();
    Badge badge = bdao.find(badgeid);  

    EventType type = switch (eventtypeid) {
        case 0 -> EventType.CLOCK_OUT;
        case 1 -> EventType.CLOCK_IN;
        case 2 -> EventType.TIME_OUT;
        default -> throw new IllegalArgumentException(
            "Invalid punchtypeid: " + eventtypeid);
    };

    punch = new Punch(id, terminalid, badge, ots, type);

    return punch;
    }
    
    public ArrayList<Punch> list(Badge badge, LocalDate date) {
    ArrayList<Punch> punches = new ArrayList<>();
    Connection conn = daoFactory.getConnection();

    if (badge == null || date == null) {
        return punches; // return empty list safely
    }

    String sql = 
        "SELECT * FROM event " +
        "WHERE badgeid = ? " +
        "AND DATE(timestamp) = ? " +
        "ORDER BY timestamp ASC";

    try 
        
        
    {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, badge.getId());
        ps.setDate(2, java.sql.Date.valueOf(date));

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id          = rs.getInt("id");
                int terminalid  = rs.getInt("terminalid");
                LocalDateTime ots = rs.getTimestamp("timestamp").toLocalDateTime();
                int eventtypeid = rs.getInt("eventtypeid");

                EventType type = switch (eventtypeid) {
                    case 0  -> EventType.CLOCK_OUT;
                    case 1  -> EventType.CLOCK_IN;
                    case 2  -> EventType.TIME_OUT;
                    default -> throw new IllegalArgumentException("Invalid eventtypeid: " + eventtypeid);
                };

                Punch p = new Punch(id, terminalid, badge, ots, type);
                punches.add(p);
            }
        }
    } catch (SQLException e) {  // ← fixed spelling
        throw new DAOException(e.getMessage());
    }

    return punches;
}
    
    
    
    
      public int create(Punch punch) {

        int newPunchId = 0;

        PreparedStatement ps = null;
        ResultSet generatedKeys = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                int terminalid = punch.getTerminalid();
                boolean authorized = false;

                // Terminal ID of 0 means administrative/manual entry — always authorized
                if (terminalid == 0) {

                    authorized = true;

                } else {

                    // Look up the employee by badge to get their department
                    EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
                    Employee employee = employeeDAO.find(punch.getBadge());

                    if (employee != null) {

                        // Look up the department to get its terminal ID
                        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                        Department department = departmentDAO.find(employee.getDepartmentid());

                        if (department != null) {
                            // Authorize only if terminal IDs match
                            authorized = (terminalid == department.getTerminalid());
                        }

                    }

                }

                if (authorized) {

                    // Map EventType enum to its database integer value
                    int eventtypeid = punch.getPunchtype().ordinal();

                    // Convert LocalDateTime to java.sql.Timestamp
                    Timestamp ts = Timestamp.valueOf(punch.getOriginaltimestamp());

                    // Prepare the INSERT statement, requesting generated keys
                    ps = conn.prepareStatement(QUERY_CREATE, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, terminalid);
                    ps.setString(2, punch.getBadge().getId());
                    ps.setTimestamp(3, ts);
                    ps.setInt(4, eventtypeid);

                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {

                        generatedKeys = ps.getGeneratedKeys();

                        if (generatedKeys.next()) {
                            newPunchId = generatedKeys.getInt(1);
                        }

                    }

                }

            }

        } catch (SQLException e) {

            throw new DAOException(e.getMessage());

        } finally {

            if (generatedKeys != null) {
                try { generatedKeys.close(); } catch (SQLException e) { throw new DAOException(e.getMessage()); }
            }
            if (ps != null) {
                try { ps.close(); } catch (SQLException e) { throw new DAOException(e.getMessage()); }
            }

        }

        return newPunchId;

    }
    
}

