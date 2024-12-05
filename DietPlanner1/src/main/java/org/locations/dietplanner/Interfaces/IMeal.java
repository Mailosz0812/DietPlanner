package org.locations.dietplanner.Interfaces;

import org.locations.dietplanner.Implementation.Builder.Recipe;

public interface IMeal {
    Double calculateCalories();
    Double calculateFat();
    Double calculateCarb();
    Double calculateProtein();
}
