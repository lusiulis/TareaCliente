/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacliente.model;

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

    private final Integer PUERTOOPONENTE;
    private final Integer PUERTOPROPIO = 421;
    private final String DIRECCIONIP;
    public static Boolean continuar = true;
    public static ServerSocket serverSocket;
    private ConexionCliente conexion;

    public ModeloCliente(String ip, Integer puerto) {
        this.DIRECCIONIP = ip;
        this.PUERTOOPONENTE = puerto;
    }
    
     public void empezarAEscuchar(GestorCliente gestor) {
        try {
            serverSocket = new ServerSocket(PUERTOPROPIO);
            continuar = true;
            //esperar conexion
            conexion = new ConexionCliente(null,gestor);
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
        } catch (IOException ex) {
        }

    }

}
