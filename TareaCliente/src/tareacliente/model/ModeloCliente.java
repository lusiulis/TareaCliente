/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacliente.model;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import tareacliente.control.GestorCliente;
import tareacliente.util.ConexionCliente;

/**
 *
 * @author Kevin
 */
public class ModeloCliente extends Observable {

    private final Integer PUERTOOPONENTE;  //puerto del oponente
    private final Integer PUERTOPROPIO = 421; //puerto donde se va abrir el socket
    private final String DIRECCIONIP; //direccion ip del oponente
    public static Boolean continuar = true; //booleano para saber si continuar escuchando
    public static ServerSocket serverSocket; //socket donde se escucha
    private ConexionCliente conexion; //instancia de clase que maneja conexiones
    private Color colorPropio = Color.BLUE; //color que pinto mis casillas
    private Color colorOponente = Color.RED; //color que pinto las casillas de mi oponente
    private Boolean miTurno; //booleano para saber si puedo jugar o ocupo esperar a que el oponente juege

    public ModeloCliente(String ip, Integer puerto) {
        this.DIRECCIONIP = ip;
        this.PUERTOOPONENTE = puerto;
        miTurno = true;
    }

    public void empezarAEscuchar(GestorCliente gestor) {
        try {
            serverSocket = new ServerSocket(PUERTOPROPIO);
            continuar = true;
            //esperar conexion
            conexion = new ConexionCliente(null, gestor);
            conexion.start();
        } catch (Exception ex) {
            System.err.println("Error al iniciar a escuchar: " + ex.getLocalizedMessage());
        }

    }

    public void enviarMensaje(String aEnviar) {
        try {
            Socket socket = new Socket(DIRECCIONIP, PUERTOOPONENTE);
            OutputStream os;
            os = socket.getOutputStream();
            os.write(aEnviar.getBytes());
            os.flush();
            socket.close();
            miTurno = false;
        } catch (IOException ex) {
        }

    }

    public void pintar(Integer x, Integer y) {
        if (miTurno) {
            //pintarcasilla
            enviarMensaje(x + "," + y);
            //revisar si hay ganadores
            System.out.println("usted jugo");
        } else {
            System.out.println("no es su turno");
            setChanged();
            notifyObservers("Debe esperar su turno...");
        }

    }

    public void pintarOponente(Integer x, Integer y) {
        //pintarcasilla
        //if(revisarGanadores){
        //miTurno=true;
        //}
        miTurno = true;
        System.out.println("el oponente jugo");
        setChanged();
        notifyObservers();

        setChanged();
        notifyObservers("Es tu turno...");
    }

}
