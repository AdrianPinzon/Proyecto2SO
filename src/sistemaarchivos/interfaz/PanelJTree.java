package sistemaarchivos.interfaz;

import sistemaarchivos.gestion.SistemaArchivos;
import sistemaarchivos.modelo.Directorio;
import sistemaarchivos.modelo.NodoSistema;
import javax.swing.*;
import javax.swing.tree.*; // Necesario para JTree y DefaultTreeModel

public class PanelJTree extends JPanel {

    private final SistemaArchivos sistema;
    private JTree fileTree;
    private DefaultTreeModel treeModel; // El modelo que gestiona la estructura del JTree
    
    // Constructor
    public PanelJTree(SistemaArchivos sistema) {
        this.sistema = sistema;

        // Inicializar el JTree
        inicializarJTree();

        // Configuración del panel
        setLayout(new BorderLayout());
        add(new JScrollPane(fileTree), BorderLayout.CENTER);

        // Añadir listener para saber cuándo se selecciona un nodo
        añadirListeners();
    }
    
    private void inicializarJTree() {
    // Convertir el DirectorioRaiz (tu modelo) a un DefaultMutableTreeNode (modelo de Swing)
    DefaultMutableTreeNode rootSwingNode = crearNodoRecursivo(sistema.getDirectorioRaiz());
    
    this.treeModel = new DefaultTreeModel(rootSwingNode);
    this.fileTree = new JTree(treeModel);
    }

    /**
     * Método recursivo para construir el modelo de árbol de Swing a partir del modelo del sistema.
     * @param nodoSistema El Directorio o Archivo del modelo.
     * @return El nodo correspondiente en el formato de Swing.
     */
    private DefaultMutableTreeNode crearNodoRecursivo(NodoSistema nodoSistema) {
        // Creamos el nodo de Swing, usando el objeto NodoSistema como el 'user object'
        DefaultMutableTreeNode nodoSwing = new DefaultMutableTreeNode(nodoSistema);

        if (nodoSistema instanceof Directorio) {
            Directorio dir = (Directorio) nodoSistema;

            // Obtenemos el primer nodo de nuestra Lista Enlazada
            NodoLista actual = dir.getListaDeHijos().getPrimerNodo();

            // Iteramos manualmente sobre la estructura de datos propia
            while (actual != null) {
                NodoSistema hijo = actual.getDato();

                // Llamada recursiva: construir el sub-árbol del hijo
                nodoSwing.add(crearNodoRecursivo(hijo));

                // Mover al siguiente nodo en la lista enlazada
                actual = actual.getSiguiente();
        }
        // Para el Archivo (que no tiene hijos), simplemente retorna el nodo.

        return nodoSwing;
    }
    }   
    
    /**
    * Refresca la vista del árbol completamente.
    */
   public void actualizarVista() {
       // 1. Reconstruir el modelo desde la raíz
       DefaultMutableTreeNode newRoot = crearNodoRecursivo(sistema.getDirectorioRaiz());

       // 2. Notificar al JTree que el modelo ha cambiado
       treeModel.setRoot(newRoot);
       treeModel.reload();
   }
   
   private void añadirListeners() {
        fileTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();

            if (nodo == null) return;

            // El objeto del sistema está almacenado en el 'user object' del nodo de Swing
            Object nodoObj = nodo.getUserObject();

            if (nodoObj instanceof NodoSistema) {
                NodoSistema seleccionado = (NodoSistema) nodoObj;

                // Lógica para mostrar información en un panel lateral o una ventana emergente
                // Ejemplo: System.out.println("Seleccionado: " + seleccionado.getNombre() + 
                //                          ", Tamaño: " + seleccionado.getTamano() + " bloques.");
            }
        });
    }


}