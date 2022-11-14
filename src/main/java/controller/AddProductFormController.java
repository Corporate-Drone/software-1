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

public class AddProductFormController implements Initializable {

    @FXML
    private Button addProductAddFormButton;

    @FXML
    private TableColumn<Part, Integer> addProductAsscIdCol;

    @FXML
    private TableColumn<Part, Integer> addProductAsscInventoryCol;

    @FXML
    private TableColumn<Part, String> addProductAsscPartCol;

    @FXML
    private TableColumn<Part, Double> addProductAsscPriceCol;

    @FXML
    private TableView<Part> addProductAsscTable;

    @FXML
    private Button addProductCancelFormButton;

    @FXML
    private TableColumn<Part, Integer> addProductIdCol;

    @FXML
    private TextField addProductIdField;

    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol;

    @FXML
    private TextField addProductInventoryField;

    @FXML
    private TextField addProductMaxField;

    @FXML
    private TextField addProductMinField;

    @FXML
    private TableColumn<Part, String> addProductNameCol;

    @FXML
    private TextField addProductNameField;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private TextField addProductPriceField;

    @FXML
    private Button addProductRemoveFormButton;

    @FXML
    private Button addProductSaveFormButton;

    @FXML
    private TextField addProductSearchFormField;

    @FXML
    private TableView<Part> addProductTable;


    Stage stage;
    Parent scene;

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int productId = Inventory.getAllProducts().size() + 1;
            String productName = addProductNameField.getText();
            int productInventory = Integer.parseInt(addProductInventoryField.getText());
            double productPrice = Double.parseDouble(addProductPriceField.getText());
            int productMax = Integer.parseInt(addProductMaxField.getText());
            int productMin = Integer.parseInt(addProductMinField.getText());

            Product newProduct = new Product(productId,productName,productPrice,productInventory,productMin,productMax);
            Inventory.addProduct(newProduct);
            System.out.println(Inventory.getAllProducts());


            //redirect after saving
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }

    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSearchPart(ActionEvent event) throws IOException {
        String queryString = addProductSearchFormField.getText();
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
            //ignore
        }
        if(parts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nothing was found!");
            Optional<ButtonType> result = alert.showAndWait();
        }
        addProductTable.setItems(parts);
    }

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        if(addProductTable.getSelectionModel().getSelectedItem() != null) {
            Product.addAssociatedPart(addProductTable.getSelectionModel().getSelectedItem());
            addProductAsscTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to add.");

            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
        if(addProductAsscTable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                Part selectedPart = (Part) addProductAsscTable.getSelectionModel().getSelectedItem();
                Product.deleteAssociatedPart(selectedPart);
                addProductAsscTable.refresh();
            }
        }

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTable.setItems(Inventory.getAllParts());
        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAsscTable.setItems(Product.getAllAssociatedParts());
        addProductAsscIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAsscPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAsscInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAsscPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}