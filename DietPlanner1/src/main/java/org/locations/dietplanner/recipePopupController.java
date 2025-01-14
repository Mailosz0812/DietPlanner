package org.locations.dietplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Implementation.Builder.RecipeStorage;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Implementation.MealType;
import org.locations.dietplanner.Implementation.mealBuilder.Meal;

import java.util.ArrayList;
import java.util.List;

public class recipePopupController {
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
    private ListView<Ingredient> ingredientList;

    @FXML
    private Label mealConfigError;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField nameRecipeInput;

    @FXML
    private TextField proteinsInput;

    @FXML
    private TextArea recipeText;

    @FXML
    private ChoiceBox<MealType> recipeTypeInput;

    @FXML
    private ChoiceBox<IngredientType> typeView;

    @FXML
    private ListView<Recipe> recipiesView;

    @FXML
    private Button removeRecipeButton;

    private RecipeStorage storage;

    private List<Ingredient> ingredientsList = new ArrayList<>();
    private ObservableList<Ingredient> ingredientObservableList;
    private RecipeBuilder builder;
    private List<Recipe> recipeList;


    @FXML
    public void initialize(){
        storage = RecipeStorage.getInstance();
        List<MealType> mealTypes = MealType.getEnumList();
        ObservableList<MealType> observableMealTypes = FXCollections.observableList(mealTypes);
        recipeTypeInput.setItems(observableMealTypes);

        List<IngredientType> ingredientTypes = IngredientType.getTypes();
        ObservableList<IngredientType> ingredientTypeObservableList = FXCollections.observableList(ingredientTypes);
        typeView.setItems(ingredientTypeObservableList);
        loadRecipesHandler();

        addButton.setOnAction(actionEvent -> {
            errorLabel.setText("");
            errorLabel.setDisable(true);
            ingredientAddHandler();
        });
        createButton.setOnAction(actionEvent ->{
            mealConfigError.setText("");
            mealConfigError.setDisable(true);
            addRecipeHandler();
            loadRecipesHandler();
        });
        removeRecipeButton.setOnAction(actionEvent -> {
            removeRecipeHandler();
            loadRecipesHandler();
        });

    }

    private void loadRecipesHandler() {
        recipeList = storage.getRecipeList();
        ObservableList<Recipe> observableRecipeList = FXCollections.observableList(recipeList);
        recipiesView.setItems(observableRecipeList);
    }

    private void removeRecipeHandler(){
        Recipe recipe = recipiesView.getSelectionModel().getSelectedItem();
        if(recipe != null){
            storage.removeRecipe(recipe);
        }
    }
    private void addRecipeHandler(){
        String recipeTextInput = recipeText.getText();
        String nameInput = nameRecipeInput.getText();
        String mealTypeInput = recipeTypeInput.getSelectionModel().getSelectedItem().toString();
        if(ingredientsList.isEmpty() || recipeTextInput.isEmpty() || nameInput.isEmpty() || mealTypeInput == null){
            mealConfigError.setText("Missing parameter :(");
            mealConfigError.setDisable(false);
            return;
        }
        builder = new RecipeBuilder();
        builder.addRecipeText(recipeTextInput)
                .addRecipeName(nameInput)
                .addRecipeType(mealTypeInput)
                .addIngredients(ingredientsList);
        ingredientsList.clear();
        ingredientObservableList.clear();
        storage.addRecipe(builder.Build());
    }
    private void ingredientAddHandler(){
        String ingredientName = nameInput.getText();
        String caloriesString = caloriesInput.getText();
        String fatsString = fatsInput.getText();
        String proteinsString = proteinsInput.getText();
        String carbsString = carbsInput.getText();
        IngredientType ingredientType = typeView.getSelectionModel().getSelectedItem();
        if(ingredientType == null|| ingredientName.isEmpty() || caloriesString.isEmpty() ||
                fatsString.isEmpty() || proteinsString.isEmpty() || carbsString.isEmpty()){
            errorLabel.setText("Incorrect input :(");
            errorLabel.setDisable(false);
            return;
        }
        try {
            String ingredientTypeString = ingredientType.toString();
            double calories = Double.parseDouble(caloriesString);
            double fats = Double.parseDouble(fatsString);
            double proteins = Double.parseDouble(proteinsString);
            double carbs = Double.parseDouble(carbsString);
            Ingredient ingredient = new Ingredient(calories,fats,carbs,proteins,ingredientTypeString,ingredientName);
            ingredientsList.add(ingredient);
            ingredientObservableList = FXCollections.observableList(ingredientsList);
            ingredientList.setItems(ingredientObservableList);
        } catch (NumberFormatException e) {
            errorLabel.setText("Incorrect input :(");
            errorLabel.setDisable(false);
        }
    }
}
