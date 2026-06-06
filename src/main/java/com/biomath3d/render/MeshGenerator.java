package com.biomath3d.render;

import com.biomath3d.math.parser.EvaluadorExpresion;
import com.biomath3d.math.parser.Token;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import java.util.List;

/**
 * Motor de renderizado encargado de construir mallas poligonales tridimensionales
 * e inyectar el entorno de navegación de JavaFX 3D.
 */
public class MeshGenerator {

    private final Group contenedorRaiz = new Group();
    private final Group grupoRotacion = new Group(); // Permite girar la superficie con el mouse
    private final PerspectiveCamera camara = new PerspectiveCamera(true);
    private final Rotate rotacionX = new Rotate(60, Rotate.X_AXIS);
    private final Rotate rotacionZ = new Rotate(45, Rotate.Z_AXIS);

    private double mouseOldX, mouseOldY;

    public MeshGenerator() {
        contenedorRaiz.getChildren().add(grupoRotacion);
        configurarCamara();
    }

    private void configurarCamara() {
        camara.setNearClip(0.1);
        camara.setFarClip(1000.0);
        camara.setTranslateZ(-35); // Nos alejamos para ver la superficie completa
        contenedorRaiz.getChildren().add(camara);
    }

    /**
     * Construye la SubScene interactiva y mapea los eventos de arrastre del mouse.
     */
    public SubScene crearEscena3D(double ancho, double alto) {
        SubScene subScene = new SubScene(contenedorRaiz, ancho, alto, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.web("#14161d")); // Color oscuro uniforme para hacer juego con tu UI
        subScene.setCamera(camara);

        grupoRotacion.getTransforms().addAll(rotacionX, rotacionZ);

        // Eventos del ratón para rotación orbital libre
        subScene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });

        subScene.setOnMouseDragged(me -> {
            double mouseDeltaX = me.getSceneX() - mouseOldX;
            double mouseDeltaY = me.getSceneY() - mouseOldY;

            rotacionZ.setAngle(rotacionZ.getAngle() - mouseDeltaX * 0.3);
            rotacionX.setAngle(rotacionX.getAngle() + mouseDeltaY * 0.3);

            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });

        return subScene;
    }

    /**
     * Genera la geometría tridimensional analizando la ecuación con el Parser punto por punto.
     */
    public void actualizarSuperficie(List<Token> tokensPostfijos, boolean modoEsqueleto) {
        grupoRotacion.getChildren().clear(); // Limpiamos renders previos

        TriangleMesh malla = new TriangleMesh();

        int divisiones = 50; // Densidad de la cuadrícula
        double minX = -5.0, maxX = 5.0;
        double minY = -5.0, maxY = 5.0;
        double escalaVisual = 3.0; // Multiplicador para darle relieve estético en pantalla

        double pasoX = (maxX - minX) / divisiones;
        double pasoY = (maxY - minY) / divisiones;

        // 1. Agregar Vértices (Coordenadas x, y, z)
        for (int i = 0; i <= divisiones; i++) {
            double x = minX + i * pasoX;
            for (int j = 0; j <= divisiones; j++) {
                double y = minY + j * pasoY;

                // Evaluamos dinámicamente con tu Parser de Dijkstra
                double z = 0.0;
                try {
                    z = EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, x, y, 0.0, 2.0);
                } catch (Exception e) {
                    // Si hay indeterminaciones numéricas, dejamos la altura plana en cero
                }

                // JavaFX requiere los componentes planos seguidos: x, z, y (z es la profundidad nativa)
                malla.getPoints().addAll((float)x * 2.0f, (float)z * (float)escalaVisual, (float)y * 2.0f);
            }
        }

        // 2. Coordenadas de Textura (Requeridas por JavaFX para compilar la geometría, aunque usemos un color plano)
        malla.getTexCoords().addAll(0f, 0f);

        // 3. Mapear las Caras (Definición de triángulos conectando los índices de los vértices)
        for (int i = 0; i < divisiones; i++) {
            for (int j = 0; j < divisiones; j++) {
                int p00 = i * (divisiones + 1) + j;
                int p01 = p00 + 1;
                int p10 = (i + 1) * (divisiones + 1) + j;
                int p11 = p10 + 1;

                // Triángulo 1 de la celda cuadrangular
                malla.getFaces().addAll(p00, 0, p10, 0, p01, 0);
                // Triángulo 2 de la celda cuadrangular
                malla.getFaces().addAll(p01, 0, p10, 0, p11, 0);
            }
        }

        MeshView meshView = new MeshView(malla);

        // Estilo visual de la superficie
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.web("#316cf4")); // Color azul tecnológico idéntico a tus bordes
        material.setSpecularColor(Color.WHITE);

        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE); // Permite ver la superficie por arriba y por abajo

        // Alternar entre sólido sombreado y red de líneas (Wireframe)
        if (modoEsqueleto) {
            meshView.setDrawMode(DrawMode.LINE);
        } else {
            meshView.setDrawMode(DrawMode.FILL);
        }

        grupoRotacion.getChildren().add(meshView);
    }

    public Group getGrupoRotacion() { return grupoRotacion; }
    public Rotate getRotacionX() { return rotacionX; }
    public Rotate getRotacionZ() { return rotacionZ; }
}