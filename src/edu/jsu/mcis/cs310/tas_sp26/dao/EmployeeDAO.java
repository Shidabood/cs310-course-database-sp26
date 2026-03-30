package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.Employee;
import edu.jsu.mcis.cs310.tas_sp26.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp26.Badge;
import edu.jsu.mcis.cs310.tas_sp26.Shift;
import java.sql.*;
import java.text.SimpleDateFormat;

public class EmployeeDAO {

    private static final String QUERY_FIND_ID =
        "SELECT e.id, e.badgeid, e.firstname, e.middlename, e.lastname, e.employeetypeid, " +
        "e.departmentid, e.active, e.shiftid, d.description AS department_description " +
        "FROM employee e JOIN department d ON e.departmentid = d.id WHERE e.id = ?";

    private static final String QUERY_FIND_BADGE =
        "SELECT e.id, e.badgeid, e.firstname, e.middlename, e.lastname, e.employeetypeid, " +
        "e.departmentid, e.active, e.shiftid, d.description AS department_description " +
        "FROM employee e JOIN department d ON e.departmentid = d.id WHERE e.badgeid = ?";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {
        Employee employee = null;
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(QUERY_FIND_ID);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = employeeMapper(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return employee;
    }

    public Employee find(Badge badge) {
        if (badge == null) return null;
        Employee employee = null;
        try {
            PreparedStatement ps = daoFactory.getConnection().prepareStatement(QUERY_FIND_BADGE);
            ps.setString(1, badge.getId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = employeeMapper(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return employee;
    }

    private Employee employeeMapper(ResultSet rs) throws SQLException {
        int id       = rs.getInt("id");
        String bID   = rs.getString("badgeid");
        String fName = rs.getString("firstname");
        String mName = rs.getString("middlename");
        String lName = rs.getString("lastname");
        int typeID   = rs.getInt("employeetypeid");
        String typeStr = EmployeeType.fromId(typeID).toString();
        String depStr  = rs.getString("department_description");
        if (depStr == null) {
            depStr = String.valueOf(rs.getObject("departmentid"));
        }
        String activeStr = "";
        Date activeDate = rs.getDate("active");
        if (activeDate != null) {
            activeStr = DATE_FORMAT.format(activeDate);
        }
        int shiftId = rs.getInt("shiftid");

        Employee emp = new Employee(id, bID, fName, mName, lName, typeStr, depStr, activeStr);
        emp.setBadge(daoFactory.getBadgeDAO().find(bID));
        emp.setShift(daoFactory.getShiftDAO().find(shiftId));

        return emp;
    }
}