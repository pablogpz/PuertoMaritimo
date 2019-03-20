import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * TODO Documentación clase ZonaRepostaje
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ZonaRepostaje {

    /**
     * Instancia Singleton de la zona de repostaje
     */
    private static ZonaRepostaje instancia = null;
    /**
     * Colección de contenedores de petróleo reservados para los barcos petroleros. Cada barco registra mediante
     * identificador un contenedor de petróleo privado
     */
    private Map<Integer, ContenedorPetroleo> contenPetroleo;
    /**
     * Aquí esperan los barcos petroleros que quieren repostar hasta que obtienen el permiso para hacerlo
     */
    private CountDownLatch esperaBarcos;
    /**
     * Barrera en la que esperan los barcos petroleros a que sus contenedores de petróleo sean repostados
     */
    private CyclicBarrier contenVacios;
    /**
     * Semáforo de exclusión mútua sobre el depósito de agua
     */
    private Semaphore mutexContenAgua;

    private ZonaRepostaje() {
        contenPetroleo = new HashMap<>();

        esperaBarcos = new CountDownLatch(Main.NUM_BARCOS_PETROLEROS_SIM);
        contenVacios = new CyclicBarrier(Main.NUM_BARCOS_PETROLEROS_SIM, this::reponerContenedores);
        mutexContenAgua = new Semaphore(1);
    }

    /**
     * Registra un contenedor de petróleo para cada barco petrolero esperado
     *
     * @param barcosPetroleros Colección de barcos petroleros esperados
     */
    public void registrarContenedores(Collection<? extends BarcoPetrolero> barcosPetroleros) {
        barcosPetroleros.forEach(barcosPetrolero -> contenPetroleo.put(barcosPetrolero.getIdentificador(),
                new ContenedorPetroleo(ContenedorPetroleo.CANT_INICIAL_CONT_PETROLEO,
                        ContenedorPetroleo.CANTIDAD_MAX_CONT_PETROLEO)));
    }

    /**
     * Pide permiso a la zona de repostaje para empezar a repostar. Ningún barco comienza a repostar hasta que no
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
     * Reposta en el barco dado la cantidad dada de petróleo
     *
     * @param barco    Barco que repostar
     * @param cantidad Cantidad de petróleo a repostar en el barco
     */
    public void repostarPetroleo(BarcoPetrolero barco, int cantidad) {
        // Mientras se pueda repostar sigue repostando. Si no se repostó ninguna cantidad el contenedor está vacio
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " INTENTA repostar " + cantidad + " L de PETROLEO");
        if (!barco.repostarPetroleo(contenPetroleo.get(barco.getIdentificador()).vaciar(cantidad))) {
            try {
                imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " tiene VACÍO su CONTENEDOR de PETROLEO");
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
        // Protocolo de entrada: Adquirir exclusión mutua sobre el depósito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " INTENTA repostar " + cantidad + " L de AGUA");
        try {
            mutexContenAgua.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Acción: Repostar una cantidad de agua
        barco.repostarAgua(cantidad);
        // Protocolo de salida: Liberar exclusión mutua sobre el depósito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " HA REPOSTADO " + cantidad +
                " L de AGUA. Repostado : " + barco.getDepositoAgua() + "L");
        mutexContenAgua.release();
    }

    /**
     * Rellena a la capacidad máxima los contenedores de petróleo
     */
    private void reponerContenedores() {
        imprimirConTimestamp("El Repostador REPOSTA los contenedores de petróleo");
        for (ContenedorPetroleo contenedorPetroleo : contenPetroleo.values())
            contenedorPetroleo.reponer();
        imprimirConTimestamp("El Repostador HA REPOSTADO los contenedores de petróleo");
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
     * Imprime un mensaje con marca de tiempo por consola en una línea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t\t[" + System.currentTimeMillis() + "] " + mensaje);
    }
}