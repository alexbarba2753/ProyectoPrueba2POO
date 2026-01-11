package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.UsuarioController;
import ec.edu.sistemalicencias.model.entities.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;

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

        btnCrear = crearBotonMenu("CREAR USUARIO");

        btnEditar = crearBotonMenu("EDITAR SELECCIONADO");


        btnEliminar = crearBotonMenu("ELIMINAR");


        btnReporte = crearBotonMenu("GENERAR REPORTE");

        btnSalir = crearBotonMenu("CERRAR SESIÓN");

        configurarEstilos();

        panelAcciones.add(lblMenu);
        panelAcciones.add(new JSeparator());
        panelAcciones.add(btnCrear);
        panelAcciones.add(btnEditar);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(Box.createVerticalGlue());
        panelAcciones.add(btnReporte);
        panelAcciones.add(btnSalir);

        String[] columnas = {"ID", "USUARIO", "NOMBRE", "EMAIL", "ROL", "ESTADO"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblUsers = new JTable(modelo);
        tblUsers.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(tblUsers);

        mainPanel.add(panelAcciones, BorderLayout.WEST);
        mainPanel.add(scroll, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void configurarEstilos() {

        // Estilo del botón de Crear (color verde)
        btnCrear.setBackground(new Color(40, 167, 69));
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setOpaque(true);
        btnCrear.setBorderPainted(false);

        // Estilo del botón de Editar (color azul)
        btnEditar.setBackground(new Color(0, 123, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setOpaque(true);
        btnEditar.setBorderPainted(false);

        // Estilo del botón de Eliminar (color rojo)
        btnEliminar.setBackground(new Color(220, 53, 69));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);

        // Estilo del botón de salir (color rojo)
        btnSalir.setBackground(new Color(160, 30, 40));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setOpaque(true);
        btnSalir.setBorderPainted(false);



    }

    private JButton crearBotonMenu(String texto) {
        JButton btn = new JButton(texto);
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
                    u.getNombreCompleto(),
                    u.getEmail(),
                    u.getRol(),
                    u.isActivo() ? "ACTIVO" : "INACTIVO"
            });
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("ACCIONES");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCrear = new JButton();
        btnCrear.setText("CREAR USUARIO");
        panel3.add(btnCrear, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEditar = new JButton();
        btnEditar.setText("EDITAR SELECCIONADO");
        panel3.add(btnEditar, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEliminar = new JButton();
        btnEliminar.setText("ELIMINAR");
        panel3.add(btnEliminar, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel2.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnReporte = new JButton();
        btnReporte.setText("GENERAR REPORTE");
        panel4.add(btnReporte, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSalir = new JButton();
        btnSalir.setText("CERRAR SESIÓN");
        panel4.add(btnSalir, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel5, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tblUsers = new JTable();
        panel5.add(tblUsers, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }
}