import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidorPlataforma extends Remote {

    String obtenerCargamentosDescargados() throws RemoteException;

}
