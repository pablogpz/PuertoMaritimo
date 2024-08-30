import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorPlataforma extends UnicastRemoteObject implements IServidorPlataforma {


    protected ServidorPlataforma() throws RemoteException {
        super();
    }

    @Override
    public String obtenerCargamentosDescargados() throws RemoteException {
        return Plataforma.recuperarInstancia().obtenerCargamentosDescargados();
    }

}
