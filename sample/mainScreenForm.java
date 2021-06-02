package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zackery Reynolds
 */

/**
 * The class that determines the main inventory screen to allow the user to choose and hold parts in the parts and
 * product table on the main screen.
 */
public class mainScreenForm {

    private static final ObservableList<part> listAllParts = FXCollections.observableArrayList();
    private static final ObservableList<product> listAllProducts = FXCollections.observableArrayList();
    public static int listedPartID = 0;
    private static int productID = 0;


    /**
     * @return listAllParts
     * This method returns a list of all parts inside of the table.
     */
    public static ObservableList<part> getParts() {
        return listAllParts;
    }

    /**
     * @return listAllProducts
     * This method returns a list of all products inside of the table.
     */
    public static ObservableList<product> getProducts() {
        return listAllProducts;
    }

    /**
     * @param part The part that is being searched for in the parts table.
     * @param searchText The user can search for a part by name or ID.
     * @return Will return a boolean value.
     * This will make sure there is a part that matches the name or ID when the user searches for it.
     */
    private boolean searchingListedPart(part part, String searchText){
        return (part.getName().contains(searchText.toLowerCase())) ||
                Integer.valueOf(part.getId()).toString().equals(searchText.toLowerCase());
    }

    /**
     * @param list A list of the parts that are being searched for.
     * @param searchText The user can search for a part by name or ID.
     * @return Will return partSearchResult
     */
    private ObservableList<part> partSearchResult(List<part> list, String searchText){
        List<part> filteredList = new ArrayList<>();
        for (sample.part part : list){
            if(searchingListedPart(part, searchText)) filteredList.add(part);
        }
        return FXCollections.observableList(filteredList);
    }

    /**
     * @param newPart Will show the part being added to the table.
     */
    public static void addPart(part newPart) {
        listAllParts.add(newPart);
    }

    /**
     * @param newProduct Will show the product being added to the table.
     */
    public static void addProduct(product newProduct) {
        listAllProducts.add(newProduct);
    }

    /**
     * @param partId Shows the ID of the part being used.
     * @return This will return the Part ID.
     */
    public static part searchingForPart(int partId) {
        return (part)listAllParts.get(partId);
    }

    /**
     * @param productId Shows the ID of the product being used.
     * @return This will return the Product ID.
     */
    public static product searchingForProduct(int productId) {
        return (product)listAllProducts.get(productId);
    }

    /**
     * @param selectedPart Shows the part that the user selected and wil allow the part from the list to be deleted.
     */
    public static void deletedPart(part selectedPart){
        listAllParts.remove(selectedPart);
    }

    /**
     * @param partName The name of the part that is being selected to match the name in the table.
     * @return Will return lookupPart.
     */
    public static ObservableList<part> lookupPart(String partName) {
        ObservableList<part> matchingParts = FXCollections.observableArrayList();
        for (sample.part part : listAllParts){
            if(part.getName().equals(partName)){
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * @param productName The name of the product that is being selected to match the name in the table.
     * @return Will return lookupProduct.
     */
    public static ObservableList<product> searchingForProduct(String productName){
        ObservableList<product> matchingProducts= FXCollections.observableArrayList();
        for (sample.product product : listAllProducts){
            if(product.getName().equals(productName)){
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * @return listAllParts
     * The method that will return all of the parts in the table.
     */
    public static ObservableList<part> getAllParts() {
        return listAllParts;
    }

    /**
     * @return listAllProducts
     * The method that will return all of the products in the table.
     */
    public static ObservableList<product> getAllProducts() {
        return listAllProducts;
    }

    /**
     * @return currentPartID
     * Will select the correct Part ID being selected.
     */
    public static int currentPartID() {
        ++listedPartID;
        return listedPartID;
    }


    /**
     * @return productID
     * Will select the correct Product ID being selected.
     */
    public static int currentProductID() {
        ++productID;
        return productID;
    }

    /**
     * @param selectedProduct This will show the product that is selected by the user and will delete the product
     *                        from the table.
     */
    public static void deletedProduct(product selectedProduct) {
        listAllProducts.remove(selectedProduct);
    }
}