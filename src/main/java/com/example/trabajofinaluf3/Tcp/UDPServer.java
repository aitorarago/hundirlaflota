package com.example.trabajofinaluf3.Tcp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    private final int port;
    private Jugador[] jugadors;
    private int resp;

   private int[] jugada;


    public UDPServer(int port) {
        this.port = port;
        jugadors = new Jugador[2];
        jugadors[0]= new Jugador();
        jugadors[1]= new Jugador();
    }

    public void listen() {
        DatagramSocket serverSocket;
        try {
            serverSocket = new DatagramSocket(port);
            byte[] buffer = new byte[1024];

            boolean turno = false;
            int jug = 0;

            int id = 1;
            while (jug < 2) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet);
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                ByteArrayInputStream bs = new ByteArrayInputStream(packet.getData());
                ObjectInputStream os = new ObjectInputStream(bs);

                Jugador jugador = new Jugador();
                jugador.setID(id);
                jugador.setIp(clientAddress);
                jugador.setPuerto(clientPort);
                jugador.setTauler((Tauler) os.readObject());

                // Enviamos el ID al jugador
                byte[] sendData = Integer.toString(id).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                if (turno) {
                    jugadors[0] = jugador;
                   jugadors[0].setTurno(true);
                    System.out.println("j1 conectado");

                } else {
                    jugadors[1] = jugador;
                   jugadors[1].setTurno(false);
                    System.out.println("j2 conectado");
                }
                jug++;
                id++;

                if (jug == 2) {
                    System.out.println("EMPEZAMOS LA PARTIDA");
                } else {
                    System.out.println("Esperando segundo jugador...");
                    turno=!turno;
                }
            }

            while (true) {
                byte[] sendData;
                if (jugadors[0].isTurno()) {
                    System.out.println("turno1");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    serverSocket.receive(packet);
                    sendData = procesarSolicitud(packet.getData(), jugadors[0], jugadors[1]);

                } else {
                    System.out.println("turno2");
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    serverSocket.receive(packet);
                    sendData = procesarSolicitud(packet.getData(), jugadors[1], jugadors[0]);
                }

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, jugadors[0].getIp(), jugadors[0].getPuerto());
                serverSocket.send(sendPacket);
                DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, jugadors[1].getIp(), jugadors[1].getPuerto());
                serverSocket.send(sendPacket1);

                cambiarTurnos();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void cambiarTurnos() {
        jugadors[0].setTurno(!jugadors[0].isTurno());
        jugadors[1].setTurno(!jugadors[1].isTurno());
    }

    private byte[] procesarSolicitud(byte[] solicitud, Jugador jugador,Jugador jugador2) throws IOException {
        String respuesta = new String(solicitud, 0, solicitud.length);
        char[] e = respuesta.toCharArray();
        char x = e[0];
        int y = Character.getNumericValue(e[1]);
        int i = jugador2.getTauler().hit(x, y);
        System.out.println(i);
        this.resp=i;
        jugada = new Jugada(i,y,x).envjug();
        int[] r = jugada;
        byte[] byteArray = new byte[r.length];
        for (int j = 0; j < r.length; j++) {
            byteArray[j] = (byte) r[j];
        }
        return byteArray;
    }

    public static void main(String[] args) {
        UDPServer server = new UDPServer(5566);
        server.listen();
    }
}
