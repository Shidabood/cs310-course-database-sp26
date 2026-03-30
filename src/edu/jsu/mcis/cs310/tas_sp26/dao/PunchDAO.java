package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND =
        "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_CREATE =
        "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";

    private final DAOFactory daoFactory;

    public PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {
        Punch punch = null;
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(QUERY_FIND);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int terminalid    = rs.getInt("terminalid");
                    String badgeid    = rs.getString("badgeid");
                    LocalDateTime ots = rs.getTimestamp("timestamp").toLocalDateTime();
                    int eventtypeid   = rs.getInt("eventtypeid");
                    Badge badge = daoFactory.getBadgeDAO().find(badgeid);
                    EventType type = switch (eventtypeid) {
                        case 0  -> EventType.CLOCK_OUT;
                        case 1  -> EventType.CLOCK_IN;
                        case 2  -> EventType.TIME_OUT;
                        default -> throw new IllegalArgumentException("Invalid eventtypeid: " + eventtypeid);
                    };
                    punch = new Punch(id, terminalid, badge, ots, type);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return punch;
    }

    public ArrayList<Punch> list(Badge badge, LocalDate date) {
        return list(badge, date, date);
    }

    public ArrayList<Punch> list(Badge badge, LocalDate begin, LocalDate end) {
        ArrayList<Punch> punches = new ArrayList<>();
        if (badge == null || begin == null || end == null) return punches;

        String sql =
            "SELECT * FROM event WHERE badgeid = ? " +
            "AND DATE(timestamp) BETWEEN ? AND ? " +
            "ORDER BY timestamp ASC";

        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(sql);
            ps.setString(1, badge.getId());
            ps.setDate(2, java.sql.Date.valueOf(begin));
            ps.setDate(3, java.sql.Date.valueOf(end));
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
                    punches.add(new Punch(id, terminalid, badge, ots, type));
                }
            }
        } catch (SQLException e) {
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
                if (terminalid == 0) {
                    authorized = true;
                } else {
                    EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
                    Employee employee = employeeDAO.find(punch.getBadge());
                    if (employee != null) {
                        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
                        Department department = departmentDAO.find(employee.getId());
                        if (department != null) {
                            authorized = (terminalid == department.getTerminalid());
                        }
                    }
                }
                if (authorized) {
                    int eventtypeid = punch.getPunchtype().ordinal();
                    Timestamp ts = Timestamp.valueOf(punch.getOriginaltimestamp());
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