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
import java.util.ResourceBundle;

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


    @FXML
    void onActionTogglePart(ActionEvent event) throws IOException {
        if(event.getSource() == addPartOutsourcedButton) {
            toggleLabel.setText("Company Name");
        } else {
            toggleLabel.setText("Machine ID");
        }
    }

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        try {
            int partId = Inventory.getAllParts().size() + 1;
            String partName = addPartNameField.getText();
            int partInventory = Integer.parseInt(addPartInventoryField.getText());
            double partPrice = Double.parseDouble(addPartPriceField.getText());
            int partMax = Integer.parseInt(addPartMaxField.getText());
            int partMin = Integer.parseInt(addPartMinField.getText());

            //Check radio button selected
            if(addPartInHouseButton.isSelected()) {
                //add InHouse part
                int partMachineId = Integer.parseInt(addPartToggleField.getText());
                InHouse newInhousePart = new InHouse(partId,partName,partPrice,partInventory,partMin,partMax,partMachineId);
                Inventory.addPart(newInhousePart);
            } else {
                //add Outsourced part
                String partCompanyName = addPartToggleField.getText();
                Outsourced newOutsourcedPart = new Outsourced(partId,partName,partPrice,partInventory,partMin,partMax,partCompanyName);
                Inventory.addPart(newOutsourcedPart);
            }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}