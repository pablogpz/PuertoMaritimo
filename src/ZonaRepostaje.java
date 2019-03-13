import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * TODO Documentación clase ZonaRepostaje
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ZonaRepostaje {

    /**
     * Cantidad inicial de los contenedores de petróleo registrados
     */
    private static final int CANT_INICIAL_CONT_PETR = 3000;
    /**
     * Capacidad máxima de los contenedores de petróleo registrados
     */
    private static final int CAP_MAX_CONT_PETR = 3000;

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
     * Semáforo de exclusión mutua sobre la colección de contenedores de petróleo
     */
    private Semaphore mutexContenPetroleo;
    /**
     * Semáforo de exclusión mútua sobre el depósito de agua
     */
    private Semaphore mutexContenAgua;
    /**
     * Semáforos para resolver la condición de sincronización inicial de que todos los barcos se esperen unos a otros
     */
    private List<Semaphore> esperaBarcos;
    /**
     * Semáforos para bloquear a cada barco cuando vacíen su contenedor
     */
    private List<Semaphore> contenVacio;
    /**
     * Semáforo en el que bloquear al repostador
     */
    private Semaphore esperaRepostador;

    private ZonaRepostaje() {
        contenPetroleo = new HashMap<>();

        /** INICIALIZACION DE LOS SEMÁFOROS */

        mutexContenPetroleo = new Semaphore(1);
        mutexContenAgua = new Semaphore(1);

        esperaBarcos = new ArrayList<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        for (int i = 0; i < Main.NUM_BARCOS_PETROLEROS_SIM; i++)
            esperaBarcos.add(new Semaphore(0));

        contenVacio = new ArrayList<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        for (int i = 0; i < Main.NUM_BARCOS_PETROLEROS_SIM; i++)
            contenVacio.add(new Semaphore(0));

        esperaRepostador = new Semaphore(0);

    }

    /**
     * Registra un nuevo contenedor de petróleo reservado para un barco por identificador
     *
     * @param barco Barco que registra el contenedor
     * @return Si se pudo registrar el contenedor. Devuelve Falso si ya hay un contenedor registrado con ese identificador
     * o el barco es nulo, Verdadero en otro caso
     */
    public boolean registrarContenedor(Barco barco) {
        if (barco != null) {
            if (!contenPetroleo.containsKey(barco.getIdentificador())) {
                contenPetroleo.put(barco.getIdentificador(), new ContenedorPetroleo(CANT_INICIAL_CONT_PETR, CAP_MAX_CONT_PETR));
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Reposta en el barco dado la cantidad dada de petróleo
     *
     * @param barco    Barco que repostar
     * @param cantidad Cantidad de petróleo a repostar en el barco
     */
    public void repostarPetroleo(BarcoPetrolero barco, int cantidad) {
        // TODO - implement ZonaRepostaje.repostarPetroleo
        barco.repostarPetroleo(contenPetroleo.get(barco.getIdentificador()).vaciar(cantidad));
    }

    /**
     * Reposta en el barco dado la cantidad dada de agua
     *
     * @param barco    Barco a repostar
     * @param cantidad Cantidad de agua a repostar en el barco
     */
    public void repostarAgua(BarcoPetrolero barco, int cantidad) {
        // TODO - implement ZonaRepostaje.repostarAgua
    }

    /**
     * Rellena a la capacidad máxima los contenedores de petróleo. Utilizado por el repostador
     */
    public void reponerContenedores() {
        // TODO - implement ZonaRepostaje.reponerContenedores
    }

    /**
     * @return Instancia Singleton de la zona de repostaje
     */
    public synchronized static ZonaRepostaje recuperarInstancia() {
        if (instancia == null)
            instancia = new ZonaRepostaje();

        return instancia;
    }

}