package com.biomath3d.controller;

import com.biomath3d.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.application.Platform;

public class MainController {

    @FXML private MenuButton menuCuenta;
    @FXML private MenuItem menuSalir;
    @FXML private TextField txtFunc;
    @FXML private Button btnRender;
    @FXML private StackPane centerContainer;
    @FXML private ListView<String> historyList;

    @FXML private ComboBox<String> comboOperacion;
    @FXML private TextField txtFuncion;

    @FXML
    public void initialize() {
        ObservableList<String> operacionesReales = FXCollections.observableArrayList(
                "Generar Malla Superficie 3D",       // CU-02: Renderizado de f(x,y)
                "Calcular Gradiente ∇f",             // CU-03: Operador Gradiente (Direccional)
                "Calcular Divergencia (Campos)",     // CU-03: Operador Divergencia
                "Calcular Rotacional ∇ × F",         // CU-03: Operador Rotacional
                "Calcular Plano Tangente y Normal"   // CU-03: Geometría Diferencial de la superficie
        );
        comboOperacion.setItems(operacionesReales);
        comboOperacion.getSelectionModel().selectFirst();
    }

    @FXML
    private void procesarOperacion() {
        String operacion = comboOperacion.getValue();
        String ecuacion = txtFuncion.getText();

        if (ecuacion == null || ecuacion.trim().isEmpty()) {
            Utils.mostrarAlertaError("Entrada Vacía", "Por favor, introduce una ecuación matemática.");
            return;
        }

        // Validamos usando tu clase Utils
        if (!Utils.esFuncionValida(ecuacion)) {
            // 1. Pintamos de rojo (lo que ya te sale bien)
            txtFuncion.setStyle("-fx-border-color: #ff4a4a; -fx-border-radius: 6; -fx-background-color: #1e222b; -fx-text-fill: white;");

            // 2. ¡ASEGÚRATE DE TENER ESTA LÍNEA AQUÍ!
            Utils.mostrarAlertaError("Error de Sintaxis", "La expresión '" + ecuacion + "' no es una función válida para BioMath 3D.");
            return;
        }

        // Si es válida, restablecemos el color azul
        txtFuncion.setStyle("-fx-border-color: #316cf4; -fx-border-radius: 6; -fx-background-color: #1e222b; -fx-text-fill: white;");
        System.out.println("Sintaxis Correcta: " + ecuacion);
    }

    // ==========================================================
    //   MÉTODOS ASOCIADOS A LAS FUNCIONES REALES DEL MENÚ FXML
    // ==========================================================
    @FXML private void nuevoProyecto() { System.out.println("CU-02: Inicializando lienzo..."); txtFuncion.clear(); }
    @FXML private void abrirProyecto() { System.out.println("CU-05: Consultando archivos del historial..."); }
    @FXML private void exportarImagen() { System.out.println("CU-08: Capturando evidencia en PNG..."); }
    @FXML private void exportarMallaOBJ() { System.out.println("CU-08: Exportando geometría a archivo .obj..."); }

    @FXML private void copiarAlPortapapeles() { System.out.println("Copiando captura al portapapeles..."); }
    @FXML private void limpiarHistorialBaseDatos() { System.out.println("Abriendo conexión SQLite para truncar tablas..."); }

    @FXML private void alternarEjes() { System.out.println("Modificando visibilidad de nodos de ejes X, Y, Z..."); }
    @FXML private void alternarWireframe() { System.out.println("Cambiando render a modo red de líneas..."); }
    @FXML private void restablecerCamara() { System.out.println("Reiniciando matriz de transformación de la cámara 3D..."); }

    @FXML private void cerrarVentana() { Platform.exit(); System.exit(0); }

}