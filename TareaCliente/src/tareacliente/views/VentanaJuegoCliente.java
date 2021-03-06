package tareacliente.views;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import tareacliente.control.GestorCliente;

public class VentanaJuegoCliente extends JFrame implements Observer {

    private final GestorCliente gestor;
    private final BarraEstado barra;

    public VentanaJuegoCliente(GestorCliente gestor) {
        super("Connect 4 - Cliente");
        this.gestor = gestor;
        barra = new BarraEstado();
        configurar();
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 instanceof String) {
            String texto = o1.toString();
            if (texto.equals("Gane propio")) {
                JOptionPane.showMessageDialog(null, "Usted ha ganado");
                gestor.cerrarAplicacion();
            } else {
                if (texto.equals("Gane enemigo")) {
                    JOptionPane.showMessageDialog(null, "Usted ha perdido");
                    gestor.cerrarAplicacion();
                }
            }
            barra.mostrarMensaje(o1.toString());
        } else {
            if (o1 instanceof Casilla[][]) {
                ActualizarTablero(this.getContentPane(), (Casilla[][]) o1);
            }
        }

    }

    private void configurar() {
        Ajustar(getContentPane());
        setResizable(true);
        setSize(900, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void Ajustar(Container c) {
        c.setLayout(new GridLayout(9, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla casilla = new Casilla(j, i, Color.WHITE);
                casilla.Actualizar();
                AgregarEscucha(casilla);
                gestor.setCasilla(j, i, casilla);
                c.add(casilla);
            }
        }
        c.add(barra);
    }

    private void ActualizarTablero(Container c, Casilla casillas[][]) {
        c.removeAll();
        c.setLayout(new GridLayout(9, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casilla casilla = casillas[j][i];
                AgregarEscucha(casilla);
                c.add(casilla);
            }
        }
        c.add(barra);
    }

    private void AgregarEscucha(Casilla casilla) {
        casilla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!casilla.isClick()) {
                    gestor.pintar(casilla.getPosx(), casilla.getPosy());
                    casilla.setClick(true);
                }
            }
        });
    }

    public void init() {
        gestor.registrar(this);
        setVisible(true);
    }
}
