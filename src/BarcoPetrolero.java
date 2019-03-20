import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Clase que modela barcos que además son petroleros. Implementa el comportamiento de los barcos petroleros del paso 7,
 * repostar sus contenedores de petróleo y agua de la zona de repostaje. Una vez realizada esta acción, los barcos petroleros
 * salen del puerto. La capacidad de sus contenedores está determinada en el constructor
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class BarcoPetrolero extends Barco {

    private static final int LIMITE_PETROLEO = 3000;               // Cantidad de petróleo a recoger
    private static final int LIMITE_AGUA = 5000;                   // Cantidad de agua a recoger
    private static final int CANTIDAD_REPOSTAJE_PETROLEO = 1000;   // Cantidad que se repone de petróleo en cada invocación
    private static final int CANTIDAD_REPOSTAJE_AGUA = 1000;       // Cantidad que se repone de agua en cada invocación

    /**
     * Cantidad de petroleo en el depósito de petróleo
     */
    private int depositoPetroleo;
    /**
     * Cantidad de agua en el depósito de agua
     */
    private int depositoAgua;

    /**
     * Executor responsable del repostado concurrente de petróleo y agua
     */
    private Executor executor;

    public BarcoPetrolero(int identificador, int depositoPetroleo, int depositoAgua) {
        super(identificador, new ComporBarcoEntrada());
        this.depositoPetroleo = depositoPetroleo;
        this.depositoAgua = depositoAgua;

        // Dos hilos para dos procesos concurrentes
        executor = Executors.newFixedThreadPool(2);
    }

    /**
     * Un barco petrolero trata de entrar en el puerto, rellenar sus depósitos y salir del puerto
     */
    public void run() {
        ZonaRepostaje zonaRepostaje = ZonaRepostaje.recuperarInstancia();

        // Protocolo común a los barcos de entrada
        super.run();

        // Protocolo específico
        zonaRepostaje.permisoRepostaje(this);                    // Pide permiso para empezar a repostar
        // Repostará petróleo
        ((ThreadPoolExecutor) executor).submit(() -> {
            while (!petroleoCompleto())
                ZonaRepostaje.recuperarInstancia().repostarPetroleo(this, CANTIDAD_REPOSTAJE_PETROLEO);

        });
        // Repostará agua
        ((ThreadPoolExecutor) executor).submit(() -> {
            while (!aguaCompleto())
                ZonaRepostaje.recuperarInstancia().repostarAgua(this, CANTIDAD_REPOSTAJE_AGUA);

        });

        ((ThreadPoolExecutor) executor).shutdown();                     // Da de baja el executor
        try {                                                           // Espera a que el barco termine de repostar para salir del puerto
            ((ThreadPoolExecutor) executor).awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ya no hay más cargamentos y abandona la zona de repostaje
        imprimirConTimestamp("El barco petrolero " + getIdentificador() + " abandona la zona de repostaje");
        // Los barcos petroleros que abandonan la zona de repostaje salen del puerto
        setComporBarco(new ComporBarcoSalida());
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
    public boolean estaLleno() {
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