import java.util.Collection;
import java.util.HashMap;
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
     * Contador de barcos petroleros esperando repostar
     */
    private int barcosEsperando;
    /**
     * Bandera para indicar si la zona de repostaje está operativa
     */
    private boolean activa;

    /* SEMÁFOROS */

    /**
     * Semáforo de exclusión mutua sobre el contador de barcos esperando para repostar
     */
    private Semaphore mutexBarcosEsperando;
    /**
     * Semáforo de exclusión mútua sobre el depósito de agua
     */
    private Semaphore mutexContenAgua;
    /**
     * Semáforos para resolver la condición de sincronización inicial de que todos los barcos se esperen unos a otros
     */
    private Map<Integer, Semaphore> esperaBarcos;
    /**
     * Semáforos para bloquear a cada barco cuando vacíen su contenedor
     */
    private Map<Integer, Semaphore> contenVacio;
    /**
     * Semáforo en el que bloquear al repostador
     */
    private Semaphore esperaRepostador;

    private ZonaRepostaje() {
        contenPetroleo = new HashMap<>();
        activa = true;

        /* INICIALIZACION DE LOS SEMÁFOROS */

        mutexBarcosEsperando = new Semaphore(1);
        mutexContenAgua = new Semaphore(1);

        esperaBarcos = new HashMap<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        contenVacio = new HashMap<>(Main.NUM_BARCOS_PETROLEROS_SIM);
        esperaRepostador = new Semaphore(0);
    }

    /**
     * Registra los semáforos requeridos para cada barco petrolero esperado
     *
     * @param barcosPetroleros Colección de barcos petroleros esperados
     */
    public void registrarSemaforos(Collection<? extends BarcoPetrolero> barcosPetroleros) {
        for (BarcoPetrolero barcosPetrolero : barcosPetroleros) {
            esperaBarcos.put(barcosPetrolero.getIdentificador(), new Semaphore(0));
            contenVacio.put(barcosPetrolero.getIdentificador(), new Semaphore(0));
        }
    }

    /**
     * Registra un contenedor de petróleo para cada barco petrolero esperado
     *
     * @param barcosPetroleros Colección de barcos petroleros esperados
     */
    public void registrarContenedores(Collection<? extends BarcoPetrolero> barcosPetroleros) {
        for (BarcoPetrolero barcosPetrolero : barcosPetroleros)
            contenPetroleo.put(barcosPetrolero.getIdentificador(), new ContenedorPetroleo(ContenedorPetroleo.CANT_INICIAL_CONT_PETROLEO,
                    ContenedorPetroleo.CANTIDAD_MAX_PETROLEO));
    }

    /**
     * Pide permiso a la zona de repostaje para empezar a repostar. Ningún barco comienza a repostar hasta que no
     * hayan llegado todos a la zona de repostaje
     *
     * @param barco Barco que llega a la zona de repostaje
     */
    public void permisoRepostaje(BarcoPetrolero barco) {
        try {

            // Protocolo de entrada - 'barcosEsperando' : Adquirir exclusión mútua
            imprimirConTimestamp("El barco " + barco.getIdentificador() + " pide permiso para repostar");
            mutexBarcosEsperando.acquire();
            // Acción - 'barcosEsperando' : Incrementar en 1 el contador de barcos esperando
            setBarcosEsperando(getBarcosEsperando() + 1);
            // Protocolo de entrada - 'barcosEsperando' : Liberar exclusión mútua
            mutexBarcosEsperando.release();

            if (getBarcosEsperando() == Main.NUM_BARCOS_PETROLEROS_SIM) {       // Comprueba si este barco es el último en llegar
                imprimirConTimestamp("El barco " + barco.getIdentificador() + " DESBLOQUEA a los barcos petroleros");
                for (Semaphore semaphore : esperaBarcos.values())               // Libera los semáforos de espera al resto de barcos
                    semaphore.release();
            } else {
                imprimirConTimestamp("El barco " + barco.getIdentificador() + " bloqueado para repostar");
                esperaBarcos.get(barco.getIdentificador()).acquire();           // El barco se bloquea esperando al resto
            }
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
        // Protocolo de entrada: Adquirir exclusión mutua sobre el depósito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " INTENTA repostar " + cantidad + " L de AGUA");
        try {
            mutexContenAgua.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Acción: Repostar una cantidad de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " REPOSTA " + cantidad + " L de AGUA");
        barco.repostarAgua(cantidad);
        // Protocolo de salida: Liberar exclusión mutua sobre el depósito de agua
        imprimirConTimestamp("El barco petrolero " + barco.getIdentificador() + " HA REPOSTADO " + cantidad +
                " L de AGUA. Repostado : " + barco.getDepositoAgua() + "L");
        mutexContenAgua.release();
    }

    /**
     * Rellena a la capacidad máxima los contenedores de petróleo. Utilizado por el repostador
     */
    public void reponerContenedores() {
        // Protocolo de entrada: Bloqueo del repostador. Los contenedores están inicialmente llenos
        imprimirConTimestamp("El Repostador INTENTA REPONER los contenedores de petróleo");
        try {
            esperaRepostador.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Acción: repostar todos los contenedores
        imprimirConTimestamp("El Repostador REPOSTA los contenedores de petróleo");
        for (ContenedorPetroleo contenedorPetroleo : contenPetroleo.values())
            contenedorPetroleo.reponer();
        // Protocolo de salida: Informar de la acción
        imprimirConTimestamp("El Repostador HA REPOSTADO los contenedores de petróleo");
    }

    /**
     * Método accesor del atributo {@link ZonaRepostaje:activa}
     */
    public boolean getActiva() {
        return activa;
    }

    /**
     * Método accesor del atributo {@link ZonaRepostaje:barcosEsperando}
     */
    private int getBarcosEsperando() {
        return barcosEsperando;
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
     * Método modificador del atributo {@link ZonaRepostaje:barcosEsperando}
     *
     * @param barcosEsperando
     */
    private void setBarcosEsperando(int barcosEsperando) {
        this.barcosEsperando = barcosEsperando;
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