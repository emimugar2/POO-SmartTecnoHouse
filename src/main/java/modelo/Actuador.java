package modelo;

/*
 * Clase abstracta para los actuadores.
 * Cada actuador concreto define que acciones puede hacer
 * y como cambia su estado al ejecutarlas.
 */
public abstract class Actuador implements IDispositivo {

    protected String id;
    protected String nombre;
    protected String estadoActual;

    public Actuador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.estadoActual = "OFF"; // todos empiezan apagados
    }

    // Cada actuador valida la accion y cambia su estado
    public abstract void ejecutarAccion(String accion);

    // Devuelve un array con las acciones que acepta este actuador
    public abstract String[] getAccionesPosibles();

    public void setEstadoActual(String estado) {
        this.estadoActual = estado;
    }

    @Override
    public String getID() { return id; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getEstadoActual() { return estadoActual; }
}
