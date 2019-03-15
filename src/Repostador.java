/**
 * TODO Documentación clase Repostador
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class Repostador implements Runnable {

    private Thread autoThread;          // Autothread

    public Repostador() {
        autoThread = new Thread(this);
        autoThread.start();
    }

    /**
     * El repostador trata de repostar los contenedores de petróleo de la zona de repostaje
     */
    public void run() {
        if (autoThread.equals(Thread.currentThread())) {
            while (ZonaRepostaje.recuperarInstancia().getActiva())
                repostar();
            System.out.println("\t\tEl Repostador ha terminado su trabajo");
        }
    }

    /**
     * Reposta todos los contenedores de petróleo de la zona de repostaje dada a su capacidad máxima
     */
    private void repostar() {
        ZonaRepostaje.recuperarInstancia().reponerContenedores();
    }
}