
package org.locations.dietplanner;

import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import org.locations.dietplanner.Interfaces.ICommand;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @FXML
    private Button addMealButton;

    @FXML
    private Button addRecipeButton;

    @FXML
    private Button summaryButton;

    private Button lastClickedButton;
    private MemoryCommandInvoker invoker = new MemoryCommandInvoker();
    private MemoryCommandInvoker storageInvoker = new MemoryCommandInvoker();
    private MealService mainMealService;
    private LocalDate creationDate;
    private RecipeStorage storage;


    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE",new Locale("en","EN"));
    private HashMap<MealType, AnchorPane> mealsContainers;

    @FXML
    public void initialize() {
        final LocalDate[] currentDate = {LocalDate.now()};

        TypeReference<MealService> typeReference = new TypeReference<MealService>() {};
        invoker.setCommand(new ImportFromJSONCommand<MealService>("meals.json", typeReference));
        mainMealService = invoker.executeCommand();

        TypeReference<RecipeStorage> recipeStorageTypeReference = new TypeReference<RecipeStorage>() {};
        storageInvoker.setCommand(new ImportFromJSONCommand<RecipeStorage>("recipes.json", recipeStorageTypeReference));
        storage = storageInvoker.executeCommand();

        if(mainMealService == null){
            mainMealService = new MealService();
        }
        if(storage == null){
            storage = RecipeStorage.getInstance();
        }
        buttonContainer.setStyle("-fx-background-color: #44e38d");
        HBox_Content_Top_ScrollPane.setStyle("-fx-background-color: #44e38d");

        mealsContainers = new HashMap<>();
        mealsContainers.put(MealType.BREAKFAST, breakfastAnchor);
        mealsContainers.put(MealType.LUNCH, lunchAnchor);
        mealsContainers.put(MealType.DINNER, dinnerAnchor);
        mealsContainers.put(MealType.DESSERT, dessertAnchor);
        mealsContainers.put(MealType.SUPPER,supperAnchor);

        loadWeek(currentDate[0]);
        creationDate = currentDate[0];


        //Top toolbar buttons init
        saveButton.setOnAction(actionEvent -> {
            invoker.setCommand(new ExportToJSONCommand<>("meals.json", mainMealService));
            invoker.executeCommand();
            storageInvoker.setCommand(new ExportToJSONCommand<>("recipes.json",storage));
            storageInvoker.executeCommand();
        });
        exportButton.setOnAction(actionEvent -> {
            StringBuilder builder = new StringBuilder();
            builder.append("/C:\\Users\\Czarny\\Desktop\\");
            builder.append("MealsExport");
            builder.append(currentDate[0]);
            builder.append(".json");
            invoker.setCommand(new ExportToJSONCommand<>(builder.toString(),mainMealService));
            invoker.executeCommand();
            builder.replace(builder.indexOf("M"),builder.lastIndexOf("t")+1,"RecipeExport");
            storageInvoker.setCommand(new ExportToJSONCommand<>(builder.toString(),storage));
            storageInvoker.executeCommand();
        });


        HBox_Content_Top_Arrow_Left.setOnAction(event -> {
            currentDate[0] = currentDate[0].minusWeeks(1);
            creationDate = currentDate[0];
            loadWeek(currentDate[0]);
        });
        HBox_Content_Top_Arrow_Right.setOnAction(event -> {
            currentDate[0] = currentDate[0].plusWeeks(1);
            creationDate = currentDate[0];
            loadWeek(currentDate[0]);
        });
        addMealButton.setOnAction(actionEvent -> openPopupHandler(mainMealService.getMealServiceByDate(creationDate)));
        addRecipeButton.setOnAction(actionEvent -> openPopupRecipeHandler());
        summaryButton.setOnAction(actionEvent -> {
            openPopupSummaryHandler();
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
                button.setStyle("-fx-background-color: #f2d457; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                loadMeals(startDate);
                loadMacros(startDate);
            }
            else {
                button.setStyle("-fx-background-color: #44e38d;-fx-text-alignment: center; -fx-alignment: center;-fx-padding: 10px;");
                button.setMinWidth(50.0);
                button.setMinHeight(50.0);
            }
                button.setOnAction(actionEvent -> {
                    lastClickedButton.setStyle("-fx-background-color: #44e38d; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    button.setStyle("-fx-background-color: #f2d457; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                    lastClickedButton = button;
                    creationDate = day;
                    loadMeals(day);
                    loadMacros(day);
                });

            buttonContainer.getChildren().add(button);
        }
    }
    private void loadMeals(LocalDate currentDate) {
        mealsContainers.values().forEach(container -> container.getChildren().clear());

        MealService mealService = mainMealService.getMealServiceByDate(currentDate);
        List<IMeal> meals = mealService.getMealByDate(currentDate);

        for (MealType mealType : MealType.values()) {
            AnchorPane container = mealsContainers.get(mealType);

            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10, 10, 10, 10));

            Label mealTypeLabel = new Label(mealType.name());
            mealTypeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
            vbox.getChildren().add(mealTypeLabel);

            meals.stream()
                    .filter(meal -> meal.getRecipe().getMealType() == mealType)
                    .forEach(meal -> {
                        Label mealLabel = new Label(formatMealInfo(meal));
                        mealLabel.setWrapText(true);
                        mealLabel.setMaxWidth(600);

                        Button removeButton = new Button("Remove");
                        removeButton.setOnAction(actionEvent -> removingMealHandler(container, mealType));

                        vbox.getChildren().addAll(mealLabel, removeButton);
                    });
            container.getChildren().add(vbox);
        }
    }
    private void removingMealHandler(AnchorPane container,MealType mealType){
        MealService mealServiceByDate = mainMealService.getMealServiceByDate(creationDate);
        List<IMeal> meals = mealServiceByDate.getMealByDate(creationDate);
        for (IMeal meal : meals) {
            if(meal.getRecipe().getMealType().equals(mealType)){
                mealServiceByDate.removeMealsGroup((IMealsGroup) meal);
            }
        }
        container.getChildren().clear();
        loadMacros(creationDate);
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
        MealService mealService = mainMealService.getMealServiceByDate(day);
        List<IMeal> meals = mealService.getMealByDate(day);
        if(!meals.isEmpty()) {
            for (IMeal meal : meals) {
                Recipe currentRecipe = meal.getRecipe();
                calories += currentRecipe.calculateCalories();
                proteins += currentRecipe.calculateProtein();
                fats += currentRecipe.calculateFat();
                carbs += currentRecipe.calculateCarb();
            }
        }
        Label caloriesLabel = new Label("Calories");
        Label caloriesValueLabel = new Label("" + String.format("%.2f",calories)  + " kcal");
        caloriesLabel.setStyle("-fx-font-weight: bold;");

        Label proteinLabel = new Label("Proteins");
        Label proteinValueLabel = new Label("" + String.format("%.2f",proteins)  + " g");
        proteinLabel.setStyle("-fx-font-weight: bold;");

        Label fatsLabel = new Label("Fat");
        Label fatsValueLabel = new Label("" + String.format("%.2f",fats)  + " g");
        fatsLabel.setStyle("-fx-font-weight: bold;");

        Label carbsLabel = new Label("Carbs");
        Label carbsValueLabel = new Label("" + String.format("%.2f",carbs) + " g");
        carbsLabel.setStyle("-fx-font-weight: bold;");

        caloriesContainer.getChildren().addAll(caloriesLabel, caloriesValueLabel);
        proteinContainer.getChildren().addAll(proteinLabel, proteinValueLabel);
        fatsContainer.getChildren().addAll(fatsLabel, fatsValueLabel);
        carbsContainer.getChildren().addAll(carbsLabel, carbsValueLabel);
    }
    private void openPopupHandler(MealService mealService){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),917,597 );
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            popupController controller = fxmlLoader.getController();
            controller.setMealsGroup(mealService);
            controller.setCreationDate(creationDate);
            controller.setOnMealAddedCallback(() -> {
                loadMeals(creationDate);
                loadMacros(creationDate);
            });
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Meal Form");
            stage.setScene(scene);
            stage.setMinWidth(917);
            stage.setMinHeight(597);
            stage.setMaxWidth(917);
            stage.setMaxHeight(597);
            stage.setResizable(false);
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.show();
        } catch (IOException e) {
            System.out.println("cos sie stalo"+e.getMessage());
        }
    }
    private void openPopupRecipeHandler(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recipePopup.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 784, 505);
            Stage stage = new Stage();
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Recipe Form");
            stage.setScene(scene);
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void openPopupSummaryHandler(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("summaryPopup.fxml"));
            fxmlLoader.setControllerFactory(param -> {
                summaryPopupController controller = new summaryPopupController();
                controller.setService(mainMealService.getMealServiceByDate(creationDate));
                return controller;
            });
            Scene scene = new Scene(fxmlLoader.load(),784,505);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Week Summary");
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.show();

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
