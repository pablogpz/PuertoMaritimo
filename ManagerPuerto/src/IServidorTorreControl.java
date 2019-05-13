import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota de servicios de la torre de control. Exporta un servicio para consultar el número de barcos
 * dentro del puerto.
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public interface IServidorTorreControl extends Remote {

    /**
     * @return Número de barcos dentro del puerto
     * @throws RemoteException Si ocurre una excepción remota
     */
    int consultarBarcosDentroPuerto() throws RemoteException;

}
