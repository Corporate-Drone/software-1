package controller;

import javafx.collections.FXCollections;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller logic that modifies a Product
 */
public class ModifyProductFormController implements Initializable {

    Stage stage;
    Parent scene;
    Product displayedProduct;

    /**
     * List of associated Parts for the Product
     */
    private static ObservableList<Part> associatedProductParts = FXCollections.observableArrayList();

    /**
     * Adds a Part to be associated with the Product
     * @param part the Part to be added
     */
    private void addAssociatedProductPart (Part part) {
        associatedProductParts.add(part);
    }

    /**
     * Deletes a Part to be associated with the Product
     * @param selectedPart the selected Part
     * @return true or false
     */
    private boolean deleteAssociatedProductPart (Part selectedPart) {
        associatedProductParts.remove(selectedPart);
        return true;
    }

    /**
     * Gets all Parts associated with the Product
     * @return all associated Parts
     */
    private ObservableList<Part> getAllAssociatedProductParts() {
        return associatedProductParts;
    }

    @FXML
    private Button modifyProductAddFormButton;

    @FXML
    private TableColumn<Product, Integer> modifyProductAsscIdCol;

    @FXML
    private TableColumn<Product, Integer> modifyProductAsscInventoryCol;

    @FXML
    private TableColumn<Product, String> modifyProductAsscPartCol;

    @FXML
    private TableColumn<Product, Double> modifyProductAsscPriceCol;

    @FXML
    private TableView<Part> modifyProductAsscTable;

    @FXML
    private Button modifyProductCancelFormButton;

    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol;

    @FXML
    private TextField modifyProductIdField;

    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol;

    @FXML
    private TextField modifyProductInventoryField;

    @FXML
    private TextField modifyProductMaxField;

    @FXML
    private TextField modifyProductMinField;

    @FXML
    private TableColumn<Part, String> modifyProductNameCol;

    @FXML
    private TextField modifyProductNameField;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

    @FXML
    private TextField modifyProductPriceField;

    @FXML
    private Button modifyProductRemoveFormButton;

    @FXML
    private Button modifyProductSaveFormButton;

    @FXML
    private TextField modifyProductSearchFormField;

    @FXML
    private TableView<Part> modifyProductTable;

    /**
     * Loads the selected Product and populates the fields accordingly
     * @param product the selected Product from the main menu
     */
    public void sendProduct(Product product) {
        modifyProductIdField.setText(String.valueOf(product.getId()));
        modifyProductNameField.setText(String.valueOf(product.getName()));
        modifyProductInventoryField.setText(String.valueOf(product.getStock()));
        modifyProductPriceField.setText(String.valueOf(product.getPrice()));
        modifyProductMaxField.setText(String.valueOf(product.getMax()));
        modifyProductMinField.setText(String.valueOf(product.getMin()));
        displayedProduct = product;

        if(!displayedProduct.getAllAssociatedParts().isEmpty()) {
            for(Part part : displayedProduct.getAllAssociatedParts()) {
                associatedProductParts.add(part);
            }
            modifyProductAsscTable.setItems(getAllAssociatedProductParts());
        } else {
            modifyProductAsscTable.setItems(getAllAssociatedProductParts());
        }

    }

    /**
     * Search for Part by full name or partial name
     * Future Enhancement: Search for Part by Inventory Level or Price
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSearchPart(ActionEvent event) throws IOException {
        String queryString = modifyProductSearchFormField.getText();
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
        modifyProductTable.setItems(parts);
    }

    /**
     * Cancels modifying a Product and redirects to the main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        modifyProductAsscTable.getItems().clear();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves the changes made to the Product and any associated Parts and redirects to Main menu
     * Runtime Error: If any field is blank or contains incorrect values, an error message will be displayed
     * Runtime Error: If the max value is less than min, an error message will be displayed
     * Runtime Error: If the inventory amount is not within the min and max value, an error message will be displayed
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        int partIndex = Inventory.getAllProducts().indexOf(displayedProduct);

        try {
            int productId = Integer.parseInt(modifyProductIdField.getText());
            String productName = modifyProductNameField.getText();
            int productInventory = Integer.parseInt(modifyProductInventoryField.getText());
            double productPrice = Double.parseDouble(modifyProductPriceField.getText());
            int productMax = Integer.parseInt(modifyProductMaxField.getText());
            int productMin = Integer.parseInt(modifyProductMinField.getText());

            if (productMin > productMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max and Max should be greater than min.");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(productInventory > productMax || productInventory < productMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv should be between min and max values.");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                Product updatedProduct = new Product(productId,productName,productPrice,productInventory,productMin,productMax);
                if (!associatedProductParts.isEmpty()) {
                    for (Part addedPart : associatedProductParts) {
                        updatedProduct.addAssociatedPart(addedPart);
                    }
                }
                Inventory.updateProduct(partIndex, updatedProduct);

                //redirect after saving
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
        modifyProductAsscTable.getItems().clear();
    }

    /**
     * Adds selected Part to associated Part table
     * Runtime Error: If no Part is selected, the delete button will not function
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        if(modifyProductTable.getSelectionModel().getSelectedItem() != null) {
            addAssociatedProductPart(modifyProductTable.getSelectionModel().getSelectedItem());
            modifyProductAsscTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please select a part to add.");

            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * Removes selected Part from associated Parts table
     * Runtime Error: If no Part is selected, the remove button will not function
     * @param event
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            //delete part
            Part selectedPart = (Part) modifyProductAsscTable.getSelectionModel().getSelectedItem();
//                Product.deleteAssociatedPart(selectedPart);
            deleteAssociatedProductPart(selectedPart);
            modifyProductAsscTable.refresh();
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Initializes the Modify Product Form and sets the Parts and Associated Parts table values
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

//        modifyProductAsscTable.setItems(Product.getAllAssociatedParts());
        modifyProductAsscIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAsscPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAsscInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAsscPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}