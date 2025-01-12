package org.locations.dietplanner;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.locations.dietplanner.Implementation.Composite.MealService;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonTypeName("listWrapper")
public class mealListWrapper {
    private List<MealService> mealServiceList;

    public mealListWrapper() {}

    public mealListWrapper(List<MealService> mealServiceList) {
        this.mealServiceList = mealServiceList;
    }

    public List<MealService> getMealServiceList() {
        return mealServiceList;
    }

    public void setMealServiceList(List<MealService> mealServiceList) {
        this.mealServiceList = mealServiceList;
    }
}