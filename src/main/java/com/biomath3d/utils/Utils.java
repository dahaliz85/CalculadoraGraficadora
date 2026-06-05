package com.biomath3d.utils;

import javafx.scene.control.Alert;

public class Utils {


    /**
     * Valida de forma estricta si la expresión ingresada cumple con la sintaxis matemática
     * requerida por BioMath 3D (CU-01).
     * * @param expresión Cadena de texto ingresada por el usuario.
     * @return true si la sintaxis es válida, false en caso contrario.
     */
    public static boolean esFuncionValida(String expresion) {
        if (expresion == null || expresion.trim().isEmpty()) {
            return false;
        }
        String exprLimpia = expresion.trim().toLowerCase();

        // 1. Validar que no contenga texto común o palabras prohibidas
        // Permitimos nombres de funciones matemáticas estándar: sin, cos, tan, log, exp, sqrt, pi
        // Agregamos en el segundo replaceAll las letras 'y', 'z' y el operador '=' para soportar "z ="
        String verificacion = exprLimpia
                .replaceAll("sin|cos|tan|log|exp|sqrt|pi", "")
                .replaceAll("[0-9xyz\\+=\\-\\*/\\^\\(\\)\\.\\s]", ""); // <-- Agregadas 'y', 'z' y '='

        // Si después de quitar lo permitido aún quedan letras extrañas, la función no es válida
        if (!verificacion.isEmpty()) {
            return false;
        }

        // 2. Validación de Balanceo de Paréntesis
        int parentesis = 0;
        for (int i = 0; i < exprLimpia.length(); i++) {
            char caracter = exprLimpia.charAt(i);
            if (caracter == '(') {
                parentesis++;
            } else if (caracter == ')') {
                parentesis--;
            }
            if (parentesis < 0) {
                return false;
            }
        }

        return parentesis == 0;
    }

    /**
     * Método auxiliar para desplegar alertas nativas en JavaFX en caso de error
     */
    public static void mostrarAlertaError(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("BioMath 3D - Validación");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        // Envolvemos el tuning del CSS en un try-catch para que si la ruta falla,
        // el Alert de todos modos aparezca en la pantalla.
        try {
            javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("mi-alerta-personalizada");
            // CORREGIDO: Apuntamos de forma directa a la raíz de resources usando el ClassLoader global
            java.net.URL cssURL = Thread.currentThread().getContextClassLoader().getResource("com/biomath3d/ui/styles/window.css");
            if (cssURL != null) {
                dialogPane.getStylesheets().add(cssURL.toExternalForm());
            } else {
                System.out.println("Advertencia: No se encontró window.css en resources.");
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar estilo CSS a la alerta: " + e.getMessage());
        }
        // Este método SIEMPRE se debe ejecutar al final
        alert.showAndWait();
    }

    public static void mostrarAlerta(javafx.scene.control.Alert.AlertType tipo, String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(tipo);
        alert.setTitle(Constants.ALERTA_TITULO_VALIDACION);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);

        // Mismo tuning de CSS que ya programaste con tu try-catch
        try {
            javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("mi-alerta-personalizada");
            java.net.URL cssURL = Thread.currentThread().getContextClassLoader().getResource("com/biomath3d/ui/styles/window.css");
            if (cssURL != null) {
                dialogPane.getStylesheets().add(cssURL.toExternalForm());
            }
        } catch (Exception e) {
            System.out.println("Error al aplicar estilo CSS a la alerta: " + e.getMessage());
        }

        alert.showAndWait();
    }
}
