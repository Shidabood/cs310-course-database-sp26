package edu.jsu.mcis.cs310.tas_sp26;

import edu.jsu.mcis.cs310.tas_sp26.dao.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import com.github.cliftonlabs.json_simple.*;
import java.math.BigDecimal;

import org.junit.*;
import static org.junit.Assert.*;

public class Version2_JSONTest3 {
    
    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }
    
    @Test
    public void testBadge1() {
        
        try {
        
            BadgeDAO badgeDao = daoFactory.getBadgeDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"id\":\"DF19620C\",\"description\":\"Forte, Genoveva G\"}";
            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Badge */

            Badge b = badgeDao.find("DF19620C");

            /* JSON Conversion */

            String actualJSON = b.toJson();
            JsonObject actual = (JsonObject)(Jsoner.deserialize(actualJSON));

            /* Compare to Expected JSON */

            assertEquals(expected, actual);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testDepartment1() {
        
        try {
        
            DepartmentDAO departmentDao = daoFactory.getDepartmentDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"id\":3,\"description\":\"Warehouse\",\"terminalid\":106}";
            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Department */

            Department d = departmentDao.find(3);

            /* JSON Conversion */

            String actualJSON = d.toJson();
            JsonObject actual = (JsonObject)(Jsoner.deserialize(actualJSON));

            /* Compare to Expected JSON */

            assertEquals(expected, actual);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testShift1() {
        
        try {
        
            ShiftDAO shiftDao = daoFactory.getShiftDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"id\":1,\"description\":\"Shift 1\",\"defaultschedule\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"schedule\":{\"1\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"2\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"3\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"4\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"5\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360}}}";
            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Shift */

            Shift s = shiftDao.find(1);

            /* JSON Conversion */

            String actualJSON = s.toJson();
            JsonObject actual = (JsonObject)(Jsoner.deserialize(actualJSON));

            /* Compare to Expected JSON */

            assertEquals(expected, actual);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testEmployee1() {
        
        try {
        
            EmployeeDAO employeeDao = daoFactory.getEmployeeDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"id\":\"94\",\"firstname\":\"Rose\",\"middlename\":\"R\",\"lastname\":\"Hill\",\"employeetype\":\"Temporary / Part-Time\",\"active\":\"01/08/2016\",\"inactive\":null,\"badge\":{\"description\":\"Hill, Rose R\",\"id\":\"C278A564\"},\"department\":{\"description\":\"Assembly\",\"id\":1,\"terminalid\":103},\"shift\":{\"schedule\":{\"1\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"},\"2\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"},\"3\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"},\"4\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"},\"5\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"}},\"description\":\"Shift 1\",\"id\":1,\"defaultschedule\":{\"lunchthreshold\":360,\"dockpenalty\":15,\"lunchstart\":\"12:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"roundinterval\":15,\"shiftstart\":\"07:00:00\"}}}";
            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Employee */

            Employee e = employeeDao.find(94);

            /* JSON Conversion */

            String actualJSON = e.toJson();
            JsonObject actual = (JsonObject)(Jsoner.deserialize(actualJSON));

            /* Compare to Expected JSON */

            assertEquals(expected, actual);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testAbsenteeism1() {
        
        try {
        
            EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
            PunchDAO punchDAO = daoFactory.getPunchDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"payperiod\":\"09/02/2018\",\"percentage\":2.5000,\"employee\":{\"id\":\"16\",\"firstname\":\"Matthew\",\"middlename\":\"S\",\"lastname\":\"Woods\",\"employeetype\":\"Full-Time\",\"active\":\"07/28/2015\",\"inactive\":null,\"badge\":{\"id\":\"28DC3FB8\",\"description\":\"Woods, Matthew S\"},\"department\":{\"id\":4,\"description\":\"Grinding\",\"terminalid\":104},\"shift\":{\"id\":1,\"description\":\"Shift 1\",\"defaultschedule\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"schedule\":{\"1\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"2\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"3\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"4\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360},\"5\":{\"shiftstart\":\"07:00:00\",\"shiftstop\":\"15:30:00\",\"lunchstart\":\"12:00:00\",\"lunchstop\":\"12:30:00\",\"graceperiod\":5,\"dockpenalty\":15,\"roundinterval\":15,\"lunchthreshold\":360}}}}}";
            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Punch/Employee Objects */

            Punch p = punchDAO.find(3634);
            Employee e = employeeDAO.find(p.getBadge());
            Shift s = e.getShift();
            Badge b = e.getBadge();

            /* Get Pay Period Punch List */

            LocalDate ts = p.getOriginaltimestamp().toLocalDate();
            LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

            ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);

            /* Adjust Punch List */

            for (Punch punch : punchlist) {
                punch.adjust(s);
            }

            /* Compute Total Absenteeism for Pay Period */

            BigDecimal percentage = DAOUtility.calculateAbsenteeism(punchlist, s);

            /* Create/Serialize Absenteeism Object */

            Absenteeism a = new Absenteeism(e, ts, percentage);

            String actualJSON = a.toJson();
            JsonObject actual = (JsonObject)(Jsoner.deserialize(actualJSON));

            /* Compare to Expected JSON */

            assertEquals(expected, actual);
        
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testPunch1() {
        
        try {

            PunchDAO punchDAO = daoFactory.getPunchDAO();
            ShiftDAO shiftDAO = daoFactory.getShiftDAO();
            
            /* Expected JSON Data */

            String expectedJSON = "[{\"id\":3634,\"terminalid\":104,\"eventtype\":\"CLOCK IN\",\"badge\":{\"description\":\"Woods, Matthew S\",\"id\":\"28DC3FB8\"},\"originaltimestamp\":\"Fri 09/07/2018 06:50:35\",\"adjustedtimestamp\":\"Fri 09/07/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\"},{\"id\":3687,\"terminalid\":104,\"eventtype\":\"CLOCK OUT\",\"badge\":{\"description\":\"Woods, Matthew S\",\"id\":\"28DC3FB8\"},\"originaltimestamp\":\"Fri 09/07/2018 12:03:54\",\"adjustedtimestamp\":\"Fri 09/07/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\"},{\"id\":3688,\"terminalid\":104,\"eventtype\":\"CLOCK IN\",\"badge\":{\"description\":\"Woods, Matthew S\",\"id\":\"28DC3FB8\"},\"originaltimestamp\":\"Fri 09/07/2018 12:23:41\",\"adjustedtimestamp\":\"Fri 09/07/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\"},{\"id\":3716,\"terminalid\":104,\"eventtype\":\"CLOCK OUT\",\"badge\":{\"description\":\"Woods, Matthew S\",\"id\":\"28DC3FB8\"},\"originaltimestamp\":\"Fri 09/07/2018 15:34:13\",\"adjustedtimestamp\":\"Fri 09/07/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\"}]";
            JsonArray expected = (JsonArray)(Jsoner.deserialize(expectedJSON));
            
            /* Get Shoft/Punch Objects */

            Shift s1 = shiftDAO.find(1);

            Punch p1 = punchDAO.find(3634);
            Punch p2 = punchDAO.find(3687);
            Punch p3 = punchDAO.find(3688);
            Punch p4 = punchDAO.find(3716);

            /* Adjust Punches According to Shift Ruleset */

            p1.adjust(s1);
            p2.adjust(s1);
            p3.adjust(s1);
            p4.adjust(s1);

            /* Serialize Punches and Compare to Expected JSON */

            assertEquals(expected.get(0), Jsoner.deserialize(p1.toJson()));
            assertEquals(expected.get(1), Jsoner.deserialize(p2.toJson()));
            assertEquals(expected.get(2), Jsoner.deserialize(p3.toJson()));
            assertEquals(expected.get(3), Jsoner.deserialize(p4.toJson()));
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}