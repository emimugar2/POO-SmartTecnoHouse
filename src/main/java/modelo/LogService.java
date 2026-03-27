package modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * Servicio de logging con patron Singleton.
 * Escribe en logs/actuators.log cada vez que un actuador
 * cambia de estado o se aplica una regla.
 */
public class LogService {

    private static LogService instancia;
    private static final String RUTA_LOG = "logs/actuators.log";
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // constructor privado -> Singleton
    private LogService() {}

    public static synchronized LogService getInstancia() {
        if (instancia == null) {
            instancia = new LogService();
        }
        return instancia;
    }

    // registra una accion de un actuador
    public void registrar(String dispositivoId, String accion, String resultado) {
        String ahora = LocalDateTime.now().format(FORMATO_FECHA);
        String linea = "[" + ahora + "] Dispositivo: " + dispositivoId
                + " | Accion: " + accion + " | Estado: " + resultado;
        escribirLinea(linea);
    }

    // registra que se ha aplicado una regla
    public void registrarRegla(String nombreRegla) {
        String ahora = LocalDateTime.now().format(FORMATO_FECHA);
        String linea = "[" + ahora + "] Regla aplicada: " + nombreRegla;
        escribirLinea(linea);
    }

    private void escribirLinea(String linea) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA_LOG, true))) {
            pw.println(linea);
        } catch (IOException e) {
            System.err.println("Error escribiendo en el log: " + e.getMessage());
        }
    }
}
