package com.example.trabajofinaluf3.Tcp;

import javafx.scene.layout.GridPane;

import java.io.Serializable;
import java.util.ArrayList;

public class Tauler extends GridPane implements Serializable{
    int[][]  tablero ; //0=agua  1=Barco
    boolean barcosvivos;

    ArrayList<Barco> barcos;

    int barcs;
    int hit=0;

    public Tauler(){
        tablero=new int[9][9];
        barcos=new ArrayList<>();
        barcosvivos=true;
        barcs=0;
    }


    public int hit(char a,int b){
        if(barcosRestantes()){
            return 5;
        }
        switch (a){
            case 'A':
                if(tablero[1][b]==1){++hit;tablero[1][b]=0;return 1;
                }
                else return 0;
            case 'B':
                if(tablero[2][b]==1){++hit;tablero[2][b]=0;return 1;
                }
                else return 0;
            case 'C':
                if(tablero[3][b]==1){++hit; tablero[3][b]=0;return 1;
                }
                else return 0;
            case 'D':
                if(tablero[4][b]==1){++hit;tablero[4][b]=0;return 1;
                }
                else return 0;
            case 'E':
                if(tablero[5][b]==1){++hit;tablero[5][b]=0;return 1;
                }
                else return 0;
            case 'F':
                if(tablero[6][b]==1){++hit;tablero[6][b]=0;return 1;
                }
                else return 0;
            case 'G':
                if(tablero[7][b]==1){++hit; tablero[7][b]=0; return 1;
                }
                else return 0;
            case 'H':
                if(tablero[8][b]==1){++hit;tablero[8][b]=0;return 1;
                }
                else return 0;
        }
        return -1;
    }

    public void colocarBarco(Barco barco) {
        for (int i = 0; i < barco.getTamany(); i++) {
            if (barco.isHorizontal() && barco.getPosy() + i < 9) {
                tablero[barco.getPosy()][barco.getPosx()+i] = 1;
            } else if (!barco.isHorizontal() && barco.getPosx() + i < 9) {
                tablero[barco.getPosy()+i][barco.getPosx()] = 1;
            } else {
                break; // Si el barco se sale del tablero, detenemos la colocación
            }
        }
    }

    public boolean barcosRestantes(){
        if(hit==12){
            return true;
        }
        else return false;
    }


    public boolean verificarPosicionBarco(Barco barco, int fila, int columna, boolean orientacionVertical) {
        int longitudBarco = barco.getTamany();
        int filaFin = fila;
        int columnaFin = columna;

        // Si la orientación es vertical, se verifica si el barco cabe hacia abajo
        if (orientacionVertical) {
            filaFin += longitudBarco - 1;
        }
        // Si la orientación es horizontal, se verifica si el barco cabe hacia la derecha
        else {
            columnaFin += longitudBarco - 1;
        }

        // Se comprueba que el barco no se salga del tablero
        if (fila < 1 || columna < 1 || filaFin > 10 || columnaFin > 10) {
            return false;
        }

        // Se comprueba que no haya otro barco en las casillas por las que pasará el barco
        for (int i = fila-1; i <= filaFin-1; i++) {
            for (int j = columna-1; j <= columnaFin-1; j++) {
                if (tablero[i][j] != 0) {
                    return false;
                }
            }
        }

        // Si la colocación del barco es válida, actualizamos la posición del barco
        return true;
    }

    public void imprimirTauler(){
        for (int[] ints : tablero) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print(ints[j]);
            }
            System.out.println();
        }
    }

    public void addBarcos(ArrayList<BarcoImageView> toArray) {
      toArray.forEach(barcoImageView -> barcos.add(new Barco(barcoImageView.getVida(),barcoImageView.getTamany(),barcoImageView.getOrientacion(),barcoImageView.getPosy(),barcoImageView.getPosx())));
    }
    public int[][] getTablero() {
        return tablero;
    }

}
