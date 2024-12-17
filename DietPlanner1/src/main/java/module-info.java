module org.locations.dietplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens org.locations.dietplanner to javafx.fxml;
    exports org.locations.dietplanner;
}