package org.locations.dietplanner.Implementation;

import org.locations.dietplanner.Implementation.Builder.Recipe;
import org.locations.dietplanner.Interfaces.IMeal;
import org.locations.dietplanner.Interfaces.IMealsGroup;

public class supper implements IMeal, IMealsGroup {
    //Kompozycja z Recipe albo OptionalRecipe
    private Recipe recipe;

    public supper(Recipe recipe){
        this.recipe = recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

}
