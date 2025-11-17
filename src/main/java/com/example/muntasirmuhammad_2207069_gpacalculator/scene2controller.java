package com.example.muntasirmuhammad_2207069_gpacalculator;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class scene2controller {

    // FXML fields
    @FXML private TextField nameField, codeField, creditField, teacher1Field, teacher2Field;
    @FXML private TextField maxCreditField; // New field
    @FXML private ComboBox<String> gradeBox;
    @FXML private Button calculateGpaButton;

    // Course list
    private final List<Course> courses = new ArrayList<>();

    // Max credit and current credit tracker
    private double maxCredit = 12.0;
    private double currentCredit = 0;

    // Initialize method
    @FXML
    public void initialize() {
        calculateGpaButton.setDisable(true);

        gradeBox.setItems(FXCollections.observableArrayList(
                "A+", "A", "A-", "B+", "B", "B-", "C", "D", "F"
        ));
    }

    // Add course
    @FXML
    private void addCourse() {
        try {
            // Read max credit if provided
            String maxCreditText = maxCreditField.getText().trim();
            if (!maxCreditText.isEmpty()) {
                try {
                    maxCredit = Double.parseDouble(maxCreditText);
                    if (maxCredit <= 0) {
                        showAlert("Error", "Max Credit must be positive.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "Enter a valid number for Max Credit.");
                    return;
                }
            }

            // Read course inputs
            String name = nameField.getText().trim();
            String code = codeField.getText().trim();
            String creditText = creditField.getText().trim();
            String teacher1 = teacher1Field.getText().trim();
            String teacher2 = teacher2Field.getText().trim();
            String grade = gradeBox.getValue();

            if (name.isEmpty() || code.isEmpty() || grade == null || creditText.isEmpty()) {
                showAlert("Error", "Course Name, Code, Credit and Grade are required.");
                return;
            }

            double credit = Double.parseDouble(creditText);
            if (credit <= 0) {
                showAlert("Error", "Credit must be positive.");
                return;
            }

            // Check if adding exceeds max credit
            if (currentCredit + credit > maxCredit) {
                calculateGpaButton.setDisable(false);

                showAlert("Max Credit Reached",
                        "Total credit cannot exceed " + maxCredit +
                                ".\nYou may now calculate GPA.");
                return;
            }
            Course newCourse = new Course(name, code, credit, teacher1, teacher2, grade);
            courses.add(newCourse);
            currentCredit += credit;

            clearFields();
            if (currentCredit >= maxCredit) {
                calculateGpaButton.setDisable(false);
            }

        } catch (NumberFormatException e) {
            showAlert("Error", "Enter a valid number for credit.");
        }
    }


    @FXML
    private void calculateGPA() throws IOException {

        double remaining = maxCredit;
        double totalPoints = 0;

        for (Course c : courses) {
            if (remaining <= 0) break;

            double useCredit = Math.min(c.getCredit(), remaining);
            totalPoints += c.getGradePoint() * useCredit;
            remaining -= useCredit;
        }

        double gpa = totalPoints / maxCredit;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
        Scene scene3 = new Scene(loader.load());

        scene3controller controller = loader.getController();
        controller.setResults(courses, gpa, maxCredit);

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene3);
        stage.setTitle("CGPA Calculator - Results");
        stage.show();
    }


    private void clearFields() {
        nameField.clear();
        codeField.clear();
        creditField.clear();
        teacher1Field.clear();
        teacher2Field.clear();
        gradeBox.getSelectionModel().clearSelection();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static class Course {
        private final String name, code, teacher1, teacher2, grade;
        private final double credit;

        public Course(String name, String code, double credit,
                      String teacher1, String teacher2, String grade) {
            this.name = name;
            this.code = code;
            this.credit = credit;
            this.teacher1 = teacher1;
            this.teacher2 = teacher2;
            this.grade = grade;
        }

        public double getCredit() { return credit; }
        public String getName() { return name; }
        public String getCode() { return code; }
        public String getGrade() { return grade; }
        public String getTeacher1() { return teacher1; }
        public String getTeacher2() { return teacher2; }

        public double getGradePoint() {
            switch (grade) {
                case "A+": case "A": return 4.0;
                case "A-": return 3.7;
                case "B+": return 3.3;
                case "B":  return 3.0;
                case "B-": return 2.7;
                case "C":  return 2.0;
                case "D":  return 1.0;
                case "F":  return 0.0;
            }
            return 0.0;
        }
    }
}
