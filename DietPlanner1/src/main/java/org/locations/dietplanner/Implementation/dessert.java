package org.locations.dietplanner.Implementation;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

public class dessert implements IMeal, IMealsGroup {
    private Recipe recipe;
    public dessert(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public Double calculateCalories(){
        return null;
    }

    @Override
    public Double calculateFat(){
        return null;
    }

    @Override
    public Double calculateProtein(){
        return null;
    }

    @Override
    public Double calculateCarb(){
        return null;
    }
}
