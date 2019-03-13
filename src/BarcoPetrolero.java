/**
 * TODO Documentación clase BarcoPetrolero
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class BarcoPetrolero extends Barco {

    /**
     * Cantidad de petroleo en el depósito de petróleo
     */
    private int depositoPetroleo;
    /**
     * Cantidad de agua en el depósito de agua
     */
    private int depositoAgua;

    /**
     * @param identificador
     * @param depositoPetroleo
     * @param depositoAgua
     */
    public BarcoPetrolero(int identificador, int depositoPetroleo, int depositoAgua) {
        super(identificador, ESTADO_BARCO.ENTRADA);
        // TODO - implement BarcoPetrolero.BarcoPetrolero
    }

    /**
     * Un barco petrolero trata de entrar en el puerto, rellenar sus depósitos y salir del puerto
     */
    public void run() {
        // TODO - implement BarcoPetrolero.run
    }

    /**
     * Reposta una cantidad dada de petróleo
     *
     * @param cantidad Cantidad a repostar de petróleo
     */
    public void repostarPetroleo(int cantidad) {
        // TODO - implement BarcoPetrolero.repostarPetroleo
    }

    /**
     * Reposta una cantidad dada de agua
     *
     * @param cantidad Cantidad a repostar de agua
     */
    public void repostarAgua(int cantidad) {
        // TODO - implement BarcoPetrolero.repostarAgua
    }

    /**
     * Método accesor del atributo depositoPetroleo
     */
    public int getDepositoPetroleo() {
        return depositoPetroleo;
    }

    /**
     * Método accesor del atributo depositoAgua
     */
    public int getDepositoAgua() {
        return depositoAgua;
    }

    /**
     * Método modificador del atributo depositoPetroleo
     *
     * @param depositoPetroleo Nueva cantidad de petróleo en el depósito
     */
    private void setDepositoPetroleo(int depositoPetroleo) {
        this.depositoPetroleo = depositoPetroleo;
    }

    /**
     * Método modificador del atributo depositoAgua
     *
     * @param depositoAgua Nuevo cantidad de agua en el depósito
     */
    private void setDepositoAgua(int depositoAgua) {
        this.depositoAgua = depositoAgua;
    }

}