package com.biomath3d.controller;

import com.biomath3d.service.IHistorialService;
import com.biomath3d.service.impl.HistorialServiceImpl;
import com.biomath3d.utils.Constants;
import com.biomath3d.utils.AlertaUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

/**
 * Administra exclusivamente la lógica visual y el flujo de datos del Historial.
 * Actúa como puente entre la interfaz principal y el servicio de persistencia.
 */
public class HistorialController {

    private final IHistorialService historialService;

    @FXML
    private TextArea txtHistoryArea; // El cuadro blanco visual "History" de la UI

    public HistorialController() {
        this.historialService = new HistorialServiceImpl();
    }

    /**
     * Procesa una nueva función: la escribe en el cuadro visual y solicita al servicio
     * que la registre de forma persistente en el archivo local.
     */
    public void registrarFuncionEnHistorial(String operacionSeleccionada, String ecuacion) {
        // 1. Darle formato visual para agregarlo al cuadro blanco "History"
        txtHistoryArea.appendText(ecuacion + "\n");

        // 2. Simular los valores técnicos requeridos por el servicio por ahora
        String nombreSimulacion = operacionSeleccionada + " Anonimo";
        double xMin = -5.0, xMax = 5.0, yMin = -5.0, yMax = 5.0, constanteA = 1.0;

        // 3. Delegar el guardado físico a la capa de negocio (Service)
        boolean guardadoExitoso = historialService.guardarNuevaSimulacion(
                nombreSimulacion, ecuacion, xMin, xMax, yMin, yMax, constanteA
        );

        if (guardadoExitoso) {
            AlertaUtils.mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", Constants.MSG_EXITO_HISTORIAL);
        } else {
            AlertaUtils.mostrarAlerta(Alert.AlertType.ERROR, "Error de Almacenamiento", Constants.ERROR_LINE_NOT_RECORDED);
        }
    }
}