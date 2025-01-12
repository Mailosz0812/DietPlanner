
package org.locations.dietplanner;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.locations.dietplanner.Implementation.Builder.Ingredient;
import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Implementation.Builder.RecipeStorage;
import org.locations.dietplanner.Implementation.Composite.MealService;
import org.locations.dietplanner.Implementation.MealType;
import org.locations.dietplanner.Implementation.command.ExportToJSONCommand;
import org.locations.dietplanner.Implementation.command.ImportFromJSONCommand;
import org.locations.dietplanner.Implementation.command.MemoryCommandInvoker;
import org.locations.dietplanner.Interfaces.IMeal;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class mainPageController {
    @FXML
    private AnchorPane AnchorPane_Left;

    @FXML
    private HBox HBox_Content_Footer;

    @FXML
    private HBox HBox_Content_Middle;

    @FXML
    private HBox HBox_Content_Top;

    @FXML
    private Button HBox_Content_Top_Arrow_Left;

    @FXML
    private Button HBox_Content_Top_Arrow_Right;

    @FXML
    private ScrollPane HBox_Content_Top_ScrollPane;

    @FXML
    private HBox HBox_Down;

    @FXML
    private HBox HBox_Top;

    @FXML
    private ToolBar ToolBar_Top;

    @FXML
    private VBox VBox_All;

    @FXML
    private VBox VBox_Content;

    @FXML
    private AnchorPane breakfastAnchor;

    @FXML
    private HBox breakfastContainer;

    @FXML
    private HBox buttonContainer;

    @FXML
    private VBox caloriesContainer;

    @FXML
    private VBox carbsContainer;

    @FXML
    private AnchorPane dessertAnchor;

    @FXML
    private HBox dessertContainer;

    @FXML
    private AnchorPane dinnerAnchor;

    @FXML
    private HBox dinnerContainer;

    @FXML
    private VBox fatsContainer;

    @FXML
    private Button importButton;

    @FXML
    private ImageView logo;

    @FXML
    private AnchorPane lunchAnchor;

    @FXML
    private HBox lunchContainer;

    @FXML
    private VBox proteinContainer;

    @FXML
    private AnchorPane supperAnchor;

    @FXML
    private HBox supperContainer;

    @FXML
    private Button exportButton;

    @FXML
    private Button saveButton;

    private Button lastClickedButton;
    private MemoryCommandInvoker invoker = new MemoryCommandInvoker();
    private MemoryCommandInvoker storageInvoker = new MemoryCommandInvoker();
    private List<MealService> mealsGroups;
    private LocalDate creationDate;
    private mealListWrapper wrapper;
    private RecipeStorage storage;


    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE",new Locale("en","EN"));
    private HashMap<MealType, AnchorPane> mealsContainers;

    @FXML
    public void initialize() {
        final LocalDate[] currentDate = {LocalDate.now()};


        TypeReference<mealListWrapper> typeReference = new TypeReference<mealListWrapper>() {};
        invoker.setCommand(new ImportFromJSONCommand<mealListWrapper>("meals.json", typeReference));
        wrapper = invoker.executeCommand();

        TypeReference<RecipeStorage> recipeStorageTypeReference = new TypeReference<RecipeStorage>() {};
        storageInvoker.setCommand(new ImportFromJSONCommand<RecipeStorage>("recipes.json", recipeStorageTypeReference));
        storage = storageInvoker.executeCommand();
        if(wrapper == null){
            mealsGroups = new ArrayList<>();
            wrapper = new mealListWrapper(mealsGroups);
        }else{
            mealsGroups = wrapper.getMealServiceList();
        }
        if(storage == null){
            storage = RecipeStorage.getInstance();
        }
        for(Recipe recipe: storage.getRecipeList()){
            System.out.println(recipe);
        }
        buttonContainer.setStyle("-fx-background-color: #76C1C4");
        HBox_Content_Top_ScrollPane.setStyle("-fx-background-color: #76C1C4");

        mealsContainers = new HashMap<>();
        mealsContainers.put(MealType.BREAKFAST, breakfastAnchor);
        mealsContainers.put(MealType.LUNCH, lunchAnchor);
        mealsContainers.put(MealType.DINNER, dinnerAnchor);
        mealsContainers.put(MealType.DESSERT, dessertAnchor);
        mealsContainers.put(MealType.SUPPER,supperAnchor);

        loadWeek(currentDate[0]);
        creationDate = currentDate[0];


        //TOP toolbar buttons init
        saveButton.setOnAction(actionEvent -> {
//            for (MealService mealsGroup : mealsGroups) {
//                System.out.println(mealsGroup.toStringGroups());
//            }
            invoker.setCommand(new ExportToJSONCommand<>("meals.json", wrapper));
            invoker.executeCommand();
        });



        HBox_Content_Top_Arrow_Left.setOnAction(event -> {
            currentDate[0] = currentDate[0].minusWeeks(1);
            loadWeek(currentDate[0]);
        });
        HBox_Content_Top_Arrow_Right.setOnAction(event -> {
            currentDate[0] = currentDate[0].plusWeeks(1);
            loadWeek(currentDate[0]);
        });
    }
    private void loadWeek(LocalDate startDate){
        buttonContainer.getChildren().clear();
        LocalDate firstDayOfWeek = startDate.with(DayOfWeek.MONDAY);
        for (int i = 0; i < 7; i++) {
            LocalDate day = firstDayOfWeek.plusDays(i);
            Button button = new Button();
            String text = day.format(dayFormatter) + "\n" + day.format(dayOfWeekFormatter);
            button.setText(text);
            if(day.equals(startDate)){
                lastClickedButton = button;
                button.setStyle("-fx-background-color: #56A3A6; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                loadMeals(startDate);
                loadMacros(startDate);
            }
            else {
                button.setStyle("-fx-background-color: #76C1C4;-fx-text-alignment: center; -fx-alignment: center;-fx-padding: 10px;");
                button.setMinWidth(50.0);
                button.setMinHeight(50.0);
            }
                button.setOnAction(actionEvent -> {
                    lastClickedButton.setStyle("-fx-background-color: #76C1C4; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    button.setStyle("-fx-background-color: #56A3A6; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    lastClickedButton = button;
                    creationDate = day;
                    loadMeals(day);
                    loadMacros(day);
                });

            buttonContainer.getChildren().add(button);
        }
    }
    private void loadMeals(LocalDate currentDate){
        mealsContainers.values().forEach(container -> container.getChildren().clear());
        if(!mealsGroups.isEmpty()) {
            for (MealService mealsGroup : mealsGroups) {
                List<IMeal> meals = mealsGroup.getMealByDate(currentDate);
                for (IMeal meal : meals) {
                    MealType mealType = meal.getRecipe().getMealType();
                    AnchorPane container = mealsContainers.get(mealType);
                    Label mealLabel = new Label(formatMealInfo(meal));
                    container.getChildren().add(mealLabel);
                }
            }
        }
        mealsContainers.values().forEach(container -> {
            if (container.getChildren().isEmpty()) {
                addingMealHandler(container);
            }
        });
    }
    private void addingMealHandler(AnchorPane container){
        Button button = new Button("Add Meal");
        container.getChildren().add(button);
        AnchorPane.setTopAnchor(button, container.getHeight() / 2 - button.getHeight() / 2);
        AnchorPane.setLeftAnchor(button, container.getWidth() / 2 - button.getWidth() / 2);
        button.setOnAction(actionEvent -> {
            openPopupHandler();
        });
        container.widthProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setLeftAnchor(button, newVal.doubleValue() / 2 - button.getWidth() / 2);
        });
        container.heightProperty().addListener((obs, oldVal, newVal) -> {
            AnchorPane.setTopAnchor(button, newVal.doubleValue() / 2 - button.getHeight() / 2);
        });
    }
    private String formatMealInfo(IMeal meal) {
        return meal.getRecipe().getName() + "\n" +
                meal.getRecipe().getRecipeText() + "\n" +
                "Kalorie: " + meal.getRecipe().getIngredientList().stream().mapToDouble(Ingredient::getCalories).sum();
    }
    private void loadMacros(LocalDate day){
        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbs = 0;
        caloriesContainer.getChildren().clear();
        proteinContainer.getChildren().clear();
        fatsContainer.getChildren().clear();
        carbsContainer.getChildren().clear();
        if(!mealsGroups.isEmpty()) {
            MealService mealServiceFittedToDate = null;
            for (MealService mealsGroup : mealsGroups) {
                if(mealsGroup.isDateWithinRange(day,mealsGroup.getStartDate(),mealsGroup.getEndDate())){
                    mealServiceFittedToDate = mealsGroup;
                    break;
                }
            }
            if(mealServiceFittedToDate != null) {
                List<IMeal> meals = mealServiceFittedToDate.getMealByDate(day);
                for (IMeal meal : meals) {
                    Recipe currentRecipe = meal.getRecipe();
                    calories += currentRecipe.calculateCalories();
                    proteins += currentRecipe.calculateProtein();
                    fats += currentRecipe.calculateFat();
                    carbs += currentRecipe.calculateCarb();
                }
            }
        }
        caloriesContainer.getChildren().addAll(new Label("Calories"), new Label("\n" + calories));
        proteinContainer.getChildren().addAll(new Label("Proteins"), new Label("\n" + proteins));
        fatsContainer.getChildren().addAll(new Label("Fats"), new Label("\n" + fats));
        carbsContainer.getChildren().addAll(new Label("Carbs"), new Label("\n" + carbs));
    }
    private void openPopupHandler(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),784,505 );
            popupController controller = fxmlLoader.getController();
            controller.setMealsGroup(mealsGroups);
            controller.setCreationDate(creationDate);

            controller.setOnMealAddedCallback(() -> {
                loadMeals(creationDate);
                loadMacros(creationDate);
            });
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Meal Form");
            stage.setScene(scene);
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
