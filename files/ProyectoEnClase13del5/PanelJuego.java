package juego;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║  ATENCION: esta clase maneja los graficos con Swing.     ║
 * ║  No es necesario entenderla por ahora.                   ║
 * ║  Solo usarla para ver el resultado en pantalla.          ║
 * ║                                                          ║
 * ║  Tecla R -> regenera el mapa                             ║
 * ╚══════════════════════════════════════════════════════════╝
 */
public class PanelJuego extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;

    private GeneradorMapa generadorMapa;
    private int           numGeneracion;

    public PanelJuego() {
        setPreferredSize(new java.awt.Dimension(
                GeneradorMapa.ANCHO_PANTALLA,
                GeneradorMapa.ALTO_PANTALLA));
        setFocusable(true);
        addKeyListener(this);

        generadorMapa = new GeneradorMapa();
        numGeneracion = 1;
        generadorMapa.generar();
    }

    // ── Dibujo ────────────────────────────────────────────────────────────────

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarFondo(g2);
        dibujarIslas(g2);
        dibujarInfo(g2);
    }

    private void dibujarFondo(Graphics2D g2) {
        GradientPaint cielo = new GradientPaint(
                0, 0, new Color(15, 10, 40),
                0, GeneradorMapa.ALTO_PANTALLA, new Color(50, 30, 80));
        g2.setPaint(cielo);
        g2.fillRect(0, 0, GeneradorMapa.ANCHO_PANTALLA, GeneradorMapa.ALTO_PANTALLA);

        // Estrellas
        g2.setColor(new Color(255, 255, 255, 160));
        int[] ex = {80,200,350,500,650,800,950,130,280,420,570,720,900};
        int[] ey = {30, 60, 20, 50, 30, 70, 25,100,120, 90,110, 95,115};
        for (int i = 0; i < ex.length; i++) {
            g2.fillOval(ex[i], ey[i], 2, 2);
        }

        // Zona de caida
        g2.setColor(new Color(180, 40, 40, 120));
        g2.fillRect(0, GeneradorMapa.ALTO_PANTALLA - 8,
                    GeneradorMapa.ANCHO_PANTALLA, 8);
    }

    private void dibujarIslas(Graphics2D g2) {
        Isla[] islas    = generadorMapa.getIslas();
        int    cantidad = generadorMapa.getCantidad();

        for (int i = 0; i < cantidad; i++) {
            Isla isla = islas[i];

            // Color segun el tipo
            Color colorBase;
            Color colorCesped;

            if (isla.getTipo() == Isla.PISO) {
                colorBase   = new Color(80,  50, 20);
                colorCesped = new Color(60, 160, 60);
            } else if (isla.getTipo() == Isla.FLOTANTE_1) {
                colorBase   = new Color(100,  65, 30);
                colorCesped = new Color( 80, 200, 80);
            } else {
                colorBase   = new Color(120,  80, 40);
                colorCesped = new Color(120, 220, 120);
            }

            // Sombra
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(isla.getX() + 4,
                             isla.getY() + 18,
                             isla.getAncho() - 4, 6, 4, 4);

            // Cuerpo
            g2.setColor(colorBase);
            g2.fillRoundRect(isla.getX(),
                             isla.getY() + 4,
                             isla.getAncho(), 14, 6, 6);

            // Cesped
            g2.setColor(colorCesped);
            g2.fillRoundRect(isla.getX(), isla.getY(),
                             isla.getAncho(), 8, 4, 4);

            // Borde
            g2.setColor(colorBase.darker());
            g2.drawRoundRect(isla.getX(), isla.getY(),
                             isla.getAncho(), 18, 6, 6);
        }
    }

    private void dibujarInfo(Graphics2D g2) {
        // Contar islas por tipo
        int nPiso = 0, nFlot1 = 0, nFlot2 = 0;
        Isla[] islas = generadorMapa.getIslas();
        for (int i = 0; i < generadorMapa.getCantidad(); i++) {
            if      (islas[i].getTipo() == Isla.PISO)       nPiso++;
            else if (islas[i].getTipo() == Isla.FLOTANTE_1) nFlot1++;
            else if (islas[i].getTipo() == Isla.FLOTANTE_2) nFlot2++;
        }

        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(10, 10, 330, 55, 10, 10);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 13));
        g2.drawString("Generacion #" + numGeneracion, 20, 30);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.drawString("Piso: " + nPiso + "   Nivel 1: " + nFlot1
                + "   Nivel 2: " + nFlot2, 20, 50);

        // Instruccion
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(10, GeneradorMapa.ALTO_PANTALLA - 40, 260, 28, 8, 8);
        g2.setColor(new Color(200, 220, 255));
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("[ R ] - Regenerar mapa aleatorio",
                18, GeneradorMapa.ALTO_PANTALLA - 22);
    }

    // ── Teclado ───────────────────────────────────────────────────────────────

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            numGeneracion++;
            generadorMapa.generar(); // genera un mapa nuevo
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e)    { }

    // ── Main ──────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Islas Flotantes");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);

        PanelJuego panel = new PanelJuego();
        ventana.add(panel);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
        panel.requestFocusInWindow();
    }
}
