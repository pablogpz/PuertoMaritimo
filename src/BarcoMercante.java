public class BarcoMercante extends Barco {

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
}
