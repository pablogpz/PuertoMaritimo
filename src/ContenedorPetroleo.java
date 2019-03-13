/**
 * TODO Documentación clase ContenedorPetroleo
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ContenedorPetroleo {

    private static final int CANTIDAD_MAX_PETROLEO = 1000;      // Cantidad de petróleo máxima por contenedor

    private int cantidad;               // Cantidad de petróleo en el contenedor
    private int maxCapacidad;           // Capacidad máxima de petróleo en el contenedor

    /**
     * @param cantidad
     * @param capacidadMaxima
     */
    public ContenedorPetroleo(int cantidad, int capacidadMaxima) {
        this.cantidad = cantidad;
        this.maxCapacidad = capacidadMaxima;
    }

    /**
     * Vacía del contenedor una cantidad dada
     *
     * @param cantidad Cantidad a vaciar
     * @return Cantidad vaciada
     */
    public int vaciar(int cantidad) {
        if (getCantidad() >= cantidad) {
            setCantidad(getCantidad() - cantidad);
            return cantidad;
        } else {
            return 0;
        }
    }

    /**
     * Rellena el contenedor con la capacidad máxima de petróleo
     */
    public void reponer() {
        setCantidad(CANTIDAD_MAX_PETROLEO);
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