package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.UsuarioController;
import ec.edu.sistemalicencias.model.entities.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormularioUsuarioDialog extends JDialog {

    private final UsuarioController controller;
    private Usuario usuarioActual;
    private JTextField txtNombre, txtEmail, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    private JCheckBox chkActivo;
    private JButton btnGuardar, btnCancelar;

    public FormularioUsuarioDialog(Window padre, UsuarioController controller, Usuario usuario) {
        super(padre, "Gestión de Usuario", ModalityType.APPLICATION_MODAL);
        this.controller = controller;
        this.usuarioActual = usuario;

        inicializarComponentes();

        if (usuarioActual != null) {
            cargarDatosEnFormulario();
            setTitle("Editar Usuario: " + usuarioActual.getUsername());
        } else {
            setTitle("Crear Nuevo Usuario");
        }

        configurarEventos();

        pack();
        setResizable(false);
        setLocationRelativeTo(padre);
    }

    private void inicializarComponentes() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // --- FILA 0: Nombre Completo ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nombre Completo:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtNombre, gbc);

        // --- FILA 1: Email ---
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtEmail, gbc);

        // --- FILA 2: Username ---
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(txtUsername, gbc);

        // --- FILA 3: Password ---
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(txtPassword, gbc);

        // --- FILA 4: Rol ---
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Rol:"), gbc);
        cmbRol = new JComboBox<>(new String[]{"ADMIN", "ANALISTA"});
        gbc.gridx = 1;
        mainPanel.add(cmbRol, gbc);

        // --- FILA 5: Estado ---
        chkActivo = new JCheckBox("Usuario Activo", true);
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(chkActivo, gbc);

        // --- PANEL DE BOTONES ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancelar = new JButton("Cancelar");
        btnGuardar = new JButton("Guardar Datos");
        btnGuardar.setBackground(new Color(40, 167, 69));
        btnGuardar.setForeground(Color.BLACK);

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(pnlBotones, gbc);

        add(mainPanel);
    }

    private void cargarDatosEnFormulario() {
        txtNombre.setText(usuarioActual.getNombreCompleto());
        txtEmail.setText(usuarioActual.getEmail());
        txtUsername.setText(usuarioActual.getUsername());
        txtPassword.setText(usuarioActual.getPassword());
        cmbRol.setSelectedItem(usuarioActual.getRol());
        chkActivo.setSelected(usuarioActual.isActivo());
    }

    private void configurarEventos() {
        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            if (validarCampos()) {
                guardar();
            }
        });
    }

    private boolean validarCampos() {
        if (txtUsername.getText().trim().isEmpty() || txtPassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Username y Password son obligatorios.");
            return false;
        }
        return true;
    }

    private void guardar() {
        // Si no existe, creamos uno nuevo. Si existe, editamos el objeto actual.
        if (usuarioActual == null) {
            usuarioActual = new Usuario();
        }

        usuarioActual.setNombreCompleto(txtNombre.getText().trim());
        usuarioActual.setEmail(txtEmail.getText().trim());
        usuarioActual.setUsername(txtUsername.getText().trim());
        usuarioActual.setPassword(new String(txtPassword.getPassword()));
        usuarioActual.setRol(cmbRol.getSelectedItem().toString());
        usuarioActual.setActivo(chkActivo.isSelected());

        // Delegamos al controlador
        controller.guardarUsuario(usuarioActual);
        dispose();
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
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(7, 2, new Insets(20, 20, 20, 20), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        final JLabel label1 = new JLabel();
        label1.setText("Nombre Completo:");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Email:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Username:");
        panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Password:");
        panel2.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Rol:");
        panel2.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtNombre = new JTextField();
        panel2.add(txtNombre, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtEmail = new JTextField();
        panel2.add(txtEmail, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtUsername = new JTextField();
        panel2.add(txtUsername, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cmbRol = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("ADMIN");
        defaultComboBoxModel1.addElement("ANALISTA");
        cmbRol.setModel(defaultComboBoxModel1);
        panel2.add(cmbRol, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chkActivo = new JCheckBox();
        chkActivo.setText("Usuario Activo");
        panel2.add(chkActivo, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnCancelar = new JButton();
        btnCancelar.setText("Cancelar");
        panel3.add(btnCancelar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnGuardar = new JButton();
        btnGuardar.setText("Guardar Datos");
        panel3.add(btnGuardar, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPassword = new JPasswordField();
        panel2.add(txtPassword, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }
}