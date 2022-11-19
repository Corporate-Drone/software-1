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

    public static ObservableList<Part> searchByPartName(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                namedParts.add(part);
            }
        }
        return namedParts;
    }

    //search part method
    public static Part lookupPart(int partId){
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    public static ObservableList<Product> searchByProductName(String productName) {
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = getAllProducts();

        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                namedProducts.add(product);
            }
        }
        return namedProducts;
    }

    //search product method
    public static Product lookupProduct(int productId){
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static void updatePart(int index, Part selectedPart) {
        //search for existing object
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == selectedPart.getId()) {
                //replace object
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }

    //delete part
    public static boolean deletePart(Part selectedPart) {
        for(Part part : getAllParts()) {
            if(part.getId() == selectedPart.getId()) {
                //returns true or false
                return getAllParts().remove(part);
            }
        }
        //no id found
        return false;
    }

    //delete product
    public static boolean deleteProduct(Product selectedProduct) {
        for(Product product : getAllProducts()) {
            if(product.getId() == selectedProduct.getId()) {
                //call remove associated part

                //returns true or false
                return getAllProducts().remove(product);
            }
        }
        //no id found
        return false;
    }

    public static void updateProduct(int index, Product newProduct) {
    //search for existing object
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == newProduct.getId()) {
                //replace object
                Inventory.getAllProducts().set(index, newProduct);
            }
        }
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
