package tests;

import model.FoodManager;
import model.foodmodel.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoodTest {
    private FoodManager foodManager;

    @BeforeEach
    public void before() {
        foodManager = new FoodManager();
    }

    @Test
    public void validFoodTest() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        Food beef = new Food("Beef", 6.00);
        assertTrue(foodManager.validFood(chickenNoodleSoup));
        assertFalse(foodManager.validFood(beef));
    }

    @Test
    public void addNewFoodTest() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        Food beef = new Food("Beef", 6.00);
        foodManager.addNewFood(beef);
        assertTrue(foodManager.getListOfAvailableFoods().contains(beef));

        foodManager.addNewFood(chickenNoodleSoup);
        assertTrue(foodManager.getListOfAvailableFoods().contains(chickenNoodleSoup));
    }

    @Test
    public void changePriceTest() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        chickenNoodleSoup.changePrice(5.00);
        assertTrue(chickenNoodleSoup.getPrice() == 5.00);
    }

    @Test
    public void changeNameTest() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        chickenNoodleSoup.changeName("Chicken Soup");
        assertEquals(chickenNoodleSoup.getName(), "Chicken Soup");
    }

    @Test
    public void getListOfNotesTest() {
        List<String> actual = new ArrayList<>();
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        chickenNoodleSoup.addSideNote("Hot");

        actual.add("Hot");

        assertEquals(chickenNoodleSoup.getListOfNotes(), actual);
    }

    @Test
    public void foodEqualsTest() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        Food chicken = new Food("Chicken Noodle Soup", 6.20);

        Food beef = new Food("Beef", 6.20);
        Food chickenDifferent = new Food("Chicken Noodle Soup", 7.20);

        assertFalse(chicken.equals(chickenDifferent));
        assertFalse(beef.equals(chicken));
        assertTrue(chicken.equals(chickenNoodleSoup));
        assertFalse(chicken.equals(new FoodManager()));
        assertFalse(chicken.equals(null));
        assertTrue(chicken.hashCode() == chickenNoodleSoup.hashCode());
    }

    @Test
    public void priceFormatTest() {
        Food food = new Food("Chicken", 6.00);
        assertEquals(food.priceInFormat(6.00),"$6.00");
    }
}