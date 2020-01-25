/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tareacliente.control;

import java.util.Observer;
import tareacliente.model.ModeloCliente;

/**
 *
 * @author Kevin
 */
public class GestorCliente {

    ModeloCliente modelo;

    public GestorCliente(String ip, Integer puerto) {
        modelo = new ModeloCliente(ip, puerto);
        modelo.empezarAEscuchar(this);
    }

    public void registrar(Observer observador) {
        modelo.addObserver(observador);
    }

    public void cerrarAplicacion() {
        modelo.deleteObservers();
        System.exit(0);
    }

    public void suprimir(Observer observador) {
        modelo.deleteObserver(observador);
        if (modelo.countObservers() == 0) {
            System.exit(0);
        }
    }

    public void mandarPorSocket(String mensaje) {
        modelo.enviarMensaje(mensaje);
    }

    public void pintar(Integer x, Integer y) {
        System.out.println("pintar:   " + x + " y:" + y);
    }

}
