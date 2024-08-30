/**
 * Interfaz que modela el comportamiento com�n a barcos de entrada (entran en el puerto) y barcos de salida (salen del puerto).
 * Por defecto un barco para poder realizar su acci�n {@link ComporBarco#permiso(Barco)} debe pedir permiso para hacerla
 * {@link ComporBarco#accion(Barco)} y, una vez que la ha llevado a cabo, notificarlo {@link ComporBarco#finPermiso(Barco)}
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public interface ComporBarco {

    /**
     * Petici�n de permiso para realizar la acci�n
     *
     * @param barco Barco que realiza la petici�n de permiso
     */
    void permiso(Barco barco);

    /**
     * Notificaci�n de acci�n completa y, por tanto, del fin del permiso adquirido para realizar la acci�n
     *
     * @param barco Barco que quiere notificar el fin de su permiso
     */
    void finPermiso(Barco barco);

    /**
     * Tarea que debe realizar la clase implementando esta interfaz una vez se le conceda el permiso para realizarla
     *
     * @param barco Barco que realiza la acci�n
     */
    void accion(Barco barco);

    /**
     * Describe la secuencia l�gica de pasos para llevar a cabo la acci�n de la clase implementado esta interfaz.
     * Por defecto se debe pedir permiso para realizar la acci�n, realizarla una vez obtenido el permiso y notificar
     * el fin del permiso una vez realizada la acci�n
     *
     * @param barco Barco que implementa la interfaz
     */
    default void comporBarco(Barco barco) {
        // Protocolo de entrada : Pide permiso para realizar la acci�n
        permiso(barco);
        // Realiza la acci�n
        accion(barco);
        // Protocolo de salida : Notifica el fin del permiso para realizar la acci�n finalizada
        finPermiso(barco);
    }

}