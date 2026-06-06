package com.biomath3d.controller;

import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

/**
 * Controlador especializado en la gestión del lienzo tridimensional.
 * Configuración en dos dimensiones puras (Plano X-Y) para actuar como una rejilla frontal perfecta.
 */
public class MeshController {

    private SubScene subSceneUnica;

    private final Group contenedorRaiz = new Group();
    private final Group grupoRotacion = new Group();
    private final PerspectiveCamera camara = new PerspectiveCamera(true);

    // Vista completamente plana y de frente (Rotaciones en cero absoluto)
    private final Rotate rotacionX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotacionY = new Rotate(0, Rotate.Y_AXIS);

    private double mouseOldX, mouseOldY;

    public MeshController() {
        contenedorRaiz.getChildren().add(grupoRotacion);
        configurarCamara();
    }

    private void configurarCamara() {
        camara.setNearClip(0.1);
        camara.setFarClip(1000.0);
        camara.setTranslateZ(-35); // Ajuste de distancia óptimo para encuadrar la rejilla en la pantalla
        contenedorRaiz.getChildren().add(camara);
    }

    /**
     * Inicializa la SubScene y la acopla dentro del contenedor de la interfaz.
     */
    public void inicializarLienzo3D(StackPane centerContainer) {
        if (centerContainer == null) return;

        if (subSceneUnica == null) {
            // Vinculamos el lienzo con las dimensiones exactas de tu cuadro negro
            subSceneUnica = new SubScene(contenedorRaiz, 780, 480, true, SceneAntialiasing.BALANCED);
            subSceneUnica.setFill(Color.web("#14161d"));
            subSceneUnica.setCamera(camara);

            grupoRotacion.getTransforms().addAll(rotacionX, rotacionY);

            // Control de arrastre opcional (por ahora se mantendrá estático de frente)
            subSceneUnica.setOnMousePressed(me -> {
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            });

            subSceneUnica.setOnMouseDragged(me -> {
                double mouseDeltaX = me.getSceneX() - mouseOldX;
                double mouseDeltaY = me.getSceneY() - mouseOldY;

                rotacionY.setAngle(rotacionY.getAngle() + mouseDeltaX * 0.3);
                rotacionX.setAngle(rotacionX.getAngle() - mouseDeltaY * 0.3);

                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            });
        }

        centerContainer.getChildren().clear();
        centerContainer.getChildren().add(subSceneUnica);

        // Generamos la cuadrícula frontal de inmediato
        dibujarMallaLimpia();
    }

    /**
     * Dibuja una rejilla cartesiana frontal utilizando cilindros tridimensionales ultra delgados.
     * Esto garantiza máxima nitidez, eliminando el efecto borroso o difuminado en la pantalla.
     */
    private void dibujarMallaLimpia() {
        grupoRotacion.getChildren().clear();

        int divisiones = 30; // Densidad de la cuadrícula

        // Límites espaciales calibrados para cubrir el StackPane (780x480)
        float anchoX = 28.5f;
        float altoY = 17.5f;

        float pasoX = (anchoX * 2) / divisiones;
        float pasoY = (altoY * 2) / divisiones;

        // Material gris técnico sólido y brillante para que resalte con nitidez
        PhongMaterial materialGrid = new PhongMaterial();
        materialGrid.setDiffuseColor(Color.web("#3a3e4b")); // Gris base
        materialGrid.setSpecularColor(Color.web("#5a5f73")); // Brillo sutil para definición

        // Radio del cilindro (actúa como el grosor del trazo de la línea)
        double radioLinea = 0.04;

        // --- 1. DIBUJAR LÍNEAS VERTICALES CORREGIDAS ---
        double alturaCilindroV = altoY * 2;
        for (int i = 0; i <= divisiones; i++) {
            double posX = -anchoX + (i * pasoX);

            // Creamos un cilindro vertical
            javafx.scene.shape.Cylinder lineaVertical = new javafx.scene.shape.Cylinder(radioLinea, alturaCilindroV);
            lineaVertical.setMaterial(materialGrid);

            // Posicionamos en el plano frontal
            lineaVertical.setTranslateX(posX);
            lineaVertical.setTranslateY(0); // Centrado verticalmente
            lineaVertical.setTranslateZ(0);

            grupoRotacion.getChildren().add(lineaVertical);
        }

        // --- 2. DIBUJAR LÍNEAS HORIZONTALES CORREGIDAS ---
        double longitudCilindroH = anchoX * 2;
        for (int j = 0; j <= divisiones; j++) {
            double posY = -altoY + (j * pasoY);

            // Creamos el cilindro horizontal (JavaFX los crea verticales por defecto)
            javafx.scene.shape.Cylinder lineaHorizontal = new javafx.scene.shape.Cylinder(radioLinea, longitudCilindroH);
            lineaHorizontal.setMaterial(materialGrid);

            // Lo rotamos 90 grados en el eje Z para acostarlo horizontalmente
            lineaHorizontal.setRotate(90);

            // Posicionamos en el plano frontal
            lineaHorizontal.setTranslateX(0); // Centrado horizontalmente
            lineaHorizontal.setTranslateY(posY);
            lineaHorizontal.setTranslateZ(0);

            grupoRotacion.getChildren().add(lineaHorizontal);
        }
    }

    public void reiniciarVista() {
        rotacionX.setAngle(0);
        rotacionY.setAngle(0);
    }
}