import java.util.concurrent.SynchronousQueue;

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
     * Bandera para indicar si la plataforma está operativa
     */
    private boolean activa;

    private SynchronousQueue<TIPO_CARGAMENTO> esperaAzucar;         // Cola síncrona para bloquear a la grúa de AZUCAR
    private SynchronousQueue<TIPO_CARGAMENTO> esperaHarina;         // Cola síncrona para bloquear a la grúa de HARINA
    private SynchronousQueue<TIPO_CARGAMENTO> esperaSal;            // Cola síncrona para bloquear a la grúa de SAL

    /**
     * Constructor por defecto
     */
    private Plataforma() {
        activa = true;

        // Todas las colas síncronas tienen política de justicia FIFO
        esperaAzucar = new SynchronousQueue<>(true);
        esperaHarina = new SynchronousQueue<>(true);
        esperaSal = new SynchronousQueue<>(true);
    }

    /**
     * Deposita un cargamento siempre que la capacidad de la plataforma lo permita y se lo notifica a la grúa correspondiente.
     *
     * @param barco      Barco mercante
     * @param cargamento Cargamento que deposita en la plataforma
     */
    public void poner(BarcoMercante barco, TIPO_CARGAMENTO cargamento) {
        // Notifica un intento de poner un cargamento en la plataforma
        imprimirConTimestamp("El barco mercante " + barco.getIdentificador() + " intenta añadir un cargamento de "
                + cargamento + " a la plataforma");
        try {
            switch (cargamento) {                                   // Determina a qué cola síncrona debe asignarse cada cargamento
                case AZUCAR:
                    esperaAzucar.put(cargamento);
                    imprimirConTimestamp("Se ha desbloqueado G-azúcar");
                    break;
                case HARINA:
                    esperaHarina.put(cargamento);
                    imprimirConTimestamp("Se ha desbloqueado G-harina");
                    break;
                case SAL:
                    esperaSal.put(cargamento);
                    imprimirConTimestamp("Se ha desbloqueado G-sal");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Notifica el éxito de poner un cargamento en la plataforma
        imprimirConTimestamp("El barco mercante " + barco.getIdentificador() +
                " finalmente ha añadido un cargamento a la plataforma. Cargamentos restantes: " + barco.getCargamentosRestantes());
    }

    /**
     * Una grua intenta coger de la plataforma
     *
     * @param grua Grua que intenta coger de la plataforma
     */
    public void coger(Grua grua) {
        // Notifica la voluntad de una grúa de coger un cargamento
        imprimirConTimestamp("La grúa (" + grua.getTipo() + ") " + grua.getIdentificador() + " está bloqueada");
        try {
            switch (grua.getTipo()) {                               // Determina de qué cola síncrona coger el cargamento según el tipo de grúa
                case AZUCAR:
                    esperaAzucar.take();
                    break;
                case HARINA:
                    esperaHarina.take();
                    break;
                case SAL:
                    esperaSal.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Notifica el éxito de la grúa de coger un cargamento de la plataforma
        if (getActiva())                                            // Comprueba que la plataforma esté operativa
            imprimirConTimestamp("La grúa (" + grua.getTipo().toString() + ") " + grua.getIdentificador() + " vacía la plataforma");
    }

    /**
     * Termina la ejecución de las grúas {@link Grua} asociadas a la plataforma de manera que el proograma no quede bloqueado por
     * esperar indefinidadmente otro cargamento
     */
    public void apagarGruas() {
        setActiva(false);                                           // Apaga la plataforma
        try {
            // Pone un elemento más para desbloquear a las grúas esperando y que se apaguen también
            esperaAzucar.put(TIPO_CARGAMENTO.AZUCAR);
            esperaHarina.put(TIPO_CARGAMENTO.HARINA);
            esperaSal.put(TIPO_CARGAMENTO.SAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * Método modificador del atributo {@link Plataforma:activa}
     *
     * @param activa Nuevo estado de la plataforma
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
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