/**
 * Implementa el partrón de diseño Singleton
 * // TODO Documentar clase TorreControl
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class TorreControl {

    private static TorreControl instancia = null;           // Instancia Singleton de la TorreControl
    private int barcosEntrando;                             // Contador de barcos entrando
    private int barcosSaliendo;                             // Contador de barcos que están saliendo
    private int barcosEsperandoSalir;                       // Contador de barcos esperando por salir

    /**
     * Constructor por defecto
     */
    private TorreControl() {
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
        System.out.println("\t[" + timeStamp() + "] El barco " + barco.getIdentificador() + " pide permiso para entrar.");
        System.out.println("\t[" + timeStamp() + "] Barcos esperando para salir: " + barcosEsperandoSalir);
        // Protocolo de entrada
        while (barcosSaliendo != 0 || barcosEsperandoSalir != 0) {
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
    public synchronized boolean permisoSalida(Barco barco) {
        System.out.print("\t[" + timeStamp() + "] El barco " + barco.getIdentificador() + " pide permiso para salir.\n");
        // Protocolo de entrada
        while (barcosEntrando != 0) {
            try {
                barcosEsperandoSalir++;
                wait();
                System.out.println("\t[" + timeStamp() + "] El barco " + barco.getIdentificador() + " está esperando para salir.\n");
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
    public synchronized void finEntrada(Barco barco) {
        // Acción
        if (barcosEntrando > 0)
            barcosEntrando--;
        // Protocolo de salida
        if (barcosEntrando == 0) notifyAll();
        System.out.println("\t[" + timeStamp() + "] El barco " + barco.getIdentificador() + " ha entrado.\n");
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public synchronized void finSalida(Barco barco) {
        // Acción
        if (barcosSaliendo > 0)
            barcosSaliendo--;
        // Protocolo de salida
        if (barcosSaliendo == 0 && barcosEsperandoSalir == 0) notifyAll();
        System.out.println("\t[" + timeStamp() + "] El barco " + barco.getIdentificador() + " ha salido.\n");
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

    /**
     * Devuelve el timeStamp medido en nanosegundos adaptado a la precisión más próxima a los threads.
     *
     * @return Cadena numérica que indica el momento temporal de ejecución.
     */
    private String timeStamp() {
        return Long.toString(System.nanoTime()).substring(8);
    }

    /**
     * Realiza una pausa en el thread que lo invoca.
     *
     * @param milis El número de milisegundos que queremos 'dormir' el thread.
     */
    private void esperar(int milis) {
        try {
            Thread.sleep(milis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}