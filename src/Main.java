import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal del proyecto
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Main {

    private static final int NUM_BARCOS_ENTRADA_SIM = 3;    // Número de barcos de entrada creados para la simulación
    private static final int NUM_BARCOS_SALIDA_SIM = 3;     // Número de barcos de salida creados para la simulación
    private static final int NUM_BARCOS_MERCANTES_SIM = 1;  // Número de barcos mercantes creados para la simulación
    public static final int NUM_BARCOS_PETROLEROS_SIM = 5;  // Número de barcos petroleros creados para la simulación

    private static final int NUM_CONT_AZUCAR_BR = 12;       // Número de contenedores de azúcar que transporta un barco mercante
    private static final int NUM_CONT_HARINA_BR = 5;        // Número de contenedores de harina que transporta un barco mercante
    private static final int NUM_CONT_SAL_BR = 20;          // Número de contenedores de sal que transporta un barco mercante

    private static final int CANT_INIC_DEP_PETR_BP = 0;     // Cantidad inicial del depósito de petróleo de los barcos petroleros
    private static final int CANT_INIC_DEP_AGUA_BP = 0;     // Cantidad inicial del depósito de augua de los barcos petroleros

    /**
     * Constructor por defecto. Inicia la simulación
     */
    public Main() {
        simulacion();
    }

    /**
     * Realiza la simulación del proyecto
     */
    private void simulacion() {
        List<BarcoPetrolero> barcosPetroleros = new ArrayList<>();
        Executor executor = Executors.newCachedThreadPool();// Executor para ejecutar los barcos según se instancien
        int id = 1;                                         // Identificador asignado a cada barco

        // CREACIÓN DE BARCOS

        for (int i = 0; i < NUM_BARCOS_ENTRADA_SIM; i++) {  // Crea los barcos que quieren entrar
            ((ThreadPoolExecutor) executor).submit(new Barco(id, new ComporBarcoEntrada()));
            id++;
        }
        for (int i = 0; i < NUM_BARCOS_SALIDA_SIM; i++) {   // Crea los barcos que quieren salir
            ((ThreadPoolExecutor) executor).submit(new Barco(id, new ComporBarcoSalida()));
            id++;
        }
        // Creación e incorporación de los barcos mercantes. Llevarán identificadores negativos para distinguirlos.
        for (int i = -1; i >= -NUM_BARCOS_MERCANTES_SIM; i--)
            ((ThreadPoolExecutor) executor).submit(new BarcoMercante(i, NUM_CONT_AZUCAR_BR, NUM_CONT_HARINA_BR, NUM_CONT_SAL_BR));
        // Creación e incorporación de los barcos petroleros. Llevarán identificadores mayores o iguales que 100
        BarcoPetrolero barcoPetrolero;
        id = 100;
        for (int i = 0; i < NUM_BARCOS_PETROLEROS_SIM; i++) {
            barcoPetrolero = new BarcoPetrolero(id, CANT_INIC_DEP_PETR_BP, CANT_INIC_DEP_AGUA_BP);
            barcosPetroleros.add(barcoPetrolero);
            id++;
        }
        // Registra los contenedores de petróleo para los barcos petroleros esperados
        ZonaRepostaje.recuperarInstancia().registrarContenedores(barcosPetroleros);
        mostrarMensaje("\t\t[" + System.currentTimeMillis() + "] Contenedores de petróleo registrados para barcos petroleros esperados");
        // Registra los semáforos para cada barco petrolero esperado
        ZonaRepostaje.recuperarInstancia().registrarSemaforos(barcosPetroleros);
        mostrarMensaje("\t\t[" + System.currentTimeMillis() + "] Semáforos registrados para barcos petroleros esperados");
        // Ejecuta los barcos petroleros instanciados
        for (BarcoPetrolero petrolero : barcosPetroleros)
            ((ThreadPoolExecutor) executor).submit(petrolero);

        // CREACIÓN DE GRÚAS. Sus indices comenzarán a partir del 10 para distinguirlas

        new Grua(10, TIPO_CARGAMENTO.AZUCAR);
        new Grua(11, TIPO_CARGAMENTO.HARINA);
        new Grua(12, TIPO_CARGAMENTO.SAL);

        // CREACIÓN DEL REPOSTADOR

        new Repostador();

        // Espera a que terminen todos los hilos de barcos

        ((ThreadPoolExecutor) executor).shutdown();
        try {
            ((ThreadPoolExecutor) executor).awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Detiene las grúas bloqueadas esperando por más cargamentos y el repostador esperando a repostar los contenedores
        Plataforma.recuperarInstancia().setActiva(false);
        ZonaRepostaje.recuperarInstancia().setActiva(false);

        mostrarMensaje("FIN del HILO PRINCIPAL");
    }

    /**
     * Muestra un mensaje en una línea por consola
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
        new Main();                                         // Inicia la simulación
    }
}