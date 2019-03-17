/**
 * // TODO Documentación clase Grua
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Grua implements Runnable {

    private Thread autoThread;              // Autothread
    /**
     * Identificador de la grúa
     */
    private final int identificador;
    /**
     * Tipo de cargamento con el que opera la grúa
     */
    private TIPO_CARGAMENTO tipo;

    /**
     * Constructor parametrizado. Instancia una nueva grua a partir de un identificador y un tipo.
     * Lanza el autothread
     *
     * @param identificador Identificador de la grua
     * @param tipo          Tipo de la grua
     */
    public Grua(int identificador, TIPO_CARGAMENTO tipo) {
        this.identificador = identificador;
        this.tipo = tipo;

        autoThread = new Thread(this);
        autoThread.start();
    }

    /**
     * // TODO Documentar método run()
     */
    @Override
    public void run() {
        // Solo se ejecuta si es el autothread el que lo invoca
        if (autoThread.equals(Thread.currentThread())) {
            Plataforma plataforma = Plataforma.recuperarInstancia();    // Instancia Singleton de la plataforma
            // Las gruas deberán estar operativas mientras la plataforma esté operativa
            while (plataforma.getActiva())
                plataforma.coger(this);
            System.out.println("\t\tLa grua " + getTipo() + " " + getIdentificador() + " ha terminado su trabajo");
        }
    }

    /**
     * Método accesor del atributo {@link Grua#identificador}
     *
     * @return Identificador actual de la grúa
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Método accesor del atributo {@link Grua#tipo}
     *
     * @return Tipo actual de la grúa
     */
    public TIPO_CARGAMENTO getTipo() {
        return tipo;
    }

    /**
     * Método modificador del atributo {@link Grua#tipo}
     *
     * @param tipo Nuevo tipo de la grúa
     */
    public synchronized void setTipo(TIPO_CARGAMENTO tipo) {
        this.tipo = tipo;
    }
}