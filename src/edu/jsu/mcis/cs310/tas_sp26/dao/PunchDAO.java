/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.*;
import edu.jsu.mcis.cs310.tas_sp26.Badge;
import edu.jsu.mcis.cs310.tas_sp26.EventType;
import java.sql.*;
import java.time.LocalDateTime;
import edu.jsu.mcis.cs310.tas_sp26.Punch;
import edu.jsu.mcis.cs310.tas_sp26.Punch;
/**
 *
 * @author emiel
 */
public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    private final DAOFactory daoFactory;
    
    PunchDAO (DAOFactory daoFactory){
   
        this.daoFactory = daoFactory;
    }
    
    
    public Punch find(int id) {

    Punch punch = null;

    int terminalid;
    String badgeid;
    LocalDateTime ots;
    int eventtypeid;

    try (
        Connection conn = daoFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(QUERY_FIND)
    ) {
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
        case 1 -> EventType.CLOCK_IN;
        case 0 -> EventType.CLOCK_OUT;
        case 2 -> EventType.TIME_OUT;
        default -> throw new IllegalArgumentException(
            "Invalid punchtypeid: " + eventtypeid);
    };

    punch = new Punch(id, terminalid, badge, ots, type);

    return punch;
    }
    
}

