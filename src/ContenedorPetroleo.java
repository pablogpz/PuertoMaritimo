/**
 * TODO Documentación clase ContenedorPetroleo
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ContenedorPetroleo {

    /**
     * Cantidad de petróleo en el contenedor
     */
    private int cantidad;
    /**
     * Capacidad máxima de petróleo en el contenedor
     */
    private int maxCapacidad;

    public ContenedorPetroleo() {
        // TODO - implement ContenedorPetroleo.ContenedorPetroleo
    }

    /**
     * @param cantidad
     * @param capacidadMaxima
     */
    public ContenedorPetroleo(int cantidad, int capacidadMaxima) {
        // TODO - implement ContenedorPetroleo.ContenedorPetroleo
    }

    /**
     * Vacía del contenedor una cantidad dada
     *
     * @param cantidad Cantidad a vaciar
     * @return Cantidad vaciada
     */
    public int vaciar(int cantidad) {
        // TODO - implement ContenedorPetroleo.vaciar
        return 0;
    }

    /**
     * Rellena el contenedor con la capacidad máxima de petróleo
     */
    public void reponer() {
        // TODO - implement ContenedorPetroleo.reponer
    }

    /**
     * Método accesor del atributo cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Método accesor del atributo maxCapacidad
     */
    public int getMaxCapacidad() {
        return maxCapacidad;
    }

    /**
     * Método modificador del atributo capacidad
     *
     * @param cantidad Nueva cantidad de petroleo del contenedor
     */
    private void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Método modificador del atributo maxCapacidad
     *
     * @param maxCapacidad Nueva capacidad máxima de petróleo del contenedor
     */
    public void setMaxCapacidad(int maxCapacidad) {
        this.maxCapacidad = maxCapacidad;
    }

}