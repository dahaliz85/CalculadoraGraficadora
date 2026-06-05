package com.biomath3d.repository.impl;

import com.biomath3d.repository.IHistorialRepository;
import com.biomath3d.utils.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta encargada del flujo de almacenamiento nativo
 * a través de archivos planos de texto (.txt).
 */
public class HistorialRepositoryFileImpl implements IHistorialRepository {

    private static final String ARCHIVO_LOCAL = Constants.HISTORIAL_ARCHIVO_NOMBRE+Constants.HISTORIAL_ARCHIVO_EXT_TXT;

    @Override
    public boolean registrarLineaPlana(String lineaFormateada) {
        // Modo append = true para que no borre lo anterior y agregue la nueva línea al final
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_LOCAL, true))) {
            writer.write(lineaFormateada);
            writer.newLine();
            return true;
        } catch (IOException e) {
            // Impresión limpia en consola para depuración local
            System.err.println(Constants.ERROR_LINE_NOT_RECORDED + e.getMessage());
            return false;
        }
    }

    @Override
    public List<String> leerTodasLasLineas() {
        List<String> lineas = new ArrayList<>();
        File archivo = new File(ARCHIVO_LOCAL);

        // Si el archivo no existe todavía, retornamos la lista vacía de forma segura
        if (!archivo.exists()) {
            return lineas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Filtramos líneas vacías por seguridad
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            System.err.println(Constants.ERROR_LINE_NOT_READ + e.getMessage());
        }
        return lineas;
    }

    @Override
    public boolean actualizarArchivoLocal(List<String> nuevasLineas) {
        // Al no pasar el parámetro 'true', FileWriter sobrescribe el archivo completo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_LOCAL))) {
            for (String linea : nuevasLineas) {
                writer.write(linea);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println(Constants.ERROR_FILE_NOT_UPDATED + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean escribirScriptSQL(File destino, String contenidoSQL) {
        // Volcado físico del buffer completo de texto en la ruta seleccionada por el FileChooser
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destino))) {
            writer.write(contenidoSQL);
            return true;
        } catch (IOException e) {
            System.err.println(Constants.ERROR_FILE_NOT_WRITTEN + e.getMessage());
            return false;
        }
    }
}