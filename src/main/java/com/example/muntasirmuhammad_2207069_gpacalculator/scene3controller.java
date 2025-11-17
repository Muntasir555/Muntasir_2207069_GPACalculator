package com.example.muntasirmuhammad_2207069_gpacalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class scene3controller {

    @FXML private TableView<CourseRow> tableView;

    @FXML private TableColumn<CourseRow, String> colName;
    @FXML private TableColumn<CourseRow, String> colCode;
    @FXML private TableColumn<CourseRow, String> colGrade;
    @FXML private TableColumn<CourseRow, Double> colCredit;
    @FXML private TableColumn<CourseRow, Double> colPoint;
    @FXML private TableColumn<CourseRow, Double> colWeighted;

    @FXML private Text gpaText;
    @FXML private Text usedCreditText; // optional: show how many credits were used in GPA

    @FXML
    public void initialize() {
        colName.setCellValueFactory(v -> v.getValue().nameProperty());
        colCode.setCellValueFactory(v -> v.getValue().codeProperty());
        colGrade.setCellValueFactory(v -> v.getValue().gradeProperty());
        colCredit.setCellValueFactory(v -> v.getValue().creditProperty().asObject());
        colPoint.setCellValueFactory(v -> v.getValue().pointProperty().asObject());
        colWeighted.setCellValueFactory(v -> v.getValue().weightedProperty().asObject());
    }

    /**
     * @param courses All entered courses (will be displayed).
     * @param gpa Calculated GPA (based on MAX_CREDIT).
     * @param maxCredit the MAX_CREDIT used for calculation (optional; used for display)
     */
    public void setResults(List<scene2controller.Course> courses, double gpa, double maxCredit) {

        ObservableList<CourseRow> rows = FXCollections.observableArrayList();

        for (scene2controller.Course c : courses) {
            rows.add(new CourseRow(
                    c.getName(),
                    c.getCode(),
                    c.getGrade(),
                    c.getCredit(),
                    c.getGradePoint(),
                    c.getGradePoint() * c.getCredit()
            ));
        }

        tableView.setItems(rows);
        gpaText.setText("Your CGPA: " + String.format("%.2f", gpa));
        if (usedCreditText != null) {
            usedCreditText.setText("Credits used for CGPA: " + (int)maxCredit);
        }
    }

    @FXML
    private void goBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("CGPA Calculator");
        stage.show();
    }

    public static class CourseRow {

        private final javafx.beans.property.SimpleStringProperty name;
        private final javafx.beans.property.SimpleStringProperty code;
        private final javafx.beans.property.SimpleStringProperty grade;
        private final javafx.beans.property.SimpleDoubleProperty credit;
        private final javafx.beans.property.SimpleDoubleProperty point;
        private final javafx.beans.property.SimpleDoubleProperty weighted;

        public CourseRow(String name, String code, String grade,
                         double credit, double point, double weighted) {

            this.name = new javafx.beans.property.SimpleStringProperty(name);
            this.code = new javafx.beans.property.SimpleStringProperty(code);
            this.grade = new javafx.beans.property.SimpleStringProperty(grade);
            this.credit = new javafx.beans.property.SimpleDoubleProperty(credit);
            this.point = new javafx.beans.property.SimpleDoubleProperty(point);
            this.weighted = new javafx.beans.property.SimpleDoubleProperty(weighted);
        }

        public javafx.beans.property.StringProperty nameProperty() { return name; }
        public javafx.beans.property.StringProperty codeProperty() { return code; }
        public javafx.beans.property.StringProperty gradeProperty() { return grade; }
        public javafx.beans.property.DoubleProperty creditProperty() { return credit; }
        public javafx.beans.property.DoubleProperty pointProperty() { return point; }
        public javafx.beans.property.DoubleProperty weightedProperty() { return weighted; }
    }
}
