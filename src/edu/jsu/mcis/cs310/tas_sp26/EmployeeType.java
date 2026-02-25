package edu.jsu.mcis.cs310.tas_sp26;

public enum EmployeeType {

    PART_TIME("Temporary / Part-Time"),
    FULL_TIME("Full-Time");
    private final String description;

    private EmployeeType(String d) {
        description = d;
    }

    @Override
    public String toString() {
        return description;
    }

    
    public static EmployeeType fromId(int id) {
        switch (id) {
            case 1: return FULL_TIME;
            case 2: return PART_TIME;
            default: return PART_TIME;
        }
    }
}
