package com.biomath3d.controller;

import javafx.scene.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

/**
 * Controlador especializado en la gestión del lienzo tridimensional.
 * Estado: Rejilla base estática y nítida.
 */
public class MeshController {

    private SubScene subSceneUnica;

    private final Group contenedorRaiz = new Group();
    private final Group grupoRotacion = new Group();
    private final PerspectiveCamera camara = new PerspectiveCamera(true);

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
        camara.setTranslateZ(-35);
        contenedorRaiz.getChildren().add(camara);
    }

    public void inicializarLienzo3D(StackPane centerContainer) {
        if (centerContainer == null) return;

        if (subSceneUnica == null) {
            subSceneUnica = new SubScene(contenedorRaiz, 780, 480, true, SceneAntialiasing.BALANCED);
            subSceneUnica.setFill(Color.web("#14161d"));
            subSceneUnica.setCamera(camara);

            grupoRotacion.getTransforms().addAll(rotacionX, rotacionY);

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

        dibujarMallaLimpia();
    }

    public void dibujarMallaLimpia() {
        grupoRotacion.getChildren().clear();

        int divisiones = 30;
        float anchoX = 28.5f;
        float altoY = 17.5f;

        float pasoX = (anchoX * 2) / divisiones;
        float pasoY = (altoY * 2) / divisiones;

        PhongMaterial materialGrid = new PhongMaterial();
        materialGrid.setDiffuseColor(Color.web("#3a3e4b"));
        materialGrid.setSpecularColor(Color.web("#5a5f73"));

        double radioLinea = 0.04;

        // 1. LÍNEAS VERTICALES
        double alturaCilindroV = altoY * 2;
        for (int i = 0; i <= divisiones; i++) {
            double posX = -anchoX + (i * pasoX);
            Cylinder lineaVertical = new Cylinder(radioLinea, alturaCilindroV);
            lineaVertical.setMaterial(materialGrid);
            lineaVertical.setTranslateX(posX);
            lineaVertical.setTranslateY(0);
            lineaVertical.setTranslateZ(0);
            grupoRotacion.getChildren().add(lineaVertical);
        }

        // 2. LÍNEAS HORIZONTALES
        double longitudCilindroH = anchoX * 2;
        for (int j = 0; j <= divisiones; j++) {
            double posY = -altoY + (j * pasoY);
            Cylinder lineaHorizontal = new Cylinder(radioLinea, longitudCilindroH);
            lineaHorizontal.setMaterial(materialGrid);
            lineaHorizontal.setRotate(90);
            lineaHorizontal.setTranslateX(0);
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