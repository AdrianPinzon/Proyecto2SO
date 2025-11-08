/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.modelo;

public class Bloque {

    // Atributos de Identificación y Estado
    private final int id; // Identificador único del bloque (su posición en el SD)
    private boolean ocupado; 

    // Atributos para la Asignación Encadenada
    // Se usa la clase Bloque a sí misma para crear el enlace (puntero)
    private Bloque siguienteBloque; 

    // Atributos para la Visualización (saber qué lo ocupa)
    private Archivo archivoAsignado; // Referencia al archivo que ocupa este bloque

    // Constantes de Estado
    public static final String ESTADO_LIBRE = "L";
    public static final String ESTADO_OCUPADO = "O";
    
    // Constructor
    public Bloque(int id) {
        this.id = id;
        this.ocupado = false; // Inicialmente libre
        this.siguienteBloque = null;
        this.archivoAsignado = null;
    }
    
    // Getters
    public int getId() {
        return id;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public Bloque getSiguienteBloque() {
        return siguienteBloque;
    }

    public Archivo getArchivoAsignado() {
        return archivoAsignado;
    }

    // Setters y Métodos de Estado

    public void setSiguienteBloque(Bloque siguienteBloque) {
        this.siguienteBloque = siguienteBloque;
    }

    /**
     * Marca el bloque como ocupado por un archivo específico.
     */
    public void asignar(Archivo archivo) {
        this.ocupado = true;
        this.archivoAsignado = archivo;
    }

    /**
     * Libera el bloque. Resetea el estado y el puntero de encadenamiento.
     */
    public void liberar() {
        this.ocupado = false;
        this.archivoAsignado = null;
        this.siguienteBloque = null; // Romper el enlace
    }

    /**
     * Método de apoyo para la visualización del estado.
     */
    public String getEstadoVisual() {
        return this.ocupado ? ESTADO_OCUPADO : ESTADO_LIBRE;
    }
    
}
