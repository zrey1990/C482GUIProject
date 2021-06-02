package sample;

/**
 * @author Zackery Reynolds
 */

/**
 * The class that determines the inHouse "part" that takes from the Add Part Controller class.
 */
public class inHouseForm extends part {
    private int inHouseMachineID;

    /**
     * @param id This will be the ID of the part in the table.
     * @param name This will be the name of the part in the table.
     * @param price This will be the price of the part in the table.
     * @param stock This will be the stock of the part in the table.
     * @param min This will be the minimum of the part in the table.
     * @param max This will be the maximum of the part in the table.
     * @param machineID This will be the Machine ID for the part in the table.
     *
     * This allows you to create a part for the inHouse radio button section.
     */
    public inHouseForm(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setStock(stock);
        this.setMin(min);
        this.setMax(max);
        this.setMachineID(machineID);
    }

    /**
     * @return machineID
     * This will return the Machine ID.
     */
    public int getMachineID() {
        return this.inHouseMachineID;
    }

    /**
     * @param machineID The machineID similar to the inHouse.
     * This method "sets" the machine ID.
     */
    public void setMachineID(int machineID) {
        this.inHouseMachineID = machineID;
    }
}