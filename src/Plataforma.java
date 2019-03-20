import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementa el patrón de diseño Singleton. Esta entidad encapsula toda la lógica de sincronización relacionada con el
 * paso 6. Los barcos mercantes deben descargar sus contenedores en una plataforma, que solo puede albergar un contenedor,
 * solo uno a la vez. Los contenedores serán entonces recogidos por su grúa pertinente. Las grúas siempre intentan coger
 * más contenedores hasta que son apagadas y solo pueden coger un contenedor si está en la plataforma
 *
 * @author Juan Pablo García Plaza Pérez
 * @author José Ángel Concha Carrasco
 */
public class Plataforma {

    /**
     * Instancia Singleton de la plataforma
     */
    private static Plataforma instancia = null;
    /**
     * Cargamento ocupando la plataforma
     */
    private TIPO_CARGAMENTO almacenado;
    /**
     * Bandera para indicar si la plataforma está operativa
     */
    private boolean activa;

    private Lock monitor;
    private Condition esperaAzucar;                 // Variable de espera para las grúas de Azúcar
    private Condition esperaHarina;                 // Variable de espera para las grúas de Harina
    private Condition esperaSal;                    // Variable de espera para las grúas de Sal
    private Condition esperaMercante;               // Varible de espera para los barcos mercantes

    /**
     * Constructor por defecto
     */
    private Plataforma() {
        activa = true;
        almacenado = null;
        monitor = new ReentrantLock(true);

        esperaAzucar = monitor.newCondition();
        esperaHarina = monitor.newCondition();
        esperaSal = monitor.newCondition();
        esperaMercante = monitor.newCondition();
    }

    /**
     * Deposita un cargamento siempre que la capacidad de la plataforma lo permita y se lo notifica a la grúa correspondiente.
     *
     * @param barco      Barco mercante
     * @param cargamento Cargamento que deposita en la plataforma
     */
    public void poner(BarcoMercante barco, TIPO_CARGAMENTO cargamento) {
        monitor.lock();
        try {
            // Protocolo de entrada
            while (getAlmacenado() != null) {
                imprimirConTimestamp("El barco mercante " + barco.getIdentificador() + " está bloqueado por la plataforma");
                esperaMercante.await();
            }
            // Acción
            setAlmacenado(cargamento);
            imprimirConTimestamp("El barco mercante " + barco.getIdentificador() + " añade un cargamento de "
                    + cargamento + " a la plataforma");
            // Protocolo de salida: Desbloquea únicamente a la grúa bloqueada que corresponda con el cargamento depositado
            switch (cargamento) {
                case AZUCAR:
                    esperaAzucar.signal();
                    imprimirConTimestamp("Se ha desbloqueado G-azúcar");
                    break;
                case HARINA:
                    esperaHarina.signal();
                    imprimirConTimestamp("Se ha desbloqueado G-harina");
                    break;
                case SAL:
                    esperaSal.signal();
                    imprimirConTimestamp("Se ha desbloqueado G-sal");
            }
            imprimirConTimestamp("El barco mercante " + barco.getIdentificador() +
                    " finalmente ha añadido un cargamento a la plataforma. Cargamentos restantes: " + barco.getCargamentosRestantes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Una grua intenta coger de la plataforma
     *
     * @param grua Grua que intenta coger de la plataforma
     */
    public void coger(Grua grua) {
        monitor.lock();
        try {
            // Protocolo de entrada: Se bloquea si la plataforma no está activa o no hay ningun cargamento en la plataforma o el cargamento no coincide con la grua
            while (getActiva() && (getAlmacenado() == null || getAlmacenado() != grua.getTipo())) {
                imprimirConTimestamp("La grúa (" + grua.getTipo() + ") " + grua.getIdentificador() + " está bloqueada");
                switch (grua.getTipo()) {
                    case AZUCAR:
                        esperaAzucar.await();
                        break;
                    case HARINA:
                        esperaHarina.await();
                        break;
                    case SAL:
                        esperaSal.await();
                }
            }
            if (getActiva()) {  // Comprueba que la plataforma sigue operativa al retormar el control
                // Acción: consiste en quitar el cargamento de la plataforma
                setAlmacenado(null);
                imprimirConTimestamp("La grúa (" + grua.getTipo().toString() + ") " + grua.getIdentificador() + " vacía la plataforma");
                // Protocolo de salida: consiste en notificárselo al barco mercante por si puediese estar bloqueado
                esperaMercante.signal();
                imprimirConTimestamp("La grúa (" + grua.getTipo().toString() + ") " + grua.getIdentificador() + " finalmente ha vaciado la plataforma");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            monitor.unlock();
        }
    }

    /**
     * Método accesor del atributo {@link Plataforma:almacenado}
     *
     * @return El cargamento almacenado
     */
    public TIPO_CARGAMENTO getAlmacenado() {
        return almacenado;
    }

    /**
     * Método accesor del atributo {@link Plataforma:nBarcosMercantes}
     *
     * @return El cargamento almacenado
     */
    public boolean getActiva() {
        return activa;
    }

    /**
     * Método modificador del atributo {@link Plataforma:almacenado}
     *
     * @param almacenado Nuevo cargamento en la plataforma
     */
    private void setAlmacenado(TIPO_CARGAMENTO almacenado) {
        this.almacenado = almacenado;
    }

    /**
     * Método modificador del atributo {@link Plataforma:activa}
     *
     * @param activa Nuevo estado de la plataforma
     */
    public void setActiva(Boolean activa) {
        monitor.lock();
        try {
            this.activa = activa;
            if (!getActiva()) {             // Desbloquea a las grúas bloqueadas esperando por cargamentos
                esperaAzucar.signal();
                esperaHarina.signal();
                esperaSal.signal();
            }
        } finally {
            monitor.unlock();
        }
    }

    /**
     * @return Instancia Singleton de la Plataforma
     */
    public synchronized static Plataforma recuperarInstancia() {
        if (instancia == null)
            instancia = new Plataforma();

        return instancia;
    }

    /**
     * Imprime un mensaje con marca de tiempo por consola en una línea
     *
     * @param mensaje Mensaje a imprimir
     */
    private void imprimirConTimestamp(String mensaje) {
        System.out.println("\t\t[" + System.currentTimeMillis() + "] " + mensaje);
    }
}