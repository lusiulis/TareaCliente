package tareacliente.control;

import java.util.Observer;
import tareacliente.model.ModeloCliente;
import tareacliente.views.Casilla;

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

    public void pintar(Integer x, Integer y) {
        modelo.pintar(x, y);
    }

    public void pintarOponente(Integer x, Integer y) {
        modelo.pintarOponente(x, y);

    }
    
    public void setCasilla(int x,int y,Casilla m){
        modelo.setCasilla(x, y, m);
    }

    public Casilla[][] getCasillas(){
        return modelo.getCasillas();
    }
    
    public Casilla getCasilla(int x,int y){
        return modelo.getCasilla(x, y);
    }
}
