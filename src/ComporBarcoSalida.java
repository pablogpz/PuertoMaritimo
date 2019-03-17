/**
 * Implementación del comportamiento de salida. Para que un barco pueda salir del puerto debe pedir permiso de salida a la torre de control, salir por la puerta y notificar el fin del permiso de vuelta a la torre de control una vez ha pasado la puerta
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ComporBarcoSalida implements ComporBarco {

    public ComporBarcoSalida() {
        // TODO - implement ComporBarcoSalida.ComporBarcoSalida
    }

    /**
     * Petición de permiso para realizar la acción
     *
     * @param barco
     */
    public void permiso(Barco barco) {
        // TODO - implement ComporBarcoSalida.permiso
    }

    /**
     * Notificación de acción completa y, por tanto, del fin del permiso adquirido para realizar la acción
     *
     * @param barco
     */
    public void finPermiso(Barco barco) {
        // TODO - implement ComporBarcoSalida.finPermiso
    }

    /**
     * Tarea que debe realizar la clase implementando esta interfaz una vez se le conceda el permiso para realizarla
     *
     * @param barco
     */
    public void accion(Barco barco) {
        // TODO - implement ComporBarcoSalida.accion
    }

    /**
     * Describe la secuencia lógica de pasos para llevar a cabo la acción de la clase implementado esta interfaz. Por defecto se debe pedir permiso para realizar la acción, realizarla una vez obtenido el permiso y notificar el fin del permiso una vez realizada la acción
     *
     * @param barco
     */
    public void comporBarco(Barco barco) {
        // TODO - implement ComporBarcoSalida.comporBarco
    }

}