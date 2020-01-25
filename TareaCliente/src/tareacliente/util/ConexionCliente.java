/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacliente.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import tareacliente.control.GestorCliente;
import tareacliente.model.ModeloCliente;
import static tareacliente.model.ModeloCliente.serverSocket;

/**
 *
 * @author Kevin F
 */
public class ConexionCliente extends Thread {

    private Socket socket;
    private GestorCliente gestor;

    public ConexionCliente(Socket socket, GestorCliente gestor) {
        this.socket = socket;
        this.gestor = gestor;
    }

    @Override
    public void run() {
        if (socket == null) {
            try {
                while (ModeloCliente.continuar) {
                    ConexionCliente conexion;
                    conexion = new ConexionCliente(serverSocket.accept(), gestor);
                    conexion.start();
                }
                serverSocket.close();
            } catch (Exception ex) {
            }

        } else {
            try {
                //utiliza un buffered reader para leer lo que llego del socket
                String mensajeRecibido = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                String[] posicion = mensajeRecibido.split(",");
                gestor.pintarOponente(Integer.valueOf(posicion[0]), Integer.valueOf(posicion[1]));

            } catch (Exception ex) {
            }
        }

    }

    static {
        System.out.println("*******************************\n\tcliente esperando respuesta del servidor\n*******************************");

    }
}
