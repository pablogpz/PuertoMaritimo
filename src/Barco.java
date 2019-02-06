/**
 * // TODO Documentación clase Barco
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Barco implements Runnable {

    /**
     * Entero único para cada barco que lo identifica. Es de solo lectura
     */
    private int identificador;
    /**
     * Estado en el que se encuentra el barco. @see ESTADO_BARCO
     */
    private ESTADO_BARCO estado;

    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador y un estado
     *
     * @param id     Identificador del barco
     * @param estado Estado inicial del barco
     */
    public Barco(int id, ESTADO_BARCO estado) {
        // TODO - implement Barco.Barco
    }

    /**
     * //TODO Documentar método run()
     */
    public void run() {
        // TODO - implement Barco.run
    }

    /**
     * Método accesor del atributo {@link Barco#identificador}
     *
     * @return Identificador del barco
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Método accesor del atributo {@link Barco#estado}
     *
     * @return Estado actual del barco
     */
    public ESTADO_BARCO getEstado() {
        return estado;
    }

    /**
     * Método modificador del atributo {@link Barco#estado}
     *
     * @param estado Nuevo estado
     */
    public void setEstado(ESTADO_BARCO estado) {
        this.estado = estado;
    }
}