import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota de servicios de la plataforma. Exporta un servicio para consultar el número de cargamentos descargados
 * de cada tipo por la plataforma.
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public interface IServidorPlataforma extends Remote {

    /**
     * @return Cadena JSON con los datos de los contenedores descargados
     * @throws RemoteException Si ocurre una excepción remota
     */
    String obtenerCargamentosDescargados() throws RemoteException;

}
