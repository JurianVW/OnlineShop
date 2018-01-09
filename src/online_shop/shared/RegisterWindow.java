package online_shop.shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import online_shop.client.Client;

public class RegisterWindow {
    public Stage registerStage;
    public boolean succes = false;

    private Client client;

    public RegisterWindow(Client client) {
        createStage();
        this.client = client;
    }

    private void createStage() {
        //Got the code for this loginscreen from this site: https://docs.oracle.com/javafx/2/get_started/form.htm
        registerStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Register");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label lblName = new Label("Full Name:");
        grid.add(lblName, 0, 1);
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        Label lblEmail = new Label("Email:");
        grid.add(lblEmail, 0, 2);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 2);

        Label lblPassword = new Label("Password:");
        grid.add(lblPassword, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        Label lblStreet = new Label("Streetname:");
        grid.add(lblStreet, 0, 4);
        TextField streetTextField = new TextField();
        grid.add(streetTextField, 1, 4);

        Label lblHouseNumber = new Label("House number:");
        grid.add(lblHouseNumber, 0, 5);
        TextField numberTextField = new TextField();
        grid.add(numberTextField, 1, 5);

        Label lblPostalCode = new Label("Postal Code:");
        grid.add(lblPostalCode, 0, 6);
        TextField postalCodeTextField = new TextField();
        grid.add(postalCodeTextField, 1, 6);

        Label lblPlace = new Label("Place:");
        grid.add(lblPlace, 0, 7);
        TextField placeTextField = new TextField();
        grid.add(placeTextField, 1, 7);

        Button btnRegister = new Button("Register");
        HBox hbBtnRegister = new HBox(10);
        hbBtnRegister.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnRegister.getChildren().add(btnRegister);
        grid.add(hbBtnRegister, 1, 8);

        final Text errorMessage = new Text();
        grid.add(errorMessage, 0, 4, 2, 1);


        btnRegister.setOnAction(e -> {
            String message = client.register(nameTextField.getText(), emailTextField.getText(), pwBox.getText(), streetTextField.getText(), numberTextField.getText(), postalCodeTextField.getText(), placeTextField.getText());
            if (message == null) {
                registerStage.close();
            }
            errorMessage.setText("Registering failed failed");
        });
        btnRegister.setDefaultButton(true);


        pwBox.setOnMouseClicked(e -> {
            errorMessage.setText("");
        });

        Scene scene = new Scene(grid, 300, 325);
        registerStage.setScene(scene);
        registerStage.setResizable(false);
        registerStage.show();
    }
}
