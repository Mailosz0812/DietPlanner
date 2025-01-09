
package org.locations.dietplanner;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.locations.dietplanner.Implementation.MealType;
import org.locations.dietplanner.Implementation.command.ExportFromJSONCommand;
import org.locations.dietplanner.Implementation.command.ImportToJSONCommand;
import org.locations.dietplanner.Implementation.command.MemoryService;
import org.locations.dietplanner.Implementation.mealBuilder.Meal;
import org.locations.dietplanner.Interfaces.ICommand;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class mainPageController {
    @FXML
    private AnchorPane AnchorPane_Left;

    @FXML
    private HBox HBoxFooter;

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
    private Button arrow_left;

    @FXML
    private Button arrow_right;

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
    private HBox hbox_karuzela;

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
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane supperAnchor;

    @FXML
    private HBox supperContainer;

    private Button lastClickedButton;
    private ICommand command;
    private IMealsGroup mealsGroups;

    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE",new Locale("en","EN"));
    private HashMap<MealType, AnchorPane> mealsContainers;

    @FXML
    public void initialize() {
        TypeReference<IMealsGroup> typeReference = new TypeReference<IMealsGroup>() {};
        command = new ImportToJSONCommand("meals.json",typeReference);
        mealsGroups = (IMealsGroup) command.execute();
        System.out.println(mealsGroups.toStringGroups());
        buttonContainer.setStyle("-fx-background-color: #f6d646");
        scrollPane.setStyle("-fx-background-color: #f6d646");
        final LocalDate[] currentDate = {LocalDate.now()};
        mealsContainers = new HashMap<>();
        mealsContainers.put(MealType.BREAKFAST, breakfastAnchor);
        mealsContainers.put(MealType.LUNCH, lunchAnchor);
        mealsContainers.put(MealType.DINNER, dinnerAnchor);
        mealsContainers.put(MealType.DESSERT, dessertAnchor);
        mealsContainers.put(MealType.SUPPER,supperAnchor);
        loadWeek(currentDate[0]);

        arrow_left.setOnAction(event -> {
            System.out.println("W lewo");
            currentDate[0] = currentDate[0].minusWeeks(1);
            loadWeek(currentDate[0]);
        });
        arrow_right.setOnAction(event -> {
            System.out.println("W prawo");
            currentDate[0] = currentDate[0].plusWeeks(1);
            loadWeek(currentDate[0]);
        });
    }
    private void loadWeek(LocalDate startDate){
        buttonContainer.getChildren().clear();
        LocalDate firstDayOfWeek = startDate.with(DayOfWeek.MONDAY);
        for (int i = 0; i < 7; i++) {
            LocalDate day = firstDayOfWeek.plusDays(i);
            System.out.println(day);
            Button button = new Button();
            String text = day.format(dayFormatter) + "\n" + day.format(dayOfWeekFormatter);
            button.setText(text);
            if(day.equals(startDate)){
                lastClickedButton = button;
                button.setStyle("-fx-background-color: #f64646; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                loadMeals(startDate);
                loadMacros(startDate);
            }
            else {
                button.setStyle("-fx-background-color: #f6d646;-fx-text-alignment: center; -fx-alignment: center;-fx-padding: 10px;");
                button.setMinWidth(50.0);
                button.setMinHeight(50.0);
            }
                button.setOnAction(actionEvent -> {
                    lastClickedButton.setStyle("-fx-background-color: #f6d646; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    button.setStyle("-fx-background-color: #f64646; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    lastClickedButton = button;
                    loadMeals(day);
                    loadMacros(day);
                });
            buttonContainer.getChildren().add(button);
        }
    }
    private void loadMeals(LocalDate currentDate){
        List<IMeal> meals = mealsGroups.getMealByDate(currentDate);
            mealsContainers.values().forEach(container -> container.getChildren().clear());
            for (IMeal meal : meals) {
                MealType mealType = meal.getRecipe().getMealType();
                AnchorPane container = mealsContainers.get(mealType);
                Label mealLabel = new Label(formatMealInfo(meal));
                container.getChildren().add(mealLabel);
            }
            mealsContainers.values().forEach(container -> {
                if(container.getChildren().isEmpty()){
                    addingMealHandler(container);
                }
            });


    }
    private void addingMealHandler(AnchorPane container){
        Button button = new Button("Add Meal");
        container.getChildren().add(button);
        button.setOnAction(actionEvent -> {
            openPopupHandler();
        });
    }
    private String formatMealInfo(IMeal meal) {
        return meal.getRecipe().getName() + "\n" +
                meal.getRecipe().getRecipeText() + "\n" +
                "Kalorie: " + meal.getRecipe().getIngredientList().stream().mapToDouble(Ingredient::getCalories).sum();
    }
    private void loadMacros(LocalDate day){
        caloriesContainer.getChildren().clear();
        proteinContainer.getChildren().clear();
        fatsContainer.getChildren().clear();
        carbsContainer.getChildren().clear();
        List<IMeal> meals = mealsGroups.getMealByDate(day);
        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbs = 0;
        for (IMeal meal : meals) {
            Recipe currentRecipe = meal.getRecipe();
            calories+=currentRecipe.calculateCalories();
            proteins+=currentRecipe.calculateProtein();
            fats+=currentRecipe.calculateFat();
            carbs+=currentRecipe.calculateCarb();
        }
        caloriesContainer.getChildren().addAll(new Label("Calories"),new Label("\n"+calories));
        proteinContainer.getChildren().addAll(new Label("Proteins"),new Label("\n"+proteins));
        fatsContainer.getChildren().addAll(new Label("Fats"),new Label("\n"+fats));
        carbsContainer.getChildren().addAll(new Label("Carbs"),new Label("\n"+carbs));
    }
    private void openPopupHandler(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),784,505 );
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Meal Form");
            stage.setScene(scene);
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
