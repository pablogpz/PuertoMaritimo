/**
 * Clase que modela el comportamiento de un contenedor de petróleo que podemos encontrar junto a otros más en la Zona de Repostaje.
 * Cada contenedor tendrá una capacidad que irá variando a medida que los distintos barcos petroleros vayan accediendo a dichos contenedores.
 * Estará definido por una cantidad actual y una capacidad máxima.
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class ContenedorPetroleo {

    public static final int CANT_INICIAL_CONT_PETROLEO = 1000;  // Cantidad inicial de los contenedores de petróleo registrados
    public static final int CANTIDAD_MAX_CONT_PETROLEO = 1000;  // Cantidad de petróleo máxima por contenedor

    /**
     * Cantidad de petróleo en el contenedor
     */
    private int cantidad;
    /**
     * Capacidad máxima de petróleo en el contenedor
     */
    private int maxCapacidad;

    public ContenedorPetroleo(int cantidad, int capacidadMaxima) {
        this.cantidad = cantidad;
        this.maxCapacidad = capacidadMaxima;
    }

    /**
     * Vacía del contenedor una cantidad dada
     *
     * @param cantidad Cantidad a vaciar
     * @return Cantidad vaciada. Si no se puede cubrir la cantidad pedida devuelve 0
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
        setCantidad(getMaxCapacidad());
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