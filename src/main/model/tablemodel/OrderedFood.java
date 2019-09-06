package model.tablemodel;

import model.foodmodel.AbstractFood;
import model.foodmodel.Food;
import model.exceptions.ListDoesNotContain;

import java.util.ArrayList;
import java.util.List;

public class OrderedFood extends AbstractFood {
    private List<Food> listOfOrderedFood = new ArrayList<>();
    private double totalPrice;

    public OrderedFood() {
        totalPrice = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds order into listOfOrderedFood and adds price to totalPrice
    public void addOrder(Food food) {
        listOfOrderedFood.add(food);
        totalPrice = totalPrice + food.getPrice();
    }

    //REQUIRES: food is in listOfOrderedFood
    //MODIFIES: this
    //EFFECTS: removes order from list and subtract from totalPrice
    public void removeOrder(Food food) throws ListDoesNotContain {
        if (listOfOrderedFood.contains(food)) {
            listOfOrderedFood.remove(food);
            totalPrice = totalPrice - food.getPrice();
        } else {
            throw new ListDoesNotContain();
        }
    }

    //MODIFIES: this
    //EFFECTS: resets list of ordered food and total price
    public void resetOrderedFood() {
        listOfOrderedFood = new ArrayList<>();
        totalPrice = 0;
    }

    //EFFECTS: returns a list of food and its notes to print
    public List<String> showFoodAndNotes() {
        List<String> foodAndNotes = new ArrayList<>();
        for (Food food : listOfOrderedFood) {
            foodAndNotes.add(food.getName());
            foodAndNotes.addAll(food.showSideNotes());
        }
        return foodAndNotes;
    }

    public List<Food> getListOfOrderedFood() {
        return listOfOrderedFood;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}