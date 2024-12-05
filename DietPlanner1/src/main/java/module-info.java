module org.locations.dietplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.locations.dietplanner to javafx.fxml;
    exports org.locations.dietplanner;
}