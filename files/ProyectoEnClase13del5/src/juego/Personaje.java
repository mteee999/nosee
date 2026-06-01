package juego;

import java.awt.Color;
import entorno.Entorno;

public class Personaje {

    private int x;
    private int y;
    private int ancho;
    private int alto;

    private int velY        = 0;
    private int gravedad    = 1;
    private int velMaxCaida = 15;
    private int fuerzaSalto = -16;
    private boolean enSuelo = false;

    public Personaje(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public void dibujar(Entorno e) {
        dibujarConCamara(e, 0);
    }

    // Dibuja el personaje desplazado segun la camara
    public void dibujarConCamara(Entorno e, int camara) {
        e.dibujarRectangulo(x - camara, y, ancho, alto, 0, Color.RED);
    }

    //Movimiento horizontal

    public void moverIzquierda() {
        x = x - 5;
    }

    public void moverDerecha() {
        x = x + 5;
    }

    //Gravedad y salto

    public void aplicarGravedad() {
        velY = velY + gravedad;
        if (velY > velMaxCaida) {
            velY = velMaxCaida;
        }
    }

    public void moverConGravedad() {
        y = y + velY;
    }

    public void saltar() {
        if (enSuelo == true) {
            velY = fuerzaSalto;
            enSuelo = false;
        }
    }

    public void aterrizar(int superficieY) {
        y = superficieY - alto / 2;
        velY = 0;
        enSuelo = true;
    }

    public void golpearTecho() {
        velY = 1;
    }

    //Colisiones con Obstaculo

    public boolean colisionaPorIzquierda(Obstaculo o) {
        boolean toca = bordeIzquierdo() <= o.bordeDerecho() && x > o.getX();
        boolean superpY = bordeInferior() > o.bordeSuperior() && bordeSuperior() < o.bordeInferior();
        return toca && superpY;
    }

    public boolean colisionaPorDerecha(Obstaculo o) {
        boolean toca = bordeDerecho() >= o.bordeIzquierdo() && x < o.getX();
        boolean superpY = bordeInferior() > o.bordeSuperior() && bordeSuperior() < o.bordeInferior();
        return toca && superpY;
    }

    public boolean colisionaPorAbajo(Obstaculo o) {
        boolean toca = bordeSuperior() <= o.bordeInferior() && y > o.getY();
        boolean superpX = bordeDerecho() > o.bordeIzquierdo() && bordeIzquierdo() < o.bordeDerecho();
        return toca && superpX;
    }

    public boolean colisionaPorArriba(Obstaculo o) {
        boolean toca = bordeInferior() >= o.bordeSuperior() && y < o.getY();
        boolean superpX = bordeDerecho() > o.bordeIzquierdo() && bordeIzquierdo() < o.bordeDerecho();
        return toca && superpX;
    }

    //Colisiones con Isla
    
    public boolean colisionaIslaIzquierda(Isla isla) {
        boolean toca = bordeIzquierdo() <= isla.bordeDerecho() && x > isla.getX();
        boolean superpY = bordeInferior() > isla.bordeSuperior() && bordeSuperior() < isla.bordeInferior();
        return toca && superpY;
    }

    public boolean colisionaIslaDerecha(Isla isla) {
        boolean toca = bordeDerecho() >= isla.bordeIzquierdo() && x < isla.getX();
        boolean superpY = bordeInferior() > isla.bordeSuperior() && bordeSuperior() < isla.bordeInferior();
        return toca && superpY;
    }

    public boolean colisionaIslaAbajo(Isla isla) {
        boolean toca = bordeSuperior() <= isla.bordeInferior() && y > isla.getY();
        boolean superpX = bordeDerecho() > isla.bordeIzquierdo() && bordeIzquierdo() < isla.bordeDerecho();
        return toca && superpX;
    }

    public boolean colisionaIslaArriba(Isla isla) {
        boolean toca = bordeInferior() >= isla.bordeSuperior() && y < isla.getY();
        boolean superpX = bordeDerecho() > isla.bordeIzquierdo() && bordeIzquierdo() < isla.bordeDerecho();
        return toca && superpX;
    }

    //Bordes

    public int bordeDerecho() {
        return x + ancho / 2;
    }

    public int bordeIzquierdo() {
        return x - ancho / 2;
    }

    public int bordeInferior() {
        return y + alto / 2;
    }

    public int bordeSuperior() {
        return y - alto / 2;
    }

    //Getters y setters

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

    public void setAncho(int a) {
        this.ancho = a;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int a) {
        this.alto = a;
    }

    public int getVelY() {
        return velY;
    }

    public boolean isEnSuelo() {
        return enSuelo;
    }

    public void setEnSuelo(boolean b) {
        this.enSuelo = b;
    }
}
