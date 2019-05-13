import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementación de la interfaz remota para acceder a servicios de la torre de control.
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
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
