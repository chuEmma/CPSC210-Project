package model.foodmodel;

import java.text.NumberFormat;

public abstract class AbstractFood {

    //EFFECTS: returns price in $_.__ format
    public String priceInFormat(double price) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return defaultFormat.format(price);
    }
}
