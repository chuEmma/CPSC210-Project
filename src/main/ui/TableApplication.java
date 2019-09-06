package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import model.TableManager;

import java.util.ArrayList;
import java.util.List;

public class TableApplication {
    public List<Button> buttons = new ArrayList<>();
    public TableManager tableManager;
    public GridPane grid = new GridPane();
    public GridPane tables;

    public TableApplication(BorderPane layout, TableManager tableManager) {
        this.tableManager = tableManager;

        grid.setVgap(20);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(tableLayout(),1,0);

        layout.setCenter(grid);
        layout.setMargin(grid,new Insets(25,10,20,90));
    }

    private GridPane tableLayout() {
        tables = new GridPane();

        tables.setVgap(5);
        tables.setHgap(200);

        for (String tableNum : tableManager.showAllTables()) {
            Button table = new Button(tableNum);
            table.setStyle("-fx-base: rgb(227, 227, 227); -fx-font-size: 30;");
            table.setPrefSize(135,100);
            buttons.add(table);
        }
        arrangeTables(tables);
        return tables;
    }

    private void arrangeTables(GridPane tables) {
        for (int i = 1; i <= buttons.size(); i++) {
            if (i % 2 == 0) {
                tables.add(buttons.get(i - 1),1, i);
            } else {
                tables.add(buttons.get(i - 1),0, i);
            }
        }
    }
}