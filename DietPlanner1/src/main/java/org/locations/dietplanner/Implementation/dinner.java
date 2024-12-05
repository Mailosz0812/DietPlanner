package org.locations.dietplanner.Implementation;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

public class dinner implements IMeal, IMealsGroup {
    //Kompozycja z Recipe albo OptionalRecipe
    private Recipe recipe;
    public dinner(Recipe recipe){
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
