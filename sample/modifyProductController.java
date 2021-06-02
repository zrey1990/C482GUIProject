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
 * The class that determines the function of the Modify Product Controller Screen.
 */
public class modifyProductController implements Initializable {
    public AnchorPane anchorModifyProduct;
    public TextField txtModifyProductSearch;
    public Label labelPartSearchResults;
    public Button buttonForPartSearching;
    public TableView<part> modifyPartLinked;
    public TableColumn<Object, Object> tableModifyProductLinkedID;
    public TableColumn<Object, Object> tableModifyProductLinkedName;
    public TableColumn<Object, Object> tableModifyProductLinkedInv;
    public TableColumn<Object, Object> tableModifyProductLinkedCost;
    public TableView<part> tableModifyAllParts;
    public TableColumn<Object, Object> tableModifyProductID;
    public TableColumn<Object, Object> tableModifyProductName;
    public TableColumn<Object, Object> tableModifyProductInv;
    public TableColumn<Object, Object> tableModifyProductCost;
    public Button removeModifyProduct;
    public Button cancelModifyProduct;
    public Button saveModifyProduct;
    public TextField textfieldModifyProductID;
    public TextField textfieldModifyProductName;
    public TextField textfieldModifyProductInv;
    public TextField textfieldModifyProductPrice;
    public TextField textfieldModifyProductMax;
    public TextField textfieldModifyProductMin;
    public Button addModifyProduct;
    private ObservableList<part> linkedPartsList = FXCollections.observableArrayList();
    private ObservableList<part> shortLinkedPartsList = FXCollections.observableArrayList();
    product clickedProduct;

    Alert integerAlert = new Alert(Alert.AlertType.ERROR);
    Alert priceAlert = new Alert(Alert.AlertType.ERROR);
    Alert textAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This will makes sure the GUI places the correct input methods into the tables and the text field in the
     * Modify Product Controller.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickedProduct = mainController.getSelectedProduct();
        linkedPartsList = clickedProduct.getAllParts();
        shortLinkedPartsList = clickedProduct.getAllParts();

        tableModifyProductLinkedID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableModifyProductLinkedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableModifyProductLinkedInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableModifyProductLinkedCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableModifyProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableModifyProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableModifyProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableModifyProductCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableModifyAllParts.setItems(mainScreenForm.getAllParts());
        modifyPartLinked.setItems(shortLinkedPartsList);


        textfieldModifyProductID.setText(String.valueOf(clickedProduct.getId()));
        textfieldModifyProductName.setText(String.valueOf(clickedProduct.getName()));
        textfieldModifyProductInv.setText(String.valueOf(clickedProduct.getStock()));
        textfieldModifyProductMin.setText(String.valueOf(clickedProduct.getMin()));
        textfieldModifyProductMax.setText(String.valueOf(clickedProduct.getMax()));
        textfieldModifyProductPrice.setText(String.valueOf(clickedProduct.getPrice()));
        modifyPartLinked.refresh();
    }

    /**
     * @param mouseEvent The user is able to click the remove associated part button.
     */
    public void modifyRemoveProduct(MouseEvent mouseEvent) {
        part selectedPart = modifyPartLinked.getSelectionModel().getSelectedItem();
        confirmationAlert.setTitle("Part Removal Confirmation");
        confirmationAlert.setContentText("Are you sure you would like to remove" + " " +selectedPart.getName() + " " + "from" + " " + clickedProduct.getName() + "?");
        confirmationAlert.setHeaderText("Removing part selected");
        Optional<ButtonType> deleteResponse = confirmationAlert.showAndWait();
        if (deleteResponse.get() == ButtonType.OK) {
            shortLinkedPartsList.remove(selectedPart);

            modifyPartLinked.setItems(shortLinkedPartsList);
        }
    }


    /**
     * @param mouseEvent The user is able to click the save button, which will save any changes made to part/product.
     * @throws IOException
     */
    public void modifySaveProduct(MouseEvent mouseEvent) throws IOException {
        int productID = Integer.parseInt(textfieldModifyProductID.getText());
        linkedPartsList = modifyPartLinked.getItems();
        String productName;
        double productPrice;
        int productStock;
        int productMin;
        int productMax;
        if (isPriceDouble(String.valueOf(textfieldModifyProductPrice.getText()))) {
            productPrice = Double.parseDouble(textfieldModifyProductPrice.getText());
            if (isFieldNotEmpty(String.valueOf(textfieldModifyProductName.getText()))) {
                productName = textfieldModifyProductName.getText();
                if (isIntCheck(String.valueOf(this.textfieldModifyProductInv.getText()))) {
                    productStock = Integer.parseInt(String.valueOf(this.textfieldModifyProductInv.getText()));
                    if (isIntCheck(String.valueOf(this.textfieldModifyProductMin.getText()))) {
                        productMin = Integer.parseInt(String.valueOf(this.textfieldModifyProductMin.getText()));
                        if (isIntCheck(String.valueOf(this.textfieldModifyProductMax.getText()))) {
                            productMax = Integer.parseInt(String.valueOf(this.textfieldModifyProductMax.getText()));
                            if (areMinMaxInvValid(productMin, productMax, productStock)) {
                                product addedProduct = new product(linkedPartsList, productID, productName, productPrice, productStock, productMin, productMax);
                                mainScreenForm.addProduct(addedProduct);
                                mainScreenForm.deletedProduct(clickedProduct);
                                exitingToInventory(mouseEvent);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param mouseEvent The user is able to click the add button to add a new product/part.
     */
    public void modifyAddProductToList(MouseEvent mouseEvent) {
        part selectedPart = tableModifyAllParts.getSelectionModel().getSelectedItem();
        shortLinkedPartsList.add(selectedPart);
        modifyPartLinked.setItems(shortLinkedPartsList);
    }

    /**
     * @param mouseEvent The user is able to click the cancel button, which will permanently delete any changes made to
     *                   the part/product and make you return to the main screen.
     * @throws IOException
     */
    public void modifyCancelProduct(MouseEvent mouseEvent) throws IOException {
        confirmationAlert.setTitle("Cancel Confirmation");
        confirmationAlert.setContentText("Are you sure you would like cancel without modifying " + clickedProduct.getName() + "?");
        confirmationAlert.setHeaderText("Back to the Inventory Management System screen");
        Optional<ButtonType> cancelResponse = confirmationAlert.showAndWait();
        if (cancelResponse.get() == ButtonType.OK) {
            modifyPartLinked.setItems(linkedPartsList);
            modifyPartLinked.refresh();
            exitingToInventory(mouseEvent);
        }
    }

    /**
     * @param mouseEvent A button which sends the user to the main inventory view was clicked.
     * @throws IOException
     * This method returns the user to the main inventory view.
     */
    public void exitingToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("main.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("");
        thisStage.setScene(nextScene);

    }


    /**
     * @param keyEvent Makes sure the user inputs the correct ID or name in search field and will list the correct
     *                 product that is typed in.
     */
    public void txtModifyProductKeyPress(KeyEvent keyEvent) {
        if (txtModifyProductSearch.getText().isEmpty()) {
            tableModifyAllParts.setItems(mainScreenForm.getAllParts());
        }
        labelPartSearchResults.setVisible(false);
        ObservableList<part> allParts = mainScreenForm.getAllParts();
        ObservableList<part> matchingProductsList = FXCollections.observableArrayList();
        String searchPartsString = txtModifyProductSearch.getText().toLowerCase();
        for (part p : allParts) {
            if (String.valueOf(p.getId()).contains(searchPartsString) || p.getName().toLowerCase().contains(searchPartsString)) {
                matchingProductsList.add(p);
            }
        }
        tableModifyAllParts.setItems(matchingProductsList);
        if (txtModifyProductSearch.getText() != null && matchingProductsList.isEmpty()){
            labelPartSearchResults.setVisible(true);
        }
    }


    /**
     * @param userInputString Allow the user to input text in the text field, which will determine if the text
     *                        is an integer typed in by the user.
     * @return Will return a boolean value.
     */
    public boolean isIntCheck(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            integerAlert.setTitle("Error, Try Again");
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
     * @param userInputString Allow the user to determine if they typed in the correct Inv number in the text field,
     *                        which will determine if the text is a number.
     * @return Will return a boolean value.
     */
    public boolean isPriceDouble(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){

            priceAlert.setTitle("Error, Try Again");
            priceAlert.setHeaderText("Incorrect Input");
            priceAlert.setContentText("Specify price");
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
     * @param userInputString Allow the user to determine the input into the text field, which will make sure the user
     *                        typed a String in the text field.
     * @return WIll return a boolean value.
     */
    public boolean isFieldNotEmpty(String userInputString){
        boolean bool = false;
        if ((userInputString == null || userInputString.length()==0)){
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Incorrect Input");
            textAlert.setContentText("Text Required");
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