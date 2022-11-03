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
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartFormController implements Initializable {

    Stage stage;
    Parent scene;

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

    public void sendPart(Part part) {
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartNameField.setText(String.valueOf(part.getName()));
        modifyPartInventoryField.setText(String.valueOf(part.getStock()));
        modifyPartPriceField.setText(String.valueOf(part.getPrice()));
        modifyPartMaxField.setText(String.valueOf(part.getMax()));
        modifyPartMinField.setText(String.valueOf(part.getMax()));

       if (part instanceof InHouse) {
           System.out.println(((InHouse) part).getMachineId());
           modifyToggleField.setText(String.valueOf(((InHouse) part).getMachineId()));
           modifyPartOutsourcedButton.setSelected(false);
           toggleLabel.setText("Machine ID");
       } else {
           modifyToggleField.setText(String.valueOf(((Outsourced) part).getCompanyName()));
           modifyPartOutsourcedButton.setSelected(true);
           toggleLabel.setText("Company Name");

       }
    }

    @FXML
    void onActionTogglePart(ActionEvent event) throws IOException {
        if(event.getSource() == modifyPartOutsourcedButton) {
            toggleLabel.setText("Company Name");
        } else {
            toggleLabel.setText("Machine ID");
        }
    }

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/fxml/MainMenu-view.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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