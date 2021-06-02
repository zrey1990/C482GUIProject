package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
 * This class establishes functionality of the main inventory screen.
 */
public class mainController implements Initializable {
    public AnchorPane anchorMain;
    public Button addingPart;
    public Button modifyingPart;
    public Button deletingPart;
    public Label labelingParts;
    public TextField textfieldSearchForPart;
    public TableView<part> tableviewParts;
    public TableColumn<Object, Object> tablePartID;
    public TableColumn<Object, Object> tablePartName;
    public TableColumn<Object, Object> tableInvLvlPart;
    public TableColumn<Object, Object> tableCostPart;
    public Button buttonToExit;
    public Button buttonToAddProduct;
    public Button buttonToModifyProduct;
    public Button buttonToDeleteProduct;
    public Label labelingAllProducts;
    public TextField textfieldSearchForProduct;
    public TableView<product> tablieviewProducts;
    public TableColumn<Object, Object> tableProductID;
    public TableColumn<Object, Object> tableProductName;
    public TableColumn<Object, Object> tableInvLvlProduct;
    public TableColumn<Object, Object> tableCostProduct;

    private static final ObservableList<part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<product> allProducts = FXCollections.observableArrayList();
    public static product selectedProduct;
    public static part selectedPart;
    public Label labelPartSearchResults;
    public Button buttonForPartSearching;
    public Label labelProductSearchResults;

    Alert incorrectAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This Inventory initialize method populates the tables and creates a listener for the search box.
     * RUNTIME ERROR - I worked a long time trying to populate the inventory screen tables with data.
     * The tables did not say "no content in table" as before I had tried to populate them, but that had no rows of parts or products actually displaying.
     * I reviewed how to populate tables several times and could not ascertain the source of my troubles. Finally, I deleted all the lines of code and completely rewrote them and got them to work.
     * I realized that I had been misnaming the types that went into the columns. I was using the "partID" or "partName" which I used to reference these values elsewhere in the project so that
     *  the difference between parts and products were more readily apparent but in the actual Part object creation these fields were simply known as "id" or "name" without the "part" designation
     *  in their name. Once I correctly named the fields as designated in their object creation everything worked as expected.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableviewParts.setItems(mainScreenForm.getAllParts());
        tablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableInvLvlPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableCostPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        tablieviewProducts.setItems(mainScreenForm.getAllProducts());
        tableProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableInvLvlProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tableCostProduct.setCellValueFactory(new PropertyValueFactory<>("price"));

        FilteredList<part> partFilteredList = new FilteredList<>(allParts, p -> true);

        textfieldSearchForPart.textProperty().addListener((observableValue, oldValue, newValue) -> {
            partFilteredList.setPredicate(part -> {if (newValue==null||newValue.isEmpty()){
                return true; }

                String lowerFilter = newValue.toLowerCase();

                if (part.getName().toLowerCase().contains(lowerFilter)){
                    return true; }
                else return String.valueOf(part.getId()).contains(newValue);
            });
        });
        SortedList<part> sortedPartsList = new SortedList<>(partFilteredList);
        sortedPartsList.comparatorProperty().bind(tableviewParts.comparatorProperty());

    }


    /**
     * @param mouseEvent User clicks on add button.
     * @throws IOException
     * This method sends the application to the AddPart view.
     */
    public void addPartClicked(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("addPart.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("");
        thisStage.setScene(nextScene);
    }

    /**
     * @param mouseEvent User clicks on modify part button.
     * @throws IOException
     * This method sends the application to the ModifyPart view.
     */
    public void modifyPartClicked(MouseEvent mouseEvent) throws IOException {
        selectedPart = tableviewParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            incorrectAlert.setTitle("Please select a part");
            incorrectAlert.setContentText("Please select a part to modify");
            incorrectAlert.showAndWait();
        }
        else{
            Parent root = (Parent)FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("modifyPart.fxml")));
            Node node = (Node)mouseEvent.getSource();
            Stage thisStage = (Stage)node.getScene().getWindow();
            Scene nextScene = new Scene(root);
            thisStage.setScene(nextScene);
            thisStage.setTitle("");}
    }

    /**
     * @return selectedPart
     * This method returns the selected part.
     */
    public static part getSelectedPart() {
        return selectedPart;
    }

    /**
     * @return selectedProduct
     * This method returns the selected product.
     */
    public static product getSelectedProduct(){return selectedProduct;}

    /**
     * @param mouseEvent User clicks on exit button.
     * This method allows the user to exit the application.
     */
    public void exitClicked(MouseEvent mouseEvent) {
        confirmationAlert.setTitle("Exit?");
        confirmationAlert.setContentText("You may lose your data if you close");
        confirmationAlert.setHeaderText("Exit Confirmation");
        Optional<ButtonType> deleteResponse = confirmationAlert.showAndWait();
        if( deleteResponse.get() == ButtonType.OK){
            Platform.exit();
        }}

    /**
     * @param mouseEvent User clicks on add product button.
     * @throws IOException
     * This method send the application to the Add Product view.
     */
    public void addProductClicked(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("addProduct.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("");
        thisStage.setScene(nextScene);
    }

    /**
     * @param mouseEvent User clicks on modify product button.
     * @throws IOException
     * This method sends the application to the Modify Product view.
     */
    public void modifyProductClicked(MouseEvent mouseEvent) throws IOException {

        selectedProduct = tablieviewProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            incorrectAlert.setTitle("Please select a product");
            incorrectAlert.setContentText("Please select a product to modify.");
            incorrectAlert.showAndWait();
        }
        else{
            Parent root = (Parent)FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("modifyProduct.fxml")));
            Node node = (Node)mouseEvent.getSource();
            Stage thisStage = (Stage)node.getScene().getWindow();
            Scene nextScene = new Scene(root);
            thisStage.setScene(nextScene);
            thisStage.setTitle("");
        }

    }


    /**
     * @param mouseEvent User clicks on delete part button.
     * This method deletes a part.
     */
    public void deletingPartClicked(MouseEvent mouseEvent) {
        selectedPart = tableviewParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            incorrectAlert.setTitle("The part was not deleted");
            incorrectAlert.setContentText("You did not select a part to delete");
            incorrectAlert.showAndWait();
        }
        else{
            confirmationAlert.setTitle("Are you sure you want to delete?");
            confirmationAlert.setContentText("Removing" + " " + selectedPart.getName() + "?");
            confirmationAlert.setHeaderText("Please Wait, Pending");
            Optional<ButtonType> deleteResponse = confirmationAlert.showAndWait();
            if( deleteResponse.get() == ButtonType.OK){
                mainScreenForm.deletedPart(selectedPart);}

        }}


    /**
     * @param mouseEvent User clicks on delete product button.
     * This method deletes a product.
     */
    public void deletingProductClicked(MouseEvent mouseEvent) {
        selectedProduct = tablieviewProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            incorrectAlert.setTitle("The product was not deleted");
            incorrectAlert.setContentText("You did not select a product to delete");
            incorrectAlert.showAndWait();
        }
        else if (selectedProduct.getAllParts().isEmpty()){
            confirmationAlert.setTitle("Are you sure you want to delete?");
            confirmationAlert.setContentText("Removing" + " " + selectedProduct.getName() + "?");
            confirmationAlert.setHeaderText("Please Wait, Pending");
            Optional<ButtonType> deleteResponse = confirmationAlert.showAndWait();
            if( deleteResponse.get() == ButtonType.OK){
                mainScreenForm.deletedProduct(selectedProduct);}

        }
        else {
            incorrectAlert.setTitle("Product not deleted");
            incorrectAlert.setContentText("Your product contains a part, please check" + " " + selectedProduct.getName()+ " " + "upon removing.");
            incorrectAlert.setHeaderText("Unable to remove.");

            incorrectAlert.showAndWait();
        }
    }


    /**
     * @param actionEvent User types in product search box.
     * This method updates the product table based on user search text.
     */
    public void searchingForProductChanged(KeyEvent actionEvent) {
        if (textfieldSearchForProduct.getText().isEmpty()){
            tablieviewProducts.setItems(mainScreenForm.getAllProducts());
        }
        labelProductSearchResults.setVisible(false);
        ObservableList<product> allProducts = mainScreenForm.getAllProducts();
        ObservableList<product> matchingProductsList = FXCollections.observableArrayList();
        String searchProductString = textfieldSearchForProduct.getText().toLowerCase();
        for (product p : allProducts) {
            if (String.valueOf(p.getId()).contains(searchProductString) || p.getName().toLowerCase().contains(searchProductString))
            { matchingProductsList.add(p); }
        }
        tablieviewProducts.setItems(matchingProductsList);
        if (textfieldSearchForProduct.getText() != null && matchingProductsList.isEmpty()){
            labelProductSearchResults.setVisible(true);
        }
    }


    /**
     * @param keyEvent User types in part search box.
     * This method updates the part table based on user search text.
     */
    public void searchingForPartChanged(KeyEvent keyEvent) {
        if (textfieldSearchForPart.getText().isEmpty()){
            tableviewParts.setItems(mainScreenForm.getAllParts());
        }
        labelPartSearchResults.setVisible(false);
        ObservableList<part> allPartsList = mainScreenForm.getAllParts();
        ObservableList<part> matchingPartsList = FXCollections.observableArrayList();
        String searchPartString = textfieldSearchForPart.getText().toLowerCase();
        for (part p : allPartsList) {
            if (String.valueOf(p.getId()).contains(searchPartString) || p.getName().toLowerCase().contains(searchPartString))
            { matchingPartsList.add(p); }
        }
        tableviewParts.setItems(matchingPartsList);
        if (textfieldSearchForPart.getText() != null && matchingPartsList.isEmpty()){
            labelPartSearchResults.setVisible(true);
        }
    }
}