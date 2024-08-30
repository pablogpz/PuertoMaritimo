import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal del proyecto.
 * Se encarga de solventar los distintos problemas planteados en la pr�ctica Puerto Mar�timo.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */

public class Main {

    private static final int NUM_BARCOS_ENTRADA_SIM = 3;    // N�mero de barcos de entrada creados para la simulaci�n
    public static final int NUM_BARCOS_SALIDA_SIM = 3;      // N�mero de barcos de salida creados para la simulaci�n
    private static final int NUM_BARCOS_MERCANTES_SIM = 1;  // N�mero de barcos mercantes creados para la simulaci�n
    public static final int NUM_BARCOS_PETROLEROS_SIM = 5;  // N�mero de barcos petroleros creados para la simulaci�n

    private static final int NUM_CONT_AZUCAR_BR = 12;       // N�mero de contenedores de az�car que transporta un barco mercante
    private static final int NUM_CONT_HARINA_BR = 5;        // N�mero de contenedores de harina que transporta un barco mercante
    private static final int NUM_CONT_SAL_BR = 20;          // N�mero de contenedores de sal que transporta un barco mercante

    private static final int CANT_INIC_DEP_PETR_BP = 0;     // Cantidad inicial del dep�sito de petr�leo de los barcos petroleros
    private static final int CANT_INIC_DEP_AGUA_BP = 0;     // Cantidad inicial del dep�sito de augua de los barcos petroleros

    // Nombre del servicio para consultar el n�mero de barcos dentro del puerto en un momento dado
    private static final String SERVICIO_BARCOS_DENTRO_PUERTO = "BarcosDentroPuerto";
    // Nombre del servicio para consultar el n�mero de cargamentos descargados
    private static final String SERVICIO_CARGAMENTOS_DESCARGADOS = "CargamentosDescargados";

    /**
     * Constructor por defecto. Inici1a la simulaci�n
     */
    public Main() {
        // Inicializa la propiedad "java.rmi.server.hostname" con la direcci�n local por seguridad
        String localhost = "localhost";
        System.setProperty("java.rmi.server.hostname", localhost);

        simulacion();
    }

    /**
     * Realiza la simulaci�n del proyecto
     */
    private void simulacion() {
        List<BarcoPetrolero> barcosPetroleros = new ArrayList<>();
        Executor executor = Executors.newCachedThreadPool();// Executor para ejecutar los barcos seg�n se instancien
        int id = 1;                                         // Identificador asignado a cada barco

        // REGISTRO DE SERVICIOS

        if (registrarServicios()) System.out.println("TODOS LOS SERVICIOS REGISTRADOS");
        else System.err.println("NO SE PUDIERON REGISTRAR TODOS LOS SERVICIOS");

        // CREACI�N DE BARCOS

        for (int i = 0; i < NUM_BARCOS_ENTRADA_SIM; i++) {  // Crea los barcos que quieren entrar
            ((ThreadPoolExecutor) executor).submit(new Barco(id, new ComporBarcoEntrada()));
            id++;
        }
        for (int i = 0; i < NUM_BARCOS_SALIDA_SIM; i++) {   // Crea los barcos que quieren salir
            ((ThreadPoolExecutor) executor).submit(new Barco(id, new ComporBarcoSalida()));
            id++;
        }
        // Creaci�n e incorporaci�n de los barcos mercantes. Llevar�n identificadores negativos para distinguirlos.
        for (int i = -1; i >= -NUM_BARCOS_MERCANTES_SIM; i--)
            ((ThreadPoolExecutor) executor).submit(new BarcoMercante(i, NUM_CONT_AZUCAR_BR, NUM_CONT_HARINA_BR, NUM_CONT_SAL_BR));
        // Creaci�n e incorporaci�n de los barcos petroleros. Llevar�n identificadores mayores o iguales que 100
        BarcoPetrolero barcoPetrolero;
        id = 100;
        for (int i = 0; i < NUM_BARCOS_PETROLEROS_SIM; i++) {
            barcoPetrolero = new BarcoPetrolero(id, CANT_INIC_DEP_PETR_BP, CANT_INIC_DEP_AGUA_BP);
            barcosPetroleros.add(barcoPetrolero);
            id++;
        }
        // Registra los contenedores de petr�leo para los barcos petroleros esperados
        ZonaRepostaje.recuperarInstancia().registrarContenedores(barcosPetroleros);
        mostrarMensaje("\t\t[" + System.currentTimeMillis() + "] Contenedores de petr�leo registrados para barcos petroleros esperados");
        // Ejecuta los barcos petroleros instanciados
        barcosPetroleros.forEach(((ThreadPoolExecutor) executor)::submit);

        // CREACI�N DE GR�AS. Sus indices comenzar�n a partir del 10 para distinguirlas

        new Grua(10, TIPO_CARGAMENTO.AZUCAR);
        new Grua(11, TIPO_CARGAMENTO.HARINA);
        new Grua(12, TIPO_CARGAMENTO.SAL);

        // Espera a que terminen todos los hilos de barcos

        ((ThreadPoolExecutor) executor).shutdown();
        try {
            ((ThreadPoolExecutor) executor).awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Detiene las gr�as bloqueadas esperando por m�s cargamentos y el repostador esperando a repostar los contenedores
        Plataforma.recuperarInstancia().apagarGruas();

        mostrarMensaje("FIN del HILO PRINCIPAL");
    }

    /**
     * Registra los servicios requeridos
     *
     * @return Si todos los servicios pudieron ser registrados
     */
    private boolean registrarServicios() {
        try {
            Registry registro = LocateRegistry.getRegistry();

            // Instancias de servidores
            IServidorTorreControl stubServidorTorreControl = new ServidorTorreControl();
            IServidorPlataforma stubServidorPlataforma = new ServidorPlataforma();

            // Registro de servicios
            registro.rebind(SERVICIO_BARCOS_DENTRO_PUERTO, stubServidorTorreControl);
            registro.rebind(SERVICIO_CARGAMENTOS_DESCARGADOS, stubServidorPlataforma);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Muestra un mensaje en una l�nea por consola
     *
     * @param mensaje Mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Punto de entrada del proyecto
     *
     * @param args No definidos
     */
    public static void main(String[] args) {
        new Main();                                         // Inicia la simulaci�n
    }

}