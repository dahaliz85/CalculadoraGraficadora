package com.biomath3d.service.impl;

import com.biomath3d.repository.IHistorialRepository;
import com.biomath3d.repository.impl.HistorialRepositoryDBImpl;
import com.biomath3d.repository.impl.HistorialRepositoryFileImpl;
import com.biomath3d.service.IHistorialService;
import com.biomath3d.utils.Constants;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementación de la lógica de negocio para la gestión del historial de simulaciones.
 * Consume centralizadamente la clase Constants para evitar textos hardcodeados.
 */
public class HistorialServiceImpl implements IHistorialService {

    private final IHistorialRepository repoFile;
    private final IHistorialRepository repoDB;
    private final DateTimeFormatter formatter;

    public HistorialServiceImpl() {
        this.repoFile = new HistorialRepositoryFileImpl();
        this.repoDB = new HistorialRepositoryDBImpl();
        this.formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
    }

    @Override
    public boolean guardarNuevaSimulacion(String nombre, String ecuacion,
                                          double xMin, double xMax,
                                          double yMin, double yMax,
                                          double constanteA) {

        String idSimulacion = UUID.randomUUID().toString();
        String fechaRegistro = LocalDateTime.now().format(formatter);

        // Construcción de la línea uniendo las variables de control de forma limpia
        String lineaFormateada = idSimulacion + Constants.PIPE_SEPARATOR_JOIN +
                nombre + Constants.PIPE_SEPARATOR_JOIN +
                ecuacion + Constants.PIPE_SEPARATOR_JOIN +
                String.format("%.4f", xMin) + Constants.PIPE_SEPARATOR_JOIN +
                String.format("%.4f", xMax) + Constants.PIPE_SEPARATOR_JOIN +
                String.format("%.4f", yMin) + Constants.PIPE_SEPARATOR_JOIN +
                String.format("%.4f", yMax) + Constants.PIPE_SEPARATOR_JOIN +
                String.format("%.4f", constanteA) + Constants.PIPE_SEPARATOR_JOIN +
                fechaRegistro;

        boolean exitoFile = repoFile.registrarLineaPlana(lineaFormateada);
        repoDB.registrarLineaPlana(lineaFormateada); // Gancho para JPA preparado

        return exitoFile;
    }

    @Override
    public List<String[]> obtenerHistorialProcesado() {
        List<String[]> historialEstructurado = new ArrayList<>();
        List<String> lineasCrudas = repoFile.leerTodasLasLineas();

        for (String linea : lineasCrudas) {
            if (linea != null && !linea.trim().isEmpty()) {
                String[] componentes = linea.split(Constants.PIPE_SEPARATOR);
                historialEstructurado.add(componentes);
            }
        }
        return historialEstructurado;
    }

    @Override
    public boolean eliminarSimulacionPorId(String idSimulacion) {
        List<String> lineasActuales = repoFile.leerTodasLasLineas();
        List<String> lineasFiltradas = new ArrayList<>();
        boolean encontrado = false;

        for (String linea : lineasActuales) {
            String[] componentes = linea.split(Constants.PIPE_SEPARATOR);
            if (componentes.length > 0 && componentes[0].equals(idSimulacion)) {
                encontrado = true;
                continue; // Lo omitimos para eliminarlo
            }
            lineasFiltradas.add(linea);
        }

        if (encontrado) {
            return repoFile.actualizarArchivoLocal(lineasFiltradas);
        }
        return false;
    }

    @Override
    public boolean exportarDumpSQL(File destinoArchivo) {
        List<String> lineasHistorial = repoFile.leerTodasLasLineas();
        StringBuilder sqlScript = new StringBuilder();

        // 1. Armar Bloque DDL estructurado desde las constantes
        sqlScript.append(Constants.SQL_LINEA_DIVISORIA);
        sqlScript.append(Constants.SQL_COMENTARIO_ENCABEZADO);
        sqlScript.append(Constants.SQL_LINEA_DIVISORIA).append("\n");
        sqlScript.append(Constants.SQL_CREATE_TABLE_SIMULACIONES);
        sqlScript.append(Constants.SQL_CREATE_TABLE_DOMINIOS);

        sqlScript.append(Constants.SQL_LINEA_DIVISORIA);
        sqlScript.append(Constants.SQL_COMENTARIO_DML);
        sqlScript.append(Constants.SQL_LINEA_DIVISORIA).append("\n");

        // 2. Generar bloque DML iterando el archivo plano
        for (String linea : lineasHistorial) {
            String[] datos = linea.split(Constants.PIPE_SEPARATOR);
            if (datos.length >= 9) {
                String id = datos[0];
                String nombre = datos[1].replace("'", "''");
                String ecuacion = datos[2].replace("'", "''");
                String xMin = datos[3];
                String xMax = datos[4];
                String yMin = datos[5];
                String yMax = datos[6];
                String constanteA = datos[7];
                String fecha = datos[8];

                // Inserciones estructuradas usando las plantillas centralizadas
                sqlScript.append(String.format(Constants.SQL_INSERT_SIMULACION, id, nombre, ecuacion, constanteA, fecha));
                sqlScript.append(String.format(Constants.SQL_INSERT_DOMINIO, id, xMin, xMax, yMin, yMax));
            }
        }

        return repoFile.escribirScriptSQL(destinoArchivo, sqlScript.toString());
    }
}