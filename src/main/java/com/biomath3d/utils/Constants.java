package com.biomath3d.utils;

public class Constants {

    // Constantes Historial Archivo
    public static final String HISTORIAL_ARCHIVO_NOMBRE = "historial_simulaciones";
    public static final String HISTORIAL_ARCHIVO_EXT_SQL = ".sql";
    public static final String HISTORIAL_ARCHIVO_EXT_TXT = ".txt";

    // Constantes Errores
    public static final String ERROR_FILE_NOT_FOUND = "Error al encontrar el archivo";
    public static final String ERROR_FILE_NOT_UPDATED = "Error al actualizar el archivo plano: ";
    public static final String ERROR_FILE_NOT_WRITTEN = "Error al escribir el script SQL en disco: ";
    public static final String ERROR_LINE_NOT_RECORDED = "Error al registrar línea en el archivo plano: ";
    public static final String ERROR_LINE_NOT_READ = "Error al leer el archivo plano: ";

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String PIPE_SEPARATOR = "\\|";
    public static final String PIPE_SEPARATOR_JOIN = "|";

    // Encabezados y Estructuras DDL para el Dump SQL
    public static final String SQL_LINEA_DIVISORIA = "-- ==========================================================\n";
    public static final String SQL_COMENTARIO_ENCABEZADO = "--   SCRIPT DUMP EXPORTADO DESDE BIOMATH 3D - HISTORIAL     \n";
    public static final String SQL_COMENTARIO_DML = "--   BLOQUE DML: INSERCIÓN DE DATOS RELACIONADOS             \n";

    public static final String SQL_CREATE_TABLE_SIMULACIONES =
            "CREATE TABLE IF NOT EXISTS simulaciones (\n" +
                    "    id_simulacion VARCHAR(36) PRIMARY KEY,\n" +
                    "    nombre_simulacion VARCHAR(100) NOT NULL,\n" +
                    "    ecuacion_funcion VARCHAR(255) NOT NULL,\n" +
                    "    constante_a DOUBLE NOT NULL,\n" +
                    "    fecha_creacion TIMESTAMP NOT NULL\n" +
                    ");\n\n";

    public static final String SQL_CREATE_TABLE_DOMINIOS =
            "CREATE TABLE IF NOT EXISTS dominios_graficacion (\n" +
                    "    id_dominio INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    id_simulacion VARCHAR(36) NOT NULL,\n" +
                    "    x_min DOUBLE NOT NULL,\n" +
                    "    x_max DOUBLE NOT NULL,\n" +
                    "    y_min DOUBLE NOT NULL,\n" +
                    "    y_max DOUBLE NOT NULL,\n" +
                    "    FOREIGN KEY (id_simulacion) REFERENCES simulaciones(id_simulacion) ON DELETE CASCADE\n" +
                    ");\n\n";

    // Plantillas para las inserciones DML
    public static final String SQL_INSERT_SIMULACION =
            "INSERT INTO simulaciones (id_simulacion, nombre_simulacion, ecuacion_funcion, constante_a, fecha_creacion) VALUES ('%s', '%s', '%s', %s, '%s');\n";

    public static final String SQL_INSERT_DOMINIO =
            "INSERT INTO dominios_graficacion (id_simulacion, x_min, x_max, y_min, y_max) VALUES ('%s', %s, %s, %s, %s);\n\n";

    public static final String TITULO_SELECCIONAR_SQL = "Guardar Script SQL";
    public static final String ALERTA_TITULO_EXITO = "Operación Exitosa";
    public static final String ALERTA_TITULO_ERROR = "Ocurrió un Error";
    public static final String MSG_SQL_EXPORTADO_EXITO = "El historial se ha exportado correctamente a un archivo SQL.";
    public static final String MSG_REGISTRO_ELIMINADO = "La simulación seleccionada ha sido eliminada del historial.";
    public static final String MSG_SELECCION_VACIA = "Por favor, seleccione un registro de la tabla primero.";

    public static final String COMBO_SELECCIONAR_OP = "--- Seleccionar Función ---";
    public static final String BTN_TEXTO_VER_HISTORIAL = "Ver Historial de Simulaciones";
    public static final String ALERTA_ERROR_SELECCION = "Por favor, seleccione una función válida antes de continuar.";
    public static final String ALERTA_CAMPOS_VACIOS = "La ecuación matemática y el nombre de la simulación son campos obligatorios.";
    public static final String ALERTA_TITULO_VALIDACION = "BioMath 3D - Validación";
    public static final String ALERTA_ECUACION_VACIA = "El campo de entrada de la función no puede estar vacío.";
    public static final String MSG_EXITO_HISTORIAL = "Simulación integrada al historial correctamente.";
}
