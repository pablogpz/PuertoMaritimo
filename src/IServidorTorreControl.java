import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota de servicios de la torre de control. Exporta un servicio para consultar el n�mero de barcos
 * dentro del puerto.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public interface IServidorTorreControl extends Remote {

    /**
     * @return N�mero de barcos dentro del puerto
     * @throws RemoteException Si ocurre una excepci�n remota
     */
    int consultarBarcosDentroPuerto() throws RemoteException;

}
