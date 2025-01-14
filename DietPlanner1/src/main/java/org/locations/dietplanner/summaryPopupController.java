package org.locations.dietplanner;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Composite.MealService;
import org.locations.dietplanner.Implementation.IngredientType;

import java.util.HashMap;
import java.util.List;

public class summaryPopupController {
    @FXML
    private Text someText;

    private MealService service;
    @FXML
    public void initialize(){
        HashMap<IngredientType, List<Ingredient>> ingredientTypeListHashMap = service.groupIngredients();
        StringBuilder builder = new StringBuilder();
        for (List<Ingredient> value : ingredientTypeListHashMap.values()) {
            builder.append(value);
            builder.append("\n");
            System.out.println(value);
        }
        someText.setText(builder.toString());
    }
    public void setService(MealService service) {
        this.service = service;
    }
}
