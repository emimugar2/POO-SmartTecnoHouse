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

public class VistaPrincipal extends JFrame {

    private DefaultTableModel modeloTablaSensores;
    private JTable tablaSensores;
    private DefaultTableModel modeloTablaActuadores;
    private JTable tablaActuadores;
    private JComboBox<String> comboActuadores;
    private JComboBox<String> comboAcciones;
    private JButton btnEjecutarAccion;
    private JPanel panelReglas;
    private JCheckBox[] checksReglas;
    private JButton btnActualizarSensores;
    private JButton btnAplicarReglas;
    private JButton btnGuardar;
    private JButton btnCargar;
    private JLabel lblEstado;

    public VistaPrincipal() {
        configurarVentana();
        construirInterfaz();
    }

    private void configurarVentana() {
        setTitle("Smart TecnoHouse - Control domótico");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
    }

    private void construirInterfaz() {
        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Smart TecnoHouse");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        JTabbedPane pestanias = new JTabbedPane();
        pestanias.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        pestanias.addTab("Sensores", crearPanelSensores());
        pestanias.addTab("Actuadores", crearPanelActuadores());
        pestanias.addTab("Reglas", crearPanelReglas());
        add(pestanias, BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSensores() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] cols = {"ID", "Nombre", "Estado actual", "Unidad"};
        modeloTablaSensores = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaSensores = new JTable(modeloTablaSensores);
        tablaSensores.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaSensores.setRowHeight(28);
        tablaSensores.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centradoCabecera = (DefaultTableCellRenderer) tablaSensores.getTableHeader().getDefaultRenderer();
        centradoCabecera.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cols.length; i++) tablaSensores.getColumnModel().getColumn(i).setCellRenderer(centrado);
        panel.add(new JScrollPane(tablaSensores), BorderLayout.CENTER);
        btnActualizarSensores = new JButton("Actualizar sensores");
        btnActualizarSensores.setFont(new Font("SansSerif", Font.BOLD, 13));
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnActualizarSensores);
        panel.add(panelBoton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelActuadores() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] cols = {"ID", "Nombre", "Estado actual", "Acciones posibles"};
        modeloTablaActuadores = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaActuadores = new JTable(modeloTablaActuadores);
        tablaActuadores.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaActuadores.setRowHeight(28);
        tablaActuadores.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        DefaultTableCellRenderer centrado2 = new DefaultTableCellRenderer();
        centrado2.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centradoCab2 = (DefaultTableCellRenderer) tablaActuadores.getTableHeader().getDefaultRenderer();
        centradoCab2.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cols.length; i++) tablaActuadores.getColumnModel().getColumn(i).setCellRenderer(centrado2);
        panel.add(new JScrollPane(tablaActuadores), BorderLayout.CENTER);
        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelControl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Control manual", TitledBorder.CENTER, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12)));
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
        btnAplicarReglas = new JButton("Aplicar reglas activas");
        btnAplicarReglas.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAplicarReglas.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panelReglas;
    }

    private JPanel crearPanelInferior() {
        JPanel panelSur = new JPanel(new BorderLayout());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        btnGuardar = new JButton("Guardar estado");
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 12));
        botones.add(btnGuardar);
        btnCargar = new JButton("Cargar estado");
        btnCargar.setFont(new Font("SansSerif", Font.BOLD, 12));
        botones.add(btnCargar);
        panelSur.add(botones, BorderLayout.CENTER);
        lblEstado = new JLabel("Sistema iniciado correctamente.");
        lblEstado.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(3, 10, 5, 10));
        panelSur.add(lblEstado, BorderLayout.SOUTH);
        return panelSur;
    }

    public void actualizarTablaSensores(List<String[]> datos) { modeloTablaSensores.setRowCount(0); for (String[] f : datos) modeloTablaSensores.addRow(f); }
    public void actualizarTablaActuadores(List<String[]> datos) { modeloTablaActuadores.setRowCount(0); for (String[] f : datos) modeloTablaActuadores.addRow(f); }
    public void configurarComboActuadores(String[] nombres, String[] ids) { comboActuadores.removeAllItems(); for (String n : nombres) comboActuadores.addItem(n); }
    public void actualizarComboAcciones(String[] acciones) { comboAcciones.removeAllItems(); for (String a : acciones) comboAcciones.addItem(a); }

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

    public void mostrarEstado(String msg) { lblEstado.setText(msg); }
    public void mostrarMensaje(String msg, String titulo) { JOptionPane.showMessageDialog(this, msg, titulo, JOptionPane.INFORMATION_MESSAGE); }
    public void mostrarError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
    public int getActuadorSeleccionado() { return comboActuadores.getSelectedIndex(); }
    public String getAccionSeleccionada() { return (String) comboAcciones.getSelectedItem(); }
    public JCheckBox[] getChecksReglas() { return checksReglas; }

    public void addActualizarSensoresListener(ActionListener l) { btnActualizarSensores.addActionListener(l); }
    public void addEjecutarAccionListener(ActionListener l) { btnEjecutarAccion.addActionListener(l); }
    public void addAplicarReglasListener(ActionListener l) { btnAplicarReglas.addActionListener(l); }
    public void addGuardarEstadoListener(ActionListener l) { btnGuardar.addActionListener(l); }
    public void addCargarEstadoListener(ActionListener l) { btnCargar.addActionListener(l); }
    public void addSeleccionActuadorListener(ActionListener l) { comboActuadores.addActionListener(l); }
    public void addCerrarVentanaListener(WindowListener l) { addWindowListener(l); }
    public void addReglaCheckListener(ActionListener l) { if (checksReglas != null) for (JCheckBox cb : checksReglas) cb.addActionListener(l); }
}
