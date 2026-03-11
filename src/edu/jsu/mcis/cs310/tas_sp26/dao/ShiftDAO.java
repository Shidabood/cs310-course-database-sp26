package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.Shift;
import edu.jsu.mcis.cs310.tas_sp26.Badge;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ShiftDAO {

    private final DAOFactory daoFactory;

    public ShiftDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Shift find(int id) {
        Shift result = null;
        String sql = "SELECT * FROM shift WHERE id = ?";
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rowToShift(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding shift " + id + ": " + e.getMessage());
        }
        return result;
    }

    public Shift find(Badge badge) {
        if (badge == null) return null;
        String sql = "SELECT e.shiftid, s.id, s.description, s.shiftstart, s.shiftstop, " +
                     "s.lunchstart, s.lunchstop, s.roundinterval, s.graceperiod, " +
                     "s.dockpenalty, s.lunchthreshold " +
                     "FROM employee e JOIN shift s ON e.shiftid = s.id " +
                     "WHERE e.badgeid = ?";
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(sql);
            ps.setString(1, badge.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToShift(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding shift for badge " + badge.getId() + ": " + e.getMessage());
        }
        return null;
    }

    private Shift rowToShift(ResultSet rs) throws SQLException {
        Map<String, String> data = new HashMap<>();
        data.put("id", rs.getString("id"));
        data.put("description", rs.getString("description"));
        data.put("start", rs.getString("shiftstart"));
        data.put("stop", rs.getString("shiftstop"));
        data.put("lunchstart", rs.getString("lunchstart"));
        data.put("lunchstop", rs.getString("lunchstop"));
        data.put("interval", rs.getString("roundinterval"));
        data.put("grace", rs.getString("graceperiod"));
        data.put("dock", rs.getString("dockpenalty"));
        data.put("lunchthreshold", rs.getString("lunchthreshold"));
        return new Shift(data);
    }
}