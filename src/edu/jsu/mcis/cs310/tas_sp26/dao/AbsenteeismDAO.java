package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp26.Employee;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class AbsenteeismDAO {

    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Absenteeism find(Employee employee, LocalDate payperiod) {
        if (employee == null || payperiod == null) return null;
        LocalDate begin = payperiod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        String sql = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, employee.getId());
            ps.setDate(2, Date.valueOf(begin));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal percentage = rs.getBigDecimal("percentage");
                    return new Absenteeism(employee, begin, percentage);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return null;
    }

    public void create(Absenteeism absenteeism) {
        if (absenteeism == null) return;
        LocalDate begin = absenteeism.getPayperiod().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        String sql =
            "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE percentage = VALUES(percentage)";
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, absenteeism.getEmployee().getId());
            ps.setDate(2, Date.valueOf(begin));
            ps.setBigDecimal(3, absenteeism.getPercentage());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}