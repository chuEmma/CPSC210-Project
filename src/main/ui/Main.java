package ui;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.FoodManager;
import model.TableManager;
import ui.employeeui.EmployeeManager;

import java.util.List;

import static javafx.application.Application.launch;

public class Main extends Application {
    AccessCodeApplication accessCodeApplication;
    TableApplication tableApplication;
    EmployeeManager employeeManager = new EmployeeManager();
    TableManager tableManager = new TableManager();
    FoodManager foodManager = new FoodManager();
    Stage primaryStage;
    Scene scene1;
    Scene scene2;
    Button currentTable;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        createWindow(primaryStage);
    }

    private void createWindow(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Menu Service Application");

        BorderPane accessCode = new BorderPane();
        scene1 = new Scene(accessCode, 900, 600);

        BorderPane tables = new BorderPane();
        scene2 = new Scene(tables, 900, 600);

        accessCodeApplication = new AccessCodeApplication(accessCode, employeeManager);
        enterHandle();

        tableApplication = new TableApplication(tables, tableManager);
        handleBack(tables);
        handleTables();

        accessCode.setMargin(accessCode.getRight(), new Insets(60,70,100,0));

        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    private void enterHandle() {
        Button enter = new Button("Enter");
        enter.setStyle("-fx-base: rgb(235, 245, 240); -fx-font-size: 20;" + accessCodeApplication.buttonFocusColor);
        enter.setPrefSize(90,90);
        accessCodeApplication.numPad.add(enter, 2, 3);

        enter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                handleAccessCode();
            }
        });
    }

    private void handleAccessCode() {
        try {
            if (!employeeManager.getSecurity().validEmployeeThroughAccessCode(
                    accessCodeApplication.textField.getText())) {
                accessCodeApplication.message.setText("No employee under access code.");
            } else {
                primaryStage.setScene(scene2);
            }
        } catch (NumberFormatException notInteger) {
            if (!accessCodeApplication.textField.getText().isEmpty()) {
                accessCodeApplication.message.setText("Access code must be in numbers.");
            } else {
                accessCodeApplication.message.setText("");
            }
        } finally {
            accessCodeApplication.textField.setText("");
            accessCodeApplication.employeeName.setText("");
        }
    }

    public void handleBack(BorderPane tables) {
        Button back = new Button("Back");
        back.setStyle("-fx-base: rgb(235, 245, 240); -fx-font-size: 18;" + accessCodeApplication.buttonFocusColor);
        back.setPrefSize(130,70);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene1);
            }
        });
        tables.setRight(back);
        tables.setMargin(back, new Insets(8,8,4,0));
    }

    public void handleTables() {
        List<Button> tableButtons = tableApplication.buttons;
        for (Button table : tableButtons) {
            table.setOnAction(handleTableSwitchScreen(table));
        }
    }

    public EventHandler<ActionEvent> handleTableSwitchScreen(final Button table) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BorderPane menu = new BorderPane();
                Scene scene3 = new Scene(menu, 900, 600);
                new MenuApplication(menu, tableManager.getValidTable(Integer.parseInt(table.getText())), foodManager);
                primaryStage.setScene(scene3);
                handleBackForMenu(menu);
            }
        };
    }

    public void handleBackForMenu(BorderPane menu) {
        Button back = new Button("Back");
        back.setStyle("-fx-base: rgb(235, 245, 240); -fx-font-size: 18;" + accessCodeApplication.buttonFocusColor);
        back.setPrefSize(130,70);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(scene2);
            }
        });
        menu.setTop(back);
        menu.setAlignment(back, Pos.TOP_RIGHT);
        menu.setMargin(back, new Insets(8,8,4,0));
    }
}