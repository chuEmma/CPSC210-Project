package tests;


import model.TableManager;
import model.exceptions.ListDoesNotContain;
import model.tablemodel.Table;
import model.foodmodel.Food;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.enums.TableEnum;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TableTest {
    private TableManager tableManager;
    private Food chicken;
    private Food beef;
    private Table table1;

    @BeforeEach
    public void before() {
        tableManager = new TableManager();
        chicken = new Food("Chicken", 5.00);
        beef = new Food("Beef", 9.00);
        table1 = tableManager.getListOfTables().get(0);
    }

    @Test
    public void showTableTestStatusCheck() {
        //Table1 should be inactive and have no ordered food
        List<String> actual = new ArrayList<>();
        actual.add("Table 1 | Inactive\n");
        assertEquals(table1.showTable(),actual);

        //Table1 should be ordered and have no ordered food
        actual = new ArrayList<>();
        actual.add("Table 1 | Ordered\n");
        table1.getTableStatus().setStatus(TableEnum.ORDERED);
        assertEquals(table1.showTable(), actual);

        //tableui 1 should be invoiced and have no ordered food
        actual = new ArrayList<>();
        actual.add("Table 1 | Invoiced\n");
        table1.getTableStatus().setStatus(TableEnum.INVOICED);
        assertEquals(table1.showTable(), actual);
    }

    @Test
    public void showTableTestWithOrderedFood() {
        //Table1 orders chicken (1 item)
        table1.order(chicken);

        List<String> actual = new ArrayList<>();
        actual.add("Table 1 | Ordered\n");
        actual.add("Chicken");
        assertEquals(table1.showTable(), actual);

        //Table1 orders beef (2 items)
        table1.order(beef);
        actual.add("Beef");
        assertEquals(table1.showTable(), actual);
    }

    @Test
    public void showTableTestWithSideNotesOneItem() {
        //chicken has 1 side note
        chicken.addSideNote("Spicy");
        table1.order(chicken);

        List<String> actual = new ArrayList<>();
        actual.add("Table 1 | Ordered\n");
        actual.add("Chicken");
        actual.add("     > Spicy");
        assertEquals(table1.showTable(), actual);

        //chicken has 2 side notes
        chicken.addSideNote("Tender");
        actual.add(3, "     > Tender");
        assertEquals(table1.showTable(), actual);
    }

    @Test
    public void showTableTestWithSideNotesMoreItems() {
        chicken.addSideNote("Spicy");
        chicken.addSideNote("Tender");
        beef.addSideNote("Spicy");
        table1.order(chicken);
        table1.order(beef);

        List<String> actual = new ArrayList<>();
        actual.add("Table 1 | Ordered\n");
        actual.add("Chicken");
        actual.add("     > Spicy");
        actual.add("     > Tender");
        actual.add("Beef");
        actual.add("     > Spicy");
        assertEquals(table1.showTable(), actual);
    }

    @Test
    public void addOrderTest() {
        table1.order(chicken);
        //Table1 has 1 food item (Chicken $5.00)

        //Table1 total price should be 5.00
        assertTrue(table1.getOrderedFood().getListOfOrderedFood().contains(chicken));
        assertEquals(table1.totalPrice(), 5.00);

        table1.order(beef);
        //Table1 has 2 food items (Chicken and Beef 14.00);

        //Table1 total price should be 14.00
        assertTrue(table1.getOrderedFood().getListOfOrderedFood().contains(beef));
        assertEquals(table1.totalPrice(), 14.00);
    }

    @Test
    public void getValidTableTest() {
        assertEquals(tableManager.getValidTable(1), new Table(1));
        assertEquals(tableManager.getValidTable(9), null);
    }

    @Test
    public void showAllTablesTest() {
        List<String> actual = new ArrayList<>();
        actual.add("1");
        actual.add("2");
        actual.add("3");
        actual.add("4");
        actual.add("5");
        assertEquals(tableManager.showAllTables(),actual);
    }

    @Test
    public void equalsTest() {
        Table table1Duplicate = new Table(1);
        assertTrue(table1.equals(table1Duplicate));

        Table table2 = new Table(2);
        assertFalse(table1.equals(table2));

        Table tableRef = table1;
        assertFalse(table1.equals(null));
        assertFalse(table1.equals(new Food("Test",5.00)));

        assertTrue(tableRef.equals(table1));
        assertTrue(table1.hashCode() == table1Duplicate.hashCode());
    }

    @Test
    public void resetOrderedFoodTest() {
        table1.order(chicken);
        table1.order(beef);

        table1.getOrderedFood().resetOrderedFood();
        assertTrue(table1.getOrderedFood().getListOfOrderedFood().isEmpty());
        assertTrue(table1.totalPrice() == 0);
    }

    @Test
    public void removeOrderTest() {
        table1.order(beef);
        table1.order(chicken);
        try {
            table1.getOrderedFood().removeOrder(beef);
            assertFalse(table1.getOrderedFood().getListOfOrderedFood().contains(beef));
            assertEquals(table1.totalPrice(),5.00);
        } catch (ListDoesNotContain noFood){
            fail("not suppose to happen");
        }

        try {
            table1.getOrderedFood().removeOrder(new Food("Gum", 1.00));
        } catch (ListDoesNotContain noFood) { }
    }
}
