package sistemaarchivos.interfaz;

import sistemaarchivos.gestion.SistemaArchivos;
import sistemaarchivos.modelo.Bloque;
import javax.swing.JPanel;
import java.awt.*; 

public class PanelDisco extends JPanel {

    private final SistemaArchivos sistema;
    
    // Constantes de Dibujo
    private static final int BLOQUES_POR_FILA = 10; 
    private static final int TAMANO_BLOQUE = 25; 
    private static final int MARGEN = 15; 
    
    // Constructor
    public PanelDisco(SistemaArchivos sistema) {
        this.sistema = sistema;
        // Establecer un tamaño preferido para el panel
        int ancho = BLOQUES_POR_FILA * TAMANO_BLOQUE + (2 * MARGEN);
        int alto = (sistema.getDiscoSD().getCapacidadMaxima() / BLOQUES_POR_FILA + 1) * TAMANO_BLOQUE + (2 * MARGEN);
        setPreferredSize(new Dimension(ancho, alto));

        // Establecer un borde para distinguir el panel en la VentanaPrincipal
        setBorder(BorderFactory.createTitledBorder("Simulación de Disco (SD)"));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        Bloque[] bloques = sistema.getDiscoSD().getBloquesSD();
        int totalBloques = sistema.getDiscoSD().getCapacidadMaxima();

        // Iterar sobre todos los bloques del disco
        for (int i = 0; i < totalBloques; i++) {
            Bloque b = bloques[i];

            // Calcular la posición (x, y) en la cuadrícula
            int columna = i % BLOQUES_POR_FILA;
            int fila = i / BLOQUES_POR_FILA;

            int x = MARGEN + (columna * TAMANO_BLOQUE);
            int y = MARGEN + (fila * TAMANO_BLOQUE);

            // 1. Determinar el color de fondo
            if (b.isOcupado()) {
                // Si está ocupado, usa el color del archivo asignado
                if (b.getArchivoAsignado() != null) {
                    // NOTA: Implementar lógica para convertir String color (e.g., "AZUL") a java.awt.Color
                    g2d.setColor(getColorFromFile(b.getArchivoAsignado().getColorRepresentacion()));
                } else {
                    g2d.setColor(Color.LIGHT_GRAY); // Ocupado sin archivo específico
                }
            } else {
                g2d.setColor(Color.GREEN.darker()); // Libre (color oscuro)
            }

            // 2. Rellenar el bloque (cuadrado)
            g2d.fillRect(x, y, TAMANO_BLOQUE - 2, TAMANO_BLOQUE - 2); 

            // 3. Dibujar el borde del bloque
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, TAMANO_BLOQUE - 2, TAMANO_BLOQUE - 2);

            // 4. Escribir el ID del bloque (opcional, pero útil)
            g2d.drawString(String.valueOf(b.getId()), x + 5, y + 15);
        }
    }
    
    /**
    * Llama a este método cada vez que un bloque cambie de estado (CRUD).
    */
   public void actualizarVista() {
       repaint(); // Le dice a Swing que redibuje el componente
   }

   // Helper simple (debe ser implementado completamente según tus String de color)
   private Color getColorFromFile(String colorStr) {
       switch (colorStr) {
           case "AZUL":
               return Color.BLUE;
           case "ROJO":
               return Color.RED;
           default:
               return Color.YELLOW;
       }
   }

}