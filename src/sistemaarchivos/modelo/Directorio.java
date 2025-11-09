/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package sistemaarchivos.modelo;

import sistemaarchivos.utilidades.ListaEnlazadaNodos;

public class Directorio extends NodoSistema {

    // Estructura de datos propia para almacenar los hijos
    private ListaEnlazadaNodos listaDeHijos; 
    
    // Constructor
    public Directorio(String nombre, Directorio padre, String propietario) {
        super(nombre, padre, propietario);
        // Inicializar la estructura propia
        this.listaDeHijos = new ListaEnlazadaNodos(); 
    }


    /**
     * Metodos
     * Agrega un nodo (Archivo o Directorio) a la lista de hijos.
     */
    public void agregarHijo(NodoSistema nodo) {
        // Usamos el método 'agregar' de tu ListaEnlazadaNodos
        this.listaDeHijos.agregar(nodo);
    }

    /**
     * Busca un nodo por su nombre.
     * @return El NodoSistema si existe, o null.
     */
    public NodoSistema buscarHijo(String nombre) {
        // Usamos el método 'buscar' de tu ListaEnlazadaNodos
        return this.listaDeHijos.buscar(nombre); 
    }

    /**
     * Elimina un nodo. (Asume que el método 'eliminar' existe en ListaEnlazadaNodos)
     */
    public boolean eliminarHijo(String nombre) {
        // El método 'eliminar' debe ser implementado en ListaEnlazadaNodos.java
        // return this.listaDeHijos.eliminar(nombre); 
        return true; // Placeholder, implementa la lógica real después
    }
    

    @Override
    public int getTamano() {
        int tamanoTotal = 0;
        
        // Usar el método getPrimerNodo() de nuestra lista enlazada para iniciar la iteración
        NodoLista actual = listaDeHijos.getPrimerNodo();

        // Iterar manualmente sobre la lista de hijos
        while (actual != null) {
            // Llamada recursiva: si es un directorio, suma su tamaño interno. Si es un archivo, suma sus bloques.
            tamanoTotal += actual.getDato().getTamano(); 

            // Mover al siguiente nodo
            actual = actual.getSiguiente();
        }                
        return tamanoTotal; 
    }

    //Getters
    public ListaEnlazadaNodos getListaDeHijos() {
        return listaDeHijos; 
    }

    public int getCantidadHijos() {
        // Usamos el contador de la lista enlazada
        return listaDeHijos.getTamano(); 
    }
}