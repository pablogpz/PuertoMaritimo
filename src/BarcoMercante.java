import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que modela barcos que adem�s son mercantes. Implementa el comportamiento de los barcos mercantes del paso 6,
 * interactuar con la plataforma de carga para descargar su contenido. El n�mero de contenedores est� definido por constructor.
 * Cuando terminan de descargar mueren y no salen del puerto
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public class BarcoMercante extends Barco {

    /**
     * Cantidad de cargamentos de az�car
     */
    private int depositoAzucar;
    /**
     * Cantidad de cargamentos de harina
     */
    private int depositoHarina;
    /**
     * Cantidad de cargamentos de sal
     */
    private int depositoSal;

    /**
     * Constructor parametrizado. Instancia un nuevo barco a partir de un identificador, estado y cantidades de cargamentos correspondientes.
     *
     * @param identificador  Identificador del barco
     * @param depositoAzucar Cantidad de cargamentos de az�car
     * @param depositoHarina Cantidad de cargamentos de harina
     * @param depositoSal    Cantidad de cargamentos de sal
     */
    public BarcoMercante(int identificador, int depositoAzucar, int depositoHarina, int depositoSal) {
        super(identificador, new ComporBarcoEntrada());
        this.depositoAzucar = depositoAzucar;
        this.depositoHarina = depositoHarina;
        this.depositoSal = depositoSal;
    }

    /**
     * Comportamiento de los barcos mercantes: Adem�s de entrar como un barco de entrada, intenta vaciar todos los
     * contenedores que carga en la plataforma de carga. La l�gica de sincronizaci�n la implementa la plataforma de carga
     */
    @Override
    public void run() {
        Plataforma plataforma = Plataforma.recuperarInstancia();

        // Protocolo com�n a los barcos de entrada
        super.run();

        // Mientras tenga cargamentos intentar� soltarlos
        while (getCargamentosRestantes() > 0)
            plataforma.poner(this, obtenerCargamentoAleatorio());
        // Ya no hay m�s cargamentos y abandona la zona de descarga
        imprimirConTimestamp("El barco " + getIdentificador() + " abandona la plataforma");
    }

    /**
     * Devuelve un cargamento al azar transportado por el barco mercante.
     *
     * @return cargamento El cargamento elegido al azar
     */
    private TIPO_CARGAMENTO obtenerCargamentoAleatorio() {
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
     * M�todo que devuelve la cantidad total de cargamentos transportados por el Barco
     *
     * @return La suma de los distintos dep�sitos
     */
    public int getCargamentosRestantes() {
        return (getDepositoAzucar() + getDepositoHarina() + getDepositoSal());
    }

    /**
     * M�todo accesor del atributo {@link BarcoMercante#depositoAzucar}
     *
     * @return Cantidad de cargamentos de az�car
     */
    public int getDepositoAzucar() {
        return depositoAzucar;
    }

    /**
     * M�todo accesor del atributo {@link BarcoMercante#depositoHarina}
     *
     * @return Cantidad de cargamentos de harina
     */
    public int getDepositoHarina() {
        return depositoHarina;
    }

    /**
     * M�todo accesor del atributo {@link BarcoMercante#depositoSal}
     *
     * @return Cantidad de cargamentos de sal
     */
    public int getDepositoSal() {
        return depositoSal;
    }

    /**
     * M�todo modificador del atributo {@link BarcoMercante#depositoAzucar}
     *
     * @param depositoAzucar Nueva cantidad del atributo depositoAzucar
     */
    private synchronized void setDepositoAzucar(int depositoAzucar) {
        this.depositoAzucar = depositoAzucar;
    }

    /**
     * M�todo modificador del atributo {@link BarcoMercante#depositoHarina}
     *
     * @param depositoHarina Nueva cantidad del atributo depositoHarina
     */
    private synchronized void setDepositoHarina(int depositoHarina) {
        this.depositoHarina = depositoHarina;
    }

    /**
     * M�todo modificador del atributo {@link BarcoMercante#depositoSal}
     *
     * @param depositoSal Nueva cantidad del atributo depositoSal
     */
    private synchronized void setDepositoSal(int depositoSal) {
        this.depositoSal = depositoSal;
    }

    /**
     * Imprime un mensaje con marca de tiempo por consola en una l�nea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t\t[" + System.currentTimeMillis() + "] " + mensaje);
    }

}
