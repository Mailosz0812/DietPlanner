package org.locations.dietplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeBuilder;
import org.locations.dietplanner.Implementation.Builder.RecipeStorage;
import org.locations.dietplanner.Implementation.Composite.MealService;
import org.locations.dietplanner.Implementation.IngredientType;
import org.locations.dietplanner.Implementation.MealType;
import org.locations.dietplanner.Implementation.mealBuilder.Meal;
import org.locations.dietplanner.Implementation.mealBuilder.MealBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private ChoiceBox<MealType> mealTypeInput;

    @FXML
    private TextField nameInputMeal;

    @FXML
    private TextField proteinsInput;

    @FXML
    private TextArea recipeText;

    @FXML
    private ChoiceBox<IngredientType> typeView;

    @FXML
    private Button clearButton;

    @FXML
    private ChoiceBox<Recipe> recipePicker;

    @FXML
    private Button removeButton;

    private MealService mealService;
    private RecipeBuilder recipeBuilder;
    private ObservableList<Ingredient> ingredientObservableList;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private MealBuilder mealBuilder;
    private LocalDate creationDate;
    private Runnable onMealAddedCallback;
    private RecipeStorage recipeStorage;

    @FXML
    public void initialize(){

        recipeBuilder = new RecipeBuilder();
        mealBuilder = new MealBuilder(recipeBuilder);
        recipeStorage = RecipeStorage.getInstance();

        List<IngredientType> ingredientTypes = IngredientType.getTypes();
        ObservableList<IngredientType>  ingredientTypeObservableList = FXCollections.observableList(ingredientTypes);
        typeView.setItems(ingredientTypeObservableList);

        List<MealType> mealTypeList = MealType.getEnumList();
        ObservableList<MealType> mealTypeObservableList = FXCollections.observableList(mealTypeList);
        mealTypeInput.setItems(mealTypeObservableList);

        ObservableList<Recipe> recipeObservableList = FXCollections.observableList(recipeStorage.getRecipeList());
        recipePicker.setItems(recipeObservableList);
        recipePicker.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue) -> {
            loadRecipeContent(newValue);
        });

        mealView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeButton.setOnAction(actionEvent -> {
            ObservableList<Ingredient> observableList = mealView.getSelectionModel().getSelectedItems();
            if(observableList != null && !observableList.isEmpty()) {
                ingredientObservableList.removeAll(observableList);
            }
        });

        addButton.setOnAction(actionEvent -> {
            errorLabel.setText("");
            errorLabel.setDisable(true);
            addIngredientHandler();
        });
        createButton.setOnAction(actionEvent -> {
            mealConfigError.setText("");
            mealConfigError.setDisable(true);
            createMealHandler();
        });
        clearButton.setOnAction(actionEvent -> {
            nameInputMeal.clear();
            mealTypeInput.setValue(null);
            recipeText.clear();
            recipePicker.setValue(null);
            ingredientObservableList.clear();
        });

    }
    private void loadRecipeContent(Recipe recipe){
        if(recipe != null) {
            System.out.println(recipe.getIngredientList());
            nameInputMeal.setText(recipe.getName());
            mealTypeInput.setValue(recipe.getMealType());
            recipeText.setText(recipe.getRecipeText());
            ingredientList = new ArrayList<>(recipe.getIngredientList());
            ingredientObservableList = FXCollections.observableList(ingredientList);
            mealView.setItems(ingredientObservableList);
        }
    }
    private void addIngredientHandler(){
        String ingredientName = nameInput.getText();
        String caloriesString = caloriesInput.getText();
        String fatsString = fatsInput.getText();
        String proteinsString = proteinsInput.getText();
        String carbsString = carbsInput.getText();
        IngredientType ingredientType = typeView.getSelectionModel().getSelectedItem();
        if(ingredientType == null|| ingredientName.isEmpty() || caloriesString.isEmpty() ||
                fatsString.isEmpty() || proteinsString.isEmpty() || carbsString.isEmpty()){
            errorLabel.setText("parameter is missing");
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
            ingredientList.add(ingredient);
            ingredientObservableList = FXCollections.observableList(ingredientList);
            mealView.setItems(ingredientObservableList);
        } catch (NumberFormatException e) {
            errorLabel.setText("Incorrect format");
            errorLabel.setDisable(false);
        }
    }

    private void createMealHandler(){
        String nameInput = nameInputMeal.getText();
        MealType mealType = mealTypeInput.getSelectionModel().getSelectedItem();
        String recipeInputText = recipeText.getText();
        if(mealType == null || nameInput.isEmpty()) {
            mealConfigError.setText("parameter is missing");
            mealConfigError.setDisable(false);
            return;
        }
        if(ingredientObservableList != null){
            String mealTypeString = mealType.toString();
            mealBuilder.addIngredients(ingredientList)
                    .addMealType(mealTypeString)
                    .addRecipeName(nameInput)
                    .setDate(creationDate);
            if(!recipeInputText.isEmpty()){
                mealBuilder.addRecipeText(recipeInputText);
            }
            Meal meal = mealBuilder.build();
            mealService.addMealGroup(meal);
            if(onMealAddedCallback != null) {
                onMealAddedCallback.run();
            }
        }
    }

    public void setMealsGroup(MealService mealService) {
        this.mealService = mealService;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setOnMealAddedCallback(Runnable callback) {
        this.onMealAddedCallback = callback;
    }
}
