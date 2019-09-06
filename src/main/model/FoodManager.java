
package model;

import model.foodmodel.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {
    List<Food> listOfAvailableFoods = new ArrayList<>();

    public FoodManager() {
        Food chickenNoodleSoup = new Food("Chicken Noodle Soup", 6.20);
        Food clubSandwich = new Food("Club Sandwich", 8.50);
        Food bubbleTea = new Food("Bubble Tea", 5.00);
        Food pizza = new Food("Pizza Slice", 2.00);
        Food hamBurger = new Food("Hamburger", 6.75);
        Food sushiRoll = new Food("Sushi Roll (6 pieces)", 4.25);
        Food chickenWings = new Food("Chicken Wings",8.00);
        Food coke = new Food("Coke", 2.00);
        Food ramen = new Food("Ramen", 9.50);

        listOfAvailableFoods.add(chickenNoodleSoup);
        listOfAvailableFoods.add(clubSandwich);
        listOfAvailableFoods.add(bubbleTea);
        listOfAvailableFoods.add(pizza);
        listOfAvailableFoods.add(hamBurger);
        listOfAvailableFoods.add(sushiRoll);
        listOfAvailableFoods.add(chickenWings);
        listOfAvailableFoods.add(ramen);
        listOfAvailableFoods.add(coke);
    }

    public boolean validFood(Food f) {
        return listOfAvailableFoods.contains(f);
    }

    public void addNewFood(Food f) {
        if (!listOfAvailableFoods.contains(f)) {
            listOfAvailableFoods.add(f);
        }
    }

    public List<Food> getListOfAvailableFoods() {
        return listOfAvailableFoods;
    }
}
