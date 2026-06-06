package com.biomath3d.math.geometry;

import com.biomath3d.math.parser.Token;
import com.biomath3d.math.parser.EvaluadorExpresion;
import javafx.scene.shape.TriangleMesh;
import java.util.List;

/**
 * Generador matemático puro. Construye mallas tridimensionales
 * aislando el dominio algebraico del espacio de renderizado de la interfaz.
 */
public class MeshGenerator {

    /**
     * Construye un TriangleMesh simétrico evaluando la función base en un dominio rígido.
     */
    public static TriangleMesh generarSuperficie(List<Token> tokensPostfijos, int divisiones, double rango, float escalaVisual, float escalaAltura) {
        TriangleMesh mesh = new TriangleMesh();

        double minDomain = -rango;
        double maxDomain = rango;
        double paso = (maxDomain - minDomain) / divisiones;

        // 1. Cómputo secuencial y ordenado de vértices por filas
        for (int i = 0; i <= divisiones; i++) {
            double xMath = minDomain + (i * paso);
            for (int j = 0; j <= divisiones; j++) {
                double yMath = minDomain + (j * paso);

                // Evaluación directa en el dominio algebraico
                double zMath = EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, xMath, yMath, 0.0, 2.0);

                // Mapeo nativo a coordenadas de pantalla 3D de JavaFX:
                // X_fx = x_matematica
                // Y_fx = -z_matematica (Eje vertical invertido)
                // Z_fx = y_matematica (Profundidad)
                float fxX = (float) (xMath * escalaVisual);
                float fxY = (float) (-zMath * escalaAltura);
                float fxZ = (float) (yMath * escalaVisual);

                mesh.getPoints().addAll(fxX, fxY, fxZ);
            }
        }

        // 2. Coordenadas de textura estáticas obligatorias
        mesh.getTexCoords().addAll(0f, 0f);

        // 3. Triangulación asociativa (Tejido de caras Clockwise)
        int filaAncho = divisiones + 1;
        for (int i = 0; i < divisiones; i++) {
            for (int j = 0; j < divisiones; j++) {
                int supIzq = i * filaAncho + j;
                int supDer = supIzq + 1;
                int infIzq = (i + 1) * filaAncho + j;
                int infDer = infIzq + 1;

                mesh.getFaces().addAll(supIzq, 0, infIzq, 0, supDer, 0);
                mesh.getFaces().addAll(supDer, 0, infIzq, 0, infDer, 0);
            }
        }

        return mesh;
    }
}