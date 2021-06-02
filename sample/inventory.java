package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * @author Zackery Reynolds
 */

/**
 * --- FUTURE ENHANCEMENT --- A couple things that I would recommend for improvement overall of the GUI would be to add
 * some functions that would show what products are available for each bike listed and when they are sold or sold out,
 * it would show up in the inventory so you don't go through the whole process of selecting items and then
 * later finding out you wasted your time. I personally experienced this before when shopping online at a
 * store, I selected a bunch of items that said they were in stock but in the end it wouldn't let me check
 * out because the item was actually not in stock. There would have to be code to automatically do the
 * inventory for you so no one has to go through in manually. Another improvement would be to add a check out
 * screen for the GUI so customers are able to add their credit card information along with a section for
 * personal information, in case a customer wants an item shipped to them. The GUI could also benefit by
 * showing the sale price compared to other stores to let customers know they're getting a great deal on the
 * product and why they should return to the store for future use.I feel this would be the business one step
 * ahead of other companies by a huge margin because there would be a lot of information provided to gain the
 * customers trust and you would also have loyal customers who wouldn't return to other stores. One last benefit
 * would be to add a review section, so customers can read over reviews about the product so they can make sure
 * they're making the right decision before buying the product. You would have to hire more developers or a
 * private company to create something like this but it would pay off in the end.
 * Main class for mainController.java
 *This class determines the main tables, search box, text fields, and options for the application.
 */
public class inventory extends Application {
    /***/
    @Override
    /** This will start up the GUI application for the main screen.
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 870, 360));
        primaryStage.show();
    }


    /**
     * @param args This will be all of the arguments that are passed to the main method.
     * This will be the main method that creates the parts and the products that will make the application pop up on
     *             your screen.
     */
    public static void main(String[] args) {
        int partInvId = mainScreenForm.currentPartID();
        inHouseForm brakes = new inHouseForm(partInvId,"Brakes", 79.99, 6, 1, 8, 980);
        partInvId = mainScreenForm.currentPartID();
        inHouseForm rims = new inHouseForm(partInvId,"Rims", 799.99, 8, 1, 8, 96);
        partInvId = mainScreenForm.currentPartID();
        inHouseForm tires = new inHouseForm(partInvId,"Tires", 999.79, 6, 1, 8, 70);
        partInvId = mainScreenForm.currentPartID();
        inHouseForm grips = new inHouseForm(partInvId,"Grips", 99.99, 9, 1, 8, 949);
        partInvId = mainScreenForm.currentPartID();
        outsourcedForm clutch = new outsourcedForm(partInvId, "Clutch",199.69, 6, 1,8, "Honda");
        mainScreenForm.addPart(brakes);
        mainScreenForm.addPart(rims);
        mainScreenForm.addPart(tires);
        mainScreenForm.addPart(grips);
        mainScreenForm.addPart(clutch);


        /**This portion will be the addition to the product and a list of items that are already pre-selected.
         * The Yamaha will be empty so its easier to modify for testing.
         */
        int productInvId = mainScreenForm.currentProductID();
        product motorBike = new product(productInvId, "Harley Davidson", 7000.00, 4, 1, 20);
        productInvId = mainScreenForm.currentProductID();
        product otherMotorBike = new product(productInvId, "Honda CB2000", 4699.99, 6, 1, 20);
        productInvId = mainScreenForm.currentProductID();
        product otherOtherMotorBike = new product(productInvId, "Yamaha 8X37", 4599.99, 6, 1, 20);
        otherOtherMotorBike.addPartToProduct(clutch);
        otherOtherMotorBike.addPartToProduct(tires);
        otherOtherMotorBike.addPartToProduct(tires);
        otherOtherMotorBike.addPartToProduct(rims);
        otherOtherMotorBike.addPartToProduct(rims);
        mainScreenForm.addProduct(motorBike);
        mainScreenForm.addProduct(otherMotorBike);
        mainScreenForm.addProduct(otherOtherMotorBike);

        launch(args);
    }
}