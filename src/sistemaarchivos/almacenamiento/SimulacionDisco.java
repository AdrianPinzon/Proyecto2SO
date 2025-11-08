/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.almacenamiento;

import sistemaarchivos.modelo.Bloque;
import sistemaarchivos.modelo.Archivo;

public class SimulacionDisco {

    // Se recomienda una cantidad máxima de bloques razonable para la simulación [cite: 35]
    private static final int CAPACIDAD_MAXIMA = 100; 
    
    // Arreglo simple para almacenar todos los bloques del SD
    private final Bloque[] bloquesSD;
    
    // Contador para seguimiento rápido
    private int bloquesLibres;
    
    // Constructor
    public SimulacionDisco() {
        this.bloquesSD = new Bloque[CAPACIDAD_MAXIMA];
        this.bloquesLibres = CAPACIDAD_MAXIMA;

        // Inicializar cada bloque
        for (int i = 0; i < CAPACIDAD_MAXIMA; i++) {
            bloquesSD[i] = new Bloque(i);
        }
    }
    
    /**
    * Asigna los N bloques necesarios para un Archivo, implementando la Asignación Encadenada.
    * @param archivo El archivo para el que se asignan los bloques.
    * @return true si se asignaron todos los bloques, false si no hay espacio.
    */
   public boolean asignarBloques(Archivo archivo) {
       int bloquesNecesarios = archivo.getTamanoBloques();
       if (bloquesNecesarios > this.bloquesLibres) {
           // No hay espacio disponible [cite: 34]
           return false;
       }

       Bloque bloqueAnterior = null;
       int asignados = 0;

       for (int i = 0; i < CAPACIDAD_MAXIMA && asignados < bloquesNecesarios; i++) {
           Bloque bloqueActual = bloquesSD[i];

           if (!bloqueActual.isOcupado()) {

               bloqueActual.asignar(archivo); // Marcar como ocupado por el archivo
               this.bloquesLibres--;

               if (asignados == 0) {
                   // Este es el primer bloque de la cadena
                   archivo.setPrimerBloque(bloqueActual);
               } else {
                   // Encadenar: el bloque anterior apunta a este
                   bloqueAnterior.setSiguienteBloque(bloqueActual);
               }

               bloqueAnterior = bloqueActual;
               asignados++;
           }
       }
       // La asignación fue exitosa
       return true; 
   }
   
   /**
    * Libera todos los bloques asociados a un archivo, rompiendo la cadena.
    * Esto ocurre al eliminar un archivo[cite: 33, 46].
    * @param archivo El archivo cuyos bloques serán liberados.
    */
   public void liberarBloques(Archivo archivo) {
       Bloque actual = archivo.getPrimerBloque();

       while (actual != null) {
           // Guardar referencia al siguiente antes de liberar el actual
           Bloque siguiente = actual.getSiguienteBloque(); 

           // Liberar el bloque
           actual.liberar(); 
           this.bloquesLibres++;

           // Mover al siguiente bloque en la cadena
           actual = siguiente;
       }
       // Importante: Asegurar que el archivo ya no referencie ningún bloque
       archivo.setPrimerBloque(null); 
   }
   
   //Getters
    public Bloque[] getBloquesSD() {
    return bloquesSD;
    }

    public int getBloquesLibres() {
        return bloquesLibres;
    }

    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }

}
