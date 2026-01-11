package ec.edu.sistemalicencias.view;

import ec.edu.sistemalicencias.controller.UsuarioController;
import ec.edu.sistemalicencias.model.entities.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminView extends JFrame {
    private final UsuarioController controller;
    private JTable tblUsers;
    private DefaultTableModel modelo;
    private JButton btnCrear, btnEditar, btnEliminar, btnReporte, btnSalir;

    public AdminView(UsuarioController controller, Usuario usuario) {
        this.controller = controller;

        setTitle("ADMINISTRACIÓN DE USUARIOS - ANT");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentesManual();
        configurarEventos();
        cargarTabla();
    }

    private void inicializarComponentesManual() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JPanel panelAcciones = new JPanel(new GridLayout(10, 1, 0, 10));
        panelAcciones.setPreferredSize(new Dimension(220, 0));
        panelAcciones.setBackground(new Color(245, 245, 245));
        panelAcciones.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblMenu = new JLabel("ACCIONES", JLabel.CENTER);
        lblMenu.setFont(new Font("Arial", Font.BOLD, 18));

        btnCrear = crearBotonMenu("CREAR USUARIO", new Color(40, 167, 69));
        btnEditar = crearBotonMenu("EDITAR SELECCIONADO", new Color(0, 123, 255));
        btnEliminar = crearBotonMenu("ELIMINAR", new Color(220, 53, 69));
        btnReporte = crearBotonMenu("GENERAR REPORTE", new Color(108, 117, 125));
        btnSalir = crearBotonMenu("CERRAR SESIÓN", Color.DARK_GRAY);

        panelAcciones.add(lblMenu);
        panelAcciones.add(new JSeparator());
        panelAcciones.add(btnCrear);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(Box.createVerticalGlue());
        panelAcciones.add(btnReporte);
        panelAcciones.add(btnSalir);

        String[] columnas = {"ID", "USUARIO", "NOMBRE", "ROL", "ESTADO"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblUsers = new JTable(modelo);
        tblUsers.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tblUsers);

        mainPanel.add(panelAcciones, BorderLayout.WEST);
        mainPanel.add(scroll, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private JButton crearBotonMenu(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void configurarEventos() {
        // CREAR: El controlador se encarga de abrir el formulario
        btnCrear.addActionListener(e -> {
            controller.abrirFormularioUsuario(this, null);
            cargarTabla();
        });

        // EDITAR
        btnEditar.addActionListener(e -> {
            int fila = tblUsers.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario.");
                return;
            }
            Long id = (Long) modelo.getValueAt(fila, 0);
            // El controlador busca al usuario y abre el formulario
            controller.prepararEdicionUsuario(this, id);
            cargarTabla();
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tblUsers.getSelectedRow();
            if (fila != -1) {
                Long id = (Long) modelo.getValueAt(fila, 0);
                String nombre = (String) modelo.getValueAt(fila, 1);

                int r = JOptionPane.showConfirmDialog(this, "¿Desea eliminar a " + nombre + "?");
                if (r == JOptionPane.YES_OPTION) {
                    controller.eliminarUsuario(id);
                    cargarTabla();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.");
            }
        });

        btnReporte.addActionListener(e -> {
            controller.generarReporteUsuarios(this);
        });

        btnSalir.addActionListener(e -> salirAplicacion());
    }

    private void salirAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Salir", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();

            new LoginView(new UsuarioController()).setVisible(true);
        }
    }


    public void cargarTabla() {
        modelo.setRowCount(0);
        List<Usuario> lista = controller.obtenerListaUsuarios();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getUsername(),
                    u.getNombreCompleto(), // Agregamos el nombre completo que incluimos en la entidad
                    u.getRol(),
                    u.isActivo() ? "ACTIVO" : "INACTIVO"
            });
        }
    }
}