import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota de servicios de la plataforma. Exporta un servicio para consultar el n�mero de cargamentos descargados
 * de cada tipo por la plataforma.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public interface IServidorPlataforma extends Remote {

    /**
     * @return Cadena JSON con los datos de los contenedores descargados
     * @throws RemoteException Si ocurre una excepci�n remota
     */
    String obtenerCargamentosDescargados() throws RemoteException;

}
