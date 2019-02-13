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
     * @see ESTADO_BARCO Estado en el que se encuentra el barco
     */
    private ESTADO_BARCO estado;

    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador y un estado
     *
     * @param identificador Identificador del barco
     * @param estado        Estado inicial del barco
     */
    public Barco(int identificador, ESTADO_BARCO estado) {
        this.identificador = identificador;
        this.estado = estado;
    }

    /**
     * // TODO Documentar método run()
     */
    @Override
    public void run() {
        Puerta puerta = Puerta.recuperarInstancia();        // Instancia Singleton de la puerta

        switch (getEstado()) {                              // Determina la acción del barco adecuada a su estado
            case ENTRADA:
                puerta.entrar(this);
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case SALIDA:
                puerta.salir(this);
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
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