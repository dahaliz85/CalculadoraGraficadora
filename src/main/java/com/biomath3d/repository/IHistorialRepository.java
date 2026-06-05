package com.biomath3d.repository;

import java.io.File;
import java.util.List;

/**
 * Contrato abstracto que define las operaciones permitidas sobre el almacenamiento de datos.
 * Permite desacoplar la lógica de negocio de la tecnología de persistencia final.
 */
public interface IHistorialRepository {

    /**
     * Guarda una simulación en el almacenamiento actual.
     */
    boolean registrarLineaPlana(String lineaFormateada);

    /**
     * Recupera la totalidad de los datos crudos del almacenamiento.
     */
    List<String> leerTodasLasLineas();

    /**
     * Actualiza el almacenamiento persistente con un lote completo de información.
     */
    boolean actualizarArchivoLocal(List<String> nuevasLineas);

    /**
     * Escribe de forma física el script o volcado relacional estructurado en disco.
     */
    boolean escribirScriptSQL(File destino, String contenidoSQL);
}