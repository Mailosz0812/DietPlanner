module org.locations.dietplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens org.locations.dietplanner.Implementation.Composite to com.fasterxml.jackson.databind;
    opens org.locations.dietplanner.Implementation.mealBuilder to com.fasterxml.jackson.databind;
    opens org.locations.dietplanner.Implementation.Builder to com.fasterxml.jackson.databind;
    opens org.locations.dietplanner to javafx.fxml;

    exports org.locations.dietplanner.Implementation;
    exports org.locations.dietplanner;
    exports org.locations.dietplanner.Implementation.Composite;
}