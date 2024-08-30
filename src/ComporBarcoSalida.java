/**
 * Implementación del comportamiento de salida. Para que un barco pueda salir del puerto debe pedir permiso de salida
 * a la torre de control, salir por la puerta y notificar el fin del permiso de vuelta a la torre de control
 * una vez ha pasado la puerta
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ComporBarcoSalida implements ComporBarco {

    public ComporBarcoSalida() {
    }

    /**
     * {@inheritDoc}
     */
    public void permiso(Barco barco) {
        TorreControl.recuperarInstancia().permisoSalida(barco);
    }

    /**
     * {@inheritDoc}
     */
    public void finPermiso(Barco barco) {
        TorreControl.recuperarInstancia().finSalida(barco);
    }

    /**
     * {@inheritDoc}
     */
    public void accion(Barco barco) {
        Puerta.recuperarInstancia().salir(barco);
    }

}