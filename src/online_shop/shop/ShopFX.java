package online_shop.shop;

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
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import online_shop.shared.LoginWindow;
import online_shop.supplier.Product;
import online_shop.supplier.Supplier;

import java.rmi.RemoteException;

public class ShopFX extends Application {
    private Shop shop;

    private float sceneWidth = 700;
    private float sceneHeight = 500;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startApplication(primaryStage);
    }

    private void startApplication(Stage primaryStage) {
        try {
            shop = new Shop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LoginWindow loginWindow = new LoginWindow(shop, false);
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

        ObservableList<Product> observeProducts = FXCollections.observableArrayList();
        observeProducts.addAll(shop.getShopProducts());

        TableView table = new TableView();
        table.setEditable(true);
        table.setPrefSize(sceneWidth - 50, sceneHeight - 100);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, String>("name"));

        TableColumn purchasePriceCol = new TableColumn("Purchase Price");
        purchasePriceCol.setPrefWidth(100);
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("purchasePrice"));

        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setPrefWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Integer>("amount"));

        TableColumn editionCol = new TableColumn("Edition");
        editionCol.setPrefWidth(60);
        editionCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Integer>("edition"));

        TableColumn priceCol = new TableColumn("Selling Price");
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<ShopProduct, Double>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ShopProduct, Double>>) t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPrice(Double.valueOf(t.getNewValue()))
        );

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setPrefWidth(100);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ShopProduct, String>>) t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setDescription(t.getNewValue())
        );

        table.getColumns().addAll(nameCol, purchasePriceCol, amountCol, editionCol, priceCol, descriptionCol);
        table.setItems(observeProducts);
        grid.add(table, 0, 0);

        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);

        Button btnRemoveProduct = new Button("Remove product");
        btnRemoveProduct.setOnAction(e -> {

        });
        hbBtns.getChildren().add(btnRemoveProduct);

        Button btnLogout = new Button("Log out");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            restartApplication(primaryStage);
        });
        hbBtns.getChildren().add(btnLogout);
        grid.add(hbBtns, 0, 1);

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        root.getChildren().add(grid);
        // Define title and assign the scene for main window
        primaryStage.setTitle("Shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void exitApplication() {
        Platform.exit();
        System.exit(0);
    }

    private void restartApplication(Stage primaryStage) {
        startApplication(primaryStage);
    }
}