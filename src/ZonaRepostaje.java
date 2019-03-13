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
     * Instancia Singleton de la zona de repostaje
     */
    private static ZonaRepostaje instancia = null;
    /**
     * Colección de contenedores de petróleo reservados para los barcos petroleros. Cada barco registra mediante
     * identificador un contenedor de petróleo privado
     */
    private Map<Integer, ContenedorPetroleo> contenPetroleo;
    /**
     * Bandera para indicar si la zona de repostaje está operativa
     */
    private boolean activa;
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
        activa = true;

        /* INICIALIZACION DE LOS SEMÁFOROS */

        mutexContenPetroleo = new Semaphore(1);
        mutexContenAgua = new Semaphore(1);

        esperaBarcos = new ArrayList<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        contenVacio = new ArrayList<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        for (int i = 0; i < Main.NUM_BARCOS_PETROLEROS_SIM; i++) {
            esperaBarcos.add(new Semaphore(0));
            contenVacio.add(new Semaphore(0));
        }
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
                contenPetroleo.put(barco.getIdentificador(), new ContenedorPetroleo(ContenedorPetroleo.CANT_INICIAL_CONT_PETROLEO,
                        ContenedorPetroleo.CANTIDAD_MAX_PETROLEO));
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
        barco.repostarAgua(cantidad);
    }

    /**
     * Rellena a la capacidad máxima los contenedores de petróleo. Utilizado por el repostador
     */
    public void reponerContenedores() {
        for (ContenedorPetroleo contenedorPetroleo : contenPetroleo.values())
            contenedorPetroleo.reponer();
    }

    /**
     * Método accesor del atributo {@link ZonaRepostaje:activa}
     */
    public boolean getActiva() {
        return activa;
    }

    /**
     * Método modificador del atributo {@link ZonaRepostaje:activa}
     *
     * @param activa Nuevo valor de la bandera
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
        if (!getActiva())                   // Si la zona no está activa desbloqueamos al repostaador para que finalice
            esperaRepostador.release();
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