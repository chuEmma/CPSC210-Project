package model.employeemanager;

import model.exceptions.ListDoesNotContain;
import model.employeemodel.Employee;

import java.util.List;

public class Security {
    private List<Employee> listOfEmployees;

    public Security(List<Employee> listOfEmployees) {
        this.listOfEmployees = listOfEmployees;
    }

    //REQUIRES: input can be converted to int
    //EFFECTS: returns employee if access code is found, else false
    //         throws NumberFormatException if not integer
    public Employee getEmployeeThroughAccessCode(String input) throws ListDoesNotContain {
        int accessCode = Integer.parseInt(input);
        for (Employee employee : listOfEmployees) {
            if (employee.getAccessCode() == accessCode) {
                return employee;
            }
        }
        throw new ListDoesNotContain();
    }

    public boolean validEmployeeThroughAccessCode(String input) {
        int accessCode = Integer.parseInt(input);
        for (Employee employee : listOfEmployees) {
            if (employee.getAccessCode() == accessCode) {
                return true;
            }
        }
        return false;

    }
}