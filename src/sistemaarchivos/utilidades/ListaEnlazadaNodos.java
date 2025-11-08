/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.utilidades;

import sistemaarchivos.modelo.NodoSistema;

public class ListaEnlazadaNodos {

    /**
     * Clase interna que representa un nodo en la lista enlazada.
     * Contiene el dato (un NodoSistema) y la referencia al siguiente nodo.
     */
    private class NodoLista {
        NodoSistema dato;
        NodoLista siguiente;

        public NodoLista(NodoSistema dato) {
            this.dato = dato;
            this.siguiente = null; // Inicialmente no apunta a nada
        }
    }

    // Atributos de la lista principal
    private NodoLista cabeza; // El primer nodo de la lista (HEAD)
    private int tamano;       // Contador para saber cuántos elementos hay

    // Constructor de la lista
    public ListaEnlazadaNodos() {
        this.cabeza = null; // La lista inicia vacía
        this.tamano = 0;
    }
    
    // Dentro de la clase ListaEnlazadaNodos

/**
 * Agrega un nuevo nodo al final de la lista.
 * @param nuevoNodo El NodoSistema (Archivo o Directorio) a agregar.
 */
public void agregar(NodoSistema nuevoNodo) {
    NodoLista nuevo = new NodoLista(nuevoNodo);

    if (this.cabeza == null) {
        // La lista está vacía, el nuevo nodo es la cabeza
        this.cabeza = nuevo;
    } else {
        // Recorrer hasta el último nodo
        NodoLista actual = this.cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        // Enganchar el nuevo nodo al final
        actual.siguiente = nuevo;
    }
    this.tamano++;
}

    /**
     * Busca un NodoSistema por su nombre.
     * @param nombre El nombre del nodo a buscar.
     * @return El NodoSistema si se encuentra, o null.
     */
    public NodoSistema buscar(String nombre) {
        NodoLista actual = this.cabeza;

        while (actual != null) {
            if (actual.dato.getNombre().equals(nombre)) {
                return actual.dato; // Encontrado
            }
            actual = actual.siguiente;
        }
        return null; // No encontrado
    }

    // Getters esenciales
    public int getTamano() {
        return tamano;
    }

    public NodoLista getCabeza() {
        return cabeza;
    }
}
