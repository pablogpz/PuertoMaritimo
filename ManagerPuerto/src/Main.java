import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    // Nombre del servicio para consultar el número de barcos dentro del puerto en un momento dado
    private static final String SERVICIO_BARCOS_DENTRO_PUERTO = "BarcosDentroPuerto";
    // Nombre del servicio para consultar el número de cargamentos descargados
    private static final String SERVICIO_CARGAMENTOS_DESCARGADOS = "CargamentosDescargados";

    public static void main(String[] args) {
        String localhost = "localhost";
        System.setProperty("java.rmi.server.hostname", localhost);

        try {
            Registry registro = LocateRegistry.getRegistry(localhost);

            IServidorTorreControl servidorTorreControl =
                    ((IServidorTorreControl) registro.lookup(SERVICIO_BARCOS_DENTRO_PUERTO));
            IServidorPlataforma servidorPlataforma =
                    ((IServidorPlataforma) registro.lookup(SERVICIO_CARGAMENTOS_DESCARGADOS));

            System.out.println("Barcos dentro del puerto : " + servidorTorreControl.consultarBarcosDentroPuerto());
            System.out.println("Contenedores descargados : " + servidorPlataforma.obtenerCargamentosDescargados());
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

}
