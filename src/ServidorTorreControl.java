import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementaci�n de la interfaz remota para acceder a servicios de la torre de control.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public class ServidorTorreControl extends UnicastRemoteObject implements IServidorTorreControl {

    protected ServidorTorreControl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int consultarBarcosDentroPuerto() throws RemoteException {
        return TorreControl.recuperarInstancia().getBarcosDentroPuerto();
    }

}
