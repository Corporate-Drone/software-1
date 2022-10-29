package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part){
        allParts.add(part);
    }

    public static void addProduct(Product product){
        allProducts.add(product);
    }

    //search part method
    public boolean lookupPart(int partId){
        //loops through all dogs
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == partId) {
                return true;
            }
        }
        //return false if no id found
        return false;
    }

    //search method
    public boolean lookupProduct(int productId){
        //loops through all dogs
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId) {
                return true;
            }
        }
        //return false if no id found
        return false;
    }


//    lookupPart(partId:int):Part-x
//    lookupProduct(productId:int):Product-x
//    lookupPart(partName:String):ObservableList<Part>
//    lookupProduct(productName:String):ObservableList<Product>
//    updatePart(index:int, selectedPart:Part):void
//    updateProduct(index:int, newProduct:Product):void
//    deletePart(selectedPart:Part):boolean
//    deleteProduct(selectedProduct:Product):boolean

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
