package com.biomath3d.controller;

import com.biomath3d.math.parser.Token;
import com.biomath3d.render.MeshGenerator;
import com.biomath3d.service.IHistorialService;
import com.biomath3d.utils.Constants;
import com.biomath3d.utils.Utils;
import com.biomath3d.utils.AlertaUtils;
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
import java.util.List;

public class MainController {

    private final HistorialController historialController;
    private final ProcessController processController;
    private final MeshController meshController;

    @FXML private MenuButton menuCuenta;
    @FXML private MenuItem menuSalir;
    @FXML private TextField txtFunc;
    @FXML private Button btnRender;
    @FXML private StackPane centerContainer;
    @FXML private ListView<String> historyList;

    @FXML private ComboBox<String> comboOperacion;
    @FXML private TextField txtFuncion;
    @FXML private TextArea txtResultados;

    public MainController(){
        this.processController = new ProcessController();
        this.historialController = new HistorialController();
        this.meshController = new MeshController();
    }

    @FXML
    public void initialize() {
        ObservableList<String> operacionesReales = FXCollections.observableArrayList(
                "Generar Malla Superficie 3D",
                "Calcular Gradiente ∇f",
                "Calcular Divergencia (Campos)",
                "Calcular Rotacional ∇ × F",
                "Calcular Plano Tangente y Normal"
        );
        operacionesReales.add(0, Constants.COMBO_SELECCIONAR_OP);
        comboOperacion.setItems(operacionesReales);
        comboOperacion.setValue(Constants.COMBO_SELECCIONAR_OP);
    }

    @FXML
    private void procesarOperacion() {
        String operacion = comboOperacion.getValue();
        String ecuacion = txtFuncion.getText().trim();

        // ... (Tus validaciones de ComboBox vacía y Sintaxis se quedan exactamente igual) ...

        // Si es válida, restablecemos el color azul
        txtFuncion.setStyle("-fx-border-color: #316cf4; -fx-border-radius: 6; -fx-background-color: #1e222b; -fx-text-fill: white;");
        System.out.println("Sintaxis Correcta: " + ecuacion);

        historyList.getItems().add(ecuacion);
        historialController.registrarFuncionEnHistorial(comboOperacion.getValue(), ecuacion);
        double constanteA = 1.0;
        processController.registrarProcesoEcuacion(comboOperacion.getValue(), ecuacion, txtResultados);
        meshController.inicializarLienzo3D(centerContainer);
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

    /**
     * Acción asignada a la subopción "txt".
     * Abre un FileChooser para que el usuario elija dónde guardar una copia del historial en texto plano.
     */
    @FXML
    private void handleExportarTXT() {
        // 1. Validar primero si hay elementos en el ListView antes de abrir ventanas
        if (historyList.getItems().isEmpty()) {
            Utils.mostrarAlertaError("Exportación Cancelada", Constants.ERROR_LINE_NOT_READ);
            return;
        }

        Stage stageActual = (Stage) txtFuncion.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // 2. Configurar el título del diálogo y el nombre sugerido
        fileChooser.setTitle("Exportar Historial Texto Plano");
        fileChooser.setInitialFileName(Constants.HISTORIAL_ARCHIVO_NOMBRE + Constants.HISTORIAL_ARCHIVO_EXT_TXT);

        // 3. Forzar el filtro para que guarde estrictamente como archivo .txt
        FileChooser.ExtensionFilter filtroTxt = new FileChooser.ExtensionFilter("Text Files (*.txt)", "*" + Constants.HISTORIAL_ARCHIVO_EXT_TXT);
        fileChooser.getExtensionFilters().add(filtroTxt);

        // 4. Abrir la ventana nativa de "Guardar como..."
        File archivoDestino = fileChooser.showSaveDialog(stageActual);

        if (archivoDestino != null) {
            // Reutilizamos el repositorio a través del HistorialController.
            // Para mantener la consistencia, le pedimos al servicio que lea las líneas actuales
            // y escriba la copia exacta en la ubicación elegida por el usuario.
            boolean exito = historialController.exportarHistorialTextoPlano(archivoDestino);

            if (exito) {
                Utils.mostrarAlertaError("Operación Exitosa", "El historial se ha exportado correctamente en formato de texto plano.");
            } else {
                Utils.mostrarAlertaError("Error de Escritura", Constants.ERROR_FILE_NOT_WRITTEN);
            }
        }
    }

    /**
     * Acción asignada a la subopción "sql".
     * Abre el FileChooser nativo y genera el Dump SQL relacional completo.
     */
    @FXML
    private void handleExportarSQL() {
        // Validamos si hay elementos en el ListView antes de intentar exportar algo vacío
        if (historyList.getItems().isEmpty()) {
            Utils.mostrarAlertaError("Exportación Cancelada", Constants.ERROR_LINE_NOT_READ);
            return;
        }

        Stage stageActual = (Stage) txtFuncion.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Configuración del diálogo usando tus constantes centralizadas
        fileChooser.setTitle("Guardar Script SQL");
        fileChooser.setInitialFileName(Constants.HISTORIAL_ARCHIVO_NOMBRE + Constants.HISTORIAL_ARCHIVO_EXT_SQL);

        // Filtro estricto para obligar la extensión .sql
        FileChooser.ExtensionFilter filtroSql = new FileChooser.ExtensionFilter("SQL Files (*.sql)", "*" + Constants.HISTORIAL_ARCHIVO_EXT_SQL);
        fileChooser.getExtensionFilters().add(filtroSql);

        File archivoDestino = fileChooser.showSaveDialog(stageActual);

        if (archivoDestino != null) {
            // El servicio se encarga de masticar el archivo plano y escupir el script DDL/DML relacional
            boolean exito = historialController.exportarDumpSQL(archivoDestino);
            if (exito) {
                // Mensaje de éxito usando tu infraestructura
                Utils.mostrarAlertaError("Operación Exitosa", "El historial se ha exportado correctamente a un archivo SQL relacional.");
            } else {
                Utils.mostrarAlertaError("Error de Escritura", Constants.ERROR_FILE_NOT_WRITTEN);
            }
        }
    }

    @FXML
    private void handleRender3D() {
        String ecuacion = txtFuncion.getText().trim();

        if (ecuacion.isEmpty() || !com.biomath3d.utils.Utils.esFuncionValida(ecuacion)) {
            txtFuncion.setStyle("-fx-border-color: #ff4a4a; -fx-border-radius: 6; -fx-background-color: #1e222b; -fx-text-fill: white;");
            return;
        }

        try {
            txtFuncion.setStyle("-fx-border-color: #316cf4; -fx-border-radius: 6; -fx-background-color: #1e222b; -fx-text-fill: white;");

            com.biomath3d.math.parser.DetectorVariables detector = new com.biomath3d.math.parser.DetectorVariables();
            detector.analizarPropuesta(ecuacion);

            List<com.biomath3d.math.parser.Token> tokensInfijos = com.biomath3d.math.parser.Tokenizador.tokenizar(detector.getExpresionLimpia(), detector);
            List<com.biomath3d.math.parser.Token> tokensPostfijos = com.biomath3d.math.parser.EvaluadorExpresion.convertirAPostfijo(tokensInfijos);

            // Refrescamos únicamente la geometría interna del grupo de rotación
            meshController.renderizarSuperficie(tokensPostfijos, false, detector);
            System.out.println("Geometría tridimensional actualizada con éxito.");

        } catch (Exception e) {
            System.err.println("Error al actualizar superficie 3D: " + e.getMessage());
        }
    }

}