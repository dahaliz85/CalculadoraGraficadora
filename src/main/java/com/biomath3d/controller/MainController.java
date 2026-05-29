package com.biomath3d.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML private MenuButton menuCuenta;
    @FXML private MenuItem menuSalir;
    @FXML private TextField txtFunc;
    @FXML private Button btnRender;
    @FXML private StackPane centerContainer;
    @FXML private ListView<String> historyList;

    @FXML
    public void initialize() {
        // Programamos la acción de salir de la app
        menuSalir.setOnAction(e -> System.exit(0));

        // Aquí conectaremos las acciones más adelante
        btnRender.setOnAction(e -> {
            System.out.println("Renderizando: " + txtFunc.getText());
        });
    }
}