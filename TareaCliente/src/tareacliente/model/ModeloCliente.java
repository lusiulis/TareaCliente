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
import tareacliente.views.Casilla;

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
    private final Color colorPropio = Color.BLUE; //color que pinto mis casillas
    private final Color colorOponente = Color.RED; //color que pinto las casillas de mi oponente
    private Boolean miTurno; //booleano para saber si puedo jugar o ocupo esperar a que el oponente juege

    private final Casilla casillas[][];

    public ModeloCliente(String ip, Integer puerto) {
        this.DIRECCIONIP = ip;
        this.PUERTOOPONENTE = puerto;
        miTurno = true;
        casillas = new Casilla[8][8];
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
            Casilla casilla = new Casilla(x, y, colorPropio);
            casilla.setClick(true);
            casillas[x][y] = casilla;
            this.miTurno = false;
            enviarMensaje(x + "," + y);
            
            if (Gane(x, y)) {
                setChanged();
                notifyObservers(casillas);
                
                setChanged();
                notifyObservers("Gane propio");
            } else {
                setChanged();
                notifyObservers(casillas);
                
                setChanged();
                notifyObservers("Ha jugado");
            }
        } else {
            setChanged();
            notifyObservers("Debe esperar su turno...");
        }

    }

    public void pintarOponente(Integer x, Integer y) {
        miTurno = true;
        Casilla casilla = new Casilla(x, y, colorOponente);
        casilla.setClick(true);
        casillas[x][y] = casilla;

        setChanged();
        notifyObservers(casillas);
        if (Gane(x, y)) {
            setChanged();
            notifyObservers("Gane enemigo");
        } else {

            setChanged();
            notifyObservers("Su turno");
        }
    }

    public void setCasilla(int x, int y, Casilla m) {
        casillas[x][y] = m;
    }

    public Casilla getCasilla(int x, int y) {
        return casillas[x][y];
    }

    public Casilla[][] getCasillas() {
        return casillas;
    }

    private boolean Gane(int x, int y) {
        boolean gane;

        gane = x <= 4;
        for (int i = x; i < x + 4 && gane; i++) {
            if (!casillas[i][y].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = x >= 3;
        for (int i = x; i > x - 4 && gane; i--) {
            if (!casillas[i][y].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = y <= 4;
        for (int i = y; i < y + 4 && gane; i++) {
            if (!casillas[x][i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = y >= 3;
        for (int i = y; i > y - 4 && gane; i--) {
            if (!casillas[x][i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = x >= 3 && y >= 3;
        for (int i = 0; i < 4 && gane; i++) {
            if (!casillas[x - i][y - i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = x >= 3 && y <= 4;
        for (int i = 0; i < 4 && gane; i++) {
            if (!casillas[x - i][y + i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = x <= 4 && y <= 4;
        for (int i = 0; i < 4 && gane; i++) {
            if (!casillas[x + i][y + i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }
        if (gane) {
            return gane;
        }

        gane = x <= 4 && y >= 3;
        for (int i = 0; i < 4 && gane; i++) {
            if (!casillas[x + i][y - i].getColorCasilla().equals(casillas[x][y].getColorCasilla())) {
                gane = false;
            }
        }

        return gane;
    }
}
