package com.biomath3d.repository.impl;

import com.biomath3d.repository.IHistorialRepository;

import java.io.File;
import java.util.List;

public class HistorialRepositoryDBImpl implements IHistorialRepository {
    @Override
    public boolean registrarLineaPlana(String lineaFormateada) {
        return false;
    }

    @Override
    public List<String> leerTodasLasLineas() {
        return List.of();
    }

    @Override
    public boolean actualizarArchivoLocal(List<String> nuevasLineas) {
        return false;
    }

    @Override
    public boolean escribirScriptSQL(File destino, String contenidoSQL) {
        return false;
    }
}
