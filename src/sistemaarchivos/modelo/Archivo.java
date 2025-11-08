/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.modelo;

// Importamos la clase Proceso que usaremos más adelante
import sistemaarchivos.modelo.Proceso; 

public class Archivo extends NodoSistema {

    private int tamanoBloques;         // Tamaño solicitado en unidades de bloques [cite: 14, 40]
    private Bloque primerBloque;        // Referencia al inicio de la cadena de bloques en el SD [cite: 62]
    private Proceso procesoCreador;     // Proceso que originó la creación del archivo [cite: 21]
    
    // Podrías añadir un color para la representación visual en el PanelDisco [cite: 63]
    private String colorRepresentacion; 
    
    // Constructor
    public Archivo(String nombre, Directorio padre, String propietario, int tamanoBloques, Proceso creador) {
        super(nombre, padre, propietario); // Llama al constructor de NodoSistema
        this.tamanoBloques = tamanoBloques;
        this.procesoCreador = creador;
        this.primerBloque = null; // Inicialmente no se ha asignado ningún bloque
        // Asigna un color por defecto o basado en una lógica, si lo requieres para la GUI [cite: 63]
        this.colorRepresentacion = "AZUL"; 
    }
    
    // Getters
    public int getTamanoBloques() {
        return tamanoBloques;
    }

    // Implementación del método abstracto de NodoSistema. Retorna el tamaño en bloques.
    @Override
    public int getTamano() {
        return tamanoBloques; 
    }

    public Bloque getPrimerBloque() {
        return primerBloque;
    }

    public Proceso getProcesoCreador() {
        return procesoCreador;
    }

    public String getColorRepresentacion() {
        return colorRepresentacion;
    }


    // Setters para la gestión de bloques

    /**
     * Establece el primer bloque de la cadena del archivo.
     * Esto se hace después de que la SimulacionDisco ha encontrado y asignado el primer bloque.
     */
    public void setPrimerBloque(Bloque primerBloque) {
        this.primerBloque = primerBloque;
    }

}