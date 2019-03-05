/**
 * Implementa el partrón de diseño Singleton
 * // TODO Documentar clase Puerta
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */

public class Puerta {

    private static final int NUM_PETICIONES = 1;            // Número de peticiones a la puerta según el estado del barco

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
     * // TODO Documenar método entrar()
     *
     * @param barco
     */
    public void entrar(Barco barco) {
        String mensaje = "[" + System.currentTimeMillis() + "] El barco con ID: " + barco.getIdentificador() + " entra";
        for (int i = 0; i < NUM_PETICIONES; i++) {
            mostrarMensaje(mensaje);
        }
    }

    /**
     * // TODO Documentar método salir()
     *
     * @param barco
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
    public static Puerta recuperarInstancia() {
        if (instancia != null)
            return instancia;
        else
            instancia = new Puerta();

        return instancia;
    }

    /**
     * Muestra un mensaje en una línea por consola
     *
     * @param mensaje Mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);

    }
}