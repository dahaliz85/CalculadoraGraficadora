package com.biomath3d.service;

import java.io.File;
import java.util.List;

/**
 * Contrato que define las operaciones de lógica de negocio para el historial.
 * Las validaciones iniciales de la interfaz de usuario se manejan en la capa de presentación.
 */
public interface IHistorialService {

    /**
     * Procesa el guardado de la simulación asumiendo que los datos obligatorios
     * ya vienen validados estructuralmente.
     */
    boolean guardarNuevaSimulacion(String nombre, String ecuacion,
                                   double xMin, double xMax,
                                   double yMin, double yMax,
                                   double constanteA);

    /**
     * Recupera los registros del archivo plano y los fragmenta en arreglos de strings.
     */
    List<String[]> obtenerHistorialProcesado();

    /**
     * Gestiona la remoción de un registro específico del historial a través de su ID.
     */
    boolean eliminarSimulacionPorId(String idSimulacion);

    /**
     * Lee las simulaciones del archivo plano, genera la estructura relacional DDL/DML
     * y delega la escritura física del script .sql.
     */
    boolean exportarDumpSQL(File destinoArchivo);
}