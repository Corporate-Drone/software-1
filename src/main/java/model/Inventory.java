package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class used for Parts and Products
 */
public class Inventory {
    /**
     * List of all Parts available
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all Products available
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add a Part to the Parts list
     * @param part part to add
     */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**
     * Add a Product to the Products list
     * @param product product to add
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * Search for Part by full name or partial name
     * @param partName the search criteria
     * @return the Parts matching search criteria
     */
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


    /**
     * Search for Part by ID
     * @param partId ID of part to search
     * @return matching part or null if no matches found
     */
    public static Part lookupPart(int partId){
        for(Part part : Inventory.getAllParts()) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Search for Product by full name or partial name
     * @param productName the search criteria
     * @return the Product matching search criteria
     */
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

    /**
     * Search for Product by ID
     * @param productId the search criteria
     * @return matching part or null if no matches found
     */
    public static Product lookupProduct(int productId){
        for(Product product : Inventory.getAllProducts()) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Update a part in the Parts list
     * @param index the index of the Part to replace
     * @param selectedPart the modified Part
     */
    public static void updatePart(int index, Part selectedPart) {
        //search for existing object
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == selectedPart.getId()) {
                //replace object
                Inventory.getAllParts().set(index, selectedPart);
            }
        }
    }


    /**
     * Remove a Part in the Parts list
     * @param selectedPart the Part to be removed
     * @return the updated list of Parts
     */
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


    /**
     * Remove a Product in the Products list
     * @param selectedProduct the Product to be removed
     * @return the updated list of Products
     */
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

    /**
     * Update a Product in the Products list
     * @param index the index of the Product to replace
     * @param newProduct the modified Product
     */
    public static void updateProduct(int index, Product newProduct) {
    //search for existing object
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == newProduct.getId()) {
                //replace object
                Inventory.getAllProducts().set(index, newProduct);
            }
        }
    }

    /**
     * Get all Parts in Inventory
     * @return list of all Parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Get all Products in Inventory
     * @return list of all Products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
