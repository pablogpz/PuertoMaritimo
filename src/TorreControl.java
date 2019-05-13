import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementa el partrón de diseño Singleton. Sirve de entidad en la que encapsular toda la sincronización de barcos que
 * quieren entrar y salir por la puerta del puerto. Se permite la entrada y salida concurrente mientras circulen en el mismo sentido.
 * Existe preferencia a los barcos de salida y se sigue una política de justicia FIFO en ambos sentidos. Un barco de entrada
 * puede entrar si no hay ningún barco saliendo ni esperando salir, mientas que un barco de salida puede salir si no hay
 * ningún barco entrando.
 * <p>
 * Interpretamos que inicialmente el número de barcos dentro del puerto es el número de barcos de salida. Cada vez que
 * entre un barco de entrada se sumará 1, y cada vez que salga un barco de salida se restará 1.
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class TorreControl {

    private static TorreControl instancia = null;   // Instancia Singleton de la TorreControl
    private int barcosEntrando;                     // Contador de barcos entrando
    private int barcosSaliendo;                     // Contador de barcos que están saliendo
    private int barcosEsperandoSalir;               // Contador de barcos esperando por salir
    private int barcosDentroPuerto;                 // Número de barcos que están dentro del puerto

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
        barcosDentroPuerto = Main.NUM_BARCOS_SALIDA_SIM;

        monitor = new ReentrantLock(true);
        esperaEntrantes = monitor.newCondition();
        esperaSalientes = monitor.newCondition();
    }

    /**
     * Protocolo de entrada de los BARCOS de ENTRADA
     */
    public void permisoEntrada(Barco barco) {
        monitor.lock();
        try {
            // Protocolo de entrada
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " pide permiso para entrar");
            while (barcosSaliendo > 0 || barcosEsperandoSalir > 0) {
                imprimirConTimestamp("El barco " + barco.getIdentificador() + " bloqueado para entrar");
                esperaEntrantes.await();
            }
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " obtiene el permiso para entrar");
            barcosEntrando++;

            esperaEntrantes.signal();   // Si comienzan a entrar barcos es posible que haya alguno bloqueado que quiera entrar también
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     */
    public void permisoSalida(Barco barco) {
        monitor.lock();
        try {
            // Protocolo de entrada
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " pide permiso para salir");
            while (barcosEntrando > 0) {
                imprimirConTimestamp("El barco " + barco.getIdentificador() + " bloqueado para salir");
                barcosEsperandoSalir++;
                esperaSalientes.await();
            }
            // Acción
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " obtiene permiso para salir");
            barcosSaliendo++;

            esperaSalientes.signal();   // Si comienzan a salir barcos es posible que haya alguno bloqueado que quiera salir también
            barcosEsperandoSalir--;     // Por tanto, el contador se pondrá a 0 cuando se hayan desbloqueado todos
        } catch (Exception e) {
            e.printStackTrace();
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
            setBarcosDentroPuerto(getBarcosDentroPuerto() + 1); // Un nuevo barco ha entrado en el puerto
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
     * a
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida(Barco barco) {
        monitor.lock();
        try {
            // Acción
            setBarcosDentroPuerto(getBarcosDentroPuerto() - 1); // Un nuevo barco ha salido del puerto
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " finalmente ha salido");
            barcosSaliendo--;
            // Protocolo de salida
            if (barcosSaliendo == 0) {
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
     * Método accesor del atributo {@link TorreControl#barcosDentroPuerto}
     *
     * @return Número de barcos dentro del puerto
     */
    public int getBarcosDentroPuerto() {
        return barcosDentroPuerto;
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
     * Método modificador del atributo {@link TorreControl#barcosDentroPuerto}
     *
     * @param barcosDentroPuerto Nuevo número de barcos dentro del puerto. Debe ser mayor que 0, sino toma el valor 0
     */
    private synchronized void setBarcosDentroPuerto(int barcosDentroPuerto) {
        if (barcosDentroPuerto >= 0) this.barcosDentroPuerto = barcosDentroPuerto;
        else this.barcosDentroPuerto = 0;
    }

    /**
     * @return Instancia Singleton de la TorreControl
     */
    public synchronized static TorreControl recuperarInstancia() {
        if (instancia == null)
            instancia = new TorreControl();

        return instancia;
    }

    /**
     * Imprime un mensaje con marca de tiempo por consola en una línea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t[" + System.currentTimeMillis() + "] " + mensaje +
                "\tbarcosDentroPuerto = " + barcosDentroPuerto);
    }
}