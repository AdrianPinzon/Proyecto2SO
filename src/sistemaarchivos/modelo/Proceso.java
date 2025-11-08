/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.modelo;

// Importamos SolicitudES que usaremos para el proceso
import sistemaarchivos.modelo.SolicitudES; 

public class Proceso {

    // Constantes de Estado
    public static final String NUEVO = "Nuevo";
    public static final String LISTO = "Listo";
    public static final String EJECUTANDO = "Ejecutando";
    public static final String BLOQUEADO = "Bloqueado";
    public static final String TERMINADO = "Terminado";

    private final String id;             // Identificador único (e.g., "P1", "P2")
    private String estado;               // Estado actual del proceso
    private String tipoUsuario;          // "ADMIN" o "USER"
    private SolicitudES solicitud;       // La operación que está solicitando realizar
    
    // Atributo opcional para planificación y métricas
    private long tiempoLlegada;          // Momento en que el proceso fue creado o ingresó a Listo
    
    // Constructor
    public Proceso(String id, String tipoUsuario, SolicitudES solicitud) {
        this.id = id;
        this.tipoUsuario = tipoUsuario; 
        this.solicitud = solicitud;
        this.estado = NUEVO; // Todo proceso inicia en estado "Nuevo"
        this.tiempoLlegada = System.currentTimeMillis(); // Registrar el tiempo de inicio
    }
    
    // Getters
    public String getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public SolicitudES getSolicitud() {
        return solicitud;
    }

    public long getTiempoLlegada() {
        return tiempoLlegada;
    }

    // Setters y Métodos de Transición de Estado
    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * Verifica si el proceso es un administrador (para control de permisos).
     */
    public boolean esAdministrador() {
        // Usamos las constantes definidas en NodoSistema o ManejadorUsuarios para la consistencia
        return this.tipoUsuario.equals("ADMIN"); 
    }
    
}
