/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.modelo;

public class SolicitudES {

    // Constantes de Operación (CRUD)
    public static final String OP_CREAR = "CREAR";
    public static final String OP_LEER = "LEER";
    public static final String OP_ACTUALIZAR = "ACTUALIZAR";
    public static final String OP_ELIMINAR = "ELIMINAR";
    
    // Atributos
    private final String tipoOperacion; // El tipo de operación (CRUD)
    private final String rutaArchivo;     // La ruta completa o el nombre del archivo/directorio objetivo
    private int tamanoSolicitado;       // Relevante solo para OP_CREAR (tamaño en bloques)
    private int posicionDisco;          // Relevante para planificación de disco (e.g., SSTF, SCAN)
    
    /**
    * Constructor para operaciones de Lectura, Actualización y Eliminación.
    */
   public SolicitudES(String tipoOperacion, String rutaArchivo) {
       this.tipoOperacion = tipoOperacion;
       this.rutaArchivo = rutaArchivo;
       this.tamanoSolicitado = 0; // No aplica
       this.posicionDisco = -1; // Se llenará al entrar a la cola
   }

   /**
    * Constructor para la operación CREAR.
    */
   public SolicitudES(String tipoOperacion, String rutaArchivo, int tamanoBloques) {
       this.tipoOperacion = tipoOperacion;
       this.rutaArchivo = rutaArchivo;
       this.tamanoSolicitado = tamanoBloques; // Tamaño en bloques
       this.posicionDisco = -1; 
   }
   
   public String getTipoOperacion() {
    return tipoOperacion;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public int getTamanoSolicitado() {
        return tamanoSolicitado;
    }

    public int getPosicionDisco() {
        return posicionDisco;
    }

    // Setter para establecer la posición en el disco (la dirección del primer bloque)
    public void setPosicionDisco(int posicionDisco) {
        this.posicionDisco = posicionDisco;
    }


}