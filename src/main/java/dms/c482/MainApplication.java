package dms.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/MainMenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InHouse inhouse1 = new InHouse(1,"Sac",19.00,5,1,7,87);
        Inventory.addPart(inhouse1);

        Outsourced outsourced1 = new Outsourced(2,"Vac",27.00,12,1,13,"Nintendo");
        Inventory.addPart(outsourced1);

        Product product1 = new Product(1,"Ski",99.99,5,1,25);
        Product product2 = new Product(2,"Frc",14.99,7,1,40);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


        launch();

    }
}