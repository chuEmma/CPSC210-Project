package ui.employeeui;

import model.employeemanager.Security;
import model.employeemodel.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> listOfEmployees = new ArrayList<>();
    private List<Employee> clockedInEmployees = new ArrayList<>();
    private Security security = new Security(listOfEmployees);
    private EmployeeStatus employeeStatus = new EmployeeStatus();

    public EmployeeManager() {
        Employee emma = new Employee("Emma", 3893);
        Employee justin = new Employee("Justin", 1);
        Employee alice = new Employee("Alice", 2);
        Employee maggie = new Employee("Maggie", 3);
        Employee amy = new Employee("Amy", 4);
        Employee cashier = new Employee("Cashier", 0);

        listOfEmployees.add(emma);
        listOfEmployees.add(justin);
        listOfEmployees.add(alice);
        listOfEmployees.add(maggie);
        listOfEmployees.add(amy);
        listOfEmployees.add(cashier);

        //cashier will always be clocked in
        cashier.clockIn();
        clockedInEmployees.add(cashier);
    }

    //MODIFIES: this and employee
    //EFFECTS: changes employee status to clocked in and adds them into clockedInEmployees list
    //         throws ClockInError if employee is in clockedInEmployees
    //         throws ListDoesNotContain if employee does not exist in listOfEmployee
    public String clockInEmployee(Employee employee) throws ClockInError {
        if (!clockedInEmployees.contains(employee)) {
            employee.clockIn();
            clockedInEmployees.add(employee);
            return "Clocked in at: " + employeeStatus.getTime();
        } else {
            throw new ClockInError();
        }
    }

    //REQUIRES: listOFEmployee contains employee and clockedInEmployees contains employee
    //MODIFIES: this and employee
    //EFFECTS: changes employee status to clocked out and removes them from clockedInEmployees
    //         throws ClockInError if employee is not in clockedInEmployees
    //         throws ListDoesNotContain if employee does not exist in listOfEmployees
    public String clockOutEmployee(Employee employee) throws ClockInError {
        if (clockedInEmployees.contains(employee)) {
            employee.clockOut();
            clockedInEmployees.remove(employee);
            return "Clocked out at: " + employeeStatus.getTime();
        } else {
            throw new ClockInError();
        }
    }

    public Security getSecurity() {
        return security;
    }
}
