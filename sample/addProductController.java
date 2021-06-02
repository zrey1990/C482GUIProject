package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Zackery Reynolds
 */

/**
 * The class that determines the functions of the Add Product Controller screen.
 */
public class addProductController implements Initializable {
    public AnchorPane anchorAddProduct;
    public TextField addProductSearch;
    public TableView<part> addProductLinked;
    public TableColumn<Object, Object> addProductLinkedID;
    public TableColumn<Object, Object> addProductLinkedName;
    public TableColumn<Object, Object> addProductLinkedInv;
    public TableColumn<Object, Object> addProductLinkedCost;
    public TableView<part> addAllProduct;
    public TableColumn<Object, Object> addTableProductID;
    public TableColumn<Object, Object> addTableProductName;
    public TableColumn<Object, Object> addTableProductInv;
    public TableColumn<Object, Object> addTableProductCost;
    public Button buttonProductRemove;
    public Button buttonProductCancel;
    public Button buttonProductSave;
    public TextField textfieldAddProductID;
    public TextField textfieldAddProductName;
    public TextField textfieldAddProductInv;
    public TextField textfieldAddProductPrice;
    public TextField textfieldAddProductMax;
    public TextField textfieldAddProductMin;
    public Button buttonAddProductAddition;
    public Label labelProductSearching;
    private ObservableList<part> linkedPartList = FXCollections.observableArrayList();
    private ObservableList<part> shortLinkedPartsList = FXCollections.observableArrayList();
    product clickedProduct;

    Alert integerAlert = new Alert(Alert.AlertType.ERROR);
    Alert priceAlert = new Alert(Alert.AlertType.ERROR);
    Alert textAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * This will determine the method for the Add Product Controller and will send the part to the table that uses
     *  the Product ID table.
     * @param url The url param.
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int productID = mainScreenForm.currentProductID();
        this.textfieldAddProductID.setText(String.valueOf(productID));

        addProductLinkedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductLinkedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductLinkedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductLinkedCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        addAllProduct.setItems(mainScreenForm.getAllParts());
        addTableProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addTableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addTableProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addTableProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        shortLinkedPartsList = linkedPartList;
    }

    /**
     * @param mouseEvent The user is able to click the remove associated part button, which will remove a part from
     *                   the product table.
     */

    public void removeProductClicked(MouseEvent mouseEvent) {
        part selectedPart = (part) addProductLinked.getSelectionModel().getSelectedItem();
        shortLinkedPartsList.remove(selectedPart);

        confirmationAlert.setTitle("Part Removal Confirmation");
        confirmationAlert.setContentText("Are you sure you would like to remove this part?");
        confirmationAlert.setHeaderText("Removing part selected");
        Optional<ButtonType> deleteResponse = confirmationAlert.showAndWait();
        if (deleteResponse.get() == ButtonType.OK) {
            shortLinkedPartsList.remove(selectedPart);

            addProductLinked.setItems(shortLinkedPartsList);

        }
    }

    /**
     * @param mouseEvent The user is able to click the cancel button, which will allow the user to return the
     *                   main screen and delete any product listed.
     * @throws IOException
     */
    public void cancelProductClicked(MouseEvent mouseEvent) throws IOException {
        addProductLinked.refresh();
        confirmationAlert.setTitle("Exit?");
        confirmationAlert.setContentText("Are you sure you would like exit without adding a product?");
        confirmationAlert.setHeaderText("Back to the Inventory Management System");
        Optional<ButtonType> cancelResponse = confirmationAlert.showAndWait();
        if( cancelResponse.get() == ButtonType.OK){
            exitToInventory(mouseEvent);}
    }

    /**
     * @param mouseEvent The user is able to click the save button, which will allow the user to create a new product
     *                   in the system.
     * @throws IOException
     */
    public void saveProductClicked(MouseEvent mouseEvent) throws IOException {
        linkedPartList = addProductLinked.getItems();
        int productID = Integer.parseInt(textfieldAddProductID.getText());
        String productName;
        double productPrice;
        int productStock;
        int productMin;
        int productMax;
        if (isPriceDouble(String.valueOf(textfieldAddProductPrice.getText()))){
            productPrice = Double.parseDouble(textfieldAddProductPrice.getText());
            if (isFieldNotEmpty(String.valueOf(textfieldAddProductName.getText()))){
                productName = textfieldAddProductName.getText();
                if(isIntCheck(String.valueOf(this.textfieldAddProductInv.getText()))) {
                    productStock = Integer.parseInt(String.valueOf(this.textfieldAddProductInv.getText()));
                    if(isIntCheck(String.valueOf(this.textfieldAddProductMin.getText()))){
                        productMin = Integer.parseInt(String.valueOf(this.textfieldAddProductMin.getText()));
                        if(isIntCheck(String.valueOf(this.textfieldAddProductMax.getText()))){
                            productMax = Integer.parseInt(String.valueOf(this.textfieldAddProductMax.getText()));
                            if(areMinMaxInvValid(productMin, productMax, productStock)){
                                product addedProduct = new product(linkedPartList, productID, productName, productPrice, productStock, productMin, productMax);
                                mainScreenForm.addProduct(addedProduct);
                                exitToInventory(mouseEvent);}
                        }}}}}}


    /**
     * @param mouseEvent The user is able to click on the add button, which will add a new part from the linked list to
     *                   the product list.
     */
    public void addProductLinkClicked(MouseEvent mouseEvent) {
        part selectedPart = addAllProduct.getSelectionModel().getSelectedItem();
        shortLinkedPartsList.add(selectedPart);
        addProductLinked.setItems(shortLinkedPartsList);
    }

    /**
     * @param mouseEvent The user is able to click on the exit button, which will exit the main screen.
     * @throws IOException
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("main.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("");
        thisStage.setScene(nextScene);

    }

    /**
     * @param keyEvent The user is able to type in a name or part/product ID in the search bar.
     */
    public void addProductPart(KeyEvent keyEvent) {
        if (addProductSearch.getText().isEmpty()){
            addAllProduct.setItems(mainScreenForm.getAllParts());
        }
        labelProductSearching.setVisible(false);
        ObservableList<part> allParts = mainScreenForm.getAllParts();
        ObservableList<part> matchingProductsList = FXCollections.observableArrayList();
        String searchPartsString = addProductSearch.getText().toLowerCase();
        for (part p : allParts) {
            if (String.valueOf(p.getId()).contains(searchPartsString) || p.getName().toLowerCase().contains(searchPartsString))
            { matchingProductsList.add(p); }
        }
        addAllProduct.setItems(matchingProductsList);
        if (addProductSearch.getText() != null && matchingProductsList.isEmpty()){
            labelProductSearching.setVisible(true);
        }

    }

    /**
     * @param userInputString The user is able to use the text field, which will make sure it is an integer.
     * @return Will return a boolean value.
     */
    public boolean isIntCheck(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            integerAlert.setTitle("Error, Try again");
            integerAlert.setHeaderText("Incorrect Input");
            integerAlert.setContentText("Integer Required");
            integerAlert.showAndWait();}
        else
            try {
                int userInputInt = Integer.parseInt(userInputString);
                if (userInputInt >= 0){
                    bool = true;
                }
            } catch (NumberFormatException e) {
                integerAlert.setTitle("Error, Try Again");
                integerAlert.setHeaderText("Incorrect Input");
                integerAlert.setContentText("Integer Required");
                integerAlert.showAndWait();
            }

        return bool;
    }

    /**
     * @param userInputString The user will be able to put text in the text field, which will make sure it is a double
     *                        integer.
     * @return Will return a boolean value.
     */
    public boolean isPriceDouble(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){

            priceAlert.setTitle("Error, Try Again");
            priceAlert.setHeaderText("Incorrect Input");
            priceAlert.setContentText("Specify Price");
            priceAlert.showAndWait();}

        else {
            try {
                double userInputDouble = Double.parseDouble(userInputString);
                if (userInputDouble == 0) {
                    priceAlert.setTitle("Error, Try Again");
                    priceAlert.setHeaderText("Incorrect Input");
                    priceAlert.setContentText("Enter price that is greater than 0");
                    priceAlert.showAndWait();
                }
                if (userInputDouble > 0) {
                    bool = true;
                }
            } catch (NumberFormatException e) {
                priceAlert.setTitle("Error, Try Again");
                priceAlert.setHeaderText("Incorrect Input");
                priceAlert.setContentText("Your text contains non-numerical characters in the price field.");
                priceAlert.showAndWait();
            }
        }return bool;
    }

    /**
     * @param userInputString The user will be able to put text in the text field, which will make sure the user inserts
     *                        the correct value in the text field.
     * @return Will return a boolean value.
     */
    public boolean isFieldNotEmpty(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Incorrect Input");
            textAlert.setContentText("Text required");
            textAlert.showAndWait();}
        else {bool = true;}
        return bool;
    }

    /**
     * @param minInput Minimum is converted to an integer from the text field String.
     * @param maxInput Maximum is converted to an integer from text field String.
     * @param invInput Inv is converted to an integer from text field String.
     * @return This will return a boolean value.
     * This will confirm that the minimum, maximum, inv are the correct integer.
     */
    public boolean areMinMaxInvValid(int minInput, int maxInput, int invInput){
        boolean minMaxValid = false;
        boolean invInRange = false;
        boolean overallValid = false;
        if (minInput < maxInput){
            minMaxValid = true;
        }
        else{
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Invalid Minimum or Maximum");
            textAlert.setContentText("Minimum must have lower number than Maximum");
            textAlert.showAndWait();}

        if (invInput >= minInput && invInput <= maxInput){
            invInRange = true;
        }
        else{
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Incorrect Inventory Level");
            textAlert.setContentText("The inventory level must be in range of the minimum and maximum");
            textAlert.showAndWait();}

        if (invInRange && minMaxValid){
            overallValid = true;
        }
        return overallValid;
    }
}