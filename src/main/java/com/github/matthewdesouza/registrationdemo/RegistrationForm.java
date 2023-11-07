package com.github.matthewdesouza.registrationdemo;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Predicate;

import static javafx.beans.binding.Bindings.createBooleanBinding;

/**
 * A JavaFX application that creates a user registration form with input validation.
 *
 * @author Matthew DeSouza
 */
public class RegistrationForm extends Application {

    // Regular expression patterns for validating input fields
    private static final String NAME_PATTERN = "^[A-Za-z]{2,25}$";
    private static final String DOB_PATTERN = "^(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d$";
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@farmingdale.edu$";
    private static final String ZIP_CODE_PATTERN = "^\\d{5}$";

    /**
     * Starts the JavaFX application and sets up the UI.
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Initialize the input fields
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField dobField = new TextField();
        TextField zipCodeField = new TextField();

        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        emailField.setPromptText("Email");
        dobField.setPromptText("Date of Birth (MM/DD/YYYY)");
        zipCodeField.setPromptText("Zip Code");


        // Initialize the "Add" button, it will be enabled based on the form's validity
        Button addButton = new Button("Add");

        // Validation predicates based on regex patterns
        Predicate<String> isNameValid = name -> name.matches(NAME_PATTERN);
        Predicate<String> isDobValid = dob -> dob.matches(DOB_PATTERN);
        Predicate<String> isEmailValid = email -> email.matches(EMAIL_PATTERN);
        Predicate<String> isZipValid = zip -> zip.matches(ZIP_CODE_PATTERN);

        // Create BooleanBindings for each TextField's validation status
        BooleanBinding isFirstNameValid = createBooleanBinding(() -> isNameValid.test(firstNameField.getText()), firstNameField.textProperty());
        BooleanBinding isLastNameValid = createBooleanBinding(() -> isNameValid.test(lastNameField.getText()), lastNameField.textProperty());
        BooleanBinding isEmailFieldValid = createBooleanBinding(() -> isEmailValid.test(emailField.getText()), emailField.textProperty());
        BooleanBinding isDobFieldValid = createBooleanBinding(() -> isDobValid.test(dobField.getText()), dobField.textProperty());
        BooleanBinding isZipCodeFieldValid = createBooleanBinding(() -> isZipValid.test(zipCodeField.getText()), zipCodeField.textProperty());

        // Button is disabled until all BooleanBindings return true
        addButton.disableProperty().bind(isFirstNameValid.not().or(isLastNameValid.not()).or(isEmailFieldValid.not()).or(isDobFieldValid.not()).or(isZipCodeFieldValid.not()));

        // Add validation listeners to text fields
        addValidationListener(firstNameField, NAME_PATTERN);
        addValidationListener(lastNameField, NAME_PATTERN);
        addValidationListener(emailField, EMAIL_PATTERN);
        addValidationListener(dobField, DOB_PATTERN);
        addValidationListener(zipCodeField, ZIP_CODE_PATTERN);

        // Layout the form using a GridPane
        GridPane gridPane = new GridPane();
        // Add all fields and button to the grid
        gridPane.add(firstNameField, 0, 0);
        gridPane.add(lastNameField, 0, 1);
        gridPane.add(emailField, 0, 2);
        gridPane.add(dobField, 0, 3);
        gridPane.add(zipCodeField, 0, 4);
        gridPane.add(addButton, 0, 5);

        // Set the action for the "Add" button
        addButton.setOnAction(event -> {
            // Navigation to new UI would be handled here
            System.out.println("Navigation to new UI");
            VBox root = new VBox();
            root.getChildren().add(new Label("Welcome to the new UI " + firstNameField.getText() + "!" ));
            primaryStage.setScene(new Scene(root, 300, 250));
            primaryStage.setTitle("New UI");
        });

        // Show the stage with the form
        primaryStage.setScene(new Scene(gridPane, 300, 250));
        primaryStage.setTitle("Registration Form");
        primaryStage.show();
    }

    /**
     * Adds a focus listener to a TextField that validates the text against a regex pattern.
     * The border color of the TextField changes based on the validation result.
     * @param textField The TextField to validate.
     * @param pattern The regex pattern to validate the TextField's text against.
     */
    private void addValidationListener(TextField textField, String pattern) {
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (Boolean.FALSE.equals(newValue)) { // Focus lost, validate the field
                boolean isValid = textField.getText().matches(pattern);
                textField.setStyle(isValid ? "-fx-border-color: green;" : "-fx-border-color: red;");
            }
        });
    }

    /**
     * The main method to launch the JavaFX application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}