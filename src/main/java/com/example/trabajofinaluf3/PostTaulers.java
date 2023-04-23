package com.example.trabajofinaluf3;

import com.example.trabajofinaluf3.Tcp.Barco;
import com.example.trabajofinaluf3.Tcp.BarcoImageView;
import com.example.trabajofinaluf3.Tcp.Cliente;
import com.example.trabajofinaluf3.Tcp.Tauler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class PostTaulers implements Initializable {
    @FXML
    private GridPane tablero;
    @FXML
    private ImageView barcogrande;
    @FXML
    private ImageView barcomediano;
    @FXML
    private ImageView barcomediano2;
    @FXML
    private ImageView barcopequenio;
    Tauler tauler = new Tauler();

    private final ArrayList<BarcoImageView> barcos = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BarcoImageView barcogr = new BarcoImageView( 4, 4, 'h',barcogrande);
        BarcoImageView barcom1 = new BarcoImageView( 3, 3, 'v',barcomediano);
        BarcoImageView barcom2 = new BarcoImageView( 3, 3, 'v',barcomediano2);
        BarcoImageView barcopq = new BarcoImageView( 2, 2, 'v',barcopequenio);
        barcos.add(barcogr);
        barcos.add(barcom1);
        barcos.add(barcom2);
        barcos.add(barcopq);
        tablero = new Tauler();
        ColumnConstraints col = new ColumnConstraints(50);
        tablero.getColumnConstraints().addAll(col, col, col, col, col, col, col, col, col);
        RowConstraints row = new RowConstraints(50);
        tablero.getRowConstraints().addAll(row, row, row, row, row, row, row, row, row);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("HUNDIR LA FLOTA!");
        alert.setContentText("""
                Bienvenido a hundir la flota, si has llegado hasta aqui,
                significa que tienes rival, en este juego la estrategia
                y la inteligencia són fundamentales, así que te invito
                a repartas bien los barcos en el mapa. Puedes rotar los
                barcos si los has seleccionado y clicando al boton rotate.
                    - El barco de arriba del todo ocupa 4 casillas.
                    - Los dos del medio son 3.
                    - El kayak son 2 casillas.
                        Buena suerte y que gane el mejor!!!""");
        alert.show();

        barcos.forEach(barco -> barco.getImageView().setOnMousePressed(mouseEvent -> {
            ImageView imagenCopia = barco.getImageView();
            imagenCopia.setOnMousePressed(event -> {
                // Verificar si se ha presionado el botón derecho del ratón
                if (event.isSecondaryButtonDown()) {
                    // Rotar la imagen copia 90 grados
                    if (barco.isHorizontal()) {
                        imagenCopia.setRotate(90);
                        barco.setOrientacion('v');
                    } else {
                        imagenCopia.setRotate(0);
                        barco.setOrientacion('h');
                    }
                }
                imagenCopia.setMouseTransparent(true);
            });
            Point2D puntoInicial = new Point2D(imagenCopia.getLayoutX(), imagenCopia.getLayoutY()); // Guardamos la posición inicial del barco
            imagenCopia.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY) {
                    // Si se hace clic con el botón izquierdo del ratón en la imagen del barco, se gira el barco
                    barco.rotarBarco();
                    barco.getImageView().setRotate(barco.isHorizontal() ? 0 : 90);
                }
            });
            imagenCopia.setOnMousePressed(event -> imagenCopia.setMouseTransparent(true));
            imagenCopia.setOnMouseDragged(event -> {
                //Actualizar la posición de la imagen copia según la posición del cursor del ratón
                //Obtener la posición del cursor del ratón en relación a la escena
                double cursorX = event.getSceneX();
                double cursorY = event.getSceneY();
                //Actualizar la posición de la imagen copia
                imagenCopia.setLayoutX(cursorX);
                imagenCopia.setLayoutY(cursorY);
            });

            imagenCopia.setOnMouseReleased(event -> {
                // Obtener la posición del cursor del ratón en relación al GridPane
                Point2D puntoEnGridPane = tablero.sceneToLocal(event.getSceneX(), event.getSceneY());

                // Calcular la fila y columna correspondiente a esas coordenadas
                int fila = (int) (puntoEnGridPane.getY() / 50) + 1;
                int columna = (int) (puntoEnGridPane.getX() / 50) + 1;

                // Comprobar si la posición es válida
                if (fila > 2 && fila < 10 && columna > 2 && columna < 10) {
                    // Comprobar si el barco cabe en la posición
                    if (tauler.verificarPosicionBarco(barco, fila-barco.getTamany(), columna-2, barco.isHorizontal())) {

                        System.out.println("Imagen soltada en columna " + (columna) + " fila " + (fila) + " " + barco.getTamany() + " " + barco.isHorizontal());
                        GridPane.setConstraints(imagenCopia, columna, fila);
                        barco.setColocado(true);
                        barco.setPosx(columna-2);
                        barco.setPosy(fila-barco.getTamany());
                        tauler.colocarBarco(barco);
                        if (todosColocados()) {
                            tauler.addBarcos(barcos);
                            Thread thread = new Thread(() -> {
                                try {
                                    Cliente.getInstance(5566,tauler);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            thread.start();
                            tauler.imprimirTauler();
                            try {
                                HelloApplication.cambiarEscena("hello-view.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        System.out.println((columna) + " " + (fila) + " " + barco.getTamany());
                        System.out.println("La imagen no está dentro del GridPane.");
                        // Si el barco no cabe en la posición, devolverlo a su posición original
                        imagenCopia.setLayoutX(puntoInicial.getX());
                        imagenCopia.setLayoutY(puntoInicial.getY());
                    }
                }
            });
        }));
    }

    public boolean todosColocados(){
        return barcos.stream().allMatch(Barco::colocado);
    }



}
