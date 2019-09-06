package tests;

import model.exceptions.ListDoesNotContain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ui.employeeui.EmployeeManager;
import model.employeemanager.Security;
import model.employeemodel.Employee;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class EmployeeTest {
    private EmployeeManager employeeManager;
    private Security security;

    @BeforeEach
    public void Before() {
        employeeManager = new EmployeeManager();
        security = employeeManager.getSecurity();
    }

    @Test
    public void clockOutTest() {
        Employee employee = new Employee("Emma", 3);
        employee.clockOut();
        assertFalse(employee.getClockedIn());
    }

    @Test
    public void equalsTest() {
        Employee employee1 = new Employee("Emma", 3);
        Employee employee2 = new Employee("Emma", 3);

        Employee employee3diffName = new Employee("Justin", 3);
        Employee employee0 = new Employee("Justin", 4);

        Employee emma = employee1;

        assertFalse(employee2.equals(employee3diffName));
        assertFalse(employee2.equals(employee0));
        assertFalse(employee1.equals(new EmployeeManager()));
        assertTrue(employee1.equals(employee2));
        assertFalse(employee1.equals(employee0));
        assertFalse(employee1.equals(null));
        assertTrue(employee1.equals(emma));
        assertTrue(employee1.hashCode() == employee2.hashCode());
        assertFalse(employee0.hashCode() == employee2.hashCode());
    }

    @Test
    public void employeeGettersTest() {
        Employee employee1 = new Employee("Emma", 3);
        assertEquals(employee1.getName(), "Emma");
        assertEquals(employee1.getAccessCode(), 3);
    }

    @Test
    public void getEmployeeThroughAccessCodeTest() {
        try {
            Employee employee = security.getEmployeeThroughAccessCode("abc");
        } catch (ListDoesNotContain noEmployee) {
            fail("Not supposed to happen");
        } catch (NumberFormatException notInteger) {
        }

        try {
            Employee employee = security.getEmployeeThroughAccessCode("789");
        } catch (ListDoesNotContain noEmployee) {
        } catch (NumberFormatException notInteger) {
            fail("Not supposed to happen");
        }

        try {
            Employee employee = security.getEmployeeThroughAccessCode("3893");
            assertTrue(employee.equals(new Employee("Emma", 3893)));
        } catch (ListDoesNotContain noEmployee) {
            fail("Not supposed to happen");
        } catch (NumberFormatException notInteger) {
            fail("Not supposed to happen");
        }
    }

    @Test
    public void validEmployeeThroughAccessCodeTest() {
        assertTrue(security.validEmployeeThroughAccessCode("3893"));
        assertFalse(security.validEmployeeThroughAccessCode("987"));
        assertThrows(NumberFormatException.class, new Executable() {
            public void execute() {
                security.validEmployeeThroughAccessCode("abc");
            }
        });
    }
}
