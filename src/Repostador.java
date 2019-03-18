/**
 * TODO Documentación clase Repostador
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class Repostador implements Runnable {

    public Repostador() {
    }

    /**
     * El repostador trata de repostar los contenedores de petróleo de la zona de repostaje
     */
    public void run() {
        repostar();
    }

    /**
     * Reposta todos los contenedores de petróleo de la zona de repostaje dada a su capacidad máxima
     */
    private void repostar() {
        ZonaRepostaje.recuperarInstancia().reponerContenedores();
    }
}