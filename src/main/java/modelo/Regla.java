package modelo;

import java.util.List;

/*
 * Interfaz para el patron Strategy.
 * Cada regla de automatizacion es un objeto independiente que
 * implementa esta interfaz. Asi se pueden añadir reglas nuevas
 * sin modificar el codigo que las ejecuta.
 */
public interface Regla {

    // Evalua sensores y actua sobre los actuadores segun la logica de la regla
    void aplicar(List<Sensor> sensores, List<Actuador> actuadores);

    String getNombre();

    String getDescripcion();
}
