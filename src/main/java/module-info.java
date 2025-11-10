module com.example.muntasirmuhammad_2207069_gpacalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.muntasirmuhammad_2207069_gpacalculator to javafx.fxml;
    exports com.example.muntasirmuhammad_2207069_gpacalculator;
}