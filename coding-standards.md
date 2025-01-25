# Coding Standards

This document outlines the coding standards and guidelines to be followed when developing in this project. These practices ensure consistency, maintainability, and readability across the codebase.

## Naming Conventions

### 1. Variable and Method Names

- **snake_case** is the preferred naming convention for variables and methods.
- Example:
    - Correct: `user_name`, `calculate_total_price()`
    - Incorrect: `userName`, `calculateTotalPrice()`

### 2. Constants

- Constants should be written in **UPPER_SNAKE_CASE**.
- Example:
    - Correct: `MAX_USERS`, `API_URL`
    - Incorrect: `maxUsers`, `apiUrl`

### 3. Class and Interface Names

- Use **CamelCase** for class and interface names, starting with an uppercase letter.
- Example:
    - Correct: `UserManager`, `ProductService`
    - Incorrect: `user_manager`, `product_service`

## Indentation and Spacing

### 1. Indentation

- **Tabs with 4 spaces** are the preferred indentation style.
    - If your editor uses spaces for indentation, set it to 4 spaces per indentation level.
    - If your editor uses tabs, configure it to display as 4 spaces.
- This ensures consistency across different editors and development environments.

### 2. Line Length

- Limit line length to **80 characters**. This improves readability, especially in terminal environments.

### 3. Blank Lines

- Use blank lines to separate logical blocks of code (e.g., functions, classes).

## Code Organization

### 1. Group Imports

- Group imports into standard library imports, third-party library imports, and project-specific imports.
- Ensure that imports are ordered alphabetically within each group.
- In Java, always use the following order:
    1. Java standard libraries (e.g., `java.util.*`)
    2. External libraries (e.g., `org.springframework.*`)
    3. Project-specific imports (e.g., `com.myproject.model.*`)

### 2. File Structure

- Organize files by their function within the project:
    - `controllers/` for handling requests
    - `models/` for data models
    - `services/` for business logic
    - `utils/` for utility functions

## Comments and Documentation

### 1. Javadoc Documentation

- Java classes, methods, and fields should be documented using **Javadoc**.
- Always include the purpose of the class or method, parameters, and return values.
- Example:
  ```java
  /**
   * Calculates the total price based on the unit price and quantity.
   *
   * @param price The price per unit
   * @param quantity The quantity of items
   * @return The total price
   */
  public double calculateTotalPrice(double price, int quantity) {
      return price * quantity;
  }
