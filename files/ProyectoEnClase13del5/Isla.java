package juego;

import java.awt.Color;

import entorno.Entorno;

/**
 * Isla — representa una plataforma del mapa.
 *
 * Cada isla tiene:
 *   x     -> posicion horizontal
 *   y     -> posicion vertical (cuanto mas chico, mas arriba)
 *   ancho -> largo de la isla
 *   tipo  -> 0 = piso, 1 = flotante nivel 1, 2 = flotante nivel 2
 */
public class Isla {

    // Constantes para el tipo de isla
    public static final int PISO       = 0;
    public static final int FLOTANTE1 = 1;
    public static final int FLOTANTE2 = 2;

    // Atributos de la isla
    private int x;
    private int y;
    private int ancho;
    private int tipo;

    // Constructor
    public Isla(int x, int y, int ancho, int tipo) {
        this.x     = x;
        this.y     = y;
        this.ancho = ancho;
        this.tipo  = tipo;
    }

    // Getters
    public int getX()     { return x; }
    public int getY()     { return y; }
    public int getAncho() { return ancho; }
    public int getTipo()  { return tipo; }

    /**
     * Devuelve true si la princesa esta parada sobre esta isla.
     *
     * pieX    -> posicion horizontal del pie de la princesa
     * pieY    -> posicion vertical   del pie de la princesa
     * velY    -> velocidad vertical (positivo = cayendo)
     */
    public boolean colisiona(int pieX, int pieY, double velY) {
        // Si va subiendo no colisiona
        if (velY < 0) {
            return false;
        }

        // El pie de la princesa en el frame anterior (antes de moverse)
        int pieAnterior = (int)(pieY - velY);

        // Tiene que estar dentro del ancho de la isla
        boolean dentroDeIsla = (pieX > x) && (pieX < x + ancho);

        // Tiene que haber cruzado la superficie de la isla
        boolean cruzaSuperficie = (pieY >= y) && (pieAnterior <= y);

        return dentroDeIsla && cruzaSuperficie;
    }
    public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, tipo, 0, Color.GREEN);
    }
}
