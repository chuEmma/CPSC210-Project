package ui.tableui;

import ui.enums.TableEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TableStatus {
    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    private TableEnum status;
    private String startTime;

    public TableStatus() {
        status = TableEnum.INACTIVE;
        startTime = "";
    }

    //MODIFIES: this
    //EFFECTS: sets time for startTime
    public void setStartTime() {
        startTime = getTime();
    }

    public String getStartTime() {
        return startTime;
    }

    //EFFECTS: resets startTime
    public void resetStartTime() {
        startTime = "";
    }

    //MODIFIES: status
    //EFFECTS: sets status to new status
    public void setStatus(TableEnum newStatus) {
        this.status = newStatus;
    }

    public TableEnum getStatus() {
        return status;
    }

    //EFFECTS: returns string form of status
    public String getStatusInString() {
        if (status == TableEnum.INACTIVE) {
            return "Inactive";
        } else if (status == TableEnum.ORDERED) {
            return "Ordered";
        } else {
            return "Invoiced";
        }
    }

    public String getTime() {
        return formatter.format(new Date());
    }
}
