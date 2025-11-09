package sistemaarchivos.gestion;

import sistemaarchivos.modelo.SolicitudES; 

public class PlanificadorDisco {

    // Constantes de Políticas (Mínimo 4 Requeridas) [cite: 51]
    public static final String FIFO = "FIFO";
    public static final String SSTF = "SSTF";
    public static final String SCAN = "SCAN";
    public static final String CSCAN = "C-SCAN"; 
    
    private String politicaActiva;
    private ColaES colaES; // Referencia a la cola de solicitudes
    private int cabezalActual; // Simula la posición actual del cabezal del disco
    
    // Constructor
    public PlanificadorDisco(ColaES colaES) {
        this.colaES = colaES;
        this.politicaActiva = FIFO; // FIFO como política inicial por defecto
        this.cabezalActual = 0;    // Inicialmente el cabezal está en la posición 0
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
               // FIFO: simplemente desencola el primero que llegó
               proximaSolicitud = colaES.desencolar();
               break;
           case SSTF:
               // SSTF (Shortest Seek Time First): Busca la solicitud más cercana a cabezalActual
               proximaSolicitud = buscarSSTF();
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

       // Si se encontró una solicitud y tiene una posición de disco válida, 
       // actualizamos la posición del cabezal
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

       // Recorrer la cola de E/S (necesitas un método en ColaES para iterar o un getter para el head)

       // **NOTA**: Aquí se requeriría iterar a través de los NodosCola
       // usando un bucle 'while (actual != null)' y la referencia 'actual.siguiente'.
       // Por ahora, solo retornamos la cabeza de forma temporal hasta que se implemente la iteración/extracción selectiva.

       // Implementación temporal, DEBE ser mejorada para buscar realmente la más cercana:
       return colaES.desencolar(); 
   }

   // Método para otras políticas (SCAN, C-SCAN)
   private SolicitudES buscarSCAN() {
       // La lógica de SCAN/C-SCAN es más compleja y se implementa después.
       return colaES.desencolar(); 
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