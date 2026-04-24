module com.biomath3d {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Para SQLite según el Diccionario de Datos
    requires exp4j;    // Para el motor Engine

    opens com.biomath3d.ui to javafx.graphics, javafx.fxml;
    exports com.biomath3d.ui;
}