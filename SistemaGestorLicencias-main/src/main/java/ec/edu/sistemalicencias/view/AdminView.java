package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class AdminView extends JFrame {
    // Componentes principales
    private JPanel mainPanel;
    private JTextField txtNewUser;
    private JTextField txtNewPass;
    private JComboBox<String> cmbNewRol;
    private JButton btnGuardarUser, btnEditarUser, btnEliminarUser, btnLimpiarUser;
    private JTable tblUsers;
    private JButton btnGenerarReporte, btnSalirUser;
    private JLabel lblTitulo, lblTitulo2;
    private DefaultTableModel modelo;

    private final LicenciaController controller;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JButton GUARDARButton;
    private JButton EDITARButton;
    private JButton ELIMINARButton;
    private JButton LIMPIARButton;
    private JTable table1;
    private JButton GENERARREPORTEDEUSUARIOSButton;
    private JButton CERRARSESIÓNButton;

    public AdminView(LicenciaController controller) {
        this.controller = controller;

        inicializarComponentes();
        configurarEstilos();
        configurarEventos();
        cargarTabla();

        setTitle("Administración de Usuarios - ANT");
        setContentPane(mainPanel);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        // === CONFIGURACIÓN DE TAMAÑOS (Ajusta a tu gusto) ===
        int tamanoFuenteEtiquetas = 16;
        int tamanoFuenteCampos = 16;
        int tamanoFuenteBotones = 16;
        int alturaBotonesCRUD = 40;
        int alturaCamposTexto = 35;


        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(Color.WHITE);

        // --- ENCABEZADO ---
        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));
        panelEncabezado.setBackground(Color.WHITE);

        lblTitulo = new JLabel("SISTEMA DE LICENCIAS DE CONDUCIR - ECUADOR");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(40, 60, 100));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTitulo2 = new JLabel("ADMINISTRACIÓN DE USUARIOS");
        lblTitulo2.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo2.setForeground(new Color(240, 30, 50));
        lblTitulo2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Agencia Nacional de Tránsito");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelEncabezado.add(lblTitulo);
        panelEncabezado.add(Box.createVerticalStrut(5));
        panelEncabezado.add(lblTitulo2);
        panelEncabezado.add(Box.createVerticalStrut(5));
        panelEncabezado.add(lblSubtitulo);
        panelEncabezado.add(Box.createVerticalStrut(20));

        // --- CUERPO ---
        JPanel panelCuerpo = new JPanel(new GridLayout(1, 2, 25, 0));
        panelCuerpo.setBackground(Color.WHITE);

        // Formulario de Ingreso
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(Color.WHITE);
        panelDatos.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                " INGRESO DE DATOS ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), new Color(40, 60, 100)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); // Más espacio entre filas
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Fuentes
        Font fontEtiquetas = new Font("Arial", Font.BOLD, tamanoFuenteEtiquetas);
        Font fontCampos = new Font("Arial", Font.PLAIN, tamanoFuenteCampos);

        // Crear y configurar componentes de texto
        JLabel l1 = new JLabel("Usuario:");
        l1.setFont(fontEtiquetas);
        txtNewUser = new JTextField();
        txtNewUser.setFont(fontCampos);
        txtNewUser.setPreferredSize(new Dimension(200, alturaCamposTexto));

        JLabel l2 = new JLabel("Password:");
        l2.setFont(fontEtiquetas);
        txtNewPass = new JTextField();
        txtNewPass.setFont(fontCampos);
        txtNewPass.setPreferredSize(new Dimension(200, alturaCamposTexto));

        JLabel l3 = new JLabel("Rol:");
        l3.setFont(fontEtiquetas);
        cmbNewRol = new JComboBox<>(new String[]{"ADMIN", "ANALISTA"});
        cmbNewRol.setFont(fontCampos);
        cmbNewRol.setPreferredSize(new Dimension(200, alturaCamposTexto));

        // Ubicar en el layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelDatos.add(l1, gbc);
        gbc.gridx = 1;
        panelDatos.add(txtNewUser, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelDatos.add(l2, gbc);
        gbc.gridx = 1;
        panelDatos.add(txtNewPass, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelDatos.add(l3, gbc);
        gbc.gridx = 1;
        panelDatos.add(cmbNewRol, gbc);

        // Panel de botones CRUD (2x2)
        JPanel panelBotonesCRUD = new JPanel(new GridLayout(2, 2, 15, 15));
        panelBotonesCRUD.setBackground(Color.WHITE);

        Font fontBtn = new Font("Arial", Font.PLAIN, tamanoFuenteBotones);

        btnGuardarUser = new JButton("GUARDAR");
        btnEditarUser = new JButton("EDITAR");
        btnEliminarUser = new JButton("ELIMINAR");
        btnLimpiarUser = new JButton("LIMPIAR");

        JButton[] listaBtns = {btnGuardarUser, btnEditarUser, btnEliminarUser, btnLimpiarUser};
        for (JButton b : listaBtns) {
            b.setFont(fontBtn);
            b.setPreferredSize(new Dimension(0, alturaBotonesCRUD)); // Ajusta altura
        }

        panelBotonesCRUD.add(btnGuardarUser);
        panelBotonesCRUD.add(btnEditarUser);
        panelBotonesCRUD.add(btnEliminarUser);
        panelBotonesCRUD.add(btnLimpiarUser);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 20, 10, 20); // Espacio superior antes de botones
        panelDatos.add(panelBotonesCRUD, gbc);

        // Tabla
        modelo = new DefaultTableModel(new Object[]{"Usuario", "Password", "Rol"}, 0);
        tblUsers = new JTable(modelo);
        tblUsers.setRowHeight(30); // Filas de la tabla más altas
        tblUsers.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollTabla = new JScrollPane(tblUsers);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                " LISTADO DE USUARIOS ", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), new Color(40, 60, 100)
        ));

        panelCuerpo.add(panelDatos);
        panelCuerpo.add(scrollTabla);

        // --- ACCIONES INFERIORES ---
        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.setBackground(Color.WHITE);

        btnGenerarReporte = new JButton("GENERAR REPORTE DE USUARIOS");
        btnGenerarReporte.setPreferredSize(new Dimension(320, 50)); // Más grande
        btnGenerarReporte.setFont(new Font("Arial", Font.BOLD, 14));

        btnSalirUser = new JButton("CERRAR SESIÓN");
        btnSalirUser.setPreferredSize(new Dimension(200, 50)); // Más grande
        btnSalirUser.setFont(new Font("Arial", Font.BOLD, 14));

        panelAcciones.add(btnGenerarReporte, BorderLayout.WEST);
        panelAcciones.add(btnSalirUser, BorderLayout.EAST);

        // --- PIE ---
        JPanel panelPie = new JPanel();
        panelPie.setBackground(Color.WHITE);
        JLabel lblFooter = new JLabel("Sistema de Licencias v2.0 - ANT @ 2026");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        panelPie.add(lblFooter);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.add(panelAcciones, BorderLayout.NORTH);
        footer.add(panelPie, BorderLayout.SOUTH);

        mainPanel.add(panelEncabezado, BorderLayout.NORTH);
        mainPanel.add(panelCuerpo, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);
    }

    private void configurarEstilos() {

        JButton[] btns = {btnGuardarUser, btnEditarUser, btnLimpiarUser, btnGenerarReporte};
        for (JButton b : btns) {

            b.setForeground(Color.BLACK);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        btnEliminarUser.setForeground(Color.RED);

        btnSalirUser.setForeground(Color.BLACK);
    }

    private void configurarEventos() {

        // Evento para cargar datos de la tabla a los campos al hacer clic
        tblUsers.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int fila = tblUsers.getSelectedRow();
                if (fila != -1) {
                    txtNewUser.setText(modelo.getValueAt(fila, 0).toString());
                    // La contraseña no se carga por seguridad, el admin debe escribir una nueva si desea editarla
                    cmbNewRol.setSelectedItem(modelo.getValueAt(fila, 2).toString());

                    // Desactivamos el campo usuario si no quieres que cambien el username (que es único)
                    txtNewUser.setEditable(false);
                }
            }
        });

        //Botón Guardar Usuario
        btnGuardarUser.addActionListener(e -> guardarUsuario());

        //Botón Editar Usuario
        btnEditarUser.addActionListener(e -> editarUsuario());

        //Botón Eliminar Usuario
        btnEliminarUser.addActionListener(e -> eliminarUsuario());

        //Botón Limpiar Usuario
        btnLimpiarUser.addActionListener(e -> {
            limpiarUsuario();
            txtNewUser.setEditable(true); // Reactivamos al limpiar
        });

        // Botón Generar Reporte
        btnGenerarReporte.addActionListener(e -> generarReporteUsuarios());

        // Botón Salir
        btnSalirUser.addActionListener(e -> salirAplicacion());

    }

    private void cargarTabla() {
        modelo.setRowCount(0); // Limpiar tabla actual
        UsuarioDAO dao = new UsuarioDAO();
        java.util.List<Usuario> lista = dao.listar();
        for (Usuario u : lista) {
            modelo.addRow(new Object[]{
                    u.getUsername(),
                    "********", // Por seguridad no mostramos la clave real
                    u.getRol()
            });
        }
    }

    private void guardarUsuario() {
        String user = txtNewUser.getText().trim();
        String pass = txtNewPass.getText().trim();
        String rol = cmbNewRol.getSelectedItem().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setUsername(user);
        nuevo.setPassword(pass);
        nuevo.setRol(rol);
        nuevo.setActivo(true);

        try {
            UsuarioDAO dao = new UsuarioDAO();
            dao.guardar(nuevo);
            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente");
            limpiarUsuario();
            cargarTabla(); // Refresca la tabla automáticamente
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        String user = txtNewUser.getText().trim();
        String pass = txtNewPass.getText().trim();
        String rol = cmbNewRol.getSelectedItem().toString();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Para editar, seleccione un usuario y escriba la nueva contraseña");
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            // Buscamos el usuario en la BD (puedes añadir un método buscarPorNombre en tu DAO)
            Usuario existente = dao.listar().stream()
                    .filter(u -> u.getUsername().equals(user))
                    .findFirst().orElse(null);

            if (existente != null) {
                existente.setPassword(pass);
                existente.setRol(rol);

                // Reutilizamos el método guardar (em.merge o em.persist según tu implementación)
                // Si tu DAO usa persist(), asegúrate de que el objeto esté gestionado o usa merge.
                dao.guardar(existente);

                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                cargarTabla();
                limpiarUsuario();
                txtNewUser.setEditable(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        int fila = tblUsers.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla para eliminar");
            return;
        }

        String user = modelo.getValueAt(fila, 0).toString();
        int confirmar = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar al usuario " + user + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario existente = dao.listar().stream()
                        .filter(u -> u.getUsername().equals(user))
                        .findFirst().orElse(null);

                if (existente != null) {
                    dao.eliminar(existente.getId());
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
                    cargarTabla();
                    limpiarUsuario();
                    txtNewUser.setEditable(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void limpiarUsuario() {
        txtNewUser.setText("");
        txtNewPass.setText("");
        cmbNewRol.setSelectedIndex(0);
    }

    private void generarReporteUsuarios() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte de Usuarios");
        chooser.setSelectedFile(new java.io.File("Reporte_Usuarios_ANT.pdf"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().getAbsolutePath();

            // Asegurar extensión .pdf
            if (!ruta.toLowerCase().endsWith(".pdf")) {
                ruta += ".pdf";
            }

            try {
                // Obtener lista actualizada desde el DAO
                ec.edu.sistemalicencias.dao.UsuarioDAO dao = new ec.edu.sistemalicencias.dao.UsuarioDAO();
                java.util.List<ec.edu.sistemalicencias.model.entities.Usuario> lista = dao.listar();

                // Llamar al generador
                ec.edu.sistemalicencias.util.PDFGenerator.generarReporteUsuariosPDF(lista, ruta);

                JOptionPane.showMessageDialog(this, "Reporte generado con éxito en:\n" + ruta,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Opcional: Abrir el PDF automáticamente
                java.awt.Desktop.getDesktop().open(new java.io.File(ruta));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al generar el PDF: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void salirAplicacion() {

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {

            // Cierra esta ventana
            this.dispose();

            // Regresa al login
            new LoginView(
                    new LicenciaController()
            ).setVisible(true);
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
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(20, 25, 20, 25), 0, 0));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Arial", Font.BOLD, 28, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-14140336));
        label1.setText("SISTEMA DE LICENCIAS DE CONDUCIR - ECUADOR");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Arial", Font.BOLD, 22, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-1040846));
        label2.setText("ADMINISTRACIÓN DE USUARIOS");
        panel3.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Arial", Font.ITALIC, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Agencia Nacional de Tránsito");
        panel3.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(15, 20, 15, 20), -1, -1));
        panel1.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(329, 288), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(4, 2, new Insets(15, 20, 15, 20), -1, -1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-3618616)), "INGRESO DE DATOS", TitledBorder.LEFT, TitledBorder.TOP, this.$$$getFont$$$("Arial", Font.BOLD, 16, panel5.getFont()), new Color(-14140316)));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Usuario: ");
        panel5.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Password:");
        panel5.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.BOLD, 16, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Rol:");
        panel5.add(label6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel5.add(textField1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 10, false));
        textField2 = new JTextField();
        panel5.add(textField2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 10, false));
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("ADMIN");
        defaultComboBoxModel1.addElement("ANALISTA");
        comboBox1.setModel(defaultComboBoxModel1);
        panel5.add(comboBox1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, -1), null, 10, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 2, new Insets(10, 0, 0, 0), 10, 10));
        panel5.add(panel6, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        GUARDARButton = new JButton();
        GUARDARButton.setText("GUARDAR");
        panel6.add(GUARDARButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EDITARButton = new JButton();
        EDITARButton.setText("EDITAR");
        panel6.add(EDITARButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ELIMINARButton = new JButton();
        ELIMINARButton.setText("ELIMINAR");
        panel6.add(ELIMINARButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LIMPIARButton = new JButton();
        LIMPIARButton.setText("LIMPIAR");
        panel6.add(LIMPIARButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(15, 20, 15, 20), -1, -1));
        panel1.add(panel7, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(2, 1, new Insets(15, 20, 15, 20), -1, -1));
        panel7.add(panel8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-3618616)), "LISTADO DE USUARIOS", TitledBorder.LEFT, TitledBorder.TOP, this.$$$getFont$$$("Arial", Font.BOLD, 16, panel8.getFont()), new Color(-14140316)));
        table1 = new JTable();
        panel8.add(table1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel8.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel9, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        GENERARREPORTEDEUSUARIOSButton = new JButton();
        GENERARREPORTEDEUSUARIOSButton.setText("GENERAR REPORTE DE USUARIOS");
        panel9.add(GENERARREPORTEDEUSUARIOSButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        CERRARSESIÓNButton = new JButton();
        CERRARSESIÓNButton.setText("CERRAR SESIÓN");
        panel9.add(CERRARSESIÓNButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Arial", Font.PLAIN, 12, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("Sistema de Licencias v2.0 - ANT @ 2026");
        panel1.add(label7, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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