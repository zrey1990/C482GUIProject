package sample;

        /**
        * @author Zackery Reynolds
        */

/**
 * This class determines the Part object.
 */
public abstract class part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id This will be the ID of the part in the table.
     * @param name This will be the name of the part in the table.
     * @param price This will be the price of the part in the table.
     * @param stock This will be the stock of the part in the table.
     * @param min This will be the minimum of the part in the table.
     * @param max This will be the maximum of the part in the table.
     *
     * This is the method for a part.
     */
    public part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return id
     * Returns the Part ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id This is the ID of a part.
     * Sets the Part ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return  name
     * Gets the Part Name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name This is the name of a part.
     * Sets the Part Name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return price
     * Returns the Part Price.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price This is the price of a part.
     * Sets the Part Price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return stock
     * Returns the Part Stock.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * @param stock This is the stock of a part.
     * Sets the part stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min
     * Returns the Part Minimum.
     */
    public int getMin() {
        return this.min;
    }

    /**
     * @param min This is the minimum of a part.
     * Sets the Part Minimum.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max
     * Gets the Part Maximum.
     */
    public int getMax() {
        return this.max;
    }

    /**
     * @param max This is the maximum of a part
     * Sets the Part Maximum.
     */
    public void setMax(int max) {
        this.max = max;
    }
}