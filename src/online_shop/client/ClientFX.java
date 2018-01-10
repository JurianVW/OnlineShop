package online_shop.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import online_shop.shared.LoginWindow;
import online_shop.shop.ShopProduct;
import online_shop.supplier.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ClientFX extends Application {
    private Client client;
    private float sceneWidth = 1000;
    private float sceneHeight = 525;

    ObservableList<ShopProduct> observeProducts;
    ObservableList<CartProductTable> cartProducts;

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        startApplication(primaryStage);
    }

    private void startApplication(Stage primaryStage) {
        try {
            client = new Client(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LoginWindow loginWindow = new LoginWindow(client);
        loginWindow.loginStage.setOnHidden(event -> {
            showApplication(primaryStage);
        });
        primaryStage.setOnCloseRequest(e -> exitApplication());
        loginWindow.loginStage.setOnCloseRequest(e -> exitApplication());
    }

    private void showApplication(Stage primaryStage) {
        //Create grid
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        observeProducts = FXCollections.observableArrayList();
        cartProducts = FXCollections.observableArrayList();
        update();

        Label lblProducts = new Label("Products");
        lblProducts.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(lblProducts, 0, 0);

        Label lblCart = new Label("Cart");
        lblCart.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(lblCart, 1, 0);

        TableView productsTable = new TableView();
        productsTable.setPrefSize(600, sceneHeight - 125);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, String>("name"));

        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setPrefWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Integer>("amount"));

        TableColumn editionCol = new TableColumn("Edition");
        editionCol.setPrefWidth(60);
        editionCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Integer>("edition"));

        TableColumn priceCol = new TableColumn("Price");
        priceCol.setPrefWidth(80);
        priceCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Double>("price"));

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setPrefWidth(225);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Double>("description"));

        productsTable.getColumns().addAll(nameCol, amountCol, editionCol, priceCol, descriptionCol);
        productsTable.setItems(observeProducts);
        grid.add(productsTable, 0, 1);

        //Cart Table
        TableView cartTable = new TableView();
        cartTable.setEditable(true);
        cartTable.setPlaceholder(new Label("Your cart is empty!"));

        TableColumn cartProductCol = new TableColumn("Product");
        cartProductCol.setPrefWidth(200);
        cartProductCol.setCellValueFactory(new PropertyValueFactory<CartProductTable, String>("name"));


        TableColumn cartPriceCol = new TableColumn("Total price");
        cartPriceCol.setPrefWidth(70);
        cartPriceCol.setCellValueFactory(new PropertyValueFactory<CartProductTable, Double>("totalPrice"));

        TableColumn cartAmountCol = new TableColumn("Amount");
        cartAmountCol.setPrefWidth(70);
        cartAmountCol.setCellValueFactory(new PropertyValueFactory<CartProductTable, Integer>("cartAmount"));
        cartAmountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cartAmountCol.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<CartProductTable, Integer>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCartAmount(Integer.valueOf(t.getNewValue()));
                    updateCart();
                }
        );
        cartAmountCol.setEditable(true);

        cartTable.getColumns().addAll(cartProductCol, cartPriceCol, cartAmountCol);
        cartTable.setItems(cartProducts);
        grid.add(cartTable, 1, 1);

        //Product buttons
        HBox hbProductButtons = new HBox(10);
        hbProductButtons.setAlignment(Pos.CENTER);
        Button btnAddToCart = new Button("Add to cart");
        btnAddToCart.setOnAction(e -> {
            ShopProduct shopProduct = (ShopProduct) productsTable.getSelectionModel().getSelectedItem();
            boolean alreadyInCart = false;
            for (CartProductTable cbt : cartProducts) {
                if (cbt.getShopProduct().getId() == shopProduct.getId()) {
                    alreadyInCart = true;
                }
            }
            if (shopProduct != null) {
                if (!alreadyInCart) {
                    cartProducts.add(new CartProductTable(shopProduct));
                }
            }
        });
        hbProductButtons.getChildren().add(btnAddToCart);
        grid.add(hbProductButtons, 0, 2);

        //Cart buttons
        HBox hbCartButtons = new HBox(10);
        hbCartButtons.setAlignment(Pos.CENTER);
        Button btnRemoveFromCart = new Button("Remove from cart");
        btnRemoveFromCart.setOnAction(e -> {
            CartProductTable cartProduct = (CartProductTable) cartTable.getSelectionModel().getSelectedItem();
            if (cartProduct != null) {
                cartProducts.remove(cartProduct);
            }
        });
        Button btnOrderProducts = new Button("Order products");
        btnOrderProducts.setOnAction(e -> {
            List<ShopProduct> productsToOrder = new ArrayList<>();
            for (CartProductTable cbt : cartProducts) {
                for (int i = 0; i < cbt.getCartAmount(); i++) {
                    productsToOrder.add(cbt.getShopProduct());
                }
            }
          client.orderProducts(productsToOrder);
            cartProducts.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Order placed!");
            alert.showAndWait();
        });
        hbCartButtons.getChildren().add(btnRemoveFromCart);
        hbCartButtons.getChildren().add(btnOrderProducts);
        grid.add(hbCartButtons, 1, 2);

        //Logout Button
        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);
        Button btnLogout = new Button("Log out");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            restartApplication(primaryStage);
        });
        hbBtns.getChildren().add(btnLogout);
        grid.add(hbBtns, 0, 3, 2, 1);

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        root.getChildren().add(grid);
        // Define title and assign the scene for main window
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void exitApplication() {
        Platform.exit();
        System.exit(0);
    }

    private void restartApplication(Stage primaryStage) {
        startApplication(primaryStage);
    }

    public void update() {
        observeProducts.clear();
        observeProducts.addAll(client.getShopProducts());
    }

    public synchronized void changedShopProduct(ShopProduct shopProduct) {
        Integer index = Integer.MAX_VALUE;
        for (ShopProduct sp:observeProducts            ) {
            if(sp.getId() == shopProduct.getId()){
                index = observeProducts.indexOf(sp);
            }
        }
        if(index < observeProducts.size()){
            //observeProducts.remove(observeProducts.get(index));
            //observeProducts.add(shopProduct);
            observeProducts.set(index, shopProduct);
        }
    }

    public void removedShopProduct(ShopProduct shopProduct) {
        Integer index = Integer.MAX_VALUE;
        for (ShopProduct sp:observeProducts            ) {
            if(sp.getId() == shopProduct.getId()){
                index = observeProducts.indexOf(sp);
            }
        }
        if(index < observeProducts.size()){
            observeProducts.remove(index);
        }
    }

    public void newShopProduct(ShopProduct shopProduct) {
        observeProducts.add(shopProduct);
    }

    private void updateCart() {
        List<CartProductTable> cartList = new ArrayList<>();
        cartList.addAll(cartProducts);
        cartProducts.clear();
        cartProducts.addAll(cartList);
    }
}
