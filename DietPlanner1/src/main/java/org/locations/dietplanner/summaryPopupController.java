package org.locations.dietplanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Composite.MealService;
import org.locations.dietplanner.Implementation.IngredientType;

import java.util.HashMap;
import java.util.List;

public class summaryPopupController {
    @FXML
    private AnchorPane anchorCalories;

    @FXML
    private AnchorPane anchorCarbs;

    @FXML
    private AnchorPane anchorFats;

    @FXML
    private AnchorPane anchorProteins;

    @FXML
    private Text caloriesSumLabel;

    @FXML
    private Rectangle caloriesSummary;

    @FXML
    private Text carbsSumLabel;

    @FXML
    private Rectangle carbsSummary;

    @FXML
    private Text fatsSumLabel;

    @FXML
    private Rectangle fatsSummary;

    @FXML
    private Text proteinsSumLabel;

    @FXML
    private Rectangle proteinsSummary;

    @FXML
    private Label weekSummaryLabel;


    private MealService service;
    @FXML
    public void initialize(){
        HashMap<IngredientType, List<Ingredient>> ingredientTypeListHashMap = service.groupIngredients();
        StringBuilder builder = new StringBuilder();
        double sumCal = 0;
        double sumCarbs = 0;
        double sumProteins = 0;
        double sumFats = 0;
        for (List<Ingredient> value : ingredientTypeListHashMap.values()) {
            System.out.println(value);
            for (Ingredient ingredient : value) {
                sumCal += ingredient.getCalories();
                sumCarbs += ingredient.getCarb();
                sumProteins += ingredient.getProtein();
                sumFats += ingredient.getFat();
            }
        }
        caloriesSumLabel.setText(String.format("%.2f",sumCal));
        carbsSumLabel.setText(String.format("%.2f",sumCarbs));
        proteinsSumLabel.setText(String.format("%.2f",sumProteins));
        fatsSumLabel.setText(String.format("%.2f",sumFats));
    }
    public void setService(MealService service) {
        this.service = service;
    }
}
