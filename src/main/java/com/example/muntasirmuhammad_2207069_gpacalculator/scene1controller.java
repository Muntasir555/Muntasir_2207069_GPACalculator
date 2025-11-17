package com.example.muntasirmuhammad_2207069_gpacalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class scene1controller {

    @FXML
    private Button startButton;

    @FXML
    private void goToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        Stage stage = (Stage) startButton.getScene().getWindow();
        Scene scene2 = new Scene(root);
        stage.setScene(scene2);
        stage.setTitle("CGPA Calculator - Enter Courses");
        stage.show();
    }
}
