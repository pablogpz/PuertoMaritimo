import java.util.Map;

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
    private static ZonaRepostaje instancia;
    /**
     * Colección de contenedores de petróleo reservados para los barcos petroleros. Cada barco registra mediante identificador un contenedor de petróleo privado
     */
    private Map<Integer, ContenedorPetroleo> contenPetroleo;

    /**
     * Registra un nuevo contenedor de petróleo reservado para un barco por identificador
     *
     * @param barco Barco que registra el contenedor
     * @return Si se pudo registrar el contenedor. Devuelve Falso si ya hay un contenedor registrado con ese identificador o el barco es nulo, Verdadero en otro caso
     */
    public boolean registrarContenedor(Barco barco) {
        // TODO - implement ZonaRepostaje.registrarContenedor
        return false;
    }

    /**
     * Reposta en el barco dado la cantidad dada de petróleo
     *
     * @param barco    Barco que repostar
     * @param cantidad Cantidad de petróleo a repostar en el barco
     */
    public void repostarPetroleo(BarcoPetrolero barco, int cantidad) {
        // TODO - implement ZonaRepostaje.repostarPetroleo
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
     * Método accesor del atributo contenPetroleo
     */
    public Map<Integer, ContenedorPetroleo> getContenPetroleo() {
        return contenPetroleo;
    }

    /**
     * @return Instancia Singleton de la zona de repostaje
     */
    public static ZonaRepostaje recuperarInstancia() {
        // TODO - implement ZonaRepostaje.recuperarInstancia
        return null;
    }

    private ZonaRepostaje() {
        // TODO - implement ZonaRepostaje.ZonaRepostaje
    }

}