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

    private Lock monitor;
    private Condition esperaEntrantes;
    private Condition esperaSalientes;

    /**
     * Constructor por defecto
     */
    private TorreControl() {
        barcosEntrando = 0;
        barcosSaliendo = 0;
        barcosEsperandoSalir = 0;

        monitor = new ReentrantLock(true);
        esperaEntrantes = monitor.newCondition();
        esperaSalientes = monitor.newCondition();
    }

    /**
     * Protocolo de entrada de los BARCOS de ENTRADA
     *
     * @return Si tiene permiso para entrar
     */
    public void permisoEntrada(Barco barco) {
        monitor.lock();
        // Protocolo de entrada
        imprimirConTimestamp("El barco " + barco.getIdentificador() + " pide permiso para entrar");
        try {
            while (barcosSaliendo > 0 || barcosEsperandoSalir > 0) {
                try {
                    imprimirConTimestamp("El barco " + barco.getIdentificador() + " bloqueado para entrar");
                    esperaEntrantes.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " obtiene el permiso para entrar");
            barcosEntrando++;
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public void permisoSalida(Barco barco) {
        monitor.lock();
        try {
            // Protocolo de entrada
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " pide permiso para salir");
            while (barcosEntrando > 0) {
                try {
                    imprimirConTimestamp("El barco " + barco.getIdentificador() + " bloqueado para salir");
                    barcosEsperandoSalir++;
                    esperaSalientes.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " obtiene permiso para salir");
            barcosSaliendo++;

            esperaSalientes.signal();   // Si comienzan a salir barcos es posible que haya alguno bloqueado que quiera salir también
            barcosEsperandoSalir = 0;   // Por tanto, el contador se pondrá a 0 cuando se hayan desbloqueado todos.
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public synchronized void finEntrada(Barco barco) {
        monitor.lock();
        try {
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " finalmente ha entrado");
            barcosEntrando--;
            // Protocolo de salida
            if (barcosEntrando == 0) {
                imprimirConTimestamp("Entran todos los barcos de entrada");
                esperaSalientes.signal();
            }
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida(Barco barco) {
        monitor.lock();
        try {
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " finalmente ha salido");
            barcosSaliendo--;
            // Protocolo de salida
            if (barcosSaliendo == 0 && barcosEsperandoSalir == 0) {
                imprimirConTimestamp("Salen todos los barcos de salida");
                esperaEntrantes.signal();
            }
        } finally {
            monitor.unlock();
        }
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
    private synchronized void setBarcosEntrando(int barcosEntrando) {
        this.barcosEntrando = barcosEntrando;
    }

    /**
     * Método modificador del atributo {@link TorreControl#barcosSaliendo}
     *
     * @param barcosSaliendo Nuevo número de barcos saliendo
     */
    private synchronized void setBarcosSaliendo(int barcosSaliendo) {
        this.barcosSaliendo = barcosSaliendo;
    }

    /**
     * @return Instancia Singleton de la TorreControl
     */
    public synchronized static TorreControl recuperarInstancia() {
        if (instancia != null)
            return instancia;
        else
            instancia = new TorreControl();

        return instancia;
    }

    /**
     * Imprime un mensaje con marca de tiempo por consola en una línea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t[" + System.currentTimeMillis() + "] " + mensaje);
    }

}