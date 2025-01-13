package org.locations.dietplanner.Implementation.Builder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonTypeName("RecipeStorage")
public class RecipeStorage  {
    private List<Recipe> recipeList = new ArrayList<>();
    private volatile static RecipeStorage recipeStorage;

    private RecipeStorage(){}

    public static RecipeStorage getInstance() {
        if(recipeStorage == null){
            synchronized (RecipeStorage.class){
                if(recipeStorage == null){
                    recipeStorage = new RecipeStorage();
                }
            }
        }
        return  recipeStorage;
    }
    @JsonCreator
    private static RecipeStorage create(
            @JsonProperty("recipes") List<Recipe> recipes
    ) {
        RecipeStorage instance = getInstance();
        instance.recipeList = recipes;
        return instance;
    }

    public Recipe getRecipe(String name){
        for (Recipe recipe : recipeList) {
            if(recipe.getName().equals(name)){
                return recipe;
            }
        }
        throw new NoSuchElementException("No such recipe in storage");
    }
    public void addRecipe(Recipe recipe){
        recipeList.add(recipe);
    }
    public void editRecipe(Recipe recipe,String recipeText){
        RecipeBuilder recipeBuilder = new RecipeBuilder();
        recipeBuilder.editRecipe(recipe,recipeText);
    }
    public void removeRecipe(Recipe recipe){
        recipeList.remove(recipe);
    }
    public List<Recipe> getRecipeList(){
        return this.recipeList;
    }
    protected Object readResolve(){
        return getInstance();
    }
}
