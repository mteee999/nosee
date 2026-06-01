package juego;

public class GeneradorMapa {

    // Tamano de pantalla
    public static int anchoPantalla = 800;
    public static int altoPantalla  = 600;

    // El mundo es 4 veces mas ancho que la pantalla
    public static int anchoMundo = anchoPantalla * 4;

    // Configuracion del piso
    public static int anchoPiso      = 130;
    public static int separacionPiso = 80;
    public static int yPiso          = 540;

    // Configuracion de flotantes
    private static int anchoMin   = 70;
    private static int anchoMax   = 160;
    private static int yNivel1    = 390;
    private static int yNivel2    = 220;
    private static int variacionY = 45;
    private static int sepMin     = 30;
    private static int sepMax     = 100;
    private static int maxIslas   = 100;

    // Array de islas y contador
    private Isla[] islas;
    private int cantidad;

    // Semilla para numeros aleatorios sin bibliotecas
    private long semilla;

    public GeneradorMapa() {
        islas    = new Isla[maxIslas];
        cantidad = 0;
        semilla  = System.currentTimeMillis();
    }

    // Devuelve un numero aleatorio entre 0 y (limite - 1)
    private int azar(int limite) {
        semilla = semilla * 1103515245 + 12345;
        int resultado = (int)(semilla % limite);
        if (resultado < 0) {
            resultado = resultado * -1;
        }
        return resultado;
    }

    public void generar() {
        cantidad = 0;
        generarPiso();
        generarFlotantes(Isla.flotante2, yNivel2);
        generarFlotantes(Isla.flotante1, yNivel1);
    }

    private void generarPiso() {
        int paso      = anchoPiso + separacionPiso;
        // Genera islas en todo el ancho del mundo, no solo la pantalla
        int cantIslas = anchoMundo / paso;
        for (int i = 0; i < cantIslas; i++) {
            agregarIsla(new Isla(i * paso, yPiso, anchoPiso, Isla.piso));
        }
    }

    private void generarFlotantes(int tipo, int yBase) {
        int x = azar(40);
        // Sigue generando hasta cubrir todo el ancho del mundo
        while (x < anchoMundo) {
            int ancho  = anchoMin + azar(anchoMax - anchoMin + 1);
            int deltaY = azar(variacionY * 2 + 1) - variacionY;
            int y      = yBase + deltaY;
            if (y < 30) {
                y = 30;
            }
            if (y > yPiso - 80) {
                y = yPiso - 80;
            }
            agregarIsla(new Isla(x, y, ancho, tipo));
            int sep = sepMin + azar(sepMax - sepMin + 1);
            x = x + ancho + sep;
        }
    }

    private void agregarIsla(Isla isla) {
        if (cantidad < maxIslas) {
            islas[cantidad] = isla;
            cantidad++;
        }
    }

    public Isla[] getIslas() {
        return islas;
    }

    public int getCantidad() {
        return cantidad;
    }
}
