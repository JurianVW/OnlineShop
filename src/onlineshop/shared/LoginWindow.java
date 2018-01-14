package onlineshop.shared;

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
import onlineshop.client.Client;
import onlineshop.shop.Shop;
import onlineshop.supplier.Supplier;

public class LoginWindow {
    private Stage loginStage;

    private Client client;
    private Shop shop;
    private Supplier supplier;
    private AccountType loginType;

    private String welcomeMessage = "Login";
    private String title;

    public LoginWindow(Client client, String shopName) {
        loginType = AccountType.CUSTOMER;
        this.client = client;
        title = shopName;
        createStage();
    }

    public LoginWindow(Shop shop, String shopName) {
        loginType = AccountType.SHOPEMPLOYEE;
        welcomeMessage = "Shop Login";
        this.shop = shop;
        title = shopName;
        createStage();
    }

    public LoginWindow(Supplier supplier, String supplierName) {
        loginType = AccountType.SUPPLIEREMPLOYEE;
        welcomeMessage = "Supplier Login";
        this.supplier = supplier;
        title = supplierName;
        createStage();
    }

    private void createStage() {
        //Got the code for this loginscreen from this site: https://docs.oracle.com/javafx/2/get_started/form.htm
        loginStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text(welcomeMessage);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btnSignIn = new Button("Sign in");
        HBox hbBtnSignIn = new HBox(10);
        hbBtnSignIn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnSignIn.getChildren().add(btnSignIn);
        grid.add(hbBtnSignIn, 1, 4);

        final Text errorMessage = new Text();
        grid.add(errorMessage, 0, 4, 2, 1);

        if (loginType == AccountType.CUSTOMER) {
            Button btnRegister = new Button("Register");
            HBox hbBtnRegister = new HBox(10);
            hbBtnRegister.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtnRegister.getChildren().add(btnRegister);
            grid.add(hbBtnRegister, 1, 5);

            final Text registerText = new Text("Don't have an account yet?");
            grid.add(registerText, 0, 5, 2, 1);

            btnRegister.setOnAction(e -> {
                RegisterWindow registerWindow = new RegisterWindow(client);
                registerWindow.getRegisterStage().setOnHidden(event -> {
                    loginStage.show();
                });
            });
        }

        btnSignIn.setOnAction(e -> {
            boolean loginFailed = false;
            switch (loginType) {
                case SUPPLIEREMPLOYEE:
                    if (supplier.logIn(userTextField.getText(), pwBox.getText())) {
                        loginStage.close();
                    } else {
                        loginFailed = true;
                    }
                    break;
                case SHOPEMPLOYEE:
                    if (shop.logInShop(userTextField.getText(), pwBox.getText())) {
                        loginStage.close();
                    } else {
                        loginFailed = true;
                    }
                    break;
                case CUSTOMER:
                    if (client.logIn(userTextField.getText(), pwBox.getText()) != null) {
                        loginStage.close();
                    } else {
                        loginFailed = true;
                    }
                    break;
            }
            if (loginFailed) {
                errorMessage.setText("Login failed");
            }
        });
        btnSignIn.setDefaultButton(true);

        userTextField.setOnMouseClicked(e -> {
            errorMessage.setText("");
        });

        pwBox.setOnMouseClicked(e -> {
            errorMessage.setText("");
        });

        Scene scene = new Scene(grid, 300, 200);
        loginStage.setTitle(title);
        loginStage.setScene(scene);
        loginStage.setResizable(false);
        loginStage.show();
    }

    public Stage getLoginStage() {
        return loginStage;
    }
}
