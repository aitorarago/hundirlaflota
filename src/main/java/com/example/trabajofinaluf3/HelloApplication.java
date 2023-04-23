package com.example.trabajofinaluf3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage pantallaprincipal;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("postTaulers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1019, 750);
        stage.setTitle("Hundir la flota!");
        stage.setScene(scene);
        stage.show();
        pantallaprincipal=stage;

    }

    public static void main(String[] args) {
        launch();
    }

    public static void cambiarEscena(String escena) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(escena));
        Scene scene = new Scene(fxmlLoader.load());
        pantallaprincipal.setScene(scene);
        System.out.println("escenacambiada");
    }
}