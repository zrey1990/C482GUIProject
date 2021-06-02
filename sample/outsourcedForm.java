package sample;

/**
 * @author Zackery Reynolds
 */

/**
 * The class that determines the inHouse "part" that takes from the Add Part Controller class.
 */
public class outsourcedForm extends part {
    private String outsourcedOompanyName;

    /**
     * @param id The id of the part.
     * @param name The name of the part.
     * @param price The price of the part.
     * @param stock The stock of the part.
     * @param min The min of the part.
     * @param max The max of the part.
     * @param companyName The company name linked with the outsourced part.
     *
     * This allows you to create a part for the outsourced radio button section.
     */
    public outsourcedForm(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.outsourcedOompanyName = companyName;
    }

    /**
     * @return returns part listed in the companyName.
     */
    public String getCompanyName() {
        return this.outsourcedOompanyName;
    }

    /**
     * @param companyName The company name linked with the part listed in outsourced button.
     */
    public void outsourcedOompanyName(String companyName) {
        this.outsourcedOompanyName = companyName;
    }
}