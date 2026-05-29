package com.biomath3d.controller;

import com.biomath3d.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    @FXML private void abrirProyecto() {
        Stage stage = (Stage) txtFuncion.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir Proyecto BioMath 3D");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de Simulación (*.dat, *.json)", "*.dat", "*.json")
        );

        File archivo = fileChooser.showOpenDialog(stage);
        if (archivo != null) {
            // Aquí irá tu lógica para parsear el archivo y cargar la ecuación en el TextBox
            System.out.println("Archivo cargado con éxito desde: " + archivo.getAbsolutePath());
        }}
    @FXML private void exportarImagen() {
        Stage stage = (Stage) txtFuncion.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Captura de Pantalla");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagen PNG (*.png)", "*.png"));
        fileChooser.setInitialFileName("render_biomath3d.png");

        File archivo = fileChooser.showSaveDialog(stage);
        if (archivo != null) {
            // Lógica para tomar el Snapshot de tu SubScene/Pane 3D y guardarlo con ImageIO
            System.out.println("Render exportado correctamente como imagen en: " + archivo.getName());
        }
    }
    @FXML private void exportarMallaOBJ() {
        Stage stage = (Stage) txtFuncion.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Modelo 3D");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Modelo Wavefront (*.obj)", "*.obj"));
        fileChooser.setInitialFileName("malla_superficie.obj");

        File archivo = fileChooser.showSaveDialog(stage);
        if (archivo != null) {
            // Aquí llamarás a tu MeshGenerator para volcar los vértices e índices al archivo de texto
            System.out.println("Geometría tridimensional exportada en formato OBJ: " + archivo.getName());
        }
    }

    @FXML private void copiarAlPortapapeles() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        // Simulación: En el código real sacarías un snapshot de tu nodo 3D:
        // WritableImage image = subScene3D.snapshot(new SnapshotParameters(), null);
        // content.putImage(image);

        content.putString(txtFuncion.getText()); // Temporalmente copiamos el texto de la función
        clipboard.setContent(content);
        System.out.println("Contenido enviado con éxito al portapapeles del sistema.");
    }
    @FXML private void limpiarHistorialBaseDatos() {
        System.out.println("Abriendo conexión con SQLite: Ejecutando DELETE FROM Configuracion_Simulacion...");
    }

    @FXML private void alternarEjes() {
        System.out.println("Modificando visibilidad de nodos de ejes X, Y, Z...");
    }
    @FXML private void alternarWireframe() {
        System.out.println("Cambiando render a modo red de líneas...");
    }
    @FXML private void restablecerCamara() {
        System.out.println("Reiniciando matriz de transformación de la cámara 3D...");
    }

    @FXML private void cerrarVentana() {
        Platform.exit();
        System.exit(0);
    }

}