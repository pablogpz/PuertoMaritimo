import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementa el partrón de diseño Singleton
 * // TODO Documentar clase TorreControl
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class TorreControl {

    Lock monitor = new ReentrantLock();
    private static TorreControl instancia = null;       // Instancia Singleton de la TorreControl
    private int barcosEntrando;                         // Contador de barcos entrando
    private int barcosSaliendo;                         // Contador de barcos que están saliendo
    private int barcosEsperandoSalir;                   // Contador de barcos esperando por salir
    Condition esperaEntrantes = monitor.newCondition();
    Condition esperaSalientes = monitor.newCondition();

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
    public synchronized boolean permisoEntrada(Barco barco) {
        monitor.lock();
        // Protocolo de entrada
        try {
            while (barcosSaliendo != 0 || barcosEsperandoSalir != 0) {
                try {
                    esperaEntrantes.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Acción
            barcosEntrando++;
        } finally {
            monitor.unlock();
        }
        return true;
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public synchronized boolean permisoSalida(Barco barco) {
        monitor.lock();
        // Protocolo de entrada
        try {
            while (barcosEntrando != 0) {
                try {
                    barcosEsperandoSalir++;
                    esperaSalientes.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Acción
            barcosSaliendo++;
            if (barcosEsperandoSalir > 0) barcosEsperandoSalir--;
        } finally {
            monitor.unlock();
        }
        return true;
    }

    /**
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public synchronized void finEntrada(Barco barco) {
        // Acción
        if (barcosEntrando > 0)
            barcosEntrando--;
        // Protocolo de salida
        if (barcosEntrando == 0) esperaSalientes.signal();
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida(Barco barco) {
        // Acción
        if (barcosSaliendo > 0)
            barcosSaliendo--;
        // Protocolo de salida
        if (barcosSaliendo == 0 && barcosEsperandoSalir == 0) esperaEntrantes.signal();
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