/**
 * Implementa el partr�n de dise�o Singleton. Representa la puerta del puerto que comparten todos los barcos que
 * quieren interactuar con el puerto. Permite entrar y salir por ella, reflej�ndolo en consola
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */

public class Puerta {

    private static final int NUM_PETICIONES = 3;            // N�mero de peticiones a la puerta seg�n el estado del barco

    /**
     * Instancia Singleton de la puerta
     */
    private static Puerta instancia = null;

    /**
     * Constructor por defecto. Inicializa la instancia Singleton
     */
    private Puerta() {
    }

    /**
     * Muestra por consola la entrada del barco pasado por argumento. Imprime el mensaje {@code NUM_PETICIONES} para detectar
     * errores en el manejo concurrente de los permisos de entrada y salida
     *
     * @param barco Barco que entra
     */
    public void entrar(Barco barco) {
        String mensaje = "[" + System.currentTimeMillis() + "] El barco con ID: " + barco.getIdentificador() + " entra";
        for (int i = 0; i < NUM_PETICIONES; i++) {
            mostrarMensaje(mensaje);
        }
    }

    /**
     * Muestra por consola la salida del barco pasado por argumento. Imprime el mensaje {@code NUM_PETICIONES} para detectar
     * errores en el manejo concurrente de los permisos de entrada y salida
     *
     * @param barco Barco que quiere salir
     */
    public void salir(Barco barco) {
        String mensaje = "[" + System.currentTimeMillis() + "] El barco con ID: " + barco.getIdentificador() + " sale";
        for (int i = 0; i < NUM_PETICIONES; i++) {
            mostrarMensaje(mensaje);
        }
    }

    /**
     * @return la instancia Singleton de la clase Puerta
     */
    public synchronized static Puerta recuperarInstancia() {
        if (instancia == null)
            instancia = new Puerta();

        return instancia;
    }

    /**
     * Muestra un mensaje en una l�nea por consola
     *
     * @param mensaje Mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}