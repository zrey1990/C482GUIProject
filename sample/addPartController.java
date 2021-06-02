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
 * The class that determines the functions of the Add Part Controller screen.
 */

public class addPartController implements Initializable {
    public AnchorPane anchorAddPart;
    public Label addPartMachineID;
    public RadioButton addPartInHouse;
    public RadioButton addPartOutsourced;
    public TextField partID;
    public TextField addPartName;
    public TextField addPartInventory;
    public TextField addPartPrice;
    public TextField addPartMax;
    public TextField addThePartMachineID;
    public TextField addPartMin;
    public Button addPartSave;
    public Button addPartCancel;
    public Label addPartCompanyName;
    public TextField addPartCompany;

    Alert integerAlert = new Alert(Alert.AlertType.ERROR);
    Alert priceAlert = new Alert(Alert.AlertType.ERROR);
    Alert textAlert = new Alert(Alert.AlertType.ERROR);
    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    /**
     * @param resourceBundle The method that establishes the Add Part to generate the part ID.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int partID = mainScreenForm.currentPartID();
        this.partID.setText(String.valueOf(partID));
    }

    /**
     * @param mouseEvent The user is able to click the inHouse button and enables/disables the controls with the outsource button.
     */
    public void inHouseClicked(MouseEvent mouseEvent) {
        this.addPartOutsourced.setSelected(false);
        this.addPartCompany.setText((String) null);
        this.addPartCompany.setDisable(true);
        this.addPartMachineID.setDisable(false);
        this.addPartCompanyName.setVisible(false);
        this.addPartCompany.setVisible(false);
        this.addPartMachineID.setVisible(true);
        this.addThePartMachineID.setVisible(true);
    }

    /**
     * @param mouseEvent The user is able to click the outsource button and enables/disables the controls with the outsource button.
     */
    public void outsourcedClicked(MouseEvent mouseEvent) {
        this.addPartInHouse.setSelected(false);
        this.addThePartMachineID.setText((String) null);
        this.addPartMachineID.setDisable(true);
        this.addPartCompany.setDisable(false);
        this.addPartMachineID.setVisible(false);
        this.addPartMachineID.setVisible(false);
        this.addPartCompanyName.setVisible(true);
        this.addPartCompany.setVisible(true);
    }

    /**
     * @param mouseEvent The user is able to click the save button.
     */
    public void partSaveClicked(MouseEvent mouseEvent) throws IOException {
        int partID = Integer.parseInt(String.valueOf(this.partID.getText()));
        String name;
        int stock;
        int min;
        int max;
        int machineID;
        double price;
        if (isPriceDouble(String.valueOf(addPartPrice.getText()))) {
            price = Double.parseDouble(addPartPrice.getText());
            if (isFieldNotEmpty(String.valueOf(addPartName.getText()))) {
                name = addPartName.getText();
                if (isIntCheck(String.valueOf(this.addPartInventory.getText()))) {
                    stock = Integer.parseInt(String.valueOf(this.addPartInventory.getText()));
                    if (isIntCheck(String.valueOf(this.addPartMin.getText()))) {
                        min = Integer.parseInt(String.valueOf(this.addPartMin.getText()));
                        if (isIntCheck(String.valueOf(this.addPartMax.getText()))) {
                            max = Integer.parseInt(String.valueOf(this.addPartMax.getText()));
                            if (areMinMaxInvValid(min, max, stock)) {
                                if (addPartInHouse.isSelected()) {
                                    if (isIntCheck(String.valueOf(this.addThePartMachineID.getText()))) {
                                        machineID = Integer.parseInt(String.valueOf(this.addThePartMachineID.getText()));
                                        part newPart = new inHouseForm(partID, name, price, stock, min, max, machineID);
                                        mainScreenForm.addPart(newPart);
                                        exitToInventory(mouseEvent);
                                    }
                                }
                                if (addPartOutsourced.isSelected()) {
                                    if (isFieldNotEmpty(addPartCompany.getText())) {
                                        String companyName = this.addPartCompany.getText();
                                        part newPart = new outsourcedForm(partID, name, price, stock, min, max, companyName);
                                        mainScreenForm.addPart(newPart);
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
     * @param mouseEvent The user is able to click on the cancel button, which cancels anything selected from inventory
     *                   and will send user back to the main screen for more options.
     */
    public void partCancelClicked(MouseEvent mouseEvent) throws IOException {
        confirmationAlert.setTitle("Cancel");
        confirmationAlert.setContentText("Are you positive?");
        confirmationAlert.setHeaderText("Back to the Inventory Management System screen");
        Optional<ButtonType> cancelResponse = confirmationAlert.showAndWait();
        if (cancelResponse.get() == ButtonType.OK) {
            exitToInventory(mouseEvent);
        }

    }

    /**
     * @param mouseEvent The user clicks on the exit button and will allow the application to exit the inventory screen.
     */
    public void exitToInventory(MouseEvent mouseEvent) throws IOException {
        Parent addPartParent = (Parent) FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("main.fxml")));
        Scene nextScene = new Scene(addPartParent);
        Stage thisStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        thisStage.setTitle("Inventory");
        thisStage.setScene(nextScene);

    }

    /**
     * @param userInputString Will return a String from the text field, which returns a boolean and confirms the user
     *                       uses a valid integer.
     */
    public boolean isIntCheck(String userInputString) {
        boolean bool = false;
        if ((userInputString == null || userInputString.length() == 0)) {
            integerAlert.setTitle("Error, Try Again");
            integerAlert.setHeaderText("Incorrect Input");
            integerAlert.setContentText("Does not contain any input");
            integerAlert.showAndWait();
        } else
            try {
                int userInputInt = Integer.parseInt(userInputString);
                if (userInputInt >= 0) {
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
     * @param userInputString String from a text box. Will return a String from the text field, which returns a boolean and confirms the user
     *      *                       uses a valid integer for double.
     */
    public boolean isPriceDouble(String userInputString) {
        boolean bool = false;
        if ((userInputString == null || userInputString.length() == 0)) {

            priceAlert.setTitle("Error, Try Again");
            priceAlert.setHeaderText("Incorrect Input");
            priceAlert.setContentText("Set Price");
            priceAlert.showAndWait();
        } else {
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
        }
        return bool;
    }

    /**
     * @param userInputString Will return a String from the text box, which returns a boolean and confirms the user
     *                        uses a valid integer. It also confirms the user used an input of a String in the text field.
     */
    public boolean isFieldNotEmpty(String userInputString) {
        boolean bool = false;
        if ((userInputString == null || userInputString.length() == 0)) {
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Incorrect Input");
            textAlert.setContentText("Text required");
            textAlert.showAndWait();
        } else {
            bool = true;
        }
        return bool;
    }

    /**
     * @param minInput Minimum is converted to an integer from the text field String.
     * @param maxInput Maximum is converted to an integer from text field String.
     * @param invInput Inv is converted to an integer from text field String.
     * @return This will return a boolean value.
     * This will confirm that the minimum, maximum, inv are the correct integer.
     */
    public boolean areMinMaxInvValid(int minInput, int maxInput, int invInput) {
        boolean minMaxValid = false;
        boolean invInRange = false;
        boolean overallValid = false;
        if (minInput < maxInput) {
            minMaxValid = true;
        } else {
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Invalid Minimum or Maximum");
            textAlert.setContentText("Minimum must have lower number than Maximum");
            textAlert.showAndWait();
        }

        if (invInput >= minInput && invInput <= maxInput) {
            invInRange = true;
        } else {
            textAlert.setTitle("Error, Try Again");
            textAlert.setHeaderText("Incorrect Inventory Level");
            textAlert.setContentText("The inventory level must be in range of the minimum and maximum");
            textAlert.showAndWait();
        }

        if (invInRange && minMaxValid) {
            overallValid = true;
        }
        return overallValid;
    }
}