package com.example.trabajofinaluf3.Tcp;

import java.net.InetAddress;

public class Jugador {

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID;
    private Tauler tauler;
    private boolean turno;

    public Tauler getTauler() {
        return tauler;
    }
    Jugador(){

    }
    public void setTauler(Tauler tauler) {
        this.tauler = tauler;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public Jugador(Tauler tauler, InetAddress ip, int puerto) {
        this.tauler = tauler;
        this.ip = ip;
        this.puerto = puerto;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }
    public boolean isTurno() {
        return turno;
    }

    private InetAddress ip;
    private int puerto;

}
