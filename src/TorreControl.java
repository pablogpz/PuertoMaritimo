import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementa el partrón de diseño Singleton
 * // TODO Documentar clase TorreControl
 * // TODO Agregar cambios al UML
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class TorreControl {

    private static TorreControl instancia = null;       // Instancia Singleton de la TorreControl
    private int barcosEntrando;                         // Contador de barcos entrando
    private int barcosSaliendo;                         // Contador de barcos que están saliendo
    private int barcosEsperandoSalir;                   // Contador de barcos esperando por salir

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
    public boolean permisoEntrada(Barco barco) {
        monitor.lock();
        esperar(500);
        System.out.println("\t" + timestamp() + " El barco " + barco.getIdentificador() + " quiere entrar.");
        esperar(500);
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
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public void finEntrada(Barco barco) {
        monitor.lock();
        esperar(500);
        try {
            // Acción
            if (barcosEntrando > 0) // Comprobación de errores
                barcosEntrando--;
            // Protocolo de salida
            if (barcosEntrando == 0) esperaSalientes.signal();
            else esperaEntrantes.signal();
            esperar(500);
            System.out.println("\t" + timestamp() + " El barco " + barco.getIdentificador() + " entra.");
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public boolean permisoSalida(Barco barco) {
        monitor.lock();
        esperar(500);
        System.out.println("\t" + timestamp() + " El barco " + barco.getIdentificador() + " quiere salir.");
        esperar(500);
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
            esperaSalientes.signal();   // Si comienzan a salir barcos es posible que haya alguno bloqueado que quiera salir también
            barcosEsperandoSalir = 0;   // Por tanto, el contador se pondrá a 0 cuando se hayan desbloqueado todos.

        } finally {
            monitor.unlock();
        }
        return true;
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public void finSalida(Barco barco) {
        monitor.lock();
        esperar(500);
        try {
            // Acción
            if (barcosSaliendo > 0)
                barcosSaliendo--;
            // Protocolo de salida
            if (barcosSaliendo == 0 && barcosEsperandoSalir == 0) esperaEntrantes.signal();
            else esperaSalientes.signal();
            System.out.println("\t" + timestamp() + " El barco " + barco.getIdentificador() + " sale.");
            esperar(500);
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

    public static void esperar(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String timestamp() {
        return Long.toString(System.nanoTime()).substring(10);
    }

}