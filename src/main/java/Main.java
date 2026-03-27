import modelo.SmartTecnoHouse;
import vista.VistaPrincipal;
import controlador.Controlador;

import javax.swing.SwingUtilities;
import java.io.File;

// Punto de entrada de la aplicacion. Crea el MVC y arranca la GUI.
public class Main {

    public static void main(String[] args) {

        // crear las carpetas si no existen
        new File("datos").mkdirs();
        new File("logs").mkdirs();

        // lanzar en el hilo de Swing (EDT) como dice la documentacion
        SwingUtilities.invokeLater(() -> {

            // obtener el modelo (Singleton)
            SmartTecnoHouse modelo = SmartTecnoHouse.getInstancia();

            // intentar cargar el estado que se guardo la ultima vez
            boolean cargado = modelo.cargarEstado();

            // crear la vista
            VistaPrincipal vista = new VistaPrincipal();

            // crear el controlador que conecta modelo y vista
            Controlador controlador = new Controlador(modelo, vista);

            // mostrar la ventana
            controlador.iniciar();

            if (cargado) {
                vista.mostrarEstado("Estado anterior restaurado desde datos/estado.json");
            }
        });
    }
}
