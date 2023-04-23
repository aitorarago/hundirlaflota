package com.example.trabajofinaluf3.Tcp;


import java.io.Serializable;

public class Barco implements Serializable {
    private int vida;
    private boolean vivo;
    private int tamany;
    private char orientacion;
    private int posy,posx;
    private boolean colocado;

    public Barco(int vida, int tamany, char orientacion, int posy, int posx) {
        this.vida = vida;
        this.tamany = tamany;
        this.orientacion = orientacion;
        this.posy = posy;
        this.posx = posx;
        vivo=true;
    }
    public Barco(int v,int t,char o){
        this.vida=v;
        this.tamany=t;
        this.vivo=true;
        this.orientacion=o;

    }
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public int getTamany() {
        return tamany;
    }

    public void setTamany(int tamany) {
        this.tamany = tamany;
    }

    public void setOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public char getOrientacion() {
        return orientacion;
    }

    public boolean isHorizontal() {
        return orientacion=='h';
    }

    public void rotarBarco() {
        if(orientacion=='h')orientacion='v';
        else orientacion='h';
    }
    public boolean colocado(){
        return colocado;
    }

    public void setColocado(boolean colocado) {
        this.colocado = colocado;
    }
}
