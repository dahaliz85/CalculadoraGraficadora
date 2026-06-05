package com.biomath3d.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class IntroController {

    @FXML private ProgressBar barCarga;
    @FXML private Label lblProgreso;

    // Mensajes para simular la carga de infraestructura
    private final String[] pasosCarga = {
            "Cargando configuraciones base...",
            "Inicializando repositorio de historial plano...",
            "Validando analizador matemático (Parser)...",
            "Preparando contenedor gráfico de superficies 3D...",
            "Estructurando operaciones de cálculo vectorial...",
            "BioMath 3D listo."
    };

    /**
     * Registra la tarea de carga y vincula las propiedades con los nodos de la UI.
     */
    public void iniciarCarga(Runnable alFinalizar) {
        Task<Void> tareaCarga = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int totalPasos = pasosCarga.length;

                for (int i = 0; i < totalPasos; i++) {
                    // Actualizamos el texto en el hilo de la UI
                    updateMessage(pasosCarga[i]);
                    // Actualizamos el porcentaje de la barra de progreso
                    updateProgress(i + 1, totalPasos);

                    // Tiempo de espera para que se note la transición en pantalla (700 milisegundos por paso)
                    Thread.sleep(700);
                }
                return null;
            }
        };

        // Enlazamos las propiedades de la tarea directamente a los componentes de JavaFX
        lblProgreso.textProperty().bind(tareaCarga.messageProperty());
        barCarga.progressProperty().bind(tareaCarga.progressProperty());

        // Al terminar de forma exitosa, ejecutamos el cambio de ventana
        tareaCarga.setOnSucceeded(event -> alFinalizar.run());

        // Arrancamos la tarea en un hilo de ejecución secundario
        Thread hiloSplash = new Thread(tareaCarga);
        hiloSplash.setDaemon(true);
        hiloSplash.start();
    }
}