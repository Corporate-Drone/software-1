package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller logic that adds a Part to the application
 */
public class AddPartFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private ToggleGroup AddPart;

    @FXML
    private Button addPartCancelFormButton;

    @FXML
    private RadioButton addPartInHouseButton;

    @FXML
    private TextField addPartInventoryField;

    @FXML
    private TextField addPartToggleField;

    @FXML
    private TextField addPartMaxField;

    @FXML
    private TextField addPartMinField;

    @FXML
    private TextField addPartNameField;

    @FXML
    private RadioButton addPartOutsourcedButton;

    @FXML
    private TextField addPartPriceField;

    @FXML
    private Button addPartSaveFormButton;

    @FXML
    private Label toggleLabel;


    /**
     * The select radio button toggles the text field label between Company Name and Machine ID
     * @param event Toggle type of Part action
     * @throws IOException
     */
    @FXML
    void onActionTogglePart(ActionEvent event) throws IOException {
        if(event.getSource() == addPartOutsourcedButton) {
            toggleLabel.setText("Company Name");
        } else {
            toggleLabel.setText("Machine ID");
        }
    }

    /**
     * Saves the new Part (InHouse or Outsourced) and redirects to main menu
     * RUNTIME ERROR: If any field is blank or contains incorrect values, an error message will be displayed
     * RUNTIME ERROR: If the max value is less than min, an error message will be displayed
     * RUNTIME ERROR: If the inventory amount is not within the min and max value, an error message will be displayed
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        try {
            int partId = Inventory.getAllParts().size() + 1;
            String partName = addPartNameField.getText();
            int partInventory = Integer.parseInt(addPartInventoryField.getText());
            double partPrice = Double.parseDouble(addPartPriceField.getText());
            int partMax = Integer.parseInt(addPartMaxField.getText());
            int partMin = Integer.parseInt(addPartMinField.getText());

            if (partMin > partMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max and Max should be greater than min.");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partInventory > partMax || partInventory < partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv should be between min and max values.");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                if(addPartInHouseButton.isSelected()) {
                    int partMachineId = Integer.parseInt(addPartToggleField.getText());
                    InHouse newInhousePart = new InHouse(partId,partName,partPrice,partInventory,partMin,partMax,partMachineId);
                    Inventory.addPart(newInhousePart);
                } else {
                    String partCompanyName = addPartToggleField.getText();
                    Outsourced newOutsourcedPart = new Outsourced(partId,partName,partPrice,partInventory,partMin,partMax,partCompanyName);
                    Inventory.addPart(newOutsourcedPart);
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }


        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }

    }

    /**
     * Cancels adding a Part and redirects to main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the Add Part Form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}