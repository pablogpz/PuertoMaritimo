/**
 * Clase que modela el comportamiento de un contenedor de petr�leo que podemos encontrar junto a otros m�s en la Zona de Repostaje.
 * Cada contenedor tendr� una capacidad que ir� variando a medida que los distintos barcos petroleros vayan accediendo a dichos contenedores.
 * Estar� definido por una cantidad actual y una capacidad m�xima.
 *
 * @author Juan Pablo Garc�a Plaza P�rez
 * @author Jos� �ngel Concha Carrasco
 */
public class ContenedorPetroleo {

    public static final int CANT_INICIAL_CONT_PETROLEO = 1000;  // Cantidad inicial de los contenedores de petr�leo registrados
    public static final int CANTIDAD_MAX_CONT_PETROLEO = 1000;  // Cantidad de petr�leo m�xima por contenedor

    /**
     * Cantidad de petr�leo en el contenedor
     */
    private int cantidad;
    /**
     * Capacidad m�xima de petr�leo en el contenedor
     */
    private int maxCapacidad;

    public ContenedorPetroleo(int cantidad, int capacidadMaxima) {
        this.cantidad = cantidad;
        this.maxCapacidad = capacidadMaxima;
    }

    /**
     * Vac�a del contenedor una cantidad dada
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
     * Rellena el contenedor con la capacidad m�xima de petr�leo
     */
    public void reponer() {
        setCantidad(getMaxCapacidad());
    }

    /**
     * M�todo accesor del atributo cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * M�todo accesor del atributo maxCapacidad
     */
    public int getMaxCapacidad() {
        return maxCapacidad;
    }

    /**
     * M�todo modificador del atributo capacidad
     *
     * @param cantidad Nueva cantidad de petroleo del contenedor
     */
    private void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * M�todo modificador del atributo maxCapacidad
     *
     * @param maxCapacidad Nueva capacidad m�xima de petr�leo del contenedor
     */
    public void setMaxCapacidad(int maxCapacidad) {
        this.maxCapacidad = maxCapacidad;
    }

}