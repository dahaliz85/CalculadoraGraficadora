module com.biomath3d {
// 1. Requerimos los módulos esenciales de JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Requerimos los módulos que usaremos para el motor y base de datos
    requires java.sql;
    requires exp4j;

    // 2. Exportamos el paquete raíz para que JavaFX pueda lanzar MainApp
    exports com.biomath3d;
    exports com.biomath3d.controller;

    // 3. ¡CRUCIAL! Abrimos el paquete a javafx.fxml para que el FXMLLoader
    // pueda leer e inyectar campos en tu MainController de forma reflexiva.
    opens com.biomath3d to javafx.fxml;
    opens com.biomath3d.controller to javafx.fxml;
}