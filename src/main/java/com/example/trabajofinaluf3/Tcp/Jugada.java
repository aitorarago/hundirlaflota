package com.example.trabajofinaluf3.Tcp;

import java.io.Serializable;

public class Jugada implements Serializable {
    int i;
    int y;
    int x;

    @Override
    public String toString() {
        return "Jugada{" +
                "resultado=" + i +
                ", y=" + y +
                ", x=" + x +
                '}';
    }

    Jugada(int ii, int yy, char xx){
        this.i=ii;
        this.y=yy;
        switch (xx) {
            case 'A' -> x=1;
            case 'B' -> x=2;
            case 'C' -> x=3;
            case 'D' -> x=4;
            case 'E' -> x=5;
            case 'F' -> x=6;
            case 'G' -> x=7;
            case 'H' -> x=8;
        }
    }
    Jugada(int ii,int yy,int x){
        this.i=ii;
        this.y=yy;
        this.x=x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return i;
    }
    public int[] envjug(){
        int[] e = new int[3];
        e[0]=i;
        e[1]=y;
        e[2]=x;
        return e;
    }
}
