package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Zackery Reynolds
 */

/**
 * This class determines the product object.
 */
public class product {


    ObservableList<part> linkedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id This will be the ID of the product in the table.
     * @param name This will be the name of the product in the table.
     * @param price This will be the price of the product in the table.
     * @param stock This will be the stock of the product in the table.
     * @param min This will be the minimum of the product in the table.
     * @param max This will be the maximum of the product in the table.
     *
     * This is the product table that isn't linked with the linked part list table.
     */
    public product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param linkedParts The list of parts linked with the product.
     * @param id This will be the ID of the product in the table.
     * @param name This will be the name of the product in the table.
     * @param price This will be the price of the product in the table.
     * @param stock This will be the stock of the product in the table.
     * @param min This will be the minimum of the product in the table.
     * @param max This will be the maximum of the product in the table.
     *
     * This is the product table to determine a product.
     */
    public product(ObservableList<part> linkedParts, int id, String name, double price, int stock, int min, int max) {
        this.linkedParts = linkedParts;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return id
     * Returns Product ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id The id of the product.
     * Sets the Product ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return name
     * Gets the Product Name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name This is the name of the product listed.
     * Sets the Product Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price
     * Returns the Product Price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price This is the price of the product listed.
     * Sets the Product Price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock
     * Returns the Product Stock.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * @param stock This is the stock of the product.
     * Sets the product stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     * Returns the product min.
     */
    public int getMin() {
        return this.min;
    }

    /**
     * @param min This is the minimum of the product.
     * Sets the Product Minimum.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     * Gets the Product Maximum.
     */
    public int getMax() {
        return this.max;
    }

    /**
     * @param max This is the max of the product.
     * This method sets the Product Maximum.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return linkedParts
     * Gets parts linked with a product.
     */
    public ObservableList<part> getAllParts(){return linkedParts;}

    /**
     * @param part This is the part linked with a product.
     * This will link a part with a product in the table.
     */
    public void addPartToProduct(part part){
        linkedParts.add(part);
    }
}