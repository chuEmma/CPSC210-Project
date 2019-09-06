
package model;

import model.tablemodel.Table;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    private List<Table> listOfTables = new ArrayList<>();

    public TableManager() {
        Table table1 = new Table(1);
        Table table2 = new Table(2);
        Table table3 = new Table(3);
        Table table4 = new Table(4);
        Table table5 = new Table(5);

        listOfTables.add(table1);
        listOfTables.add(table2);
        listOfTables.add(table3);
        listOfTables.add(table4);
        listOfTables.add(table5);
    }

    //EFFECTS: returns true if listOfTables contains given table
    public Table getValidTable(int number) {
        for (Table table : listOfTables) {
            if (table.getTableNumber() == number) {
                return table;
            }
        }
        return null;
    }

    //EFFECTS: returns a list of table numbers and its status for menuService main.ui
    public List<String> showAllTables() {
        List<String> forPrint = new ArrayList<>();
        for (Table table : listOfTables) {
            forPrint.add(Integer.toString(table.getTableNumber()));
        }
        return forPrint;
    }

    public List<Table> getListOfTables() {
        return listOfTables;
    }
}