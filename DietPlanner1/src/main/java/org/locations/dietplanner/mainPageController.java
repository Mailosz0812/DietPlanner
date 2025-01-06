
package org.locations.dietplanner;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.locations.dietplanner.Implementation.command.ExportFromJSONCommand;
import org.locations.dietplanner.Implementation.command.ImportToJSONCommand;
import org.locations.dietplanner.Implementation.command.MemoryService;
import org.locations.dietplanner.Interfaces.ICommand;
import org.locations.dietplanner.Interfaces.IMealsGroup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class mainPageController {

    @FXML
    private HBox HBoxFooter;

    @FXML
    private Button arrow_left;

    @FXML
    private Button arrow_right;

    @FXML
    private HBox breakfastContainer;

    @FXML
    private HBox buttonContainer;

    @FXML
    private HBox dessertContainer;

    @FXML
    private HBox dinnerContainer;

    @FXML
    private Button importButton;

    @FXML
    private ImageView logo;

    @FXML
    private HBox lunchContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox supperContainer;

    @FXML
    private AnchorPane toolBarLeft;

    @FXML
    private ToolBar toolBarUpper;
    private Button lastClickedButton;
    private ICommand command;

    private final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private final DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE",new Locale("en","EN"));
    @FXML
    public void initialize() {

        TypeReference<List<IMealsGroup>> typeReference = new TypeReference<List<IMealsGroup>>() {};
        command = new ImportToJSONCommand("meals.json",typeReference);
        command.execute();
        buttonContainer.setStyle("-fx-background-color: #f6d646");
        scrollPane.setStyle("-fx-background-color: #f6d646");
        final LocalDate[] currentDate = {LocalDate.now()};
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
        LocalDate firstDayOfWeek = startDate.with(java.time.DayOfWeek.MONDAY);
        for (int i = 0; i < 7; i++) {
            LocalDate day = firstDayOfWeek.plusDays(i);
            System.out.println(day);
            Button button = new Button();
            String text = day.format(dayFormatter) + "\n" + day.format(dayOfWeekFormatter);
            button.setText(text);
            button.setStyle("-fx-background-color: #f6d646;-fx-text-alignment: center; -fx-alignment: center;-fx-padding: 10px;");
            button.setMinWidth(50.0);
            button.setMinHeight(50.0);
            button.setOnAction(actionEvent -> {
                if (lastClickedButton != null) {
                    lastClickedButton.setStyle("-fx-background-color: #f6d646; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");
                }

                button.setStyle("-fx-background-color: #f64646; -fx-text-alignment: center; -fx-alignment: center; -fx-padding: 10px;");

                lastClickedButton = button;
                loadMeals(day);

            });
            buttonContainer.getChildren().add(button);
        }
    }
    private void loadMeals(LocalDate currentDate){



    }
}
