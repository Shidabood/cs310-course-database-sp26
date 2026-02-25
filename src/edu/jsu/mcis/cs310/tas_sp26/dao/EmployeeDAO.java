package edu.jsu.mcis.cs310.tas_sp26.dao;
import edu.jsu.mcis.cs310.tas_sp26.Employee;
import edu.jsu.mcis.cs310.tas_sp26.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp26.Badge;
import java.sql.*;
import java.text.SimpleDateFormat;

public class EmployeeDAO {
//Here I used join for the department to make sure the employee exists, if they dont exist in a department it wont work
    private static final String QUERY_FIND_ID = "SELECT e.id, e.badgeid, e.firstname, e.middlename, e.lastname, e.employeetypeid, e.departmentid, e.active, d.description AS department_description " +
        "FROM employee e JOIN department d ON e.departmentid = d.id WHERE e.id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT e.id, e.badgeid, e.firstname, e.middlename, e.lastname, e.employeetypeid, e.departmentid, e.active, d.description AS department_description " +
        "FROM employee e JOIN department d ON e.departmentid = d.id WHERE e.badgeid = ?";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Employee find(int id) {
        Employee employee = null;
        Connection con = daoFactory.getConnection();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(QUERY_FIND_ID);
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = employeeMapper(rs);
                }
            }
        } catch (SQLException e) {
            //System.out.println("SQL PROBLEM");
            throw new DAOException(e.getMessage());
        }
        return employee;
    }

    public Employee find(Badge badge) {
        Connection con = daoFactory.getConnection();
        if (badge == null) return null;
        Employee employee = null;
        try{
            
            PreparedStatement ps = con.prepareStatement(QUERY_FIND_BADGE);
            ps.setString(1, badge.getId());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    employee = employeeMapper(rs);
                }
            }
        } catch (SQLException e) {
            //System.out.println("SQL PROBLEM");
            throw new DAOException(e.getMessage());
            
        }
        return employee;
    }

    
    //Here I grabbed the data from the resultset, turning it into the right format for Employee
    //Earlier I had done this manually in both classes
    private Employee employeeMapper(ResultSet rs) throws SQLException {//It took me forevor to realize I needed to throw the exception like this instead of a try catch
        
        int id = rs.getInt("id");
        String bID = rs.getString("badgeid");
        String fName = rs.getString("firstname");
        String mName = rs.getString("middlename");
        String lName = rs.getString("lastname");
        int typeID = rs.getInt("employeetypeid");
        String typeStr = EmployeeType.fromId(typeID).toString();
        String depStr = rs.getString("department_description");
        if (depStr == null) {
            depStr = String.valueOf(rs.getObject("departmentid"));
        }
        String activeStr = "";
        Date activeDate = rs.getDate("active");
        if (activeDate != null) {
            activeStr = DATE_FORMAT.format(activeDate);
        }
        //I originally coded it so that employee was initialized as null at the start and all the values were assigned here so the return emp would work outside of the try catch
        //I changed it because no matter what it would return the employee as null
        Employee emp = new Employee(id, bID, fName, mName, lName, typeStr, depStr, activeStr);
      
        
        //System.out.println(emp);
        return emp ;
       
       
        
    }
}
