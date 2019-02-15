import java.util.*;

/**
 * Clase principal del proyecto
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Main {

    private static final int NUM_BARCOS_ENTRADA_SIM = 3;    // Número de barcos de entrada creados para la simulación
    private static final int NUM_BARCOS_SALIDA_SIM = 3;     // Número de barcos de salida creados para la simulación

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
        Set<Barco> barcos = new HashSet<>();                // Colección de barcos simulados
        int id = 1;                                         // Identificador asignado a cada barco

        // Creación de barcos

        for (int i = 0; i < NUM_BARCOS_ENTRADA_SIM; i++) {  // Crea los barcos que quieren entrar
            barcos.add(new Barco(id, ESTADO_BARCO.ENTRADA));
            id++;
        }
        for (int i = 0; i < NUM_BARCOS_SALIDA_SIM; i++) {   // Crea los barcos que quieren salir
            barcos.add(new Barco(id, ESTADO_BARCO.SALIDA));
            id++;
        }

        // Ejecución de la simulación

        Iterator<Barco> barcoIterator = barcos.iterator();
        List<Thread> hilos = new ArrayList<>();             // Colección de hilos instanciados
        while (barcoIterator.hasNext()) {
            Thread hiloBarco = new Thread(barcoIterator.next());
            hiloBarco.start();                              // Lanza cada hilo
            hilos.add(hiloBarco);                           // Guarda el hilo instanciado y lanzado
        }

        for (Thread hilo : hilos) {                         // Espera a que terminen todos los hilos
            try {
                hilo.join();
            } catch (InterruptedException e) {
                mostrarMensaje(e.getMessage());
            }
        }
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