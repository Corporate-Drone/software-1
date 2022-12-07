package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller logic for the main menu screen
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private Button addPartsButton;

    @FXML
    private Button addProductsButton;

    @FXML
    private Button deletePartsButton;

    @FXML
    private Button deleteProductsButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button modifyPartsButton;

    @FXML
    private Button modifyProductsButton;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevelCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevelCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Part, Double> productPriceCol;

    @FXML
    private TextField searchPartsBar;

    @FXML
    private TextField searchProductsBar;


    /**
     * Loads and redirects to the Add Part Form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddParts(ActionEvent event) throws IOException {
        //casing, lets event handler know cause of event is a button
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //reference variable

        //load fxml
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddPartForm-view.fxml"));

        //add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();

    }

    /**
     * Loads the Part data of the selected Part and redirects to the Modify Part Form
     * RUNTIME ERROR: If no Part is selected, the modify button will not bring up a notification
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        if(partsTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ModifyPartForm-view.fxml"));
            loader.load();

            ModifyPartFormController MPController = loader.getController(); //loads controller associated with fxml doc
            MPController.sendPart(partsTableView.getSelectionModel().getSelectedItem()); //send part to ModifyPartFormController

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Part to modify.");
            Optional<ButtonType> result = alert.showAndWait();
        }


    }

    /**
     * The Part selected will be deleted and the table will be refreshed
     * RUNTIME ERROR: If no Part is selected, the delete button will not function
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDeleteParts(ActionEvent event) throws IOException {
        if (partsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This item will be permanently deleted, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
                Inventory.deletePart(selectedPart);
                searchPartsBar.clear();
                partsTableView.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Part to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * Loads and redirects to the Add Product Form
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddProductForm-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Loads the Part data of the selected Part and redirects to the Modify Part Form
     * RUNTIME ERROR: If no Part is selected, the modify button will bring up a notification
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        if (productsTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ModifyProductForm-view.fxml"));
            loader.load();

            ModifyProductFormController MPController = loader.getController(); //loads controller associated with fxml doc
            MPController.sendProduct(productsTableView.getSelectionModel().getSelectedItem()); //send part to ModifyPartFormController

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Product to modify.");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * Search for Part by full name or partial name
     * FUTURE ENHANCEMENT: Search for Part by Inventory Level or Price
     * @param event
     * @throws IOException
     */
    @FXML
    void onPartsTableSearch(ActionEvent event) throws IOException {
        String queryString = searchPartsBar.getText();
        ObservableList<Part> parts = Inventory.searchByPartName(queryString);

        try {
            if(parts.size() == 0) {
                int partId = Integer.parseInt(queryString);
                Part foundPart = Inventory.lookupPart(partId);
                if(foundPart != null) {
                    parts.add(foundPart);
                }
            }
        } catch(NumberFormatException e) {

        }
        if(parts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nothing was found!");
            Optional<ButtonType> result = alert.showAndWait();
        }
        partsTableView.setItems(parts);
    }

    /**
     * Search for Product by full name or partial name
     * FUTURE ENHANCEMENT: Search for Product by Inventory Level or Price
     * @param event
     * @throws IOException
     */
    @FXML
    void onProductsTableSearch(ActionEvent event) throws IOException {
        String queryString = searchProductsBar.getText();
        ObservableList<Product> products = Inventory.searchByProductName(queryString);

        try {
            if(products.size() == 0) {
                int productId = Integer.parseInt(queryString);
                Product foundProduct = Inventory.lookupProduct(productId);
                if(foundProduct != null) {
                    products.add(foundProduct);
                }
            }
        } catch(NumberFormatException e) {

        }
        if(products.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nothing was found!");
            Optional<ButtonType> result = alert.showAndWait();
        }
        productsTableView.setItems(products);
    }

    /**
     * The Part selected will be deleted and the table will be refreshed
     * RUNTIME ERROR: If no Part is selected, the delete button will bring up a notification
     * LOGICAL ERROR: If the selected Product has associated Parts, a notification will pop up
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {
        if (productsTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This item will be permanently deleted, do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedProduct = (Product) productsTableView.getSelectionModel().getSelectedItem();
                if(selectedProduct.getAllAssociatedParts().size() > 0) {
                    Alert partAlert = new Alert(Alert.AlertType.WARNING, "There are part(s) associated with this product and cannot be deleted.");
                    Optional<ButtonType> partAlertResult = partAlert.showAndWait();
                } else {
                    Inventory.deleteProduct(selectedProduct);
                    searchProductsBar.clear();
                    productsTableView.refresh();
                }

            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select a Product to delete.");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }


    /**
     * Exits the application
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Load the main menu and populate the Parts and Products tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}