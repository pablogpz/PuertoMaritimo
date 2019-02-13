/**
 * // TODO Documentar clase TorreControl
 */

public class TorreControl {

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
        // TODO - implement TorreControl.TorreControl
    }

    /**
     * Protocolo de entrada de los BARCOS de ENTRADA
     *
     * @return Si tiene permiso para entrar
     */
    public boolean permisoEntrada() {
        // TODO - implement TorreControl.permisoEntrada
        return false;
    }

    /**
     * Protocolo de entrada de los BARCOS de SALIDA
     *
     * @return Si tiene permiso para salir
     */
    public boolean permisoSalida() {
        // TODO - implement TorreControl.permisoSalida
        return false;
    }

    /**
     * Protocolo de salida de los BARCOS de ENTRADA
     */
    public void finEntrada() {
        // TODO - implement TorreControl.finEntrada
    }

    /**
     * Protocolo de salida de los BARCOS de SALIDA
     */
    public void finSalida() {
        // TODO - implement TorreControl.finSalida
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

}