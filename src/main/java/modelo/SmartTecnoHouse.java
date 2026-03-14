package modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Clase principal del modelo - Patron Singleton
 * Solo hay una instancia del sistema domotico en toda la aplicacion.
 * Aqui se guardan todos los sensores, actuadores y reglas.
 */
public class SmartTecnoHouse {

    private static SmartTecnoHouse instancia;

    private List<Sensor> sensores;
    private List<Actuador> actuadores;
    private List<Regla> reglas;
    private Map<String, Boolean> reglasActivas;

    private SmartTecnoHouse() {
        sensores = new ArrayList<>();
        actuadores = new ArrayList<>();
        reglas = new ArrayList<>();
        reglasActivas = new LinkedHashMap<>();

        crearDispositivos();
        crearReglas();
    }

    public static synchronized SmartTecnoHouse getInstancia() {
        if (instancia == null) {
            instancia = new SmartTecnoHouse();
        }
        return instancia;
    }

    private void crearDispositivos() {
        sensores.add(new SensorTemperatura());
        sensores.add(new SensorLuz());
        sensores.add(new SensorPresencia());

        actuadores.add(new ActuadorBombilla());
        actuadores.add(new ActuadorVentilador());
    }

    private void crearReglas() {
        Regla r1 = new ReglaVentilacionConfortable();
        Regla r2 = new ReglaIluminacionAutomatica();

        reglas.add(r1);
        reglas.add(r2);

        reglasActivas.put(r1.getNombre(), true);
        reglasActivas.put(r2.getNombre(), true);
    }

    public void actualizarSensores() {
        for (Sensor s : sensores) {
            s.actualizarValor();
        }
    }

    public void aplicarReglas() {
        LogService log = LogService.getInstancia();
        for (Regla regla : reglas) {
            if (Boolean.TRUE.equals(reglasActivas.get(regla.getNombre()))) {
                regla.aplicar(sensores, actuadores);
                log.registrarRegla(regla.getNombre());
            }
        }
    }

    public void ejecutarAccionManual(String actuadorId, String accion) {
        for (Actuador a : actuadores) {
            if (a.getID().equals(actuadorId)) {
                a.ejecutarAccion(accion);
                LogService.getInstancia().registrar(actuadorId, accion, a.getEstadoActual());
                return;
            }
        }
    }

    public void setReglaActiva(String nombreRegla, boolean activa) {
        reglasActivas.put(nombreRegla, activa);
    }

    public void guardarEstado() {
        Persistencia.guardarEstado(sensores, actuadores, reglasActivas);
    }

    public boolean cargarEstado() {
        return Persistencia.cargarEstado(sensores, actuadores, reglasActivas);
    }

    public List<Sensor> getSensores() { return sensores; }
    public List<Actuador> getActuadores() { return actuadores; }
    public List<Regla> getReglas() { return reglas; }
    public Map<String, Boolean> getReglasActivas() { return reglasActivas; }
}
