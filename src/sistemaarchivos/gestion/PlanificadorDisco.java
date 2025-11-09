package sistemaarchivos.gestion;

import sistemaarchivos.modelo.SolicitudES; 

public class PlanificadorDisco {

    // Constantes de Políticas (Mínimo 4 Requeridas) [cite: 51]
    public static final String FIFO = "FIFO";
    public static final String SSTF = "SSTF";
    public static final String SCAN = "SCAN";
    public static final String CSCAN = "C-SCAN";
    // Constantes de Dirección
    private static final int DIRECCION_ARRIBA = 1;
    private static final int DIRECCION_ABAJO = -1;

    // Atributo
    private int direccionActual; // 1 (Arriba/Creciente) o -1 (Abajo/Decreciente)
    private static final int POSICION_MAXIMA = 99; // Asumiendo CAPACIDAD_MAXIMA - 1
    
    private String politicaActiva;
    private ColaES colaES; // Referencia a la cola de solicitudes
    private int cabezalActual; // Simula la posición actual del cabezal del disco
    
    // Constructor
    public PlanificadorDisco(ColaES colaES) {
        this.colaES = colaES;
        this.politicaActiva = FIFO; // FIFO como política inicial por defecto
        this.cabezalActual = 0;    // Inicialmente el cabezal está en la posición 0
        this.cabezalActual = 0;
        this.direccionActual = DIRECCION_ARRIBA; // Comienza moviéndose hacia arriba por defecto
    }
    
    /**
    * Selecciona la próxima SolicitudES a atender según la política activa.
    * * NOTA: Para las políticas no FIFO, este método primero busca la mejor solicitud 
    * en la cola y luego la extrae (implementación pendiente en ColaES).
    * * @return La SolicitudES seleccionada, o null si la cola está vacía.
    */

    public SolicitudES seleccionarSiguienteSolicitud() {
        if (colaES.estaVacia()) {
            return null;
        }

        SolicitudES proximaSolicitud = null;

        switch (politicaActiva) {
            case FIFO:
                proximaSolicitud = colaES.desencolar();
                break;
            case SSTF:
                proximaSolicitud = buscarSSTF(); // <--- Ahora llama al método implementado
                break;
            case SCAN:
            case CSCAN:
                // Lógica para buscar SCAN o C-SCAN
                proximaSolicitud = buscarSCAN(); 
                break;
            default:
                proximaSolicitud = colaES.desencolar();
                break;
        }

        // ... (Actualización del cabezal)
        if (proximaSolicitud != null && proximaSolicitud.getPosicionDisco() != -1) {
            this.cabezalActual = proximaSolicitud.getPosicionDisco();
        }

        return proximaSolicitud;
    }
   
    /**
    * Implementación de la lógica SSTF (Shortest Seek Time First).
    * Busca la solicitud cuya posición de disco sea la más cercana al cabezal actual.
    */
   private SolicitudES buscarSSTF() {
        SolicitudES mejorSolicitud = null;
        int menorDistancia = Integer.MAX_VALUE;

        // Usamos el nodo interno de ColaES para poder iterar
        ColaES.NodoCola actual = colaES.getFrente(); 

        // 1. Recorrer la cola para encontrar el mejor candidato (el más cercano)
        while (actual != null) {
            SolicitudES solicitud = actual.solicitud;

            // Calcular la distancia absoluta entre el cabezal actual y la posición solicitada
            int distancia = Math.abs(solicitud.getPosicionDisco() - this.cabezalActual);

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                mejorSolicitud = solicitud;
            }

            actual = actual.siguiente;
        }

        // 2. Si se encontró una solicitud, la removemos de la cola usando el método de eliminación selectiva
        if (mejorSolicitud != null) {
            colaES.removerSolicitud(mejorSolicitud);
        }

        return mejorSolicitud;
    }

   // Dentro de PlanificadorDisco.java

    /**
     * Implementación de la lógica SCAN y C-SCAN.
     * Busca la solicitud que siga la dirección de movimiento actual.
     * @return La SolicitudES seleccionada según la política.
     */
    private SolicitudES buscarSCAN() {
        SolicitudES mejorSolicitud = null;
        SolicitudES solicitudMasLejana = null; // Para C-SCAN

        int mejorDistancia = Integer.MAX_VALUE;
        int posicionMaxRecorrida = -1;

        ColaES.NodoCola actual = colaES.getFrente(); 

        // 1. Recorrer la cola en busca del mejor candidato en la dirección actual
        while (actual != null) {
            SolicitudES solicitud = actual.solicitud;
            int distancia = solicitud.getPosicionDisco() - this.cabezalActual;

            // Criterio de búsqueda:
            // a) La solicitud está en la dirección de movimiento (distancia > 0 si ARRIBA, distancia < 0 si ABAJO).
            // b) Es la solicitud más cercana a cabezalActual en esa dirección.

            if ((direccionActual == DIRECCION_ARRIBA && distancia >= 0) || 
                (direccionActual == DIRECCION_ABAJO && distancia <= 0)) {

                int absDistancia = Math.abs(distancia);
                if (absDistancia < mejorDistancia) {
                    mejorDistancia = absDistancia;
                    mejorSolicitud = solicitud;
                }
            }

            // 2. Para C-SCAN: rastreamos el elemento más lejano en la dirección de reinicio.
            if (politicaActiva.equals(CSCAN) && solicitud.getPosicionDisco() > posicionMaxRecorrida) {
                 solicitudMasLejana = solicitud;
                 posicionMaxRecorrida = solicitud.getPosicionDisco();
            }

            actual = actual.siguiente;
        }

        // 3. Manejo del Cambio de Dirección (Si no se encontró ninguna solicitud en la dirección actual)
        if (mejorSolicitud == null) {
            if (politicaActiva.equals(SCAN)) {
                // SCAN: Invertir la dirección y buscar de nuevo (recursivamente o forzando la búsqueda)
                this.direccionActual *= -1; // Cambia ARRIBA a ABAJO o viceversa
                // Intentar buscar de nuevo. Si vuelve a ser null, la cola debe estar vacía o solo tener una solicitud.
                return buscarSCAN(); 
            } else if (politicaActiva.equals(CSCAN) && solicitudMasLejana != null) {
                // C-SCAN: Reiniciar el cabezal al principio (0) o al final (MAX) y luego buscar.
                this.cabezalActual = (direccionActual == DIRECCION_ARRIBA) ? 0 : POSICION_MAXIMA;
                // Después del reinicio, SCAN es simple: selecciona la solicitud con la posición más pequeña (si sube)
                // Para simplificar, aquí podríamos retornar la solicitud que estaba más cerca del reinicio (la más pequeña si sube).

                // NOTE: Para el C-SCAN real, debemos encontrar la solicitud con la menor posición
                // entre todas las restantes (la más cercana al inicio). Para simplificar la implementación 
                // no recursiva, retornaremos la que estaba más lejos si el cabezal no puede moverse, 
                // aunque el reinicio real ocurre en la próxima llamada.
                return buscarSSTF(); // Usar SSTF después del reinicio es una aproximación viable
            }
        }

        // 4. Remover y retornar la solicitud seleccionada
        if (mejorSolicitud != null) {
            colaES.removerSolicitud(mejorSolicitud);
        }

        return mejorSolicitud;
    }
    
    // Dentro de la clase SistemaArchivos

    /**
     * Método de alto nivel para crear un Archivo o Directorio.
     * @param nombre Nombre del nuevo nodo.
     * @param padre Directorio contenedor.
     * @param esDirectorio True si es Directorio, false si es Archivo.
     * @param tamanoBloques Tamaño (solo para archivos).
     * @param procesoCreador Proceso que ejecuta la operación.
     * @return El nodo creado, o null si falla.
     */
    public NodoSistema crearNodoSistema(String nombre, Directorio padre, boolean esDirectorio, int tamanoBloques, Proceso procesoCreador) {

        // 1. VERIFICAR PERMISOS (Requisito del Proyecto)
        [cite_start]// El modo usuario está restringido a solo lectura[cite: 12].
        if (!manejadorUsuarios.esAdministrador()) {
            System.out.println("CREACIÓN FALLIDA: Solo el Administrador puede crear nodos.");
            return null;
        }

        // 2. VERIFICAR EXISTENCIA (Evitar duplicados en el mismo directorio)
        if (padre.buscarHijo(nombre) != null) {
            System.out.println("CREACIÓN FALLIDA: Ya existe un nodo con el nombre '" + nombre + "'.");
            return null;
        }

        NodoSistema nuevoNodo = null;

        if (esDirectorio) {
            // La creación de Directorios es lógica y no requiere E/S de disco
            nuevoNodo = new Directorio(nombre, padre, procesoCreador.getTipoUsuario());
            padre.agregarHijo(nuevoNodo);
            System.out.println("Directorio creado exitosamente: " + nombre);

        } else { // Creación de Archivo (Requiere asignación de bloques y E/S)

            // 3. VERIFICAR ESPACIO EN DISCO
            if (tamanoBloques <= 0 || tamanoBloques > discoSD.getBloquesLibres()) {
                System.out.println("CREACIÓN FALLIDA: Tamaño inválido o espacio insuficiente.");
                return null;
            }

            // 4. CREAR EL OBJETO ARCHIVO Y ENCOLAR LA SOLICITUD
            Archivo nuevoArchivo = new Archivo(nombre, padre, procesoCreador.getTipoUsuario(), tamanoBloques, procesoCreador);

            // Creamos la Solicitud E/S para la asignación de bloques (E/S Lenta)
            SolicitudES solicitud = new SolicitudES(
                SolicitudES.OP_CREAR, 
                padre.getNombre() + "/" + nombre, 
                tamanoBloques
            );

            // 5. ENCOLAR LA SOLICITUD
            procesoCreador.setSolicitud(solicitud);
            procesoCreador.setEstado(Proceso.BLOQUEADO);
            colaES.encolar(solicitud);

            System.out.println("Solicitud de CREACIÓN encolada para Archivo: " + nombre);
            nuevoNodo = nuevoArchivo;
        }

        // Si la creación es encolada (Archivo), la agregación al padre se hace DENTRO
        // del método 'atenderSolicitudCREAR' (ver paso 2).

        return nuevoNodo;
    }
   
   public void setPoliticaActiva(String nuevaPolitica) {
    this.politicaActiva = nuevaPolitica;
    }

    public String getPoliticaActiva() {
        return politicaActiva;
    }

    public int getCabezalActual() {
        return cabezalActual;
    }

}