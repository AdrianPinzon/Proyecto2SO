package sistemaarchivos.gestion;

import sistemaarchivos.modelo.*;
import sistemaarchivos.almacenamiento.*;
import sistemaarchivos.utilidades.ManejadorPersistencia;

public class SistemaArchivos {

    // Componentes del Modelo Lógico
    private Directorio directorioRaiz;
    
    // Componentes de Gestión de Hardware Simulado
    private SimulacionDisco discoSD;
    private TablaAsignacion tablaAsignacion;
    
    // Componentes de Gestión Operacional
    private ColaES colaES;
    private PlanificadorDisco planificador;
    private ManejadorUsuarios manejadorUsuarios; // Para control de permisos y modo
    
    // Servicios
    private ManejadorPersistencia persistencia;
    
    // Referencia al proceso en ejecución (para simular el CPU)
    private Proceso procesoActual; 
    
    // Constructor
    public SistemaArchivos() {
        // 1. Inicializar componentes de Almacenamiento
        this.discoSD = new SimulacionDisco();
        this.tablaAsignacion = new TablaAsignacion();

        // 2. Inicializar componentes de E/S y Gestión
        this.colaES = new ColaES();
        this.planificador = new PlanificadorDisco(this.colaES);
        this.manejadorUsuarios = new ManejadorUsuarios();

        // 3. Inicializar el Directorio Raíz
        // El sistema arranca con un directorio raíz (padre = null, propietario = ADMIN)
        this.directorioRaiz = new Directorio("/", null, NodoSistema.ROL_ADMIN);

        // 4. Inicializar Persistencia
        this.persistencia = new ManejadorPersistencia(this); // Necesita referencia a sí mismo

        // Intenta cargar el estado anterior si existe
        // this.persistencia.cargarEstado(); // Implementación pendiente
    }
    
    /**
    * Crea un Archivo o Directorio si el usuario tiene permisos y hay espacio.
    * @param solicitud La solicitud con los detalles de CREAR.
    * @param proceso El proceso que realiza la solicitud.
    * @return true si la operación se realiza o se encola, false si falla.
    */
   public boolean ejecutarCrear(SolicitudES solicitud, Proceso proceso) {

       // 1. Verificación de Permisos (Solo Admin puede crear, según el documento [cite: 37, 40])
       if (!proceso.esAdministrador() && solicitud.getTipoOperacion().equals(SolicitudES.OP_CREAR)) {
           System.out.println("Error: El modo usuario no puede CREAR nuevos archivos/directorios.");
           return false;
       }

       // 2. Simular E/S: Encapsular la solicitud y encolarla para el Planificador
       // En un sistema real, la solicitud de asignación de bloques iría aquí:

       // Para simplificar la demo, encolamos toda la solicitud para que el planificador la gestione.
       proceso.setEstado(Proceso.BLOQUEADO);
       this.colaES.encolar(solicitud);

       return true;
   }

   /**
    * Atiende el siguiente proceso de la cola de E/S.
    */
   public void despacharSiguienteOperacion() {

       // 1. Seleccionar la próxima solicitud según la política activa
       SolicitudES solicitud = this.planificador.seleccionarSiguienteSolicitud();

       if (solicitud != null) {
           // En un sistema completo, el proceso asociado a la solicitud se establecería aquí
           // (La lógica para extraer el Proceso de la Solicitud sería más compleja)

           // 2. Ejecutar la operación de E/S
           switch (solicitud.getTipoOperacion()) {
               case SolicitudES.OP_CREAR:
                   // Lógica de creación: buscar el padre, verificar espacio y llamar a discoSD.asignarBloques()
                   // ...
                   break;
               case SolicitudES.OP_ELIMINAR:
                   // Lógica de eliminación: buscar el archivo, llamar a discoSD.liberarBloques() y tablaAsignacion.desregistrarArchivo()
                   // ...
                   break;
               case SolicitudES.OP_LEER:
                   // Lógica de lectura: verificar permisos (si es propio o público [cite: 12]), acceder a la cadena de bloques.
                   // ...
                   break;
           }

           // 3. Simular fin de E/S: Mover el proceso de BLOQUEADO a TERMINADO o LISTO
           // ...
       }
   }
   
    public Directorio getDirectorioRaiz() {
    return directorioRaiz;
    }

    public SimulacionDisco getDiscoSD() {
        return discoSD;
    }

    public TablaAsignacion getTablaAsignacion() {
        return tablaAsignacion;
    }

    public PlanificadorDisco getPlanificador() {
        return planificador;
    }

    public ColaES getColaES() {
        return colaES;
    }

    public ManejadorUsuarios getManejadorUsuarios() {
        return manejadorUsuarios;
    }
    // ...

}