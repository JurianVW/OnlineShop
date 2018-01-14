package onlineshop.shop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import onlineshop.shared.IShopFX;
import onlineshop.shared.LoginWindow;
import onlineshop.supplier.Product;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ShopFX extends Application implements IShopFX {
    private static final Logger LOGGER = Logger.getLogger(ShopFX.class.getName());
    private Shop shop;

    private float sceneWidth = 700;
    private float sceneHeight = 500;

    ObservableList<ShopProductTable> observeProducts;
    List<String> supplierNames;

    private String shopName = "";

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startApplication(primaryStage);
    }

    private void startApplication(Stage primaryStage) {
        supplierNames = new ArrayList<>();
        supplierNames.add("Blufans");
        supplierNames.add("FilmArena");
        supplierNames.add("Kimchi");
        supplierNames.add("MantaLab");
        supplierNames.add("NovaMedia");

        List<String> shopNames = new ArrayList<>();
        shopNames.add("Bol.com");
        shopNames.add("Mediamarkt");
        shopNames.add("Zavvi");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(shopNames.get(0), shopNames);
        dialog.setTitle("Shop");
        dialog.setHeaderText("Choose a shop");
        dialog.setContentText("Shop name:");

        String shopName = null;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            shopName = result.get();
        }

        if (shopName != null) {
            try {
                shop = new Shop(this, shopName);
            } catch (RemoteException e) {
                LOGGER.severe(e.getMessage());
            }
            this.shopName = shopName;

            observeProducts = FXCollections.observableArrayList();
            update();

            LoginWindow loginWindow = new LoginWindow(shop, shopName);
            loginWindow.getLoginStage().setOnHidden(event -> showApplication(primaryStage));
            primaryStage.setOnCloseRequest(e -> exitApplication());
            loginWindow.getLoginStage().setOnCloseRequest(e -> exitApplication());
        }
    }

    private void showApplication(Stage primaryStage) {
        //Create grid
        GridPane grid;
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        TableView table = new TableView();
        table.setEditable(true);
        table.setPrefSize(sceneWidth - 50, sceneHeight - 100);

        TableColumn nameCol = new TableColumn("Name");
        nameCol.setPrefWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<ShopProductTable, String>("name"));

        TableColumn purchasePriceCol = new TableColumn("Purchase Price");
        purchasePriceCol.setPrefWidth(100);
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<ShopProductTable, Double>("purchasePrice"));

        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setPrefWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<ShopProductTable, Integer>("amount"));

        TableColumn editionCol = new TableColumn("Edition");
        editionCol.setPrefWidth(60);
        editionCol.setCellValueFactory(new PropertyValueFactory<ShopProductTable, Integer>("edition"));

        TableColumn priceCol = new TableColumn("Selling Price");
        priceCol.setPrefWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<ShopProductTable, Double>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priceCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ShopProductTable, Double>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPrice(Double.valueOf(t.getNewValue()));
                    shop.shopProductChanged(new ShopProduct(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );

        TableColumn descriptionCol = new TableColumn("Description");
        descriptionCol.setPrefWidth(100);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                (EventHandler<TableColumn.CellEditEvent<ShopProductTable, String>>) t -> {
                    (t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDescription(t.getNewValue());
                    shop.shopProductChanged(new ShopProduct(t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ));
                }
        );

        table.getColumns().addAll(nameCol, purchasePriceCol, amountCol, editionCol, priceCol, descriptionCol);
        table.setItems(observeProducts);
        grid.add(table, 0, 0);

        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);

        Button btnAddSupplier = new Button("Add Supplier");
        btnAddSupplier.setOnAction(e -> {
            List<String> currentSuppliers = shop.getShopSuppliers();
            List<String> newSuppliers = new ArrayList<>();
            for (String s : supplierNames) {
                if (!currentSuppliers.contains(s)) {
                    newSuppliers.add(s);
                }
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(newSuppliers.get(0), newSuppliers);
            dialog.setTitle("Add Supplier");
            dialog.setHeaderText("Choose a supplier");
            dialog.setContentText("Supplier name:");

            String supplierName = null;
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                supplierName = result.get();
            }

            if (supplierName != null) {
                shop.addShopSupplier(supplierName);
            }
        });
        hbBtns.getChildren().add(btnAddSupplier);

        Button btnRemoveSupplier = new Button("Remove Supplier");
        btnRemoveSupplier.setOnAction(e -> {
            List<String> currentSuppliers = shop.getShopSuppliers();
            ChoiceDialog<String> dialog = new ChoiceDialog<>(currentSuppliers.get(0), currentSuppliers);
            dialog.setTitle("Remove Supplier");
            dialog.setHeaderText("Choose a supplier");
            dialog.setContentText("Supplier name:");

            String supplierName = null;
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                supplierName = result.get();
            }

            if (supplierName != null) {
                shop.removeShopSupplier(supplierName);
            }
        });
        hbBtns.getChildren().add(btnRemoveSupplier);

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
        scene.setOnKeyPressed(event -> {
            if (event.getText().equals("r")) {
                update();
            } else if (event.getText().equals("c")) {
                shop.reconnectToSuppliers();
            }
        });
        root.getChildren().add(grid);
        // Define title and assign the scene for main window
        primaryStage.setTitle("Shop: " + shopName);
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

    public synchronized void update() {
        observeProducts.clear();
        for (ShopProduct p : shop.getShopProducts()) {
            observeProducts.add(new ShopProductTable(p));
        }
    }
}