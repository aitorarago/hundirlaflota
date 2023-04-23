package com.example.trabajofinaluf3;


import com.example.trabajofinaluf3.Tcp.Cliente;
import com.example.trabajofinaluf3.Tcp.Jugada;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Text turnos;
    public TextField casillastxt;
    public Button buttoncasilla;
    public Text comunicador;
    @FXML
    private GridPane tablero1;
    @FXML
    private GridPane tablero2;
    boolean miturno;
    int[][] tablero1_1;
    int[][] tablero2_2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tablero1.setGridLinesVisible(true);
            tablero2.setGridLinesVisible(true);





            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("en 5 segundos empiza la partida");
            alert.show();
            Thread.sleep(5000);

            alert.setOnCloseRequest(dialogEvent -> {
                rellenarTableroPersonal();
                try {
                    continuarJugando();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void continuarJugando() throws IOException {
        this.miturno=Cliente.getCliente().getId().compareTo("2")==0;
      continuarPartida();
    }

    private boolean fin() {
        return Cliente.getCliente().getRespuesta().compareTo("5")==0;
    }

    private void continuarPartida() throws IOException {
        if (!fin()) {
            if (miturno) {
                comunicador.setText("Tu turno! Esperando tu movimiento...");
                buttoncasilla.setDisable(false);
                Miturno();
            } else {
                buttoncasilla.setDisable(true);
                comunicador.setText("Turno del rival. Esperando su movimiento...");
                TurnoRival();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("partida finalizada");
            alert.setContentText("FELICIDADES");
        }
    }


    private void Miturno() {
        System.out.println("miturno");
        buttoncasilla.setOnMouseClicked(mouseEvent ->{
            try {
                Cliente.getCliente().runClient(casillastxt.getText());
                casillastxt.clear();
                miturno=false;
                pintarTableroRival(Cliente.getCliente().getJugada());
                continuarPartida();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void TurnoRival() throws IOException {
        System.out.println("turno rival");
        // Esperar el movimiento del oponente
        Cliente.getCliente().runClientnoturno();
        pintarmitablero(Cliente.getCliente().getJugada());
        miturno=true;
        continuarPartida();
    }

    private void pintarTableroRival(Jugada jugada) {
        int i = jugada.getI();
        int y = jugada.getY();
        int x = jugada.getX();
        for (Node node : tablero2.getChildren()) {
            if (node instanceof Button) {
                int posX = GridPane.getColumnIndex(node);
                int posY = GridPane.getRowIndex(node);
                if (posX == x && posY == y) {
                    if (i == 0) {
                        node.setStyle("-fx-background-color: blue;");
                    } else {
                        node.setStyle("-fx-background-color: red;");
                    }
                    break;
                }
            }
        }


        tablero2.requestLayout();

    }
    private void pintarmitablero(Jugada jugada) {
        int i = jugada.getI();
        int y = jugada.getY();
        int x = jugada.getX();
        System.out.println(x+" "+y);

        // Calcular la posición en el GridPane según las coordenadas x e y
        for (Node node : tablero1.getChildren()) {
            if (node instanceof Button) {
                int posX = GridPane.getColumnIndex(node);
                int posY = GridPane.getRowIndex(node);
                if (posX == x && posY == y) {
                    if (i == 0) {
                        node.setStyle("-fx-background-color: blue;");
                    } else {
                        node.setStyle("-fx-background-color: red;");
                    }
                    break;
                }
            }
        }


        tablero1.requestLayout();
    }


    public void rellenarTableroPersonal() {
        tablero1_1 = Cliente.getCliente().getTauler().getTablero();
        tablero1.getChildren().clear(); // Eliminar los botones existentes en el GridPane

        // Añadir botones al GridPane y pintarlos según el valor en el array de enteros
        for(int i = 0; i < tablero1_1.length; i++) {
            for(int j = 0; j < tablero1_1[0].length; j++) {
                // Si el índice de la fila o de la columna es igual a cero, se omite la creación del botón
                if (i == 0 || j == 0) {
                    if(i==0 & j==0){
                        continue;
                    }
                    if (i == 0) {
                        Button label1 = new Button(Character.toString((char) ('A' + j - 1))); // Convertir el índice a una letra mayúscula
                        Button label2 = new Button(Character.toString((char) ('A' + j - 1))); // Convertir el índice a una letra mayúscula
                        label1.setPrefSize(48, 48);
                        label1.setAlignment(Pos.CENTER); // Centrar el texto
                        label2.setPrefSize(48, 48);
                        label2.setAlignment(Pos.CENTER); // Centrar el texto
                        tablero1.add(label1, j, i); // añadir la etiqueta al GridPane
                        tablero2.add(label2, j, i); // añadir la etiqueta al GridPane

                    }
                    if(j==0){
                        Button button1 = new Button(Integer.toString(i));
                        Button button2 = new Button(Integer.toString(i));
                        button1.setPrefSize(48, 48);
                        button2.setPrefSize(48, 48);
                        button1.setAlignment(Pos.CENTER);
                        button2.setAlignment(Pos.CENTER);
                        tablero1.add(button1, j, i); // añadir el botón al GridPane
                        tablero2.add(button2, j, i); // añadir la etiqueta al GridPane
                    }
                }
                else {
                    Button button1 = new Button();
                    Button button2 = new Button();
                    button1.setPrefSize(48, 48);
                    button2.setPrefSize(48, 48);

                    button2.setStyle("-fx-background-color: white");
                    if(tablero1_1[i][j] == 0) {
                        button1.setStyle("-fx-background-color: white"); // si el valor es 0, pintar de blanco
                    } else {
                        button1.setStyle("-fx-background-color: grey"); // si el valor es 1, pintar de gris
                    }

                    tablero1.add(button1, j, i); // añadir el botón al GridPane
                    tablero2.add(button2, j, i); // añadir el botón al GridPane
                }
            }

    }
        tablero1.requestLayout(); // actualizar la vista del GridPane
        tablero2.requestLayout();
        refreshStage();
    }

    public void refreshStage(){
        HelloApplication.pantallaprincipal.show();
    }






}






