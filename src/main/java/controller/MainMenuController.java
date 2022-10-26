package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;


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

    @FXML
    void onActionModifyParts(ActionEvent event) throws IOException {
        //casing, lets event handler know cause of event is a button
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //reference variable

        //load fxml
        scene = FXMLLoader.load(getClass().getResource("/fxml/ModifyPartForm-view.fxml"));

        //add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();

    }

    @FXML
    void onActionDeleteParts(ActionEvent event) throws IOException {

    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        //casing, lets event handler know cause of event is a button
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //reference variable

        //load fxml
        scene = FXMLLoader.load(getClass().getResource("/fxml/AddProductForm-view.fxml"));

        //add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        //casing, lets event handler know cause of event is a button
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //reference variable

        //load fxml
        scene = FXMLLoader.load(getClass().getResource("/fxml/ModifyProductForm-view.fxml"));

        //add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) throws IOException {

    }


    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}