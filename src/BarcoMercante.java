import java.util.ArrayList;
import java.util.Random;

public class BarcoMercante extends Barco {

    private int depositoAzucar;         // Cantidad de cargamentos de azúcar
    private int depositoHarina;         // Cantidad de cargamentos de harina
    private int depositoSal;            // Cantidad de cargamentos de sal

    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador, estado y cantidades de cargamentos correspondientes.
     *
     * @param identificador  Identificador del barco
     * @param depositoAzucar Cantidad de cargamentos de azúcar
     * @param depositoHarina Cantidad de cargamentos de harina
     * @param depositoSal    Cantidad de cargamentos de sal
     */
    public BarcoMercante(int identificador, int depositoAzucar, int depositoHarina, int depositoSal) {
        super(identificador, ESTADO_BARCO.ENTRADA);
        this.depositoAzucar = depositoAzucar;
        this.depositoHarina = depositoHarina;
        this.depositoSal = depositoSal;
    }

    @Override
    public void run() {
        Plataforma plataforma = Plataforma.recuperarInstancia();

        super.run();

        // Mientras tenga cargamentos intentará soltarlos
        while (getCargamentosRestantes() > 0)
            plataforma.poner(this, obtenerCargamentoAleatorio());
        // Ya no hay más cargamentos y abandona la zona de descarga
        imprimirConTimestamp("El barco " + getIdentificador() + " abandona la plataforma");
    }

    /**
     * Devuelve un cargamento al azar transportado por el barco mercante.
     *
     * @return cargamento El cargamento elegido al azar
     */
    public TIPO_CARGAMENTO obtenerCargamentoAleatorio() {
        ArrayList<TIPO_CARGAMENTO> cargamentosDisponibles = new ArrayList<>();

        // Inicializa la lista de posibilidades
        if (depositoAzucar > 0) cargamentosDisponibles.add(TIPO_CARGAMENTO.AZUCAR);
        if (depositoHarina > 0) cargamentosDisponibles.add(TIPO_CARGAMENTO.HARINA);
        if (depositoSal > 0) cargamentosDisponibles.add(TIPO_CARGAMENTO.SAL);

        // Extrae un cargamento aleatorio
        TIPO_CARGAMENTO cargamento = cargamentosDisponibles.get(new Random().nextInt(cargamentosDisponibles.size()));

        // Decrementa el contador de dicho cargamento
        switch (cargamento) {
            case AZUCAR:
                setDepositoAzucar(getDepositoAzucar() - 1);
                break;
            case HARINA:
                setDepositoHarina(getDepositoHarina() - 1);
                break;
            case SAL:
                setDepositoSal(getDepositoSal() - 1);
        }

        return cargamento;
    }

    /**
     * Método accesor del atributo {@link BarcoMercante#depositoAzucar}
     *
     * @return Cantidad de cargamentos de azúcar
     */
    public int getDepositoAzucar() {
        return depositoAzucar;
    }

    /**
     * Método accesor del atributo {@link BarcoMercante#depositoHarina}
     *
     * @return Cantidad de cargamentos de harina
     */
    public int getDepositoHarina() {
        return depositoHarina;
    }

    /**
     * Método accesor del atributo {@link BarcoMercante#depositoSal}
     *
     * @return Cantidad de cargamentos de sal
     */
    public int getDepositoSal() {
        return depositoSal;
    }

    /**
     * Método modificador del atributo {@link BarcoMercante#depositoAzucar}
     *
     * @param depositoAzucar Nueva cantidad del atributo depositoAzucar
     */
    public synchronized void setDepositoAzucar(int depositoAzucar) {
        this.depositoAzucar = depositoAzucar;
    }

    /**
     * Método modificador del atributo {@link BarcoMercante#depositoHarina}
     *
     * @param depositoHarina Nueva cantidad del atributo depositoHarina
     */
    public synchronized void setDepositoHarina(int depositoHarina) {
        this.depositoHarina = depositoHarina;
    }

    /**
     * Método modificador del atributo {@link BarcoMercante#depositoSal}
     *
     * @param depositoSal Nueva cantidad del atributo depositoSal
     */
    public synchronized void setDepositoSal(int depositoSal) {
        this.depositoSal = depositoSal;
    }

    /**
     * Método que devuelve la cantidad total de cargamentos transportados por el Barco
     *
     * @return La suma de los distintos depósitos
     */
    public int getCargamentosRestantes() {
        return (getDepositoAzucar() + getDepositoHarina() + getDepositoSal());
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
