package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * The class that determines the function of the Modify Part Controller Screen.
 */
public class modifyPartController implements Initializable {
    public AnchorPane anchorModifyPart;
    public Label labelModifyPartMachineID;
    public RadioButton modifyPartInHouse;
    public RadioButton radBtnModifyPartOutsourced;
    public TextField modifyPartID;
    public TextField modifyPartName;
    public TextField modifyPartInventory;
    public TextField modifyPartPrice;
    public TextField modifyPartMax;
    public TextField textfieldModifyPartMachineID;
    public TextField modifyPartMin;
    public Button modifyPartSave;
    public Button modifyPartCancel;
    public Label labelModifyPartCompany;
    public TextField textfieldModifyPartCompany;
    private part clickedPart;

    Alert integerAlert = new Alert(Alert.AlertType.ERROR);
    Alert textAlert = new Alert(Alert.AlertType.ERROR);
    Alert priceAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);


    /**
     * @param url The url parameter.
     * @param resourceBundle
     * This will fill in the field with the correct data when you modify any part from the table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickedPart = mainController.getSelectedPart();
        modifyPartID.setText(String.valueOf(clickedPart.getId()));
        modifyPartName.setText(clickedPart.getName());
        modifyPartInventory.setText(String.valueOf(clickedPart.getStock()));
        modifyPartMin.setText(String.valueOf(clickedPart.getMin()));
        modifyPartMax.setText(String.valueOf(clickedPart.getMax()));
        modifyPartPrice.setText(String.valueOf(clickedPart.getPrice()));
        if (clickedPart instanceof inHouseForm){
            modifyPartInHouse.setSelected(true);
            radBtnModifyPartOutsourced.setSelected(false);
            this.labelModifyPartCompany.setVisible(false);
            this.textfieldModifyPartCompany.setVisible(false);
            this.labelModifyPartMachineID.setVisible(true);
            this.textfieldModifyPartMachineID.setVisible(true);
            textfieldModifyPartMachineID.setText(String.valueOf(((inHouseForm) clickedPart).getMachineID()));

        }
        if (clickedPart instanceof outsourcedForm){
            radBtnModifyPartOutsourced.setSelected(true);
            modifyPartInHouse.setSelected(false);
            this.labelModifyPartMachineID.setVisible(false);
            this.textfieldModifyPartMachineID.setVisible(false);
            this.labelModifyPartCompany.setVisible(true);
            this.textfieldModifyPartCompany.setVisible(true);
            textfieldModifyPartCompany.setText(((outsourcedForm) clickedPart).getCompanyName());
        }


    }

    /**
     * @param mouseEvent The user is able to click the save button.
     * @throws IOException
     *This will allow the user to modify a product and save it to the table.
     */
    public void modifyPartSaveClick(MouseEvent mouseEvent) throws IOException {
        int partID = clickedPart.getId();
        String name;
        int stock;
        int min;
        int max;
        int machineID;
        double price;
        if (isPriceDouble(String.valueOf(modifyPartPrice.getText()))){
            price = Double.parseDouble(modifyPartPrice.getText());
            if (isFieldNotEmpty(String.valueOf(modifyPartName.getText()))){
                name = modifyPartName.getText();
                if(isIntCheck(String.valueOf(this.modifyPartInventory.getText()))) {
                    stock = Integer.parseInt(String.valueOf(this.modifyPartInventory.getText()));
                    if(isIntCheck(String.valueOf(this.modifyPartMin.getText()))){
                        min = Integer.parseInt(String.valueOf(this.modifyPartMin.getText()));
                        if(isIntCheck(String.valueOf(this.modifyPartMax.getText()))){
                            max = Integer.parseInt(String.valueOf(this.modifyPartMax.getText()));
                            if(areMinMaxInvValid(min, max, stock)){
                                if (modifyPartInHouse.isSelected()) {
                                    if(isIntCheck(String.valueOf(this.textfieldModifyPartMachineID.getText()))){
                                        machineID = Integer.parseInt(String.valueOf(this.textfieldModifyPartMachineID.getText()));
                                        part newPart = new inHouseForm(partID, name, price, stock, min, max, machineID);
                                        mainScreenForm.addPart(newPart);
                                        mainScreenForm.deletedPart(clickedPart);
                                        exitToInventory(mouseEvent);
                                    }
                                }
                                if (radBtnModifyPartOutsourced.isSelected()) {
                                    if (isFieldNotEmpty(textfieldModifyPartCompany.getText())){
                                        String companyName = this.textfieldModifyPartCompany.getText();
                                        part newPart = new outsourcedForm(partID, name, price, stock, min, max, companyName);
                                        mainScreenForm.addPart(newPart);
                                        mainScreenForm.deletedPart(clickedPart);
                                        exitToInventory(mouseEvent);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * @param mouseEvent The user will be able to click on the cancel button.
     * @throws IOException
     * This will allow the user the permanently delete any changes made while modifying a part.
     */
    public void modifyPartCancelClick(MouseEvent mouseEvent) throws IOException {
        confirmationAlert.setTitle("Exit?");
        confirmationAlert.setContentText("Are you positive?");
        confirmationAlert.setHeaderText("Back to the Inventory Management System screen");
        Optional<ButtonType> cancelResponse = confirmationAlert.showAndWait();
        if( cancelResponse.get() == ButtonType.OK){
            exitToInventory(mouseEvent);}
    }

    /**
     * @param mouseEvent The user is able to click the exit button.The user clicks on the exit button.
     * @throws IOException
     * The user will be able to exit to the main screen.
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("main.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Inventory");
        thisStage.setScene(nextScene);

    }

    /**
     * @param mouseEvent The user is able to click on the In-House button in the GUI.
     */
    public void inHouseClicked(MouseEvent mouseEvent) {
        this.radBtnModifyPartOutsourced.setSelected(false);
        this.textfieldModifyPartCompany.setText((String)null);
        this.textfieldModifyPartCompany.setDisable(true);
        this.textfieldModifyPartMachineID.setDisable(false);
        this.labelModifyPartCompany.setVisible(false);
        this.textfieldModifyPartCompany.setVisible(false);
        this.labelModifyPartMachineID.setVisible(true);
        this.textfieldModifyPartMachineID.setVisible(true);
    }
    /**
     * @param mouseEvent The user is able to click on the Outsourced button in the GUI.
     */
    public void buttonOutsourcedClick(MouseEvent mouseEvent) {
        this.modifyPartInHouse.setSelected(false);
        this.textfieldModifyPartMachineID.setText((String)null);
        this.textfieldModifyPartMachineID.setDisable(true);
        this.textfieldModifyPartCompany.setDisable(false);
        this.labelModifyPartMachineID.setVisible(false);
        this.textfieldModifyPartMachineID.setVisible(false);
        this.labelModifyPartCompany.setVisible(true);
        this.textfieldModifyPartCompany.setVisible(true);
    }

    /**
     * @param userInputString Allow the user to input text in the text field, which will
     *                        determine if the user put a String as an integer.
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
     * @param userInputString Allow the user to input text in the text field, which will determine if the user put a
     *                        String in the field.
     * @return Will return a boolean value.
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

    /**
     * @param userInputString User can put input in the inv text field, which will determine that the user put a
     *                        number in the text field.
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
}