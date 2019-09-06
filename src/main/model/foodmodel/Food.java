package model.foodmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Food extends AbstractFood {
    private String name;
    private Double price;
    private List<String> listOfNotes = new ArrayList<>();

    public Food(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    //MODIFIES: this
    //EFFECTS: sets price to new price
    public void changePrice(Double newPrice) {
        this.price = newPrice;
    }

    //MODIFIES: this
    //EFFECTS: sets name to new name
    public void changeName(String newName) {
        name = newName;
    }

    //MODIFIES: this
    //EFFECTS: adds note to listOfNotes
    public void addSideNote(String note) {
        listOfNotes.add(note);
    }

    //EFFECTS: returns a list of side notes to print
    public List<String> showSideNotes() {
        List<String> sideNotes = new ArrayList<>();
        for (String note : listOfNotes) {
            sideNotes.add("     > " + note);
        }
        return sideNotes;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public List<String> getListOfNotes() {
        return listOfNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food food = (Food) o;
        return Objects.equals(name, food.name) && Objects.equals(price, food.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price);
    }
}
