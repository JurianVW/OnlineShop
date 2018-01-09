package online_shop.supplier;

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

import java.rmi.RemoteException;

public class SupplierFX extends Application {
    private Supplier supplier;
    private static final int port = 1098;

    private float sceneWidth = 500;
    private float sceneHeight = 500;

    private ObservableList<ProductTable> observeProducts;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startApplication(primaryStage);
    }

    private void startApplication(Stage primaryStage) {
        try {
            supplier = new Supplier(port, "Novamedia");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LoginWindow loginWindow = new LoginWindow(supplier);
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
        resetList();

        TableView table = new TableView();
        table.setEditable(true);
        table.setPrefSize(sceneWidth - 50, sceneHeight - 100);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ProductTable, String>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                    supplier.productChanged(new Product(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );

        TableColumn purchasePriceCol = new TableColumn("Purchase Price");
        purchasePriceCol.setPrefWidth(100);
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<ProductTable, Double>("purchasePrice"));
        purchasePriceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        purchasePriceCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ProductTable, Double>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPurchasePrice(Double.valueOf(t.getNewValue()));
                    supplier.productChanged(new Product(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );

        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setPrefWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("amount"));
        amountCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        amountCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ProductTable, Integer>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAmount(t.getNewValue());
                    supplier.productChanged(new Product(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );

        TableColumn editionCol = new TableColumn("Edition");
        editionCol.setPrefWidth(60);
        editionCol.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("edition"));
        editionCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        editionCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ProductTable, Integer>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setEdition(t.getNewValue());
                    supplier.productChanged(new Product(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );


        table.getColumns().addAll(nameCol, purchasePriceCol, amountCol, editionCol);
        table.setItems(observeProducts);
        grid.add(table, 0, 0);

        final TextField addName = new TextField();
        addName.setMaxWidth(nameCol.getPrefWidth());
        addName.setPromptText("Name");
        final TextField addPurchasePrice = new TextField();
        addPurchasePrice.setMaxWidth(purchasePriceCol.getPrefWidth());
        addPurchasePrice.setPromptText("Purchase price");
        final TextField addAmount = new TextField();
        addAmount.setMaxWidth(amountCol.getPrefWidth());
        addAmount.setPromptText("Amount");
        final TextField addEdition = new TextField();
        addEdition.setMaxWidth(editionCol.getPrefWidth());
        addEdition.setPromptText("Edition");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            supplier.addProduct(new Product(
                    addName.getText(),
                    1008,
                    Double.valueOf(addPurchasePrice.getText()),
                    Integer.valueOf(addAmount.getText()),
                    Integer.valueOf(addEdition.getText())
            ));
            resetList();
            addName.clear();
            addPurchasePrice.clear();
            addAmount.clear();
            addEdition.clear();
        });

        HBox hbaddProduct = new HBox(10);
        hbaddProduct.getChildren().add(addName);
        hbaddProduct.getChildren().add(addPurchasePrice);
        hbaddProduct.getChildren().add(addAmount);
        hbaddProduct.getChildren().add(addEdition);
        hbaddProduct.getChildren().add(addButton);
        grid.add(hbaddProduct, 0, 1);

        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);

        Button btnRemoveProduct = new Button("Remove product");
        btnRemoveProduct.setOnAction(e -> {
            ProductTable pt = (ProductTable) table.getSelectionModel().getSelectedItem();
            Product p = pt.getProduct();
            supplier.removeProduct(p);
            resetList();
        });
        hbBtns.getChildren().add(btnRemoveProduct);

        Button btnLogout = new Button("Log out");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            restartApplication(primaryStage);
        });
        hbBtns.getChildren().add(btnLogout);
        grid.add(hbBtns, 0, 2);

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        root.getChildren().add(grid);
        // Define title and assign the scene for main window
        primaryStage.setTitle("Supplier");
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

    private void resetList() {
        observeProducts.clear();
        for (Product p : supplier.getProducts()) {
            observeProducts.add(new ProductTable(p));
        }
    }

}


