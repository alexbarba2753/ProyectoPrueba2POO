package ec.edu.sistemalicencias.controller;

import ec.edu.sistemalicencias.model.entities.Usuario;
import ec.edu.sistemalicencias.model.exceptions.AutenticacionException;
import ec.edu.sistemalicencias.service.UsuarioService;
import ec.edu.sistemalicencias.util.PDFGenerator;
import ec.edu.sistemalicencias.view.AdminView;
import ec.edu.sistemalicencias.view.AnalistView;
import ec.edu.sistemalicencias.view.FormularioUsuarioDialog;
import ec.edu.sistemalicencias.view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    /**
     * Método principal que llama la vista de Login al presionar "Ingresar"
     */
    public void procesarLogin(String username, String password, JFrame vistaLogin) {
        try {
            Usuario usuarioLogueado = usuarioService.login(username, password);

            vistaLogin.dispose();

            // Ahora abrimos la siguiente ventana
            redirigirPorRol(usuarioLogueado);

        } catch (AutenticacionException e) {
            // Si hay error, NO cerramos la vistaLogin, solo mostramos el mensaje
            JOptionPane.showMessageDialog(vistaLogin, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void redirigirPorRol(Usuario u) {
        String rol = u.getRol().toUpperCase();

        if (rol.equals("ADMIN")) {
            JOptionPane.showMessageDialog(null, "Bienvenido Administrador: " + u.getNombreCompleto());


            AdminView adminWindow = new AdminView(this, u);
            adminWindow.setVisible(true);

        } else if (rol.equals("ANALISTA")) {
            JOptionPane.showMessageDialog(null, "Bienvenido Analista: " + u.getNombreCompleto());

            AnalistView analistaWindow = new AnalistView(new LicenciaController(), u);
            analistaWindow.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "Rol no reconocido", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Dentro de UsuarioController.java
    public List<Usuario> obtenerListaUsuarios() {
        try {
            return usuarioService.obtenerTodosLosUsuarios();
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }


    public void eliminarUsuario(Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }

    public void prepararEdicionUsuario(JFrame ventanaPadre, Long id) {
        try {
            Usuario u = usuarioService.obtenerPorId(id);
            abrirFormularioUsuario(ventanaPadre, u);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventanaPadre, "Error al recuperar datos.");
        }
    }

    public void abrirFormularioUsuario(Window padre, Usuario u) {
        FormularioUsuarioDialog dial = new FormularioUsuarioDialog(padre, this, u);
        dial.setVisible(true);
    }



    public void guardarUsuario(Usuario u) {
        try {
            usuarioService.guardarUsuario(u);
            JOptionPane.showMessageDialog(null, "Usuario guardado exitosamente en Supabase.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarReporteUsuarios(JFrame vista) {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new java.io.File("Reporte_Usuarios_ANT.pdf"));

        if (chooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            try {
                List<Usuario> lista = usuarioService.obtenerTodosLosUsuarios();
                PDFGenerator.generarReporteUsuariosPDF(lista, chooser.getSelectedFile().getAbsolutePath());

                JOptionPane.showMessageDialog(vista, "Reporte generado con éxito.");
                java.awt.Desktop.getDesktop().open(chooser.getSelectedFile());

            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error al generar PDF: " + e.getMessage());
            }
        }
    }
}