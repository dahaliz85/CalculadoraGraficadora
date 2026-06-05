package com.biomath3d;

import com.biomath3d.controller.IntroController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stageIntro) throws Exception {
        // Apunta correctamente a la carpeta ui exclusiva de resources
        FXMLLoader loaderIntro = new FXMLLoader(getClass().getResource("/com/biomath3d/ui/view/IntroView.fxml"));
        Parent rootIntro = loaderIntro.load();

        stageIntro.setScene(new Scene(rootIntro));
        stageIntro.initStyle(StageStyle.UNDECORATED); // Quita los botones de cerrar/minimizar del Intro
        stageIntro.centerOnScreen();
        stageIntro.show();

        IntroController introController = loaderIntro.getController();
        introController.iniciarCarga(() -> {
            try{
                stageIntro.close();
                Stage stageMain = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/biomath3d/ui/view/MainView.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 1200, 800);
                stageMain.setTitle("BioMath 3D - Calculadora Gráfica");
                stageMain.setScene(scene);

                stageMain.initStyle(StageStyle.UNDECORATED);

                root.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    stageMain.setX(event.getScreenX() - xOffset);
                    stageMain.setY(event.getScreenY() - yOffset);
                });

                // Carga el icono desde la ubicación exclusiva de resources
                try {
                    stageMain.getIcons().add(new Image(getClass().getResourceAsStream("/com/biomath3d/ui/img/logo.png")));
                } catch (Exception e) {
                    System.out.println("Nota: Icono no encontrado.");
                }

                stageMain.initStyle(javafx.stage.StageStyle.UNDECORATED);
                stageMain.show();
            }catch (Exception e) {
                System.out.println("Error al levantar la interfaz principal: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}