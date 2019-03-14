/**
 * TODO Documentación clase BarcoPetrolero
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class BarcoPetrolero extends Barco {

    private static final int LIMITE_PETROLEO = 3000;                // Cantidad de petróleo a recoger
    private static final int LIMITE_AGUA = 5000;                    // Cantidad de agua a recoger
    private static final int CANTIDAD_REPOSTAJE_PETROLEO = 1000;    // Cantidad que se repone de petróleo en cada invocación
    private static final int CANTIDAD_REPOSTAJE_AGUA = 1000;        // Cantidad que se repone de agua en cada invocación

    /**
     * Cantidad de petroleo en el depósito de petróleo
     */
    private int depositoPetroleo;
    /**
     * Cantidad de agua en el depósito de agua
     */
    private int depositoAgua;

    public BarcoPetrolero(int identificador, int depositoPetroleo, int depositoAgua) {
        super(identificador, ESTADO_BARCO.ENTRADA);
        this.depositoPetroleo = depositoPetroleo;
        this.depositoAgua = depositoAgua;
    }

    /**
     * Un barco petrolero trata de entrar en el puerto, rellenar sus depósitos y salir del puerto
     */
    public void run() {
        ZonaRepostaje zonaRepostaje = ZonaRepostaje.recuperarInstancia();

        // Protocolo común a los barcos de entrada
        super.run();

        // Protocolo específico
        zonaRepostaje.permisoRepostaje(this);                   // Pide permiso para empezar a repostar
        while (!estaLleno()) {
            // En caso de que le falte petróleo repostará petróleo.
            if (!petroleoCompleto()) zonaRepostaje.repostarPetroleo(this, CANTIDAD_REPOSTAJE_PETROLEO);
            // En caso de que le falte agua repostará agua.
            if (!aguaCompleto()) zonaRepostaje.repostarAgua(this, CANTIDAD_REPOSTAJE_AGUA);
        }

        // Ya no hay más cargamentos y abandona la zona de repostaje
        imprimirConTimestamp("El barco " + getIdentificador() + " abandona la zona de repostaje");
        // Los barcos petroleros que abandonan la zona de repostaje salen del puerto
        setEstado(ESTADO_BARCO.SALIDA);
        // Protocolo común a los barcos de salida
        super.run();
    }

    /**
     * Reposta una cantidad dada de petróleo
     *
     * @param cantidad Cantidad a repostar de petróleo
     * @return Si realmente se repostó alguna cantidad. Devuelve Falso si el argumento 'cantidad' es 0, Verdadero
     * en otro caso
     */
    public boolean repostarPetroleo(int cantidad) {
        setDepositoPetroleo(getDepositoPetroleo() + cantidad);
        return cantidad != 0;
    }

    /**
     * Reposta una cantidad dada de agua
     *
     * @param cantidad Cantidad a repostar de agua
     */
    public void repostarAgua(int cantidad) {
        setDepositoAgua(getDepositoAgua() + cantidad);
    }

    /**
     * Devuelve true en caso de que el barco haya llenado sus depositos. False en caso contrario.
     *
     * @return True si los depósitos del barco están llenos.
     */
    private boolean estaLleno() {
        return aguaCompleto() && petroleoCompleto();
    }

    /**
     * Devuelve true en caso de que el depósito de petróleo esté completo. False en caso contrario.
     *
     * @return True si el depósito de petróleo está lleno.
     */
    private boolean petroleoCompleto() {
        return getDepositoPetroleo() == LIMITE_PETROLEO;
    }

    /**
     * Devuelve true en caso de que el depósito de agua esté completo. False en caso contrario.
     *
     * @return True si el depósito de agua está lleno.
     */
    private boolean aguaCompleto() {
        return getDepositoAgua() == LIMITE_AGUA;
    }

    /**
     * Método accesor del atributo {@link BarcoPetrolero:depositoPetroleo}
     */
    public int getDepositoPetroleo() {
        return depositoPetroleo;
    }

    /**
     * Método accesor del atributo {@link BarcoPetrolero:depositoAgua}
     */
    public int getDepositoAgua() {
        return depositoAgua;
    }

    /**
     * Método modificador del atributo {@link BarcoPetrolero:depositoPetroleo}
     *
     * @param depositoPetroleo Nueva cantidad de petróleo en el depósito
     */
    private void setDepositoPetroleo(int depositoPetroleo) {
        this.depositoPetroleo = depositoPetroleo;
    }

    /**
     * Método modificador del atributo {@link BarcoPetrolero:depositoAgua}
     *
     * @param depositoAgua Nuevo cantidad de agua en el depósito
     */
    private void setDepositoAgua(int depositoAgua) {
        this.depositoAgua = depositoAgua;
    }

    /**
     * Imprime un mensaje con marca de tiempo por consola en una línea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t\t[" + System.currentTimeMillis() + "] " + mensaje);
    }

}