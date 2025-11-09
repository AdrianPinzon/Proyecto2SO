package sistemaarchivos.interfaz;

import sistemaarchivos.gestion.SistemaArchivos;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*; 

public class PanelTablas extends JPanel {

    private final SistemaArchivos sistema;
    
    // Tabla 1: Asignación de Archivos
    private JTable tablaArchivos;
    private DefaultTableModel modeloTablaArchivos;
    private final String[] columnasArchivos = {"Nombre", "Bloques", "1er Bloque (ID)", "Proceso Creador", "Color"};
    
    // Tabla 2: Cola de Procesos
    private JTable tablaProcesos;
    private DefaultTableModel modeloTablaProcesos;
    private final String[] columnasProcesos = {"ID Proceso", "Estado", "Operación E/S", "Ruta/Archivo"};
    
    // Constructor
    public PanelTablas(SistemaArchivos sistema) {
        this.sistema = sistema;
        setLayout(new GridLayout(2, 1)); // Dividir el panel en dos filas (una para cada tabla)

        // Inicializar ambas tablas
        inicializarTablaArchivos();
        inicializarTablaProcesos();

        // Agregar ambas tablas a este panel
        add(new JScrollPane(tablaArchivos));
        add(new JScrollPane(tablaProcesos));

        setBorder(BorderFactory.createTitledBorder("Metadatos y Procesos"));
    }
    
    private void inicializarTablaArchivos() {
    this.modeloTablaArchivos = new DefaultTableModel(null, columnasArchivos);
    this.tablaArchivos = new JTable(modeloTablaArchivos);
    // Personalizar si es necesario (e.g., tamaño de columnas)
    
    // Cargar datos iniciales
    actualizarTablaArchivos(); 
    }

    private void inicializarTablaProcesos() {
        this.modeloTablaProcesos = new DefaultTableModel(null, columnasProcesos);
        this.tablaProcesos = new JTable(modeloTablaProcesos);
        // Personalizar si es necesario

        // Cargar datos iniciales
        actualizarTablaProcesos();
    }
    
    /**
    * Actualiza la Tabla de Asignación de Archivos.
    */
   public void actualizarTablaArchivos() {
       // 1. Limpiar datos antiguos
       modeloTablaArchivos.setRowCount(0);

       // 2. Obtener los nuevos datos de la estructura propia (TablaAsignacion)
       String[][] datos = sistema.getTablaAsignacion().obtenerDatosTabla();

       // 3. Añadir las filas (metadatos)
       for (String[] fila : datos) {
           modeloTablaArchivos.addRow(fila);
       }
   }

   /**
    * Actualiza la Tabla/Vista de la Cola de Procesos.
    */
   public void actualizarTablaProcesos() {
       // 1. Limpiar datos antiguos
       modeloTablaProcesos.setRowCount(0);

       // 2. Obtener los datos de la ColaES
       // **NOTA**: Se requiere un método de iteración en ColaES.java para recorrer 
       // y obtener todos los datos de los procesos y sus solicitudes.

       // Implementación temporal (asumiendo que se tiene un array de datos):
       // String[][] datosProcesos = sistema.getColaES().obtenerDatosTabla(); 

       // Simulación de un proceso en cola:
       if (!sistema.getColaES().estaVacia()) {
           // La iteración real aquí debe usar el NodoCola.getFrente() y recorrer el 'siguiente'
           // Por simplicidad, asumimos un método auxiliar para obtener la lista de procesos en cola
           // para la visualización.
       }

       // Ejemplo de añadir un proceso simulado
       // modeloTablaProcesos.addRow(new Object[]{"P5", "BLOQUEADO", "CREAR", "/home/nuevo.txt"});
   }

   /**
    * Método que debe ser llamado por el controlador para refrescar ambas tablas.
    */
   public void actualizarVista() {
       actualizarTablaArchivos();
       actualizarTablaProcesos();
   }

}