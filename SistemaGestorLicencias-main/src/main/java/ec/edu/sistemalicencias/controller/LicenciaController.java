package ec.edu.sistemalicencias.controller;

import com.itextpdf.text.DocumentException;
import ec.edu.sistemalicencias.model.entities.Conductor;
import ec.edu.sistemalicencias.model.entities.Licencia;
import ec.edu.sistemalicencias.model.entities.PruebaPsicometrica;
import ec.edu.sistemalicencias.model.exceptions.LicenciaException;
import ec.edu.sistemalicencias.service.LicenciaService;
import ec.edu.sistemalicencias.util.PDFGenerator;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controlador principal del patrón MVC.
 * Gestiona la comunicación entre la Vista y el Modelo (Servicios).
 * Implementa la lógica de control de flujo de la aplicación.
 *
 * @author Sistema Licencias Ecuador
 * @version 1.0
 */
public class LicenciaController {

    // Servicio de negocio (capa de modelo)
    private final LicenciaService licenciaService;

    /**
     * Constructor que inicializa el servicio
     */
    public LicenciaController() {
        this.licenciaService = new LicenciaService();
    }

    /**
     * Registra un nuevo conductor en el sistema
     * @param conductor Conductor a registrar
     * @return ID del conductor registrado
     * @throws LicenciaException Si hay errores de validación o persistencia
     */
    public Long registrarConductor(Conductor conductor) throws LicenciaException {
        return licenciaService.registrarConductor(conductor);
    }

    /**
     * Actualiza los datos de un conductor
     * @param conductor Conductor con datos actualizados
     * @throws LicenciaException Si hay errores
     */
    public void actualizarConductor(Conductor conductor) throws LicenciaException {
        licenciaService.actualizarConductor(conductor);
    }

    /**
     * Valida los documentos de un conductor
     * @param conductorId ID del conductor
     * @param documentosValidos Resultado de la validación
     * @param observaciones Observaciones
     * @throws LicenciaException Si hay errores
     */
    public void validarDocumentos(Long conductorId, boolean documentosValidos, String observaciones)
            throws LicenciaException {
        licenciaService.validarDocumentos(conductorId, documentosValidos, observaciones);
    }

    /**
     * Registra una prueba psicométrica
     * @param prueba Prueba a registrar
     * @return ID de la prueba registrada
     * @throws LicenciaException Si hay errores
     */
    public Long registrarPruebaPsicometrica(PruebaPsicometrica prueba) throws LicenciaException {
        return licenciaService.registrarPruebaPsicometrica(prueba);
    }

    /**
     * Emite una nueva licencia de conducir
     * @param conductorId ID del conductor
     * @param tipoLicencia Tipo de licencia (usar constantes de TipoLicenciaConstantes)
     * @param pruebaPsicometricaId ID de la prueba (opcional)
     * @return Licencia emitida
     * @throws LicenciaException Si no se cumplen los requisitos
     */
    public Licencia emitirLicencia(Long conductorId, String tipoLicencia, Long pruebaPsicometricaId)
            throws LicenciaException {
        return licenciaService.emitirLicencia(conductorId, tipoLicencia, pruebaPsicometricaId);
    }


    /**
     * Busca un conductor por cédula
     * @param cedula Número de cédula
     * @return Conductor encontrado o null
     * @throws LicenciaException Si hay errores
     */
    public Conductor buscarConductorPorCedula(String cedula) throws LicenciaException {
        return licenciaService.buscarConductorPorCedula(cedula);
    }

    /**
     * Busca un conductor por ID
     * @param id ID del conductor
     * @return Conductor encontrado o null
     * @throws LicenciaException Si hay errores
     */
    public Conductor buscarConductorPorId(Long id) throws LicenciaException {
        return licenciaService.buscarConductorPorId(id);
    }

    /**
     * Obtiene todos los conductores
     * @return Lista de conductores
     * @throws LicenciaException Si hay errores
     */
    public List<Conductor> obtenerTodosConductores() throws LicenciaException {
        return licenciaService.obtenerTodosConductores();
    }

    /**
     * Busca conductores por nombre
     * @param nombre Nombre a buscar
     * @return Lista de conductores
     * @throws LicenciaException Si hay errores
     */
    public List<Conductor> buscarConductoresPorNombre(String nombre) throws LicenciaException {
        return licenciaService.buscarConductoresPorNombre(nombre);
    }

    /**
     * Obtiene las pruebas psicométricas de un conductor
     * @param conductorId ID del conductor
     * @return Lista de pruebas
     * @throws LicenciaException Si hay errores
     */
    public List<PruebaPsicometrica> obtenerPruebasConductor(Long conductorId) throws LicenciaException {
        return licenciaService.obtenerPruebasConductor(conductorId);
    }

    /**
     * Obtiene la última prueba aprobada de un conductor
     * @param conductorId ID del conductor
     * @return Última prueba aprobada o null
     * @throws LicenciaException Si hay errores
     */
    public PruebaPsicometrica obtenerUltimaPruebaAprobada(Long conductorId) throws LicenciaException {
        return licenciaService.obtenerUltimaPruebaAprobada(conductorId);
    }

    /**
     * Busca una licencia por número
     * @param numeroLicencia Número de licencia
     * @return Licencia encontrada o null
     * @throws LicenciaException Si hay errores
     */
    public Licencia buscarLicenciaPorNumero(String numeroLicencia) throws LicenciaException {
        return licenciaService.buscarLicenciaPorNumero(numeroLicencia);
    }

    /**
     * Obtiene las licencias de un conductor
     * @param conductorId ID del conductor
     * @return Lista de licencias
     * @throws LicenciaException Si hay errores
     */
    public List<Licencia> obtenerLicenciasConductor(Long conductorId) throws LicenciaException {
        return licenciaService.obtenerLicenciasConductor(conductorId);
    }

    /**
     * Obtiene todas las licencias
     * @return Lista de licencias
     * @throws LicenciaException Si hay errores
     */
    public List<Licencia> obtenerTodasLicencias() throws LicenciaException {
        return licenciaService.obtenerTodasLicencias();
    }

    /**
     * Obtiene las licencias vigentes
     * @return Lista de licencias vigentes
     * @throws LicenciaException Si hay errores
     */
    public List<Licencia> obtenerLicenciasVigentes() throws LicenciaException {
        return licenciaService.obtenerLicenciasVigentes();
    }

    /**
     * Desactiva una licencia
     * @param licenciaId ID de la licencia
     * @param motivo Motivo de desactivación
     * @throws LicenciaException Si hay errores
     */
    public void desactivarLicencia(Long licenciaId, String motivo) throws LicenciaException {
        licenciaService.desactivarLicencia(licenciaId, motivo);
    }

    /**
     * Genera un documento PDF de licencia
     * @param licenciaId ID de la licencia
     * @param rutaArchivo Ruta donde guardar el PDF
     * @return true si se generó correctamente
     */
    public boolean generarDocumentoLicencia(Long licenciaId, String rutaArchivo) {
        try {
            // Buscar licencia por ID
            List<Licencia> todasLicencias = licenciaService.obtenerTodasLicencias();
            Licencia licenciaEncontrada = todasLicencias.stream()
                    .filter(l -> l.getId().equals(licenciaId))
                    .findFirst()
                    .orElse(null);

            if (licenciaEncontrada == null) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró la licencia",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Usar variable final para la lambda
            final Licencia licencia = licenciaEncontrada;
            Conductor conductor = licenciaService.buscarConductorPorId(licencia.getConductorId());

            PruebaPsicometrica prueba = null;
            if (licencia.getPruebaPsicometricaId() != null) {
                prueba = licenciaService.obtenerPruebasConductor(conductor.getId())
                        .stream()
                        .filter(p -> p.getId().equals(licencia.getPruebaPsicometricaId()))
                        .findFirst()
                        .orElse(null);
            }

            // Generar PDF
            PDFGenerator.generarLicenciaPDF(licencia, conductor, prueba, rutaArchivo);

            JOptionPane.showMessageDialog(null,
                    "Documento generado exitosamente en:\n" + rutaArchivo,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (LicenciaException | DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar el documento:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Genera un documento PDF de licencia con selector de archivo
     * @param licenciaId ID de la licencia
     * @return true si se generó correctamente
     */
    public boolean generarDocumentoLicenciaConDialogo(Long licenciaId) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Licencia PDF");
        fileChooser.setSelectedFile(new File("Licencia_" + licenciaId + ".pdf"));

        int resultado = fileChooser.showSaveDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            if (!rutaArchivo.toLowerCase().endsWith(".pdf")) {
                rutaArchivo += ".pdf";
            }
            return generarDocumentoLicencia(licenciaId, rutaArchivo);
        }

        return false;
    }

    /**
     * Muestra un mensaje de error en la interfaz
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Muestra un mensaje de éxito en la interfaz
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null,
                mensaje,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un mensaje de confirmación
     * @param mensaje Mensaje a mostrar
     * @return true si el usuario confirma
     */
    public boolean confirmar(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(null,
                mensaje,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return respuesta == JOptionPane.YES_OPTION;
    }





    // ===================== LOGIN / SEGURIDAD =====================

    /**
     * Valida las credenciales del usuario
     * @param rol Rol seleccionado
     * @param password Contraseña ingresada
     * @return true si es válido
     */
    public boolean validarLogin(String rol, String password) {

        // 🔐 Credenciales simples (proyecto universitario)
        if (rol.equals("Administrador") && password.equals("admin123")) {
            return true;
        }

        if (rol.equals("Analista") && password.equals("analista123")) {
            return true;
        }

        return false;
    }

    /**
     * Abre la vista correspondiente según el rol
     * @param rol Rol del usuario
     */
    public void abrirVistaPorRol(String rol) {

        if (rol.equals("Administrador")) {
            new ec.edu.sistemalicencias.view.MainView(this).setVisible(true);
        }

        if (rol.equals("Analista")) {
            new ec.edu.sistemalicencias.view.AnalistView(this).setVisible(true);
        }
    }







}
