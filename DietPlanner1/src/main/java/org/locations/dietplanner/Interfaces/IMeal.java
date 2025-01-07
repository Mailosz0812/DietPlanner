package org.locations.dietplanner.Interfaces;


import org.locations.dietplanner.Implementation.Builder.Recipe;

import java.time.LocalDate;

public interface IMeal {
    Recipe getRecipe();
    void setRecipe(Recipe recipe);
    void setDay(String date);
    LocalDate getDay();
}
