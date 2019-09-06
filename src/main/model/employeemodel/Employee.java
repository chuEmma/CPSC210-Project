package model.employeemodel;

import java.util.Objects;

public class Employee {
    private String name;
    private boolean clockedIn;
    private int accessCode;

    public Employee(String name, int accessCode) {
        this.name = name;
        this.accessCode = accessCode;
        clockedIn = false;
    }

    public String getName() {
        return name;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void clockIn() {
        clockedIn = true;
    }

    public void clockOut() {
        clockedIn = false;
    }

    public Boolean getClockedIn() {
        return clockedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return accessCode == employee.accessCode && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, accessCode);
    }
}
