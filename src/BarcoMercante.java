import java.util.ArrayList;
import java.util.Random;

public class BarcoMercante extends Barco {

    private static final int CARGAMENTOS_DISTINTOS = 3;     // Número de barcos de salida creados para la simulación

    private int depositoAzucar;         // Cantidad de cargamentos de azúcar
    private int depositoHarina;         // Cantidad de cargamentos de harina
    private int depositoSal;            // Cantidad de cargamentos de sal


    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador, estado y cantidades de cargamentos correspondientes.
     *
     * @param identificador  Identificador del barco
     * @param estado         Estado inicial del barco
     * @param depositoAzucar Cantidad de cargamentos de azúcar
     * @param depositoHarina Cantidad de cargamentos de harina
     * @param depositoSal    Cantidad de cargamentos de sal
     */
    public BarcoMercante(int identificador, ESTADO_BARCO estado, int depositoAzucar, int depositoHarina, int depositoSal) {
        super(identificador, estado);
        this.depositoAzucar = depositoAzucar;
        this.depositoHarina = depositoHarina;
        this.depositoSal = depositoSal;
    }


    /**
     * Devuelve un cargamento al azar transportado por el barco mercante.
     *
     * @return cargamento El cargamento elegido al azar
     */
    public TIPO_CARGAMENTO obtenerCargamentoAleatorio() {

        // Cargamento a devolver

        TIPO_CARGAMENTO cargamento = null;

        // Almacenamos los valores con cantidades positivas

        ArrayList<String> cargamentosDisponibles = new ArrayList<>();
        if (depositoAzucar != 0) cargamentosDisponibles.add("Azucar");
        if (depositoHarina != 0) cargamentosDisponibles.add("Harina");
        if (depositoSal != 0) cargamentosDisponibles.add("Sal");

        // Elegimos aleatoriamente un cargamento de los almacenados en la nueva colección y lo devolvemos

        Random aleatorio = new Random(System.currentTimeMillis());
        int seleccion = aleatorio.nextInt(cargamentosDisponibles.size()) + 1;
        String resultado;

        resultado = cargamentosDisponibles.get(seleccion);

        switch (resultado) {
            case "Azucar":
                cargamento = TIPO_CARGAMENTO.AZUCAR;
                depositoAzucar--;
                break;
            case "Harina":
                cargamento = TIPO_CARGAMENTO.HARINA;
                depositoHarina--;
                break;
            case "Sal":
                cargamento = TIPO_CARGAMENTO.SAL;
                depositoSal--;
                break;
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
    public void setDepositoAzucar(int depositoAzucar) {
        this.depositoAzucar = depositoAzucar;
    }

    /**
     * Método modificador del atributo {@link BarcoMercante#depositoHarina}
     *
     * @param depositoHarina Nueva cantidad del atributo depositoHarina
     */
    public void setDepositoHarina(int depositoHarina) {
        this.depositoHarina = depositoHarina;
    }

    /**
     * Método modificador del atributo {@link BarcoMercante#depositoSal}
     *
     * @param depositoSal Nueva cantidad del atributo depositoSal
     */
    public void setDepositoSal(int depositoSal) {
        this.depositoSal = depositoSal;
    }

    /**
     * Método que devuelve la cantidad total de cargamentos transportados por el Barco
     *
     * @return La suma de los distintos depósitos
     */
    public int getCargamentosRestantes() {
        return depositoAzucar + depositoHarina + depositoSal;
    }
}
