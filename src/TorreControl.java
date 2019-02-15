/**
 * Implementa el partrón de diseño Singleton
 * // TODO Documentar clase TorreControl
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class TorreControl {

    /**
     * Instancia Singleton de la TorreControl
     */
    private static TorreControl instancia = null;

    /**
     * Contador de barcos entrando
     */
    private int barcosEntrando;
    /**
     * Contador de barcos que están saliendo
     */
    private int barcosSaliendo;

    /**
     * Constructor por defecto
     */
    public TorreControl() {
        barcosEntrando = 0;
        barcosSaliendo = 0;
    }

    /**
     * Protocolo de entrada de los BARCOS de ENTRADA
     *
     * @return Si tiene permiso para entrar
     */
    public synchronized boolean permisoEntrada() {
        boolean permiso = false;

        // Protocolo de entrada
        while (barcosSaliendo != 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Acción
        permiso = true;
        barcosEntrando++;

        return permiso;
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public synchronized boolean permisoSalida() {
        boolean permiso = false;

        // Protocolo de entrada
        while (barcosEntrando != 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Acción
        permiso = true;
        barcosSaliendo++;

        return permiso;
    }

    /**
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public synchronized void finEntrada() {
        // Acción
        barcosEntrando--;
        // Protocolo de salida
        if (barcosEntrando == 0) notifyAll();
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida() {
        // Acción
        barcosSaliendo--;
        // Protocolo de salida
        if (barcosSaliendo == 0) notifyAll();
    }

    /**
     * Método accesor del atributo {@link TorreControl#barcosEntrando}
     *
     * @return Número de barcos entrando
     */
    public int getBarcosEntrando() {
        return barcosEntrando;
    }

    /**
     * Método accesor del atributo {@link TorreControl#barcosSaliendo}
     *
     * @return Número de barcos saliendo
     */
    public int getBarcosSaliendo() {
        return barcosSaliendo;
    }

    /**
     * Método modificador del atributo {@link TorreControl#barcosEntrando}
     *
     * @param barcosEntrando Nuevo número de barcos entrando
     */
    private void setBarcosEntrando(int barcosEntrando) {
        this.barcosEntrando = barcosEntrando;
    }

    /**
     * Método modificador del atributo {@link TorreControl#barcosSaliendo}
     *
     * @param barcosSaliendo Nuevo número de barcos saliendo
     */
    private void setBarcosSaliendo(int barcosSaliendo) {
        this.barcosSaliendo = barcosSaliendo;
    }

    /**
     * @return la instancia Singleton de la clase Puerta
     */
    public static TorreControl recuperarInstancia() {
        if (instancia != null)
            return instancia;
        else
            instancia = new TorreControl();

        return instancia;
    }

}