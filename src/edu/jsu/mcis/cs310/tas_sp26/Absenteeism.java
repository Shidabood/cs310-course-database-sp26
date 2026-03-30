package edu.jsu.mcis.cs310.tas_sp26;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Absenteeism {

    private final Employee employee;
    private final LocalDate payperiod;
    private final BigDecimal percentage;

    public Absenteeism(Employee employee, LocalDate payperiod, BigDecimal percentage) {
        this.employee   = employee;
        this.payperiod  = payperiod;
        this.percentage = percentage;
    }

    public Employee getEmployee()     { return employee; }
    public LocalDate getPayperiod()   { return payperiod; }
    public BigDecimal getPercentage() { return percentage; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate begin = payperiod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        return String.format("#%s (Pay Period Starting %s): %.2f%%",
            employee.getBadgeID(),
            begin.format(fmt),
            percentage
        );
    }
}