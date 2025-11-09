package sistemaarchivos.interfaz;

import sistemaarchivos.gestion.SistemaArchivos;
import javax.swing.*; // Para componentes Swing (JFrame, JPanel, etc.)
import java.awt.BorderLayout; // Para organizar el layout

public class VentanaPrincipal extends JFrame {

    private final SistemaArchivos sistema;
    
    // Paneles visuales
    private PanelJTree panelJTree;
    private PanelDisco panelDisco;
    private PanelTablas panelTablas;
    
    // Controles de configuración
    private JComboBox<String> selectorPolitica;
    private JComboBox<String> selectorModo;
    
    // Constructor
    public VentanaPrincipal(SistemaArchivos sistema) {
        this.sistema = sistema;

        // Configuración básica de la ventana
        setTitle("Proyecto 2: Simulador de Sistema de Archivos - SO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout()); // Usamos un layout base

        // 1. Inicializar y configurar los paneles
        inicializarComponentes();

        // 2. Organizar la interfaz
        organizarLayout();

        // Hacer la ventana visible
        setVisible(true);
    }
    
    private void inicializarComponentes() {
    // Los paneles se instancian pasando la referencia al SistemaArchivos para que puedan obtener datos
    this.panelJTree = new PanelJTree(sistema);
    this.panelDisco = new PanelDisco(sistema);
    this.panelTablas = new PanelTablas(sistema);
    
    // Inicializar controles de configuración
    selectorPolitica = new JComboBox<>(new String[]{
        sistema.getPlanificador().FIFO, 
        sistema.getPlanificador().SSTF,
        sistema.getPlanificador().SCAN, 
        sistema.getPlanificador().CSCAN
    });
    
    selectorModo = new JComboBox<>(new String[]{
        sistema.getManejadorUsuarios().ROL_ADMIN, 
        sistema.getManejadorUsuarios().ROL_USUARIO
    });

    // Añadir Listeners (manejo de eventos)
        añadirListenersControl(); 
    }

    private void organizarLayout() {
        // Contenedor superior para controles y botones
        JPanel panelControles = new JPanel();
        panelControles.add(new JLabel("Modo de Usuario:"));
        panelControles.add(selectorModo);
        panelControles.add(new JLabel("Política de Disco:"));
        panelControles.add(selectorPolitica);
        // ... Agregar botones CRUD aquí

        add(panelControles, BorderLayout.NORTH);

        // Dividir la vista central
        JSplitPane splitCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                                                panelJTree, 
                                                panelDisco);
        splitCentral.setDividerLocation(300); // 300 píxeles para el JTree

        // Colocar las tablas abajo
        JSplitPane splitInferior = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                                  splitCentral,
                                                  panelTablas);
        splitInferior.setDividerLocation(500); // 500 píxeles para la vista de disco/JTree

        add(splitInferior, BorderLayout.CENTER);
    }
    
    private void añadirListenersControl() {
    // 1. Listener para cambio de Modo
    selectorModo.addActionListener(e -> {
        String modoSeleccionado = (String) selectorModo.getSelectedItem();
        sistema.getManejadorUsuarios().setModoActual(modoSeleccionado);
        // Actualizar la interfaz para reflejar los permisos
        // Ejemplo: deshabilitar botones de CRUD si es modo Usuario
    });

    // 2. Listener para cambio de Política de Disco
    selectorPolitica.addActionListener(e -> {
        String politicaSeleccionada = (String) selectorPolitica.getSelectedItem();
        sistema.getPlanificador().setPoliticaActiva(politicaSeleccionada);
        // NOTA: No olvidar actualizar la vista de la cola de E/S.
    });

    // 3. Listener para las operaciones (Ejemplo: botón de Crear)
    // Se requiere un botón de "Crear Archivo", que llamaría a:
    // sistema.ejecutarCrear(nuevaSolicitud, procesoActual);
    
    // 4. Mecanismo de Actualización de la GUI en Tiempo Real
    // Necesitas un Timer de Swing o un hilo para llamar periódicamente 
    // a los métodos 'actualizarVista()' en panelDisco, panelJTree, etc.
    }

    // Método main para ejecutar el programa
    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            SistemaArchivos motor = new SistemaArchivos();
            new VentanaPrincipal(motor);
        });
    }

}
