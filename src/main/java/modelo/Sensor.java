package modelo;

/*
 * Clase abstracta que agrupa la logica comun de todos los sensores.
 * Los sensores concretos heredan de aqui y sobreescriben actualizarValor()
 * para generar sus propios datos (polimorfismo).
 */
public abstract class Sensor implements IDispositivo {

    protected String id;
    protected String nombre;
    protected String unidad;
    protected double valor;

    public Sensor(String id, String nombre, String unidad) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.valor = 0.0;
    }

    // Cada sensor implementa su propia forma de generar valores
    public abstract void actualizarValor();

    public double getValor() { return valor; }

    public void setValor(double valor) { this.valor = valor; }

    public String getUnidad() { return unidad; }

    @Override
    public String getID() { return id; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public String getEstadoActual() {
        return String.format("%.1f %s", valor, unidad);
    }
}
