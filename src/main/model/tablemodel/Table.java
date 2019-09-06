
package model.tablemodel;

import model.foodmodel.Food;
import ui.tableui.TableStatus;
import ui.enums.TableEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {

    private int tableNumber;
    private TableStatus tableStatus = new TableStatus();
    private OrderedFood orderedFood = new OrderedFood();

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public OrderedFood getOrderedFood() {
        return orderedFood;
    }

    public TableStatus getTableStatus() {
        return tableStatus;
    }

    //EFFECTS: calls orderedFood to add food
    public void order(Food f) {
        orderedFood.addOrder(f);
        tableStatus.setStatus(TableEnum.ORDERED);
        tableStatus.setStartTime();
    }

    //EFFECTS calls orderedFood and returns totalPrice
    public double totalPrice() {
        return orderedFood.getTotalPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Table table = (Table) o;
        return tableNumber == table.tableNumber;
    }

    //REQUIRES: table is a valid table
    //EFFECTS: returns a list of things to print in order for menuService main.ui
    //         (note: after each element will be a break in line)
    public List<String> showTable() {
        List<String> forPrint = new ArrayList<>();
        List<String> orderedFood = getOrderedFood().showFoodAndNotes();
        forPrint.add("Table " + getTableNumber() + " | " + getTableStatus().getStatusInString() + "\n");
        forPrint.addAll(orderedFood);
        return forPrint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber);
    }
}