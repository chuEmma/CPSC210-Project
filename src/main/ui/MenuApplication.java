package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.FoodManager;
import model.TableManager;
import model.exceptions.ListDoesNotContain;
import model.foodmodel.Food;
import model.tablemodel.Table;
import ui.enums.TableEnum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.SimpleFormatter;

public class MenuApplication {

    private GridPane menuItems = new GridPane();
    private Table currentTable;
    private FoodManager foodManager;
    private BorderPane layout;

    public MenuApplication(BorderPane layout, Table table, FoodManager foodManager) {
        currentTable = table;
        this.foodManager = foodManager;
        this.layout = layout;

        layout.setLeft(tableLayOut());
        layout.setBottom(priceLabel());
        layout.setRight(menuItemsLayout());
        layout.setMargin(menuItems, new Insets(4,8,0,0));
    }

    public GridPane menuItemsLayout() {
        menuItems.setHgap(5);
        menuItems.setVgap(5);
        int i = 0;
        for (Food f : foodManager.getListOfAvailableFoods()) {
            Button button = new Button(f.getName());
            button.setPrefSize(150,70);
            button.setFont(Font.font(13.5));
            menuItems.add(button, i % 3, (int) Math.ceil(i / 3));
            i++;
            button.setOnAction(handleFoodButtons(f));
        }
        payment();
        return menuItems;
    }

    public void payment() {
        Button payment = new Button("Invoice");
        payment.setStyle("-fx-base: #d6dcff");
        payment.setPrefSize(150,70);
        payment.setFont(Font.font(18));
        menuItems.add(payment,2,4);
        payment.setOnAction(handlePayment());
    }

    public EventHandler<ActionEvent> handlePayment() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentTable.getTableStatus().getStatus() == TableEnum.ORDERED
                        || currentTable.getTableStatus().getStatus() == TableEnum.INVOICED) {
                    currentTable.getTableStatus().setStatus(TableEnum.INVOICED);
                    layout.setLeft(tableLayOut());
                    Stage paymentWindow = new Stage();
                    paymentWindow.setTitle("Payment Invoice");

                    BorderPane plane = new BorderPane();
                    Scene pay = new Scene(plane, 350, 550);

                    receipt(plane, paymentWindow);

                    paymentWindow.setScene(pay);
                    paymentWindow.show();
                }
            }
        };
    }

    public void receipt(BorderPane plane, Stage window) {
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        VBox receipt = new VBox();
        restaurant(receipt);
        listingFoods(receipt);
        pricing(receipt);

        VBox end = new VBox(2);
        Label date = new Label(simpleFormatter.format(new Date()));
        Label thankyou = new Label("Thank you for coming!");
        thankyou.setFont(Font.font(17));
        end.getChildren().addAll(thankyou,date);
        end.setAlignment(Pos.CENTER);
        end.setPadding(new Insets(20,0,0,0));

        receipt.getChildren().add(end);
        payOrCancel(plane, window);
        plane.setCenter(receipt);
    }

    public void payOrCancel(BorderPane plane, final Stage window) {
        HBox payOrCancel = new HBox(20);
        Button payment = new Button("Pay");
        payment.setStyle("-fx-base: #e3facd");
        payment.setPrefSize(150,70);
        payment.setFont(Font.font(18));
        payment.setOnAction(handlePay(window));

        payOrCancel.getChildren().addAll(cancelButton(window), payment);
        payOrCancel.setAlignment(Pos.CENTER);
        payOrCancel.setPadding(new Insets(0,0,20,0));
        plane.setBottom(payOrCancel);
    }

    public Button cancelButton(final Stage window) {
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-base: #ffcec7");
        cancel.setPrefSize(150,70);
        cancel.setFont(Font.font(18));
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        return cancel;
    }

    public EventHandler<ActionEvent> handlePay(final Stage window) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentTable.getOrderedFood().resetOrderedFood();
                currentTable.getTableStatus().setStatus(TableEnum.INACTIVE);
                layout.setBottom(priceLabel());
                layout.setLeft(tableLayOut());
                window.close();
            }
        };
    }

    public void restaurant(VBox receipt) {
        VBox restaurantBox = new VBox(1);

        Label restaurant = new Label("\"I Don't Know\" Cafe");
        restaurant.setFont(Font.font("Segoe UI", FontWeight.BOLD, 34));
        Label phoneNumber = new Label("(604)XXX-XXXX");
        phoneNumber.setFont(Font.font(16));
        Label tableNumber = new Label("Table " + currentTable.getTableNumber());
        tableNumber.setFont(Font.font(14));

        restaurantBox.getChildren().addAll(restaurant,phoneNumber,tableNumber);
        restaurantBox.setAlignment(Pos.CENTER);
        restaurantBox.setPadding(new Insets(10,10,20,10));

        receipt.getChildren().add(restaurantBox);
    }

    public void listingFoods(VBox receipt) {
        for (Food food : currentTable.getOrderedFood().getListOfOrderedFood()) {
            HBox foodText = new HBox(100);
            Label foodLabel = new Label(food.getName());
            foodLabel.setPrefWidth(150);
            Label foodPrice = new Label(food.priceInFormat(food.getPrice()));
            foodText.getChildren().addAll(foodLabel,foodPrice);
            foodText.setStyle("-fx-font-size: 14");
            foodText.setAlignment(Pos.CENTER);
            receipt.getChildren().add(foodText);
        }
    }

    public void pricing(VBox receipt) {
        HBox totalBox = new HBox(30);
        Label amount = new Label("Amount:");
        Label priceAmount = new Label(currentTable.getOrderedFood().priceInFormat(currentTable.totalPrice()
                + (currentTable.totalPrice() * 0.05)));
        amount.setPrefWidth(80);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        totalBox.getChildren().addAll(amount,priceAmount);
        totalBox.setStyle("-fx-font-size: 16");

        VBox prices = new VBox(2);
        prices.getChildren().addAll(subTotalBox(),taxBox(),totalBox);
        prices.setPadding(new Insets(20,20,0,0));

        receipt.getChildren().add(prices);
    }

    public HBox taxBox() {
        HBox taxBox = new HBox(30);
        Label tax = new Label("Tax:");
        tax.setPrefWidth(80);
        Label priceTax = new Label(currentTable.getOrderedFood().priceInFormat(currentTable.totalPrice() * 0.05));
        taxBox.setAlignment(Pos.CENTER_RIGHT);
        taxBox.getChildren().addAll(tax,priceTax);
        return taxBox;
    }

    public HBox subTotalBox() {
        HBox subTotalBox = new HBox(30);
        Label subTotal = new Label("Subtotal:");
        subTotal.setPrefWidth(80);
        Label priceSub = new Label(currentTable.getOrderedFood().priceInFormat(currentTable.totalPrice()));
        subTotalBox.setAlignment(Pos.CENTER_RIGHT);
        subTotalBox.getChildren().addAll(subTotal,priceSub);
        subTotalBox.setStyle("-fx-font-size:13;");
        return subTotalBox;
    }

    public EventHandler<ActionEvent> handleFoodButtons(final Food f) {
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                currentTable.order(new Food(f.getName(), f.getPrice()));
                layout.setBottom(priceLabel());
                layout.setLeft(tableLayOut());
            }
        };
    }

    public VBox tableLayOut() {
        VBox update = new VBox();
        Label tableName = new Label("Table " + currentTable.getTableNumber());
        tableName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 40));
        update.getChildren().add(tableName);

        Label status = new Label(currentTable.getTableStatus().getStatusInString());

        settingLabel(status);
        status.setPadding(new Insets(0,0,5,0));

        HBox tableStatus = new HBox(80);
        tableStatus.getChildren().addAll(status);

        update.getChildren().add(tableStatus);
        update.setPadding(new Insets(5, 50, 10, 13));
        update.setSpacing(0);

        tableFoodLayout(update);
        return update;
    }


    public void settingLabel(Label status) {
        status.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        if (currentTable.getTableStatus().getStatus() == TableEnum.INACTIVE) {
            status.setTextFill(Color.web("#c9c9c9"));
        } else if (currentTable.getTableStatus().getStatus() == TableEnum.ORDERED) {
            status.setTextFill(Color.web("#a0e07e"));
        } else {
            status.setTextFill(Color.web("#c6cdf5"));
        }
    }

    public void tableFoodLayout(VBox update) {
        for (Food food : currentTable.getOrderedFood().getListOfOrderedFood()) {
            Button label = new Button(food.getName());
            label.setStyle("-fx-background-radius: 0; -fx-base: #ffffff");
            label.setFont(Font.font(15));
            label.setAlignment(Pos.BASELINE_LEFT);
            label.setPrefSize(365,25);
            update.getChildren().add(label);
            displaySideNotes(food,update);
            label.setOnAction(handleFoodButtonsForSideNotes(food));
        }
    }

    public void displaySideNotes(Food food, VBox update) {
        for (String s : food.showSideNotes()) {
            Label note = new Label(s);
            note.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            note.setFont(Font.font(14));
            note.setAlignment(Pos.BASELINE_LEFT);
            note.setPrefSize(365,25);
            update.getChildren().add(note);
        }
    }

    public EventHandler<ActionEvent> handleFoodButtonsForSideNotes(final Food f) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage popUpForSideNote = new Stage();
                popUpForSideNote.setTitle("Add Side Note Application");

                BorderPane plane = new BorderPane();
                Scene popUp = new Scene(plane, 450, 230);

                popUpWindow(plane, popUpForSideNote, f);

                popUpForSideNote.setScene(popUp);
                popUpForSideNote.show();
            }
        };
    }

    public void popUpWindow(BorderPane plane, Stage window, Food f) {
        VBox layout = new VBox();
        Label message = new Label("Enter side note for food:");
        message.setPadding(new Insets(25,0,10,0));
        message.setFont(Font.font(16));

        TextField userInput = new TextField();
        userInput.setPrefWidth(300);
        userInput.setFont(Font.font(16));

        layout.getChildren().add(message);
        layout.getChildren().add(userInput);

        HBox cancelSaveRemove = cancelSaveRemove(f,window,userInput);

        plane.setBottom(cancelSaveRemove);
        plane.setCenter(layout);
        plane.setMargin(cancelSaveRemove, new Insets(10,30,65,30));
        plane.setMargin(layout, new Insets(10,50,10,50));
    }

    public HBox cancelSaveRemove(Food f, Stage window, TextField userInput) {
        HBox cancelSaveRemove = new HBox();

        cancelSaveRemove.getChildren().addAll(removeFood(f,window),cancel(window),save(userInput,f,window));
        cancelSaveRemove.setSpacing(20);

        return cancelSaveRemove;
    }

    public Button save(final TextField userInput, final Food food, final Stage window) {
        Button save = new Button("Save");
        save.setStyle("-fx-base: #e3facd");
        save.setPrefSize(100,60);
        save.setFont(Font.font(16));
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!userInput.getText().isEmpty()) {
                    food.addSideNote(userInput.getText());
                    window.close();
                    layout.setLeft(tableLayOut());
                }
            }
        });
        return save;
    }

    public Button cancel(final Stage window) {
        Button cancel = new Button("Cancel");
        cancel.setFont(Font.font(16));
        cancel.setPrefSize(100,60);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                window.close();
            }
        });
        return cancel;
    }

    public Button removeFood(final Food food, final Stage window) {
        Button remove = new Button("    Remove\nselected food");
        remove.setStyle("-fx-base: #ffcec7");
        remove.setPrefSize(155,60);
        remove.setFont(Font.font(15));
        remove.setOnAction(removeHandle(food,window));
        return remove;
    }

    public EventHandler<ActionEvent> removeHandle(final Food food, final Stage window) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    currentTable.getOrderedFood().removeOrder(food);
                    if (currentTable.getOrderedFood().getListOfOrderedFood().isEmpty()) {
                        currentTable.getTableStatus().setStatus(TableEnum.INACTIVE);
                        currentTable.getTableStatus().resetStartTime();
                    }
                    layout.setBottom(priceLabel());
                    layout.setLeft(tableLayOut());
                    window.close();
                } catch (ListDoesNotContain notContain) {
                    window.close();
                }
            }
        };
    }

    public Label priceLabel() {
        Label price = new Label("Total: " + currentTable.getOrderedFood().priceInFormat(currentTable.totalPrice()));
        price.setFont(Font.font(30));
        price.setPadding(new Insets(0,50,9,13));
        return price;
    }
}
