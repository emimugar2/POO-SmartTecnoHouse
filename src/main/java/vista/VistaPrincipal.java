package vista;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Map;

/*
 * Ventana principal de la aplicacion (patron MVC - Vista).
 * Muestra los datos del modelo mediante tablas y permite
 * interactuar con botones y combos. No tiene logica de negocio,
 * todo se delega al Controlador mediante listeners.
 */
public class VistaPrincipal extends JFrame {

    // componentes del panel de sensores
    private DefaultTableModel modeloTablaSensores;
    private JTable tablaSensores;

    // componentes del panel de actuadores
    private DefaultTableModel modeloTablaActuadores;
    private JTable tablaActuadores;
    private JComboBox<String> comboActuadores;
    private JComboBox<String> comboAcciones;
    private JButton btnEjecutarAccion;

    // componentes del panel de reglas
    private JPanel panelReglas;
    private JCheckBox[] checksReglas;

    // botones generales
    private JButton btnActualizarSensores;
    private JButton btnAplicarReglas;
    private JButton btnGuardar;
    private JButton btnCargar;

    // barra de estado abajo del todo
    private JLabel lblEstado;

    public VistaPrincipal() {
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setTitle("Smart TecnoHouse - Control dom\u00f3tico");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // centrar en pantalla
        setLayout(new BorderLayout(10, 10));

        // intentar poner el look & feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // si falla no pasa nada, se queda con el de Java
        }
    }

    private void construirInterfaz() {
        // --- Titulo arriba ---
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        JLabel titulo = new JLabel("Control dom\u00f3tico");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // --- Pestañas en el centro ---
        JTabbedPane pestanias = new JTabbedPane();
        pestanias.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        pestanias.addTab("Sensores", crearPanelSensores());
        pestanias.addTab("Actuadores", crearPanelActuadores());
        pestanias.addTab("Reglas", crearPanelReglas());
        add(pestanias, BorderLayout.CENTER);

        // --- Botones y estado abajo ---
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSensores() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // tabla para mostrar los sensores
        String[] cols = {"ID", "Nombre", "Estado actual", "Unidad"};
        modeloTablaSensores = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaSensores = new JTable(modeloTablaSensores);
        tablaSensores.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaSensores.setRowHeight(28);
        tablaSensores.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        // centrar el texto de las celdas y las cabeceras
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centradoCabecera = (DefaultTableCellRenderer) tablaSensores.getTableHeader().getDefaultRenderer();
        centradoCabecera.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cols.length; i++) {
            tablaSensores.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        panel.add(new JScrollPane(tablaSensores), BorderLayout.CENTER);

        // boton para actualizar sensores
        btnActualizarSensores = new JButton("Actualizar sensores");
        btnActualizarSensores.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnActualizarSensores.setBackground(new Color(46, 204, 113));
        btnActualizarSensores.setForeground(Color.BLACK);
        btnActualizarSensores.setFocusPainted(false);
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizarSensores);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelActuadores() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // tabla
        String[] cols = {"ID", "Nombre", "Estado actual", "Acciones posibles"};
        modeloTablaActuadores = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaActuadores = new JTable(modeloTablaActuadores);
        tablaActuadores.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaActuadores.setRowHeight(28);
        tablaActuadores.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        DefaultTableCellRenderer centrado2 = new DefaultTableCellRenderer();
        centrado2.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centradoCab2 = (DefaultTableCellRenderer) tablaActuadores.getTableHeader().getDefaultRenderer();
        centradoCab2.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cols.length; i++) {
            tablaActuadores.getColumnModel().getColumn(i).setCellRenderer(centrado2);
        }
        panel.add(new JScrollPane(tablaActuadores), BorderLayout.CENTER);

        // panel de control manual
        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelControl.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Control manual",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12)));

        panelControl.add(new JLabel("Dispositivo:"));
        comboActuadores = new JComboBox<>();
        comboActuadores.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelControl.add(comboActuadores);

        panelControl.add(new JLabel("Accion:"));
        comboAcciones = new JComboBox<>();
        comboAcciones.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panelControl.add(comboAcciones);

        btnEjecutarAccion = new JButton("Ejecutar");
        btnEjecutarAccion.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnEjecutarAccion.setBackground(new Color(231, 76, 60));
        btnEjecutarAccion.setForeground(Color.BLACK);
        btnEjecutarAccion.setFocusPainted(false);
        panelControl.add(btnEjecutarAccion);

        panel.add(panelControl, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelReglas() {
        panelReglas = new JPanel();
        panelReglas.setLayout(new BoxLayout(panelReglas, BoxLayout.Y_AXIS));
        panelReglas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel info = new JLabel("Reglas de automatizacion (patron Strategy):");
        info.setFont(new Font("SansSerif", Font.BOLD, 15));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelReglas.add(info);
        panelReglas.add(Box.createVerticalStrut(15));

        // el boton de aplicar reglas se crea aqui pero los checkboxes
        // se añaden dinamicamente desde el controlador
        btnAplicarReglas = new JButton("Aplicar reglas activas");
        btnAplicarReglas.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAplicarReglas.setBackground(new Color(155, 89, 182));
        btnAplicarReglas.setForeground(Color.BLACK);
        btnAplicarReglas.setFocusPainted(false);
        btnAplicarReglas.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panelReglas;
    }

    private JPanel crearPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));

        btnGuardar = new JButton("Guardar estado");
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(52, 73, 94));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        botones.add(btnGuardar);

        btnCargar = new JButton("Cargar estado");
        btnCargar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCargar.setBackground(new Color(52, 73, 94));
        btnCargar.setForeground(Color.BLACK);
        btnCargar.setFocusPainted(false);
        botones.add(btnCargar);

        panelSur.add(botones, BorderLayout.CENTER);

        lblEstado = new JLabel("Sistema iniciado correctamente.");
        lblEstado.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(3, 10, 5, 10));
        panelSur.add(lblEstado, BorderLayout.SOUTH);

        return panelSur;
    }

    // ---------- Metodos que llama el Controlador para actualizar la vista ----------

    public void actualizarTablaSensores(List<String[]> datos) {
        modeloTablaSensores.setRowCount(0);
        for (String[] fila : datos) {
            modeloTablaSensores.addRow(fila);
        }
    }

    public void actualizarTablaActuadores(List<String[]> datos) {
        modeloTablaActuadores.setRowCount(0);
        for (String[] fila : datos) {
            modeloTablaActuadores.addRow(fila);
        }
    }

    public void configurarComboActuadores(String[] nombres, String[] ids) {
        comboActuadores.removeAllItems();
        for (String nombre : nombres) {
            comboActuadores.addItem(nombre);
        }
    }

    public void actualizarComboAcciones(String[] acciones) {
        comboAcciones.removeAllItems();
        for (String acc : acciones) {
            comboAcciones.addItem(acc);
        }
    }

    // crea los checkboxes de las reglas dinamicamente
    public void configurarReglas(Map<String, String> reglas, Map<String, Boolean> activas) {
        panelReglas.removeAll();

        JLabel info = new JLabel("Reglas de automatizacion (patron Strategy):");
        info.setFont(new Font("SansSerif", Font.BOLD, 15));
        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelReglas.add(info);
        panelReglas.add(Box.createVerticalStrut(15));

        checksReglas = new JCheckBox[reglas.size()];
        int i = 0;

        for (Map.Entry<String, String> entry : reglas.entrySet()) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            JCheckBox cb = new JCheckBox(entry.getKey());
            cb.setFont(new Font("SansSerif", Font.BOLD, 13));
            cb.setSelected(Boolean.TRUE.equals(activas.get(entry.getKey())));
            cb.setActionCommand(entry.getKey());
            checksReglas[i] = cb;

            JLabel desc = new JLabel("   " + entry.getValue());
            desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
            desc.setForeground(Color.DARK_GRAY);

            fila.add(cb, BorderLayout.WEST);
            fila.add(desc, BorderLayout.CENTER);
            panelReglas.add(fila);
            panelReglas.add(Box.createVerticalStrut(10));
            i++;
        }

        panelReglas.add(Box.createVerticalStrut(10));
        btnAplicarReglas.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelReglas.add(btnAplicarReglas);

        panelReglas.revalidate();
        panelReglas.repaint();
    }

    public void mostrarEstado(String msg) {
        lblEstado.setText(msg);
    }

    public void mostrarMensaje(String msg, String titulo) {
        JOptionPane.showMessageDialog(this, msg, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public int preguntarGuardarAntesDeSalir() {
        return JOptionPane.showConfirmDialog(this,
            "Desea guardar el estado antes de salir?",
            "Salir de Smart TecnoHouse",
            JOptionPane.YES_NO_CANCEL_OPTION);
    }

    // ---------- Getters para que el controlador lea la seleccion del usuario ----------

    public int getActuadorSeleccionado() { return comboActuadores.getSelectedIndex(); }
    public String getAccionSeleccionada() { return (String) comboAcciones.getSelectedItem(); }
    public JCheckBox[] getChecksReglas() { return checksReglas; }

    // ---------- Metodos para registrar listeners (los pone el Controlador) ----------

    public void addActualizarSensoresListener(ActionListener l) { btnActualizarSensores.addActionListener(l); }
    public void addEjecutarAccionListener(ActionListener l) { btnEjecutarAccion.addActionListener(l); }
    public void addAplicarReglasListener(ActionListener l) { btnAplicarReglas.addActionListener(l); }
    public void addGuardarEstadoListener(ActionListener l) { btnGuardar.addActionListener(l); }
    public void addCargarEstadoListener(ActionListener l) { btnCargar.addActionListener(l); }
    public void addSeleccionActuadorListener(ActionListener l) { comboActuadores.addActionListener(l); }
    public void addCerrarVentanaListener(WindowListener l) { addWindowListener(l); }

    public void addReglaCheckListener(ActionListener l) {
        if (checksReglas != null) {
            for (JCheckBox cb : checksReglas) {
                cb.addActionListener(l);
            }
        }
    }
}
