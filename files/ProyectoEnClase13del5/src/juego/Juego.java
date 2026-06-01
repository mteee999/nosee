package juego;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje p;
	private Obstaculo o;
	private GeneradorMapa m;
	private boolean inicializado;
	private Castillo castillo;
    private int     camara;
    private boolean gano;
	// Variables y métodos propios de cada grupo
	// ...
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		// Inicializar lo que haga falta para el juego
		// ...
		// Inicia el juego!
		this.entorno.iniciar();
		inicializado  = false;
	}
	private void inicializar() {
        p    = new Personaje(200, 300, 20, 50);
        o    = new Obstaculo(400, 300, 50, 50);
        m = new GeneradorMapa();
        m.generar();
 
        int xCastillo = GeneradorMapa.anchoMundo - 200;
        int yCastillo = GeneradorMapa.yPiso - 180;
        castillo = new Castillo(xCastillo, yCastillo);
 
        camara = 0;
        gano   = false;
 
        inicializado = true;}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		// ...
		 if (inicializado == false) {
	            inicializar();
	            return;
		 }

		 Isla[] islas    = m.getIslas();
	        int    cantidad = m.getCantidad();
	 
	        // ── 1. CAMARA ─────────────────────────────────────────────────────────
	 
	        camara = p.getX() - entorno.ancho() / 2;
	 
	        if (camara < 0) {
	            camara = 0;
	        }
	        if (camara > GeneradorMapa.anchoMundo - entorno.ancho()) {
	            camara = GeneradorMapa.anchoMundo - entorno.ancho();
	        }
	 
	        // ── 2. GRAVEDAD ───────────────────────────────────────────────────────
	 
	        p.aplicarGravedad();
	        p.moverConGravedad();
	 
	        // ── 3. COLISIONES VERTICALES ──────────────────────────────────────────
	 
	        p.setEnSuelo(false);
	 
	        if (p.colisionaPorArriba(o)) {
	            p.aterrizar(o.bordeSuperior());
	        }
	        if (p.colisionaPorAbajo(o)) {
	            p.golpearTecho();
	        }
	 
	        for (int i = 0; i < cantidad; i++) {
	            if (p.colisionaIslaArriba(islas[i])) {
	                p.aterrizar(islas[i].bordeSuperior());
	            }
	            if (p.colisionaIslaAbajo(islas[i])) {
	                p.golpearTecho();
	            }
	        }
	 
	        if (p.bordeSuperior() > entorno.alto()) {
	            p.setY(50);
	        }
	 
	        // ── 4. COLISIONES HORIZONTALES ────────────────────────────────────────
	 
	        boolean bloqIzq = p.colisionaPorIzquierda(o);
	        boolean bloqDer = p.colisionaPorDerecha(o);
	 
	        for (int i = 0; i < cantidad; i++) {
	            if (p.colisionaIslaIzquierda(islas[i])) {
	                bloqIzq = true;
	            }
	            if (p.colisionaIslaDerecha(islas[i])) {
	                bloqDer = true;
	            }
	        }
	 
	        // ── 5. MOVIMIENTO HORIZONTAL ──────────────────────────────────────────
	 
	        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)
	                && p.bordeIzquierdo() > 0
	                && bloqIzq == false) {
	            p.moverIzquierda();
	        }
	 
	        if (entorno.estaPresionada(entorno.TECLA_DERECHA)
	                && p.bordeDerecho() < GeneradorMapa.anchoMundo
	                && bloqDer == false) {
	            p.moverDerecha();
	        }
	 
	        // ── 6. SALTO ──────────────────────────────────────────────────────────
	 
	        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
	            p.saltar();
	        }
	 
	        // ── 7. CHEQUEAR SI LLEGO AL CASTILLO ─────────────────────────────────
	 
	        if (castillo.colisiona(p)) {
	            setGano(true);
	        }
	 
	        // ── 8. DIBUJAR ────────────────────────────────────────────────────────
	 
	        o.dibujarConCamara(entorno, camara);
	        
	        for (int i = 0; i < cantidad; i++) {
	            int xEnPantalla = islas[i].getX() - camara;
	            if (xEnPantalla > -200 && xEnPantalla < entorno.ancho() + 200) {
	                islas[i].dibujarConCamara(entorno, camara);
	            }
	        }
	 
	        castillo.dibujarConCamara(entorno, camara);
	 
	        p.dibujarConCamara(entorno, camara);
	    
	    	if(castillo.colisiona(p) == true) {
	    		castillo.dibujarVictoria(entorno);
	    	}
	}
	    @SuppressWarnings("unused")
	    public static void main(String[] args) {
	        Juego juego = new Juego();
	    }
		public boolean isGano() {
			return gano;
		}
		public void setGano(boolean gano) {
			this.gano = gano;
		}
	}