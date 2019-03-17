/**
 * Implementación del comportamiento de entrada. Para que un barco pueda entrar en el puerto debe pedir permiso de entrada
 * a la torre de control, entrar por la puerta y notificar el fin del permiso de vuelta a la torre de control una vez
 * ha pasado la puerta
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ComporBarcoEntrada implements ComporBarco {

    public ComporBarcoEntrada() {
    }

    /**
     * {@inheritDoc}
     */
    public void permiso(Barco barco) {
        TorreControl.recuperarInstancia().permisoEntrada(barco);
    }

    /**
     * {@inheritDoc}
     */
    public void finPermiso(Barco barco) {
        TorreControl.recuperarInstancia().finEntrada(barco);
    }

    /**
     * {@inheritDoc}
     */
    public void accion(Barco barco) {
        Puerta.recuperarInstancia().entrar(barco);
    }

}