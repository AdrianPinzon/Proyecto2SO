package sistemaarchivos.gestion;

import sistemaarchivos.modelo.SolicitudES; 

public class ColaES {

    // ---------------------------------------------------
    // ESTRUCTURA INTERNA DE LA COLA (Lista Enlazada Propia)
    // ---------------------------------------------------

    /**
     * Clase interna que representa un nodo en la cola enlazada.
     */
    private class NodoCola {
        SolicitudES solicitud;
        NodoCola siguiente;

        public NodoCola(SolicitudES solicitud) {
            this.solicitud = solicitud;
            this.siguiente = null;
        }
    }

    // ---------------------------------------------------
    // ATRIBUTOS DE LA COLA PRINCIPAL
    // ---------------------------------------------------
    
    private NodoCola frente; // HEAD: El elemento que sale (primero en entrar)
    private NodoCola finalCola; // TAIL: El elemento que entra (último en entrar)
    private int tamano;

    // Constructor de la cola
    public ColaES() {
        this.frente = null;
        this.finalCola = null;
        this.tamano = 0;
    }
    

    /**
     * Agrega una solicitud al final de la cola (Enqueue).
     * @param solicitud La SolicitudES a añadir.
     */
    public void encolar(SolicitudES solicitud) {
        NodoCola nuevoNodo = new NodoCola(solicitud);

        if (this.finalCola == null) {
            // La cola está vacía
            this.frente = nuevoNodo;
            this.finalCola = nuevoNodo;
        } else {
            // Enganchar el nuevo nodo al final
            this.finalCola.siguiente = nuevoNodo;
            // Mover el puntero final al nuevo nodo
            this.finalCola = nuevoNodo;
        }
        this.tamano++;
    }

    /**
     * Remueve y retorna la solicitud del frente de la cola (Dequeue - para FIFO).
     * NOTA: Esta función DEBE ser usada con cuidado, el PlanificadorDisco 
     * probablemente usará un método para "sacar" un elemento específico de la lista.
     * @return La SolicitudES del frente, o null si está vacía.
     */
    public SolicitudES desencolar() {
        if (this.frente == null) {
            return null;
        }

        SolicitudES solicitudSaliente = this.frente.solicitud;

        // Mover el frente al siguiente
        this.frente = this.frente.siguiente;

        if (this.frente == null) {
            // Si el frente es null, la cola quedó vacía
            this.finalCola = null;
        }

        this.tamano--;
        return solicitudSaliente;
    }

    // Getters esenciales
    public int getTamano() {
        return tamano;
    }

    public NodoCola getFrente() {
        return frente;
    }

    /**
     * Método para verificar si la cola está vacía.
     */
    public boolean estaVacia() {
        return this.frente == null;
    }

}