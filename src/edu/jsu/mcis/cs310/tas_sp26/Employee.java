package edu.jsu.mcis.cs310.tas_sp26;

import com.github.cliftonlabs.json_simple.*;
import java.io.*;
import java.time.*;

public class Employee implements Jsonable {

    private int id;
    private String badgeID;
    private String fName;
    private String mName;
    private String lName;
    private String typeID;
    private String depID;
    private String shiftID;
    private String active;
    private LocalDateTime inactive;
    private Badge badge;
    private Department department;
    private Shift shift;

    public Employee(int id, String badgeID, String fName, String mName, String lName,
                    String typeID, String depID, String active) {
        this.id = id;
        this.badgeID = badgeID;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.typeID = typeID;
        this.depID = depID;
        this.active = active;
    }

    public Employee() {}

    public int getId() { return id; }
    public String getBadgeID() { return badgeID; }
    public String getfName() { return fName; }
    public String getmName() { return mName; }
    public String getlName() { return lName; }
    public String getTypeID() { return typeID; }
    public String getDepID() { return depID; }
    public String getShiftID() { return shiftID; }
    public String getActive() { return active; }
    public LocalDateTime getInactive() { return inactive; }
    public Badge getBadge() { return badge; }
    public Department getDepartment() { return department; }
    public Shift getShift() { return shift; }

    public void setId(int id) { this.id = id; }
    public void setBadgeID(String badgeID) { this.badgeID = badgeID; }
    public void setfName(String fName) { this.fName = fName; }
    public void setmName(String mName) { this.mName = mName; }
    public void setlName(String lName) { this.lName = lName; }
    public void setTypeID(String typeID) { this.typeID = typeID; }
    public void setDepID(String depID) { this.depID = depID; }
    public void setShiftID(String shiftID) { this.shiftID = shiftID; }
    public void setActive(String active) { this.active = active; }
    public void setInactive(LocalDateTime inactive) { this.inactive = inactive; }
    public void setBadge(Badge badge) { this.badge = badge; }
    public void setDepartment(Department department) { this.department = department; }
    public void setShift(Shift shift) { this.shift = shift; }

    @Override
    public String toString() {
        String emp = "ID #" + id + ": " + lName + ", " + fName + " " + mName +
                     " (#" + badgeID + "), Type: " + typeID + ", Department: " + depID +
                     ", Active: " + active;
        System.out.println(emp);
        return emp;
    }

    @Override
    public String toJson() {
        return Jsoner.serialize(toJsonObject());
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        toJsonObject().toJson(writer);
    }

    private JsonObject toJsonObject() {
        JsonObject json = new JsonObject();
        json.put("id", String.valueOf(this.id));
        json.put("firstname", this.fName);
        json.put("middlename", this.mName);
        json.put("lastname", this.lName);
        json.put("employeetype", this.typeID);
        json.put("active", this.active);
        json.put("inactive", (inactive == null) ? null : inactive.toString());
        json.put("badge", this.badge);
        json.put("department", this.department);
        json.put("shift", this.shift);
        return json;
    }
}