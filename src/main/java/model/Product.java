package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Gets Product or creates a new Product that contains a list of associated Parts
 *
 */
public class Product {
    /**
     * The list of associated Parts for the Product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * the ID for the Product
     */
    private int id;
    /**
     * the name of the Product
     */
    private String name;
    /**
     * the price of the Product
     */
    private double price;
    /**
     * the current inventory of the Product
     */
    private int stock;
    /**
     * the minimum inventory of the Product
     */
    private int min;
    /**
     * the maximum inventory of the Product
     */
    private int max;

    /**
     * the Constructor used to create a new Product
     * @param id the ID of the Product
     * @param name the name of the Product
     * @param price the price of the product
     * @param stock the current inventory of the Product
     * @param min the minimum inventory of the Product
     * @param max the maximum inventory of the Product
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Get the ID of the Product
     * @return the ID of the Product
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the Product
     * @param id the ID of the Product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the Product
     * @return the name of the Product
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the Product
     * @param name the name of the Product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the price of the Product
     * @return the price of the Product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the Product
     * @param price the price of the Product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the current inventory amount of the Product
     * @return the current inventory amount of Product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set the current inventory amount of the Product
     * @param stock the current inventory amount of the Product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get the minimum inventory of the Product
     * @return the minimum inventory of the Product
     */
    public int getMin() {
        return min;
    }

    /**
     * Set the minimum inventory of the Product
     * @param min the minimum inventory of the Product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get the maximum inventory amount of the Product
     * @return the maximum inventory of the Product
     */
    public int getMax() {
        return max;
    }

    /**
     * Set the maximum inventory amount of the Product
     * @param max the maximum inventory of the Product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get all Parts associated with the Product
     * @return the list of associated Parts of the Product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * Add a part to the list of associated Parts of the Product
     * @param part the associated Part to add to Product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Removes a part from the list of associated Parts of the Product
     * @param selectedAssociatedPart the associated Part to delete
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for(Part part : associatedParts) {
            if(part.getId() == selectedAssociatedPart.getId()) {
                return associatedParts.remove(part);
            }
        }
        //no id found
        return false;
    }

}
