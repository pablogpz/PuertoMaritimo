/**
 * // TODO Documentación clase Grua
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Grua implements Runnable {

    private int identificador;          // Identificador de la Grúa
    private TIPO_GRUA tipo;             // Tipo de la grúa (dependiente de los cargamentos que vaya a coger)

    /**
     * Constructor parametrizado. Instancia una nueva grua a partir de un identificador y un tipo
     *
     * @param identificador Identificador de la grua
     * @param tipo          Tipo de la grua
     */
    public Grua(int identificador, TIPO_GRUA tipo) {
        this.identificador = identificador;
        this.tipo = tipo;
    }

    /**
     * // TODO Documentar método run()
     */
    @Override
    public void run() {

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
    public TIPO_GRUA getTipo() {
        return tipo;
    }

    /**
     * Método modificador del atributo {@link Grua#identificador}
     *
     * @param identificador Nuevo identificador de la grúa
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    /**
     * Método modificador del atributo {@link Grua#tipo}
     *
     * @param tipo Nuevo tipo de la grúa
     */
    public void setTipo(TIPO_GRUA tipo) {
        this.tipo = tipo;
    }
}