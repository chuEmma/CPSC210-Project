package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.employeemodel.Employee;
import ui.employeeui.ClockInError;
import model.exceptions.ListDoesNotContain;
import ui.employeeui.EmployeeManager;

import java.util.ArrayList;
import java.util.List;

public class AccessCodeApplication {

    public GridPane grid = new GridPane();
    private EmployeeManager employeeManager;
    public GridPane numPad;
    private HBox clockInClockOutPlane;
    public PasswordField textField;
    public Label employeeName;
    public Label message;
    private List<Button> numPadButtons = new ArrayList<>();
    public String buttonFocusColor = "-fx-focus-color: rgb(173, 222, 194); -fx-faint-focus-color: rgb(173, 222, 194)";

    public AccessCodeApplication(BorderPane layout, EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;

        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        grid.add(createClockInClockOut(), 0, 3);
        grid.add(accessCodeTextBox(), 0, 2);
        grid.add(messageText(), 0,1);
        grid.add(employeeNameText(), 0, 0);
        grid.add(numberPad(), 0, 4);

        layout.setRight(grid);

        Image image = new Image("File:\\Users\\Emma_\\Pictures\\potato-png-kawaii-11.png");
        ImageView imageView = new ImageView(image);
        layout.setLeft(imageView);
        layout.setAlignment(imageView, Pos.CENTER_RIGHT);
        layout.setMargin(imageView, new Insets(10,0,0,70));
    }

    private GridPane numberPad() {
        numPad = new GridPane();
        for (int i = 0; i < 9; i++) {
            Button button = createNumPadButton(Integer.toString(i + 1));
            numPad.add(button, i % 3, (int) Math.ceil(i / 3));
        }

        Button button0 = createNumPadButton("0");
        numPad.add(button0, 1, 3);

        clearButton();

        numPad.setHgap(5);
        numPad.setVgap(5);

        for (Button button : numPadButtons) {
            button.setOnAction(handleNumberButton(button));
        }
        return numPad;
    }

    private EventHandler<ActionEvent> handleNumberButton(final Button button) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = textField.getText();
                textField.setText(text + numPadButtons.indexOf(button));
                message.setText("");
                employeeName.setText("");
            }
        };
    }

    private void clearButton() {
        Button clear = new Button("Clear");
        clear.setStyle("-fx-font-size: 18;" + buttonFocusColor);
        clear.setPrefSize(90, 90);
        numPad.add(clear,0,3);

        clear.setOnAction(handleClearButton());
    }

    private EventHandler<ActionEvent> handleClearButton() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setText("");
                message.setText("");
                employeeName.setText("");
            }
        };
    }

    private Button createNumPadButton(String number) {
        Button button = new Button(number);
        button.setStyle("-fx-font-size: 30;" + buttonFocusColor);
        button.setPrefSize(90, 90);
        if (number.equals("0")) {
            numPadButtons.add(0, button);
        } else {
            numPadButtons.add(button);
        }
        return button;
    }

    private HBox createClockInClockOut() {
        clockInClockOutPlane = new HBox();

        Button buttonIn = new Button("Clock In");
        buttonIn.setStyle("-fx-base: rgb(221, 240, 228); -fx-font-size: 20;" + buttonFocusColor);

        Button buttonOut = new Button("Clock Out");
        buttonOut.setStyle("-fx-base: rgb(221, 240, 228); -fx-font-size: 20;" + buttonFocusColor);

        clockInClockOutPlane.getChildren().addAll(buttonIn, buttonOut);
        buttonIn.setPrefWidth(137);
        buttonOut.setPrefWidth(137);
        clockInClockOutPlane.setSpacing(5);

        buttonInButtonOutSetAction(buttonIn, buttonOut);

        return clockInClockOutPlane;
    }

    private void buttonInButtonOutSetAction(Button buttonIn, Button buttonOut) {
        buttonIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setHandleClockInClockOut(true);
            }
        });
        buttonOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setHandleClockInClockOut(false);
            }
        });
    }

    private void setHandleClockInClockOut(Boolean clock) {
        try {
            Button button = new Button("");
            Employee employee = employeeManager.getSecurity().getEmployeeThroughAccessCode(textField.getText());
            employeeName.setText(employee.getName());
            handleIfEmployeeClockOutOrIn(clock, employee);
        } catch (NumberFormatException notInteger) {
            if (!textField.getText().isEmpty()) {
                message.setText("Access code must be in numbers.");
            } else {
                message.setText("");
            }
            employeeName.setText("");
        } catch (ListDoesNotContain noEmployee) {
            message.setText("No employee under access code.");
        } finally {
            textField.setText("");
        }
    }

    private void handleIfEmployeeClockOutOrIn(Boolean clock, Employee employee) {
        if (clock) {
            try {
                message.setText(employeeManager.clockInEmployee(employee));
            } catch (ClockInError clockInError) {
                message.setText("Employee has already been clocked in.");
            }
        } else {
            try {
                message.setText(employeeManager.clockOutEmployee(employee));
            } catch (ClockInError clockInError) {
                message.setText("Employee is not clocked in.");
            }
        }
    }

    private TextField accessCodeTextBox() {
        textField = new PasswordField();
        textField.setFont(Font.font(20));
        textField.setStyle(buttonFocusColor);
        textField.setPromptText("Enter employee access code.");
        return textField;
    }

    private Label messageText() {
        message = new Label();
        message.setTextFill(Color.web("#ff6966"));
        message.setFont(Font.font(15));
        return message;
    }

    private Label employeeNameText() {
        employeeName = new Label();
        employeeName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
        return employeeName;
    }
}
