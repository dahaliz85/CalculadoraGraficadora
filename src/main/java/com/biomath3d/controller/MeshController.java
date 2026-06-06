package com.biomath3d.controller;

import com.biomath3d.math.parser.EvaluadorExpresion;
import com.biomath3d.math.parser.Token;
import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import java.util.List;

/**
 * Controlador especializado encargado de gestionar el lienzo tridimensional,
 * la navegación por cámara y el renderizado de la malla poligonal en BioMath 3D.
 */
public class MeshController {

    private SubScene subSceneUnica;

    private final Group contenedorRaiz = new Group();
    private final Group grupoRotacion = new Group();
    private final PerspectiveCamera camara = new PerspectiveCamera(true);
    private final Rotate rotacionX = new Rotate(60, Rotate.X_AXIS);
    private final Rotate rotacionZ = new Rotate(45, Rotate.Z_AXIS);

    private double mouseOldX, mouseOldY;
    private MeshView meshViewActual;

    public MeshController() {
        contenedorRaiz.getChildren().add(grupoRotacion);
        configurarCamara();
    }

    private void configurarCamara() {
        camara.setNearClip(0.1);
        camara.setFarClip(1000.0);
        camara.setTranslateZ(-35); // Distancia para encuadrar la superficie completa
        contenedorRaiz.getChildren().add(camara);
    }

    /**
     * Inicializa la SubScene 3D interactiva y la acopla dentro del contenedor de la interfaz.
     */
    public void inicializarLienzo3D(StackPane centerContainer) {
        if (centerContainer == null) return;

        if (subSceneUnica == null) {
            subSceneUnica = new SubScene(contenedorRaiz, 780, 480, true, SceneAntialiasing.BALANCED);
            subSceneUnica.setFill(Color.web("#14161d"));
            subSceneUnica.setCamera(camara);

            grupoRotacion.getTransforms().addAll(rotacionX, rotacionZ);

            subSceneUnica.setOnMousePressed(me -> {
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            });

            subSceneUnica.setOnMouseDragged(me -> {
                double mouseDeltaX = me.getSceneX() - mouseOldX;
                double mouseDeltaY = me.getSceneY() - mouseOldY;

                rotacionZ.setAngle(rotacionZ.getAngle() - mouseDeltaX * 0.3);
                rotacionX.setAngle(rotacionX.getAngle() + mouseDeltaY * 0.3);

                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            });
        }

        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(subSceneUnica);
    }

    /**
     * Reconstruye los polígonos de la superficie matemática iterando sobre la cuadrícula.
     * Se utiliza la ruta absoluta del paquete para blindar la compatibilidad de argumentos.
     */
    public void renderizarSuperficie(List<Token> tokensPostfijos, boolean modoWireframe, com.biomath3d.math.parser.DetectorVariables detector) {
        grupoRotacion.getChildren().clear();

        TriangleMesh malla = new TriangleMesh();

        int divisiones = 50;
        double minX = -5.0, maxX = 5.0;
        double minY = -5.0, maxY = 5.0;
        double escalaVisualHeight = 3.0;

        double pasoX = (maxX - minX) / divisiones;
        double pasoY = (maxY - minY) / divisiones;

        // --- PARTE 1: CÁLCULO DE VÉRTICES (Orden unificado por filas) ---
        for (int i = 0; i <= divisiones; i++) {
            double x = minX + i * pasoX;
            for (int j = 0; j <= divisiones; j++) {
                double y = minY + j * pasoY;

                double z = 0.0;
                // LÍNEA 96 CORREGIDA: Se pasa el detector con la firma calificada exacta
                z = EvaluadorExpresion.evaluarPostfijo(tokensPostfijos, x, y, 0.0, 2.0, detector);

                // Escalamos horizontalmente y aplicamos la inversión vertical nativa de JavaFX
                float coordX = (float) x * 1.5f;
                float coordY = (float) -z * (float) escalaVisualHeight;
                float coordZ = (float) y * 1.5f;

                malla.getPoints().addAll(coordX, coordY, coordZ);
            }
        }

        // --- PARTE 2: COORDENADAS DE TEXTURA ---
        malla.getTexCoords().addAll(0f, 0f);

        // --- PARTE 3: TRIANGULACIÓN Y ORIENTACIÓN DE CARAS ---
        int filaAncho = divisiones + 1;
        for (int i = 0; i < divisiones; i++) {
            for (int j = 0; j < divisiones; j++) {
                int superiorIzquierda = i * filaAncho + j;
                int superiorDerecha   = superiorIzquierda + 1;
                int inferiorIzquierda = (i + 1) * filaAncho + j;
                int inferiorDerecha   = inferiorIzquierda + 1;

                malla.getFaces().addAll(superiorIzquierda, 0, inferiorIzquierda, 0, superiorDerecha, 0);
                malla.getFaces().addAll(superiorDerecha, 0, inferiorDerecha, 0, inferiorIzquierda, 0);
            }
        }

        meshViewActual = new MeshView(malla);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.web("#316cf4")); // Azul BioMath
        material.setSpecularColor(Color.WHITE);

        meshViewActual.setMaterial(material);
        meshViewActual.setCullFace(CullFace.NONE);

        ajustarModoDibujo(modoWireframe);
        grupoRotacion.getChildren().add(meshViewActual);
    }

    public void ajustarModoDibujo(boolean modoWireframe) {
        if (meshViewActual == null) return;
        if (modoWireframe) {
            meshViewActual.setDrawMode(DrawMode.LINE);
        } else {
            meshViewActual.setDrawMode(DrawMode.FILL);
        }
    }

    public void reiniciarVista() {
        rotacionX.setAngle(60);
        rotacionZ.setAngle(45);
    }
}