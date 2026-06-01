package juego;

import java.awt.Color;

import entorno.Entorno;


public class Castillo {

    private int x;      // posicion real en el mundo (no en pantalla)
    private int y;
    private int ancho;
    private int alto;

    public Castillo(int x, int y) {
        this.x     = x;
        this.y     = y;
        this.ancho = 120;
        this.alto  = 180;
    }

    // Dibuja el castillo desplazado por la camara
    public void dibujarConCamara(Entorno e, int camara) {
        int xd = this.x - camara; // x en pantalla

        // Base del castillo
        e.dibujarRectangulo(xd + 60, y + 130, 120, 100, 0, new Color(150, 130, 100));

        // Torre izquierda
        e.dibujarRectangulo(xd + 15, y + 80, 30, 130, 0, new Color(130, 110, 80));

        // Torre derecha
        e.dibujarRectangulo(xd + 105, y + 80, 30, 130, 0, new Color(130, 110, 80));

        // Techo torre izquierda (triangulo simulado con rectangulo angosto)
        e.dibujarRectangulo(xd + 15, y + 10, 30, 40, 0, new Color(180, 40, 40));

        // Techo torre derecha
        e.dibujarRectangulo(xd + 105, y + 10, 30, 40, 0, new Color(180, 40, 40));

        // Puerta
        e.dibujarRectangulo(xd + 60, y + 155, 30, 50, 0, new Color(60, 40, 20));

        // Ventana izquierda
        e.dibujarRectangulo(xd + 30, y + 110, 18, 18, 0, new Color(80, 160, 220));

        // Ventana derecha
        e.dibujarRectangulo(xd + 90, y + 110, 18, 18, 0, new Color(80, 160, 220));

        // Bandera encima torre izquierda
        e.dibujarRectangulo(xd + 13, y + 3, 4, 20, 0, new Color(80, 60, 40));
        e.dibujarRectangulo(xd + 22, y + 3, 14, 10, 0, new Color(220, 220, 50));

        // Bandera encima torre derecha
        e.dibujarRectangulo(xd + 103, y + 3, 4, 20, 0, new Color(80, 60, 40));
        e.dibujarRectangulo(xd + 112, y + 3, 14, 10, 0, new Color(220, 220, 50));
    }

    // Devuelve true si el personaje toco el castillo
    public boolean colisiona(Personaje p) {
        boolean tocaX = p.bordeDerecho() >= x && p.bordeIzquierdo() <= x + ancho;
        boolean tocaY = p.bordeInferior() >= y && p.bordeSuperior() <= y + alto;
        return tocaX && tocaY;
    }
    void dibujarVictoria(final Entorno e) {
    	e.dibujarRectangulo(400, 300, 800, 600, 0, new Color(220, 180, 0));
        e.escribirTexto("GANASTE!", 250, 250);
        e.escribirTexto("Llegaste al castillo!", 180, 320);
    }

    public int getX() {
        return x;
    }
}
