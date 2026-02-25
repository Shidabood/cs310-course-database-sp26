package edu.jsu.mcis.cs310.tas_sp26;

import edu.jsu.mcis.cs310.tas_sp26.dao.DAOFactory;
import org.junit.*;
import static org.junit.Assert.*;
import edu.jsu.mcis.cs310.tas_sp26.Department;
import edu.jsu.mcis.cs310.tas_sp26.dao.DepartmentDAO;

public class DepartmentFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testFindDepartment1() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d1 = departmentDAO.find(1);

        /* Compare to Expected Values */
        
        assertEquals("#1 (Assembly), Terminal ID: 103", d1.toString());

    }

    @Test
    public void testFindDepartment2() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d2 = departmentDAO.find(6);

        /* Compare to Expected Values */
        
        assertEquals("#6 (Office), Terminal ID: 102", d2.toString());

    }

    @Test
    public void testFindDepartment3() {

        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();

        /* Retrieve Department from Database */
        
        Department d3 = departmentDAO.find(8);

        /* Compare to Expected Values */
        
        assertEquals("#8 (Shipping), Terminal ID: 107", d3.toString());

    }
    /**
     * Tests retrieval of department ID 2.
     * Verifies the object is not null and has the correct ID.
     */
@Test
    public void testFindDepartment4() {
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        Department d4 = departmentDAO.find(2);
        assertNotNull(d4);
        assertEquals(2, d4.getId());
    }
    /**
     * Tests that searching for a non-existent department ID returns null.
     * Verifies the DAO handles missing records gracefully.
     */
    @Test
    public void testFindDepartmentNotFound() {
        DepartmentDAO departmentDAO = daoFactory.getDepartmentDAO();
        Department d5 = departmentDAO.find(999);
        assertNull(d5);
    }
}
