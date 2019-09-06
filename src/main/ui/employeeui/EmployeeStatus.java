package ui.employeeui;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeStatus {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //MODIFIES: this
    //EFFECTS: changes clockedIn to false

    public String getTime() {
        return formatter.format(new Date());
    }
}
