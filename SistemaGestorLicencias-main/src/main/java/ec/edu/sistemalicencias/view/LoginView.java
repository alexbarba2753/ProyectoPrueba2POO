package ec.edu.sistemalicencias.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import ec.edu.sistemalicencias.controller.LicenciaController;
import ec.edu.sistemalicencias.dao.UsuarioDAO;
import ec.edu.sistemalicencias.model.entities.Usuario;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LoginView extends JFrame {

    private final LicenciaController controller;
    private JComboBox<Usuario> cmbUser;
    private JPasswordField txtPassword;
    private JButton BtnIngreso, BtnLimpiar;
    private JPanel PanelPrincipal;

    public LoginView(LicenciaController controller) {
        this.controller = controller;

        // Configuración de la ventana
        setTitle("SISTEMA DE LICENCIAS - ACCESO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 550); // Tamaño más balanceado
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentesManual();
        cargarUsuarios();
        configurarEventos();
    }

    private void inicializarComponentesManual() {
        // Panel Principal con imagen de fondo o color sólido elegante
        PanelPrincipal = new JPanel(new BorderLayout());
        PanelPrincipal.setBackground(new Color(245, 247, 250));

        // --- PARTE IZQUIERDA: IMAGEN/LOGO ---
        // Usaremos un panel con la imagen de la EPN o el logo de la ANT
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(600, 0));
        panelLateral.setBackground(new Color(40, 60, 100)); // Azul institucional

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        // Intentar cargar la imagen login.png, si no, poner un texto
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/login.png"));
            // Redimensionar imagen si es necesario
            Image img = icon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblImagen.setText("SISTEMA ANT");
            lblImagen.setForeground(Color.WHITE);
            lblImagen.setFont(new Font("Arial", Font.BOLD, 30));
        }
        panelLateral.add(lblImagen);

        // --- PARTE DERECHA: FORMULARIO ---
        JPanel panelDerecho = new JPanel(new GridBagLayout());
        panelDerecho.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Icono de Usuario (Avatar)
        JLabel lblAvatar = new JLabel();
        try {
            ImageIcon iconAvatar = new ImageIcon(getClass().getResource("/imagenes/icono.png"));
            Image imgAvatar = iconAvatar.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblAvatar.setIcon(new ImageIcon(imgAvatar));
        } catch (Exception e) {
        }
        lblAvatar.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelDerecho.add(lblAvatar, gbc);

        // Título
        JLabel lblLogin = new JLabel("INICIAR SESIÓN");
        lblLogin.setFont(new Font("Arial", Font.BOLD, 24));
        lblLogin.setForeground(new Color(40, 60, 100));
        lblLogin.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        panelDerecho.add(lblLogin, gbc);

        // Separador
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 40, 20, 40);
        panelDerecho.add(new JSeparator(), gbc);

        // Campos
        gbc.insets = new Insets(5, 40, 5, 40);
        gbc.gridwidth = 2;

        JLabel l1 = new JLabel("Usuario:");
        l1.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 3;
        panelDerecho.add(l1, gbc);

        cmbUser = new JComboBox<>();
        cmbUser.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbUser.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 4;
        panelDerecho.add(cmbUser, gbc);

        JLabel l2 = new JLabel("Contraseña:");
        l2.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 5;
        panelDerecho.add(l2, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 6;
        panelDerecho.add(txtPassword, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(Color.WHITE);

        BtnIngreso = new JButton("INGRESAR");
        BtnIngreso.setForeground(Color.BLUE);
        BtnIngreso.setFont(new Font("Arial", Font.BOLD, 12));
        BtnIngreso.setFocusPainted(false);
        BtnIngreso.setCursor(new Cursor(Cursor.HAND_CURSOR));

        BtnLimpiar = new JButton("LIMPIAR");
        BtnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        BtnLimpiar.setFocusPainted(false);
        BtnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(BtnLimpiar);
        panelBotones.add(BtnIngreso);

        gbc.gridy = 7;
        gbc.insets = new Insets(30, 40, 10, 40);
        panelDerecho.add(panelBotones, gbc);

        // Pie
        JLabel lblCopy = new JLabel("© 2026 Agencia Nacional de Tránsito - v2.0");
        lblCopy.setFont(new Font("Arial", Font.PLAIN, 10));
        lblCopy.setForeground(Color.GRAY);
        lblCopy.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 8;
        panelDerecho.add(lblCopy, gbc);

        // Ensamblaje
        PanelPrincipal.add(panelLateral, BorderLayout.WEST);
        PanelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(PanelPrincipal);
    }

    private void cargarUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        for (Usuario u : dao.listar()) {
            cmbUser.addItem(u);
        }
    }

    private void configurarEventos() {
        BtnLimpiar.addActionListener(e -> txtPassword.setText(""));

        BtnIngreso.addActionListener(e -> {
            if (cmbUser.getSelectedItem() == null) return;

            Usuario u = (Usuario) cmbUser.getSelectedItem();
            String pass = new String(txtPassword.getPassword());

            Usuario validado = controller.login(u.getUsername(), pass);

            if (validado == null) {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();
            controller.abrirVistaSegunUsuario(validado);
        });
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
        PanelPrincipal = new JPanel();
        PanelPrincipal.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelPrincipal.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/imagenes/icono.png")));
        label1.setText("");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(null, "Ingrese sus Datos", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label2 = new JLabel();
        label2.setText("Seleccione su usuario:");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Contraseña:");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BtnLimpiar = new JButton();
        BtnLimpiar.setText("Limpiar");
        panel3.add(BtnLimpiar, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPassword = new JPasswordField();
        panel3.add(txtPassword, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cmbUser = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        cmbUser.setModel(defaultComboBoxModel1);
        panel3.add(cmbUser, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BtnIngreso = new JButton();
        BtnIngreso.setText("Iniciar Sesión");
        panel3.add(BtnIngreso, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        PanelPrincipal.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setIcon(new ImageIcon(getClass().getResource("/imagenes/login.png")));
        label4.setText("");
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return PanelPrincipal;
    }

}
    // GUI initializer generated by IntelliJ IDEA GUI Designer

    // >>> IMPORTANT!! <<<