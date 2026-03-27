package controlador;

import modelo.*;
import vista.VistaPrincipal;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/*
 * Controlador del patron MVC.
 * Es el que conecta el Modelo con la Vista: escucha los eventos
 * de la GUI, llama al modelo para hacer la logica y despues
 * actualiza la vista con los datos nuevos.
 */
public class Controlador {

    private SmartTecnoHouse modelo;
    private VistaPrincipal vista;

    public Controlador(SmartTecnoHouse modelo, VistaPrincipal vista) {
        this.modelo = modelo;
        this.vista = vista;

        // cargar datos iniciales en la vista
        refrescarSensores();
        refrescarActuadores();
        refrescarReglas();

        // registrar los listeners de los botones
        registrarEventos();
    }

    private void registrarEventos() {
        vista.addActualizarSensoresListener(e -> onActualizarSensores());
        vista.addEjecutarAccionListener(e -> onEjecutarAccion());
        vista.addAplicarReglasListener(e -> onAplicarReglas());
        vista.addGuardarEstadoListener(e -> onGuardar());
        vista.addCargarEstadoListener(e -> onCargar());
        vista.addSeleccionActuadorListener(e -> onCambioActuador());

        // para preguntar al cerrar la ventana
        vista.addCerrarVentanaListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCerrar();
            }
        });
    }

    // ---------- Handlers de eventos ----------

    private void onActualizarSensores() {
        modelo.actualizarSensores();
        refrescarSensores();
        vista.mostrarEstado("Sensores actualizados correctamente.");
    }

    private void onEjecutarAccion() {
        int idx = vista.getActuadorSeleccionado();
        if (idx < 0) {
            vista.mostrarError("Selecciona un actuador primero.");
            return;
        }
        String accion = vista.getAccionSeleccionada();
        if (accion == null) {
            vista.mostrarError("Selecciona una accion.");
            return;
        }

        Actuador act = modelo.getActuadores().get(idx);
        try {
            modelo.ejecutarAccionManual(act.getID(), accion);
            refrescarActuadores();
            vista.mostrarEstado("Ejecutada accion '" + accion + "' en " + act.getNombre());
        } catch (IllegalArgumentException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void onAplicarReglas() {
        // primero actualizamos que reglas estan marcadas
        JCheckBox[] checks = vista.getChecksReglas();
        if (checks != null) {
            for (JCheckBox cb : checks) {
                modelo.setReglaActiva(cb.getActionCommand(), cb.isSelected());
            }
        }

        modelo.aplicarReglas();
        refrescarActuadores();
        refrescarSensores();
        vista.mostrarEstado("Reglas aplicadas correctamente.");
    }

    private void onGuardar() {
        modelo.guardarEstado();
        vista.mostrarEstado("Estado guardado en datos/estado.json");
        vista.mostrarMensaje("Estado guardado correctamente.", "Guardar");
    }

    private void onCargar() {
        boolean ok = modelo.cargarEstado();
        if (ok) {
            refrescarSensores();
            refrescarActuadores();
            refrescarReglas();
            vista.mostrarEstado("Estado cargado desde datos/estado.json");
            vista.mostrarMensaje("Estado cargado correctamente.", "Cargar");
        } else {
            vista.mostrarError("No se encontro el fichero datos/estado.json");
        }
    }

    private void onCambioActuador() {
        int idx = vista.getActuadorSeleccionado();
        if (idx >= 0 && idx < modelo.getActuadores().size()) {
            Actuador act = modelo.getActuadores().get(idx);
            vista.actualizarComboAcciones(act.getAccionesPosibles());
        }
    }

    private void onCerrar() {
        int opcion = vista.preguntarGuardarAntesDeSalir();
        if (opcion == JOptionPane.YES_OPTION) {
            modelo.guardarEstado();
            System.exit(0);
        } else if (opcion == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
        // si da a cancelar no hacemos nada
    }

    // ---------- Metodos para refrescar la vista con datos del modelo ----------

    private void refrescarSensores() {
        List<String[]> datos = new ArrayList<>();
        for (Sensor s : modelo.getSensores()) {
            datos.add(new String[]{
                s.getID(), s.getNombre(), s.getEstadoActual(), s.getUnidad()
            });
        }
        vista.actualizarTablaSensores(datos);
    }

    private void refrescarActuadores() {
        List<String[]> datos = new ArrayList<>();
        List<Actuador> actuadores = modelo.getActuadores();
        String[] nombres = new String[actuadores.size()];
        String[] ids = new String[actuadores.size()];

        for (int i = 0; i < actuadores.size(); i++) {
            Actuador a = actuadores.get(i);
            datos.add(new String[]{
                a.getID(), a.getNombre(), a.getEstadoActual(),
                String.join(", ", a.getAccionesPosibles())
            });
            nombres[i] = a.getNombre();
            ids[i] = a.getID();
        }

        vista.actualizarTablaActuadores(datos);
        vista.configurarComboActuadores(nombres, ids);

        // poner las acciones del primer actuador en el combo
        if (!actuadores.isEmpty()) {
            vista.actualizarComboAcciones(actuadores.get(0).getAccionesPosibles());
        }
    }

    private void refrescarReglas() {
        Map<String, String> infoReglas = new LinkedHashMap<>();
        for (Regla r : modelo.getReglas()) {
            infoReglas.put(r.getNombre(), r.getDescripcion());
        }
        vista.configurarReglas(infoReglas, modelo.getReglasActivas());

        // volver a registrar los listeners de los checkboxes
        vista.addReglaCheckListener(e -> {
            JCheckBox cb = (JCheckBox) e.getSource();
            modelo.setReglaActiva(cb.getActionCommand(), cb.isSelected());
        });
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
