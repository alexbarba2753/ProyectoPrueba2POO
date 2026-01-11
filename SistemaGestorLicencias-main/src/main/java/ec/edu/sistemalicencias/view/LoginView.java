package ec.edu.sistemalicencias.view;

import ec.edu.sistemalicencias.controller.UsuarioController;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

public class LoginView extends JFrame {

    private final UsuarioController controller;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton BtnIngreso, BtnLimpiar;
    private JPanel PanelPrincipal;

    public LoginView(UsuarioController controller) {
        this.controller = controller;

        setTitle("SISTEMA DE LICENCIAS - ACCESO");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentesManual();
        configurarEventos();
    }

    private void inicializarComponentesManual() {
        // Panel Principal con imagen de fondo o color sólido elegante
        PanelPrincipal = new JPanel(new BorderLayout());
        PanelPrincipal.setBackground(new Color(245, 247, 250));

        // --- PARTE IZQUIERDA: IMAGEN/LOGO ---
        // Usamos un panel con la imagen de la EPN
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(600, 0));
        panelLateral.setBackground(new Color(40, 60, 100)); // Azul institucional

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/login.png"));
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
        gbc.gridy = 3; // <-- Asegúrate de que no esté comentado
        panelDerecho.add(l1, gbc); // Añadimos la etiqueta

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(0, 35));
        gbc.gridy = 4; // <-- Posición debajo de la etiqueta
        panelDerecho.add(txtUsername, gbc); // !!! ESTA LÍNEA FALTABA: Añadimos el campo de texto

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


    private void configurarEventos() {
        BtnLimpiar.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
        });

        BtnIngreso.addActionListener(e -> {
            String user = txtUsername.getText().trim();
            String pass = new String(txtPassword.getPassword());

            controller.procesarLogin(user, pass, this);
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/imagenes/login.png")));
        label1.setText("");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/imagenes/icono.png")));
        label2.setText("");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(4, 1, new Insets(10, 20, 20, 20), -1, -1));
        panel3.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Usuario:");
        panel5.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtUsername = new JTextField();
        panel5.add(txtUsername, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Contraseña:");
        panel5.add(label4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPassword = new JPasswordField();
        panel5.add(txtPassword, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(10, 20, 20, 20), -1, -1));
        panel3.add(panel6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        BtnLimpiar = new JButton();
        BtnLimpiar.setText("LIMPIAR");
        panel6.add(BtnLimpiar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BtnIngreso = new JButton();
        BtnIngreso.setText("INGRESAR");
        panel6.add(BtnIngreso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 20, 30, 20), -1, -1));
        panel3.add(panel7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("© 2026 Agencia Nacional de Tránsito - v2.0");
        panel7.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Arial", Font.BOLD, 24, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-14140316));
        label6.setText("INICIAR SESIÓN");
        panel3.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
    // GUI initializer generated by IntelliJ IDEA GUI Designer

    // >>> IMPORTANT!! <<<