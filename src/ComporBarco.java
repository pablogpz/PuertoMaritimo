/**
 * Interfaz que modela el comportamiento común a barcos de entrada (entran en el puerto) y barcos de salida (salen del puerto). Por defecto un barco para poder realizar su acción ('accion()') debe pedir permiso para hacerla (mediante 'permiso()') y, una vez que la ha llevado a cabo, notificarlo mediante 'finPermiso()'
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
     * Describe la secuencia lógica de pasos para llevar a cabo la acción de la clase implementado esta interfaz. Por defecto se debe pedir permiso para realizar la acción, realizarla una vez obtenido el permiso y notificar el fin del permiso una vez realizada la acción
     *
     * @param barco Barco que implementa la interfaz
     */
    void comporBarco(Barco barco);

}