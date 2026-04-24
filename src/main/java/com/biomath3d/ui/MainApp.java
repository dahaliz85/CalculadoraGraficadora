package com.biomath3d.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        // Fondo general ultra oscuro
        root.setStyle("-fx-background-color: #0d0d0d;");

        // --- MÓDULO UI: Visor 3D (Centro) ---
        Group world = new Group();
        SubScene subScene = new SubScene(world, 850, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.web("#050505"));

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setTranslateZ(-150);
        subScene.setCamera(camera);

        StackPane canvasContainer = new StackPane(subScene);
        // Borde con brillo sutil (Glow)
        canvasContainer.setStyle("-fx-border-color: #1a1a1a; -fx-border-width: 1; -fx-background-color: black;");
        root.setCenter(canvasContainer);

        MenuBar menuBar = new MenuBar();
        // Estilo futurista para la barra de menú
        menuBar.setStyle("-fx-background-color: #1a1a1a; -fx-border-color: #333; -fx-border-width: 0 0 1 0;");

        // --- MENÚ ARCHIVO (File) ---
        Menu fileMenu = new Menu("ARCHIVO");
        MenuItem newItem = new MenuItem("Nuevo Proyecto");
        MenuItem openItem = new MenuItem("Abrir...");
        Menu exportMenu = new Menu("Exportar");
        MenuItem exportImg = new MenuItem("Como Imagen (.png)");
        MenuItem exportObj = new MenuItem("Como Modelo 3D (.obj)");
        MenuItem exitItem = new MenuItem("Salir");

        exportMenu.getItems().addAll(exportImg, exportObj);
        fileMenu.getItems().addAll(newItem, openItem, new SeparatorMenuItem(), exportMenu, new SeparatorMenuItem(), exitItem);

        // --- MENÚ EDITAR (Edit) ---
        Menu editMenu = new Menu("EDITAR");
        MenuItem copyItem = new MenuItem("Copiar Imagen");
        MenuItem clearHistory = new MenuItem("Limpiar Historial");
        editMenu.getItems().addAll(copyItem, clearHistory);

        // --- MENÚ VER (View) ---
        Menu viewMenu = new Menu("VER");
        CheckMenuItem showAxes = new CheckMenuItem("Mostrar Ejes");
        showAxes.setSelected(true);
        CheckMenuItem wireframeMode = new CheckMenuItem("Modo Alámbrico");
        viewMenu.getItems().addAll(showAxes, wireframeMode);

        // --- MENU AYUDA (Help) ---
        Menu helpMenu = new Menu("AYUDA");
        CheckMenuItem showAcercaDe = new CheckMenuItem("Acerca de...");
        showAxes.setSelected(true);
        helpMenu.getItems().addAll(showAcercaDe);

        String menuStyle = "-fx-text-fill: #ffffff; -fx-font-family: 'Segoe UI'; -fx-font-size: 11px; -fx-font-weight: bold;";
        fileMenu.setStyle(menuStyle);
        editMenu.setStyle(menuStyle);
        viewMenu.setStyle(menuStyle);
        helpMenu.setStyle(menuStyle);

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
        root.setTop(menuBar);

        // --- MÓDULO UI: Panel de Control (Derecha) ---
        VBox sidePanel = new VBox(20);
        sidePanel.setPrefWidth(300);
        sidePanel.setPadding(new Insets(25));
        // Degradado lateral para dar profundidad
        sidePanel.setStyle("-fx-background-color: linear-gradient(to bottom, #1e1e1e, #121212); " +
                "-fx-border-color: #333; -fx-border-width: 0 0 0 1;");

        // Estilos CSS para componentes futuristas
        String labelStyle = "-fx-text-fill: #00d4ff; -fx-font-weight: bold; -fx-font-family: 'Consolas'; -fx-font-size: 11px; -fx-letter-spacing: 1px;";
        String fieldStyle = "-fx-background-color: #1a1a1a; -fx-text-fill: #ffffff; -fx-border-color: #00d4ff33; -fx-border-radius: 5; -fx-background-radius: 5; -fx-padding: 8;";
        String btnStyleBase = "-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20; -fx-cursor: hand; -fx-font-family: 'Segoe UI Semibold';";

        Label lblFunc = new Label("> ANALYZER_INPUT");
        lblFunc.setStyle(labelStyle);
        TextField txtFunction = new TextField("x^2 + y^2");
        txtFunction.setStyle(fieldStyle);

        Label lblParams = new Label("> MESH_PARAMETERS");
        lblParams.setStyle(labelStyle);

        GridPane gridParams = new GridPane();
        gridParams.setHgap(10); gridParams.setVgap(10);

        TextField txtMin = new TextField("-10"); txtMin.setPrefWidth(70); txtMin.setStyle(fieldStyle);
        TextField txtMax = new TextField("10");  txtMax.setPrefWidth(70); txtMax.setStyle(fieldStyle);

        gridParams.addRow(0, createMiniLabel("MIN_LIMIT:"), txtMin);
        gridParams.addRow(1, createMiniLabel("MAX_LIMIT:"), txtMax);

        // Botones con estilo moderno (Brillo sutil)
        Button btnPlot = new Button("GENERATE MESH");
        btnPlot.setMaxWidth(Double.MAX_VALUE);
        btnPlot.setStyle(btnStyleBase + "-fx-background-color: linear-gradient(to bottom, #2196F3, #1565C0); -fx-effect: dropshadow(three-pass-box, rgba(33,150,243,0.3), 10, 0, 0, 0);");

        Button btnAnalyze = new Button("DIFFERENTIAL ANALYZE");
        btnAnalyze.setMaxWidth(Double.MAX_VALUE);
        btnAnalyze.setStyle(btnStyleBase + "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); -fx-effect: dropshadow(three-pass-box, rgba(76,175,80,0.3), 10, 0, 0, 0);");

        sidePanel.getChildren().addAll(lblFunc, txtFunction, new Separator(), lblParams, gridParams, new Separator(), btnPlot, btnAnalyze);
        root.setRight(sidePanel);

        // --- MÓDULO UI: Consola (Abajo) ---
        TextArea consoleOutput = new TextArea("BIOMATH_CORE_OS [Version 1.0.0]\n(c) 2026 Engine. System initialized.\nREADY_FOR_INPUT > ");
        consoleOutput.setEditable(false);
        consoleOutput.setPrefHeight(150);
        consoleOutput.setStyle("-fx-control-inner-background: #050505; -fx-font-family: 'Consolas'; -fx-text-fill: #00ff41; -fx-border-color: #00ff4133; -fx-border-width: 1 0 0 0;");
        root.setBottom(consoleOutput);

        Scene scene = new Scene(root, 1200, 800);
        try {
            javafx.scene.image.Image appIcon = new javafx.scene.image.Image(
                    getClass().getResourceAsStream("/com/biomath3d/ui/icon.png")
            );
            primaryStage.getIcons().add(appIcon);
        } catch (Exception e) {
            consoleOutput.appendText("\nERROR_SYSTEM > No se pudo cargar el recurso: icon.png");
        }

        primaryStage.setTitle("BioMath 3D - Advanced Scientific Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createMiniLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #888; -fx-font-size: 10px; -fx-font-family: 'Consolas';");
        return l;
    }

    public static void main(String[] args) {
        launch(args);
    }
}