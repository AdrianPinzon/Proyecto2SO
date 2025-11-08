/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.almacenamiento;

import sistemaarchivos.modelo.Archivo;

public class TablaAsignacion {

    // ---------------------------------------------------
    // ESTRUCTURA INTERNA DE LA TABLA (Lista Enlazada Propia)
    // ---------------------------------------------------

    /**
     * Clase interna que representa una fila o entrada en la tabla.
     */
    private class EntradaTabla {
        // La entrada contiene una referencia directa al Archivo para obtener sus datos
        Archivo archivo; 
        EntradaTabla siguiente; // Puntero para la lista enlazada

        public EntradaTabla(Archivo archivo) {
            this.archivo = archivo;
            this.siguiente = null;
        }
    }

    // ---------------------------------------------------
    // ATRIBUTOS DE LA TABLA PRINCIPAL
    // ---------------------------------------------------
    
    private EntradaTabla cabeza; // El inicio de la lista (HEAD)
    private int cantidadArchivos; 

    // Constructor
    public TablaAsignacion() {
        this.cabeza = null;
        this.cantidadArchivos = 0;
    }

    // Dentro de la clase TablaAsignacion

    /**
     * Agrega la metadata de un nuevo archivo a la tabla.
     * @param archivo El archivo recién creado.
     */
    public void registrarArchivo(Archivo archivo) {
        EntradaTabla nuevaEntrada = new EntradaTabla(archivo);

        if (this.cabeza == null) {
            this.cabeza = nuevaEntrada;
        } else {
            EntradaTabla actual = this.cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevaEntrada;
        }
        this.cantidadArchivos++;
    }

    /**
     * Elimina la metadata de un archivo que ha sido borrado del sistema.
     * @param nombreArchivo El nombre del archivo a desregistrar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean desregistrarArchivo(String nombreArchivo) {
        if (this.cabeza == null) {
            return false;
        }

        // Caso 1: Eliminar la cabeza
        if (this.cabeza.archivo.getNombre().equals(nombreArchivo)) {
            this.cabeza = this.cabeza.siguiente;
            this.cantidadArchivos--;
            return true;
        }

        // Caso 2: Eliminar un nodo intermedio
        EntradaTabla actual = this.cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.archivo.getNombre().equals(nombreArchivo)) {
                // Saltamos el nodo: el actual apunta al siguiente del siguiente
                actual.siguiente = actual.siguiente.siguiente; 
                this.cantidadArchivos--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false; // No encontrado
    }
    
    public EntradaTabla getCabeza() {
    return cabeza;
    }

    public int getCantidadArchivos() {
        return cantidadArchivos;
    }

    // Método para obtener una representación de los datos para el JTable
    public String[][] obtenerDatosTabla() {
        String[][] datos = new String[this.cantidadArchivos][5]; // 5 columnas
        EntradaTabla actual = this.cabeza;
        int i = 0;

        while (actual != null && i < this.cantidadArchivos) {
            datos[i][0] = actual.archivo.getNombre();                                 // Nombre [cite: 60]
            datos[i][1] = String.valueOf(actual.archivo.getTamanoBloques());           // Cantidad de bloques [cite: 61]

            // Dirección del primer bloque [cite: 62]
            datos[i][2] = actual.archivo.getPrimerBloque() != null 
                        ? String.valueOf(actual.archivo.getPrimerBloque().getId()) 
                        : "N/A";

            datos[i][3] = actual.archivo.getProcesoCreador().getId();                 // Proceso que lo creó
            datos[i][4] = actual.archivo.getColorRepresentacion();                     // Color (si se usa) [cite: 63]

            actual = actual.siguiente;
            i++;
        }
        return datos;
    }
}