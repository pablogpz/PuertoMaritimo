import com.google.gson.Gson;
import pcd.util.Ventana;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class Main {

    // Nombre del servicio para consultar el número de barcos dentro del puerto en un momento dado
    private static final String SERVICIO_BARCOS_DENTRO_PUERTO = "BarcosDentroPuerto";
    // Nombre del servicio para consultar el número de cargamentos descargados
    private static final String SERVICIO_CARGAMENTOS_DESCARGADOS = "CargamentosDescargados";

    // Claves del diccionario que almacena el número de cargamentos descargados por la plataforma
    private static final String CLAVE_AZUCAR = "azucar";
    private static final String CLAVE_HARINA = "harina";
    private static final String CLAVE_SAL = "sal";

    public static void main(String[] args) {
        Ventana ventana = new Ventana("Manager Puerto");
        Gson gson = new Gson();

        try {
            // Inicializa un administrador de seguridad RMI si no existe ya uno
            if (System.getSecurityManager() == null) System.setSecurityManager(new SecurityManager());

            // Localiza el registro RMI del servidor
            Registry registro = LocateRegistry.getRegistry();

            // Creación de recibos para acceder a los servicios
            IServidorTorreControl servidorTorreControl =
                    ((IServidorTorreControl) registro.lookup(SERVICIO_BARCOS_DENTRO_PUERTO));
            IServidorPlataforma servidorPlataforma =
                    ((IServidorPlataforma) registro.lookup(SERVICIO_CARGAMENTOS_DESCARGADOS));

            // Llamada al servicio de barcos dentro del puerto
            int barcosDentroPuerto = servidorTorreControl.consultarBarcosDentroPuerto();

            // Llamada al servicio de contenedores descargados
            String JSONData = servidorPlataforma.obtenerCargamentosDescargados();   // Cadena JSON con los datos
            Properties properties = gson.fromJson(JSONData, Properties.class);      // Interpretación del JSON
            int contAzucar = Integer.parseInt(properties.getProperty(CLAVE_AZUCAR));
            int contHarina = Integer.parseInt(properties.getProperty(CLAVE_HARINA));
            int contSal = Integer.parseInt(properties.getProperty(CLAVE_SAL));
            int total = contAzucar + contHarina + contSal;

            // Salida por la ventana creada
            String salidaPorVentana = "SERVICIO - " + SERVICIO_BARCOS_DENTRO_PUERTO +
                    "\n     Barcos dentro del puerto : " + barcosDentroPuerto +
                    "\n\nSERVICIO - " + SERVICIO_CARGAMENTOS_DESCARGADOS +
                    "\n     Contenedores descargados " +
                    "\n         Contenedores de Azúcar : " + contAzucar +
                    "\n         Contenedores de Harina : " + contHarina +
                    "\n         Contenedores de Sal : " + contSal +
                    "\n     CONTENEDORES TOTALES DESCARGADOS : " + total;
            ventana.addText(salidaPorVentana);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
}
