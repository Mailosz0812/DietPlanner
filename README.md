# DietPlanner

## Overview
DietPlanner is a Java-based application designed to help users manage their diet plans efficiently. It allows users to add meals, track nutritional values, and organize recipes. The project was developed as part of a group practical class on **design patterns**.

## Features
- **Meal Planning** – Add and categorize meals (Breakfast, Lunch, Dinner, Supper, Dessert).
- **Recipe Management** – Store, edit, and delete recipes.
- **Nutritional Tracking** – Calculate and display total calories, carbohydrates, proteins, and fats.
- **Export & Save** – Save meal plans and export them.
- **User-Friendly Interface** – Easy navigation with an intuitive UI.

## Design Patterns Used
The application follows software design principles and incorporates the following **design patterns**:

1. **Builder Pattern** – Used for constructing meal objects in a step-by-step manner.
2. **Command Pattern** – Implements undo/redo functionality for meal management actions.
3. **Composite Pattern** – Organizes meals and ingredients in a hierarchical structure.
4. **Singleton Pattern** – Manages global application state (e.g., user settings, database connection).

## Technology Stack
- **Language:** Java
- **GUI Framework:** JavaFX
- **Build Tool:** Maven

## Installation & Setup
### Prerequisites
- Windows OS (for .exe execution)
- Java Runtime Environment (JRE 11 or later) must be installed

### Steps
1. Download the `.exe` file.
2. Ensure **Java Runtime Environment (JRE)** is installed on your system.
3. Run the application by double-clicking `DietPlanner.exe`.
4. If any issues occur, check if required dependencies are installed or try running the `.exe` via the command prompt.
   ```sh
   DietPlanner.exe
   ```

## Usage Guide
1. **Adding Meals**
   - Click on "Add Meal" to input a new meal.
   - Select meal type (Breakfast, Lunch, Dinner, Supper, Dessert).
   - Enter meal details and nutritional information.

2. **Managing Recipes**
   - Navigate to the "Recipes" section.
   - Add, edit, or delete stored recipes.

3. **Tracking Nutrition**
   - View total calories, carbs, proteins, and fats at the bottom of the screen.

4. **Saving & Exporting**
   - Click "Save" to store meal plans.
   - Click "Export" to generate a file for external use.
