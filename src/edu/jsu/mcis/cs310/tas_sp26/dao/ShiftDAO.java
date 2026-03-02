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
        String sql = "SELECT * FROM shift WHERE id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToShift(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding shift " + id);
        }
        return null;
    }
    
    public Shift find(Badge badge) {
        if (badge == null) return null;
        String sql = "SELECT shiftid FROM badge WHERE id = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, badge.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return find(rs.getInt("shiftid"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error finding shift for badge " + badge.getId());
        }
        return null;
    }
    
    private Shift rowToShift(ResultSet rs) throws SQLException {
        Map<String, String> data = new HashMap<>();
        data.put("id", rs.getString("id"));
        data.put("weekstart", rs.getString("weekstart"));
        data.put("start", rs.getString("start"));
        data.put("stop", rs.getString("stop"));
        data.put("lunchstart", rs.getString("lunchstart"));
        data.put("lunchstop", rs.getString("lunchstop"));
        data.put("interval", rs.getString("interval"));
        data.put("grace", rs.getString("grace"));
        data.put("dock", rs.getString("dock"));
        return new Shift(data);
    }
}
