package juego;

import java.awt.Color;
import entorno.Entorno;

public class Isla {
    public static int piso       = 0;
    public static int flotante1  = 1;
    public static int flotante2  = 2;

    // Atributos
    private int x;
    private int y;
    private int ancho;
    private int tipo;

    public Isla(int x, int y, int ancho, int tipo) {
        this.x     = x;
        this.y     = y;
        this.ancho = ancho;
        this.tipo  = tipo;
    }

    public void dibujar(Entorno e) {
        if (tipo == piso) {
            e.dibujarRectangulo(x + ancho / 2, y, ancho, 18, 0, new Color(80, 50, 20));
        } else if (tipo == flotante1) {
            e.dibujarRectangulo(x + ancho / 2, y, ancho, 18, 0, new Color(100, 65, 30));
        } else {
            e.dibujarRectangulo(x + ancho / 2, y, ancho, 18, 0, new Color(120, 80, 40));
        }
    }
    public void dibujarConCamara(Entorno e, int camara) {
        int xDibujo = x - camara + ancho / 2;
        if (tipo == piso) {
            e.dibujarRectangulo(xDibujo, y, ancho, 18, 0, new Color(80, 50, 20));
        } else if (tipo == flotante1) {
            e.dibujarRectangulo(xDibujo, y, ancho, 18, 0, new Color(100, 65, 30));
        } else {
            e.dibujarRectangulo(xDibujo, y, ancho, 18, 0, new Color(120, 80, 40));
        }
    }

    // Bordes de la isla
    public int bordeIzquierdo() { return x; }
    public int bordeDerecho()   { return x + ancho; }
    public int bordeSuperior()  { return y; }
    public int bordeInferior()  { return y + 18; }

	public static int getPiso() {
		return piso;
	}

	public static void setPiso(int piso) {
		Isla.piso = piso;
	}

	public static int getFlotante1() {
		return flotante1;
	}

	public static void setFlotante1(int flotante1) {
		Isla.flotante1 = flotante1;
	}

	public static int getFlotante2() {
		return flotante2;
	}

	public static void setFlotante2(int flotante2) {
		Isla.flotante2 = flotante2;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}


}
