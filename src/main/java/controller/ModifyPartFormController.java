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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller logic that modifies a Part
 */
public class ModifyPartFormController implements Initializable {

    Stage stage;
    Parent scene;
    Part displayedPart;

    @FXML
    private ToggleGroup ModifyPart;

    @FXML
    private Button modifyPartCancelFormButton;

    @FXML
    private RadioButton modifyPartInHouseButton;

    @FXML
    private TextField modifyPartId;

    @FXML
    private TextField modifyPartInventoryField;

    @FXML
    private TextField modifyToggleField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private RadioButton modifyPartOutsourcedButton;

    @FXML
    private TextField modifyPartPriceField;

    @FXML
    private Button saveModifyPartFormButton;

    @FXML
    private Label toggleLabel;

    /**
     * Loads the selected Part and populates the fields accordingly
     * @param part the selected Part from the main menu
     */
    public void sendPart(Part part) {
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartNameField.setText(String.valueOf(part.getName()));
        modifyPartInventoryField.setText(String.valueOf(part.getStock()));
        modifyPartPriceField.setText(String.valueOf(part.getPrice()));
        modifyPartMaxField.setText(String.valueOf(part.getMax()));
        modifyPartMinField.setText(String.valueOf(part.getMin()));

       if (part instanceof InHouse) {
           modifyToggleField.setText(String.valueOf(((InHouse) part).getMachineId()));
           modifyPartOutsourcedButton.setSelected(false);
           toggleLabel.setText("Machine ID");
       } else {
           modifyToggleField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
           modifyPartOutsourcedButton.setSelected(true);
           toggleLabel.setText("Company Name");

       }

       displayedPart = part;
    }

    /**
     * The select radio button toggles the text field label between Company Name and Machine ID
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionTogglePart(ActionEvent event) throws IOException {
        if(event.getSource() == modifyPartOutsourcedButton) {
            toggleLabel.setText("Company Name");
        } else {
            toggleLabel.setText("Machine ID");
        }
    }

    /**
     * Saves the changes made to the Part
     * RUNTIME ERROR: If any field is blank or contains incorrect values, an error message will be displayed
     * RUNTIME ERROR: If the max value is less than min, an error message will be displayed
     * RUNTIME ERROR: If the inventory amount is not within the min and max value, an error message will be displayed
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        int partIndex = Inventory.getAllParts().indexOf(displayedPart);

        try {
            int partId = Integer.parseInt(modifyPartId.getText());
            String partName = modifyPartNameField.getText();
            int partInventory = Integer.parseInt(modifyPartInventoryField.getText());
            double partPrice = Double.parseDouble(modifyPartPriceField.getText());
            int partMax = Integer.parseInt(modifyPartMaxField.getText());
            int partMin = Integer.parseInt(modifyPartMinField.getText());

            if (partMin > partMax) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min should be less than Max and Max should be greater than min.");
                Optional<ButtonType> result = alert.showAndWait();
            } else if(partInventory > partMax || partInventory < partMin) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv should be between min and max values.");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
                if(modifyPartInHouseButton.isSelected()) {
                    int partMachineId = Integer.parseInt(modifyToggleField.getText());
                    InHouse updatedInhousePart = new InHouse(partId,partName,partPrice,partInventory,partMin,partMax,partMachineId);
                    Inventory.updatePart(partIndex, updatedInhousePart);
                } else {
                    String partCompanyName = modifyToggleField.getText();
                    Outsourced updatedOutsourcedPart = new Outsourced(partId,partName,partPrice,partInventory,partMin,partMax,partCompanyName);
                    Inventory.updatePart(partIndex, updatedOutsourcedPart);
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
     * Cancels modifying a Part and redirects to the main menu
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
     * Initializes the Modify Part Form
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}