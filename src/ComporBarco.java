/**
 * Interfaz que modela el comportamiento común a barcos de entrada (entran en el puerto) y barcos de salida (salen del puerto).
 * Por defecto un barco para poder realizar su acción {@link ComporBarco#permiso(Barco)} debe pedir permiso para hacerla
 * {@link ComporBarco#accion(Barco)} y, una vez que la ha llevado a cabo, notificarlo {@link ComporBarco#finPermiso(Barco)}
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public interface ComporBarco {

    /**
     * Petición de permiso para realizar la acción
     *
     * @param barco Barco que realiza la petición de permiso
     */
    void permiso(Barco barco);

    /**
     * Notificación de acción completa y, por tanto, del fin del permiso adquirido para realizar la acción
     *
     * @param barco Barco que quiere notificar el fin de su permiso
     */
    void finPermiso(Barco barco);

    /**
     * Tarea que debe realizar la clase implementando esta interfaz una vez se le conceda el permiso para realizarla
     *
     * @param barco Barco que realiza la acción
     */
    void accion(Barco barco);

    /**
     * Describe la secuencia lógica de pasos para llevar a cabo la acción de la clase implementado esta interfaz.
     * Por defecto se debe pedir permiso para realizar la acción, realizarla una vez obtenido el permiso y notificar
     * el fin del permiso una vez realizada la acción
     *
     * @param barco Barco que implementa la interfaz
     */
    default void comporBarco(Barco barco) {
        // Protocolo de entrada : Pide permiso para realizar la acción
        permiso(barco);
        // Realiza la acción
        accion(barco);
        // Protocolo de salida : Notifica el fin del permiso para realizar la acción finalizada
        finPermiso(barco);
    }

}