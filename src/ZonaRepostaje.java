import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Clase que modela el comportamiento de la Zona de Repostaje. Sigue un patr�n de dise�o Singleton (derivado del plantemiento
 * de los problemas). Su finalidad es la de albergar los distintos contenedores y gestionar las posibles operaciones que ciertos
 * barcos determinados pueden realizar con estos. Entre estas funciones destacamos Repostar petr�leo y Repostar agua.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public class ZonaRepostaje {

    /**
     * Instancia Singleton de la zona de repostaje
     */
    private static ZonaRepostaje instancia = null;
    /**
     * Colecci�n de contenedores de petr�leo reservados para los barcos petroleros. Cada barco registra mediante
     * identificador un contenedor de petr�leo privado
     */
    private Map<Integer, ContenedorPetroleo> contenPetroleo;
    /**
     * Aqu� esperan los barcos petroleros que quieren repostar hasta que obtienen el permiso para hacerlo
     */
    private CountDownLatch esperaBarcos;
    /**
     * Barrera en la que esperan los barcos petroleros a que sus contenedores de petr�leo sean repostados
     */
    private CyclicBarrier contenVacios;
    /**
     * Sem�foro de exclusi�n m�tua sobre el dep�sito de agua
     */
    private Semaphore mutexContenAgua;

    private ZonaRepostaje() {
        contenPetroleo = new HashMap<>();

        esperaBarcos = new CountDownLatch(Main.NUM_BARCOS_PETROLEROS_SIM);
        contenVacios = new CyclicBarrier(Main.NUM_BARCOS_PETROLEROS_SIM, this::reponerContenedores);
        mutexContenAgua = new Semaphore(1);
    }

    /**
     * Registra un contenedor de petr�leo para cada barco petrolero esperado
     *
     * @param barcosPetroleros Colecci�n de barcos petroleros esperados
     */
    public void registrarContenedores(Collection<? extends BarcoPetrolero> barcosPetroleros) {
        barcosPetroleros.forEach(barcosPetrolero -> contenPetroleo.put(barcosPetrolero.getIdentificador(),
                new ContenedorPetroleo(ContenedorPetroleo.CANT_INICIAL_CONT_PETROLEO,
                        ContenedorPetroleo.CANTIDAD_MAX_CONT_PETROLEO)));
    }

    /**
     * Pide permiso a la zona de repostaje para empezar a repostar. Ning�n barco comienza a repostar hasta que no
     * hayan llegado todos a la zona de repostaje
     *
     * @param barco Barco que llega a la zona de repostaje
     */
    public void permisoRepostaje(BarcoPetrolero barco) {
        try {
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " PIDE PERMISO para REPOSTAR");
            esperaBarcos.countDown();
            if (esperaBarcos.getCount() != 0) esperaBarcos.await();
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " OBTIENE PERMISO para REPOSTAR");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reposta en el barco dado la cantidad dada de petr�leo
     *
     * @param barco    Barco que repostar
     * @param cantidad Cantidad de petr�leo a repostar en el barco
     */
    public void repostarPetroleo(BarcoPetrolero barco, int cantidad) {
        // Mientras se pueda repostar sigue repostando. Si no se repost� ninguna cantidad el contenedor est� vacio
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " INTENTA repostar " + cantidad + " L de PETROLEO");
        if (!barco.repostarPetroleo(contenPetroleo.get(barco.getIdentificador()).vaciar(cantidad))) {
            try {
                imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " tiene VAC�O su CONTENEDOR de PETROLEO");
                contenVacios.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        } else {
            imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " REPOSTA " + cantidad + " L de PETROLEO. Repostado : " +
                    barco.getDepositoPetroleo() + "L");
        }
    }

    /**
     * Reposta en el barco dado la cantidad dada de agua
     *
     * @param barco    Barco a repostar
     * @param cantidad Cantidad de agua a repostar en el barco
     */
    public void repostarAgua(BarcoPetrolero barco, int cantidad) {
        // Protocolo de entrada: Adquirir exclusi�n mutua sobre el dep�sito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " INTENTA repostar " + cantidad + " L de AGUA");
        try {
            mutexContenAgua.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Acci�n: Repostar una cantidad de agua
        barco.repostarAgua(cantidad);
        // Protocolo de salida: Liberar exclusi�n mutua sobre el dep�sito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " HA REPOSTADO " + cantidad +
                " L de AGUA. Repostado : " + barco.getDepositoAgua() + "L");
        mutexContenAgua.release();
    }

    /**
     * Rellena a la capacidad m�xima los contenedores de petr�leo
     */
    private void reponerContenedores() {
        imprimirConTimestamp("El Repostador REPOSTA los contenedores de petr�leo");
        for (ContenedorPetroleo contenedorPetroleo : contenPetroleo.values())
            contenedorPetroleo.reponer();
        imprimirConTimestamp("El Repostador HA REPOSTADO los contenedores de petr�leo");
    }

    /**
     * @return Instancia Singleton de la zona de repostaje
     */
    public synchronized static ZonaRepostaje recuperarInstancia() {
        if (instancia == null)
            instancia = new ZonaRepostaje();

        return instancia;
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