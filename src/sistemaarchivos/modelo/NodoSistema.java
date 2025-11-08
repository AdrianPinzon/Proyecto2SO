/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaarchivos.modelo;

/**
 * Clase abstracta que sirve como base para Archivo y Directorio.
 */
public abstract class NodoSistema {

    private String nombre;
    private String propietario; // Podría ser el nombre de usuario o "Administrador"
    private String permisos; // E.g., "RWX", "R" (solo lectura), etc.
    private Directorio padre; // Referencia al directorio contenedor
    
    // Constantes de Permisos/Roles
    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_USUARIO = "USER";
    public static final String PERMISO_LECTURA = "R";
    public static final String PERMISO_TOTAL = "RWX";
    
    // Constructor
    public NodoSistema(String nombre, Directorio padre, String propietario) {
        this.nombre = nombre;
        this.padre = padre;
        this.propietario = propietario;

        // Por defecto, asignamos el permiso de solo lectura
        this.permisos = PERMISO_LECTURA; 
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }

    public Directorio getPadre() {
        return padre;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getPermisos() {
        return permisos;
    }

    // Setters (solo se permite modificar el nombre y permisos)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    // Método abstracto para obtener el tamaño (diferente para Archivo y Directorio)
    public abstract int getTamano();

}
