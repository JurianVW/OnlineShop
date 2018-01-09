package online_shop.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import online_shop.shared.LoginWindow;
import online_shop.shop.ShopProduct;
import online_shop.supplier.Product;

import java.rmi.RemoteException;

public class ClientFX extends Application {
    private Client client;
    private float sceneWidth = 1000;
    private float sceneHeight = 500;

    ObservableList<ShopProduct> observeProducts ;

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
        update();

        TableView productsTable = new TableView();
        productsTable.setPrefSize(600 , sceneHeight - 100);

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
        descriptionCol.setPrefWidth(200);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("description"));

        productsTable.getColumns().addAll(nameCol, amountCol, editionCol, priceCol, descriptionCol);
        productsTable.setItems(observeProducts);
        grid.add(productsTable, 0, 0);

        TableView cartTable = new TableView();
        cartTable.setEditable(true);


        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);

        Button btnLogout = new Button("Log out");
        btnLogout.setOnAction(e -> {
            primaryStage.close();
            restartApplication(primaryStage);
        });
        hbBtns.getChildren().add(btnLogout);
        grid.add(hbBtns, 0, 1, 1,2);

        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        root.getChildren().add(grid);
        // Define title and assign the scene for main window
        primaryStage.setTitle("Client");
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

    public void update() {
        observeProducts.clear();
        observeProducts.addAll(client.getShopProducts());
    }
}
