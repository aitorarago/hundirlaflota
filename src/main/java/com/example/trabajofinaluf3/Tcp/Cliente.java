package com.example.trabajofinaluf3.Tcp;

import java.io.*;
import java.net.*;

public class Cliente implements Serializable {
    private static Cliente instance = null;
    InetAddress serverIP;
    int serverPort;
    DatagramSocket socket;
    Tauler tauler;
     Jugada jugada;
    boolean firstrequest= true;

    public String getId() {
        return respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    String respuesta="";

    private Cliente(int i,Tauler tauler) throws IOException {
        this.tauler=tauler;
        this.init("localhost",i);
        this.runClient();
    }

    public static void getInstance(int i, Tauler tauler) throws IOException {
        if (instance == null) {
            instance = new Cliente(i, tauler);
        }
    }

    public static Cliente getCliente(){
        return instance;
    }

    public void init(String host, int port) throws SocketException,
            UnknownHostException {
        serverIP = InetAddress.getByName(host);
        serverPort = port;
        socket = new DatagramSocket();
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];
        byte [] sendingData = new byte[1024];

        //a l'inici
        if(firstrequest)sendingData = getFirstRequest();
        //el servidor atén el port indefinidament
        DatagramPacket packet = new DatagramPacket(sendingData,
                sendingData.length,
                serverIP,
                serverPort);
        //enviament de la resposta
        socket.send(packet);

        //creació del paquet per rebre les dades
        packet = new DatagramPacket(receivedData, 4);
        //espera de les dades
        socket.receive(packet);
        //processament de les dades rebudes i obtenció de la resposta
        getfirstDataToRequest(packet.getData(),packet.getLength());
    }

    public void runClient(String resp) throws IOException{
        byte [] receivedData = new byte[1024];
        byte [] sendingData ;

        //a l'inici
       sendingData = getSecondRequest(resp);
        //el servidor atén el port indefinidament
        DatagramPacket packet = new DatagramPacket(sendingData,
                sendingData.length,
                serverIP,
                serverPort);
        //enviament de la resposta
        socket.send(packet);

        //creació del paquet per rebre les dades
        packet = new DatagramPacket(receivedData, 4);
        //espera de les dades
        socket.receive(packet);
        //processament de les dades rebudes i obtenció de la resposta
        try {
            getDataToRequest(packet.getData(),packet.getLength());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void runClientnoturno() throws IOException{
        byte [] sendingData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(sendingData,
                sendingData.length,
                serverIP,
                serverPort);
        //espera de les dades
        socket.receive(packet);
        //processament de les dades rebudes i obtenció de la resposta
        try {
            getDataToRequest(packet.getData(),packet.getLength());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private void getfirstDataToRequest(byte[] data, int length) {
        String id = new String(data,0,length);
        this.respuesta=id;
        System.out.println("id= "+id);
    }


    private void getDataToRequest(byte[] data, int length1) throws ClassNotFoundException {
        int[] intArray = new int[3];
        for (int i = 0; i < 3; i++) {
            intArray[i] = data[i] & 0xFF;
        }
        int a = intArray[0];
        int b = intArray[1];
        int x = intArray[2];
        jugada = new Jugada(a, b, x);
        System.out.println(a);
    }


    public Jugada getJugada() {
        return jugada;
    }

    public byte[] getSecondRequest(String respuesta){
        return respuesta.getBytes();
    }



    public byte[] getFirstRequest() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(tauler);
        return bos.toByteArray();
    }
    public Tauler getTauler(){
        return tauler;
    }
}
