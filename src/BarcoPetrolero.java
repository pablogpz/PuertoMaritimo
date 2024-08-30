import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Clase que modela barcos que adem�s son petroleros. Implementa el comportamiento de los barcos petroleros del paso 7,
 * repostar sus contenedores de petr�leo y agua de la zona de repostaje. Una vez realizada esta acci�n, los barcos petroleros
 * salen del puerto. La capacidad de sus contenedores est� determinada en el constructor
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public class BarcoPetrolero extends Barco {

    private static final int LIMITE_PETROLEO = 3000;               // Cantidad de petr�leo a recoger
    private static final int LIMITE_AGUA = 5000;                   // Cantidad de agua a recoger
    private static final int CANTIDAD_REPOSTAJE_PETROLEO = 1000;   // Cantidad que se repone de petr�leo en cada invocaci�n
    private static final int CANTIDAD_REPOSTAJE_AGUA = 1000;       // Cantidad que se repone de agua en cada invocaci�n

    /**
     * Cantidad de petroleo en el dep�sito de petr�leo
     */
    private int depositoPetroleo;
    /**
     * Cantidad de agua en el dep�sito de agua
     */
    private int depositoAgua;

    /**
     * Executor responsable del repostado concurrente de petr�leo y agua
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
     * Un barco petrolero trata de entrar en el puerto, rellenar sus dep�sitos y salir del puerto
     */
    public void run() {
        ZonaRepostaje zonaRepostaje = ZonaRepostaje.recuperarInstancia();

        // Protocolo com�n a los barcos de entrada
        super.run();

        // Protocolo espec�fico
        zonaRepostaje.permisoRepostaje(this);                    // Pide permiso para empezar a repostar
        // Repostar� petr�leo
        ((ThreadPoolExecutor) executor).submit(() -> {
            while (!petroleoCompleto())
                zonaRepostaje.repostarPetroleo(this, CANTIDAD_REPOSTAJE_PETROLEO);
        });
        // Repostar� agua
        ((ThreadPoolExecutor) executor).submit(() -> {
            while (!aguaCompleto())
                zonaRepostaje.repostarAgua(this, CANTIDAD_REPOSTAJE_AGUA);
        });

        ((ThreadPoolExecutor) executor).shutdown();                     // Da de baja el executor
        try {                                                           // Espera a que el barco termine de repostar para salir del puerto
            ((ThreadPoolExecutor) executor).awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ya no hay m�s cargamentos y abandona la zona de repostaje
        imprimirConTimestamp("El barco petrolero " + getIdentificador() + " abandona la zona de repostaje");
        // Los barcos petroleros que abandonan la zona de repostaje salen del puerto
        setComporBarco(new ComporBarcoSalida());
        super.run();
    }

    /**
     * Reposta una cantidad dada de petr�leo
     *
     * @param cantidad Cantidad a repostar de petr�leo
     * @return Si realmente se repost� alguna cantidad. Devuelve Falso si el argumento 'cantidad' es 0, Verdadero
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
     * @return True si los dep�sitos del barco est�n llenos.
     */
    public boolean estaLleno() {
        return aguaCompleto() && petroleoCompleto();
    }

    /**
     * Devuelve true en caso de que el dep�sito de petr�leo est� completo. False en caso contrario.
     *
     * @return True si el dep�sito de petr�leo est� lleno.
     */
    private boolean petroleoCompleto() {
        return getDepositoPetroleo() == LIMITE_PETROLEO;
    }

    /**
     * Devuelve true en caso de que el dep�sito de agua est� completo. False en caso contrario.
     *
     * @return True si el dep�sito de agua est� lleno.
     */
    private boolean aguaCompleto() {
        return getDepositoAgua() == LIMITE_AGUA;
    }

    /**
     * M�todo accesor del atributo {@link BarcoPetrolero:depositoPetroleo}
     */
    public int getDepositoPetroleo() {
        return depositoPetroleo;
    }

    /**
     * M�todo accesor del atributo {@link BarcoPetrolero:depositoAgua}
     */
    public int getDepositoAgua() {
        return depositoAgua;
    }

    /**
     * M�todo modificador del atributo {@link BarcoPetrolero:depositoPetroleo}
     *
     * @param depositoPetroleo Nueva cantidad de petr�leo en el dep�sito
     */
    private void setDepositoPetroleo(int depositoPetroleo) {
        this.depositoPetroleo = depositoPetroleo;
    }

    /**
     * M�todo modificador del atributo {@link BarcoPetrolero:depositoAgua}
     *
     * @param depositoAgua Nuevo cantidad de agua en el dep�sito
     */
    private void setDepositoAgua(int depositoAgua) {
        this.depositoAgua = depositoAgua;
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