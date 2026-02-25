package edu.jsu.mcis.cs310.tas_sp26.dao;

import edu.jsu.mcis.cs310.tas_sp26.Department;
import java.sql.*;
/**
 * DAO for the Department model.
 * Handles all database interactions related to the department table.
 * Follows the same DAO pattern used by BadgeDAO
 */
public class DepartmentDAO {

    private DAOFactory daoFactory;

    public DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Department find(int id) {

        Department department = null;

        String sql = "SELECT * FROM department WHERE id = ?";

        try (
            PreparedStatement ps =
                daoFactory.getConnection().prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int deptId = rs.getInt("id");
                String description = rs.getString("description");
                int terminalid = rs.getInt("terminalid");

                department = new Department(deptId, description, terminalid);
            }

        }
        catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

        return department;
    }

}