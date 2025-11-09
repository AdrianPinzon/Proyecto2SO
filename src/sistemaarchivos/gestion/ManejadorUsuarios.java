package sistemaarchivos.gestion;

import sistemaarchivos.modelo.NodoSistema;

public class ManejadorUsuarios {

    // Roles definidos en la base del modelo
    public static final String ROL_ADMIN = NodoSistema.ROL_ADMIN; // "ADMIN"
    public static final String ROL_USUARIO = NodoSistema.ROL_USUARIO; // "USER"

    private String modoActual;
    
    // Podrías almacenar una lista de usuarios reales aquí si el proyecto creciera,
    // pero para este simulador simple, basta con el modo activo.
    
    // Constructor
    public ManejadorUsuarios() {
        // Inicializar en modo administrador para permitir la configuración inicial
        this.modoActual = ROL_ADMIN;
    }
    
    // Getters y Setters de Modo

    public String getModoActual() {
        return modoActual;
    }

    /**
     * Permite cambiar el modo de operación, seleccionado mediante la interfaz.
     * @param nuevoModo Debe ser ROL_ADMIN o ROL_USUARIO.
     */
    public void setModoActual(String nuevoModo) {
        if (nuevoModo.equals(ROL_ADMIN) || nuevoModo.equals(ROL_USUARIO)) {
            this.modoActual = nuevoModo;
            System.out.println("Modo de operación cambiado a: " + nuevoModo);
        } else {
            System.out.println("Modo no válido.");
        }
    }

    /**
     * Verifica si el usuario actual tiene permisos de administración.
     * @return true si el modo actual es Administrador.
     */
    public boolean esAdministrador() {
        return this.modoActual.equals(ROL_ADMIN);
    }

    /**
     * Verifica si una operación CRUD es válida para el modo actual.
     * @param esEscritura True si es Crear, Actualizar o Eliminar.
     * @return true si la operación está permitida.
     */
    public boolean puedeRealizarOperacion(boolean esEscritura) {
        if (esEscritura) {
            // Solo el administrador puede CREAR, ACTUALIZAR o ELIMINAR[cite: 11, 40, 43, 46].
            return esAdministrador();
        } else {
            // Lectura: el administrador siempre puede; el usuario puede leer archivos propios o públicos[cite: 12].
            // Para este nivel de verificación, asumimos que siempre puede leer si es Admin o si el archivo es público/propio.
            return true; 
        }
    }

}