package com.biomath3d.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import java.net.URL;

public class AlertaUtils {

    // El método original para errores que ya tienes en la captura...

    /**
     * Sobrecarga para permitir otros tipos de alertas (Advertencias, Información)
     * manteniendo el mismo estilo CSS personalizado del proyecto.
     */
    public static void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(Constants.ALERTA_TITULO_VALIDACION);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        try {
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("mi-alerta-personalizada");
            URL cssURL = Thread.currentThread().getContextClassLoader().getResource("com/biomath3d/ui/styles/window.css");
            if (cssURL != null) {
                dialogPane.getStylesheets().add(cssURL.toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar estilo CSS a la alerta: " + e.getMessage());
        }

        alert.showAndWait();
    }
}