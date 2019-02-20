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
     * Contador de barcos esperando por salir
     */
    private int barcosEsperandoSalir;

    /**
     * Constructor por defecto
     */
    public TorreControl() {
        barcosEntrando = 0;
        barcosSaliendo = 0;
        barcosEsperandoSalir = 0;
    }

    /**
     * Protocolo de entrada de los BARCOS de ENTRADA
     *
     * @return Si tiene permiso para entrar
     */
    public synchronized boolean permisoEntrada() {
        // Protocolo de entrada
        while (barcosSaliendo != 0 && barcosEsperandoSalir != 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Acción
        barcosEntrando++;
        return true;
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public synchronized boolean permisoSalida() {
        // Protocolo de entrada
        while (barcosEntrando != 0) {
            try {
                wait();
                barcosEsperandoSalir++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Acción
        barcosSaliendo++;
        if (barcosEsperandoSalir > 0) barcosEsperandoSalir--;
        return true;
    }

    /**
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public synchronized void finEntrada() {
        // Acción
        if (barcosEntrando > 0)
            barcosEntrando--;
        // Protocolo de salida
        if (barcosEntrando == 0) notifyAll();
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida() {
        // Acción
        if (barcosSaliendo > 0)
            barcosSaliendo--;
        // Protocolo de salida
        if (barcosSaliendo == 0 && barcosEsperandoSalir == 0) notifyAll();
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
     * @return Instancia Singleton de la TorreControl
     */
    public static TorreControl recuperarInstancia() {
        if (instancia != null)
            return instancia;
        else
            instancia = new TorreControl();

        return instancia;
    }

}