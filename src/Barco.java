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
    private final int identificador;

    /**
     * @see ComporBarco Estado en el que se encuentra el barco
     */
    private ComporBarco comporBarco;

    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador y un estado
     *
     * @param identificador Identificador del barco
     * @param comporBarco   Comportamiento inicial del barco
     */
    public Barco(int identificador, ComporBarco comporBarco) {
        this.identificador = identificador;
        this.comporBarco = comporBarco;
    }

    /**
     * TODO Documentar método run()
     */
    @Override
    public void run() {
        comporBarco.comporBarco(this);
    }

    /**
     * Método accesor del atributo {@link Barco#identificador}
     *
     * @return Identificador del barco
     */
    protected int getIdentificador() {
        return identificador;
    }

    /**
     * Método accesor del atributo {@link Barco#comporBarco}
     *
     * @return Comportamiento actual del barco
     */
    protected ComporBarco getComporBarco() {
        return comporBarco;
    }

    /**
     * Método modificador del atributo {@link Barco#comporBarco}
     *
     * @param comporBarco Nuevo estado
     */
    protected void setComporBarco(ComporBarco comporBarco) {
        this.comporBarco = comporBarco;
    }
}