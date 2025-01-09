package org.locations.dietplanner;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Implementation.command.ImportToJSONCommand;
import org.locations.dietplanner.Interfaces.ICommand;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class popupController {

    @FXML
    private Button addButton;

    @FXML
    private TextField caloriesInput;

    @FXML
    private TextField carbsInput;

    @FXML
    private Button createButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField fatsInput;

    @FXML
    private Label mealConfigError;

    @FXML
    private ListView<Ingredient> mealView;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField nameInputMeal;

    @FXML
    private TextField proteinsInput;

    @FXML
    private TextArea recipeText;

    @FXML
    private TextField typeInput;

    @FXML
    private ChoiceBox<IngredientType> typeView;

    private ICommand command;
    private RecipeBuilder recipeBuilder;
    private ObservableList<Ingredient> ingredientObservableList;
    private List<Ingredient> ingredientList = new ArrayList<>();

    @FXML
    public void initialize(){
        TypeReference<List<Recipe>> typeReference = new TypeReference<List<Recipe>>() {};
        command = new ImportToJSONCommand("recipes.json",typeReference);
        recipeBuilder = new RecipeBuilder();
        List<Recipe> recipeList = (List<Recipe>) command.execute();
        List<IngredientType> ingredientTypes = IngredientType.getTypes();
        ObservableList<IngredientType>  ingredientTypeObservableList = FXCollections.observableList(ingredientTypes);
        typeView.setItems(ingredientTypeObservableList);
        for (Recipe recipe : recipeList) {
            System.out.println(recipe);
        }
        addButton.setOnAction(actionEvent -> {
            errorLabel.setText("");
            errorLabel.setDisable(true);
            addIngredientHandler();
        });
    }

    private void addIngredientHandler(){
        String ingredientName = nameInput.getText();
        String caloriesString = caloriesInput.getText();
        String fatsString = fatsInput.getText();
        String proteinsString = proteinsInput.getText();
        String carbsString = carbsInput.getText();
        String ingredientType = typeView.getSelectionModel().getSelectedItem().toString();
        if(ingredientType.isEmpty() || ingredientName.isEmpty() || caloriesString.isEmpty() ||
                fatsString.isEmpty() || proteinsString.isEmpty() || carbsString.isEmpty()){
            errorLabel.setText("parameter is missing");
            errorLabel.setDisable(false);
            return;
        }
        try {
            double calories = Double.parseDouble(caloriesString);
            double fats = Double.parseDouble(fatsString);
            double proteins = Double.parseDouble(proteinsString);
            double carbs = Double.parseDouble(carbsString);
            Ingredient ingredient = new Ingredient(calories,fats,carbs,proteins,ingredientType,ingredientName);
            recipeBuilder.addIngredient(ingredient);
            ingredientList.add(ingredient);
            ingredientObservableList = FXCollections.observableList(ingredientList);
            mealView.setItems(ingredientObservableList);
        } catch (NumberFormatException e) {
            errorLabel.setText("Incorrect format");
            errorLabel.setDisable(false);
        }

    }


}
