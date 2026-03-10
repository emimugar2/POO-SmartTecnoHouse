package modelo;

import java.util.List;

/*
 * Regla 2 - Iluminacion automatica (patron Strategy)
 *
 * Si hay alguien en la habitacion Y hay poca luz -> encender bombilla
 * Si no hay nadie o hay luz suficiente -> apagar bombilla
 */
public class ReglaIluminacionAutomatica implements Regla {

    @Override
    public void aplicar(List<Sensor> sensores, List<Actuador> actuadores) {

        Sensor sensorLuz = null;
        Sensor sensorPir = null;
        Actuador bombilla = null;

        for (Sensor s : sensores) {
            if (s.getID().equals("light")) sensorLuz = s;
            if (s.getID().equals("pir"))   sensorPir = s;
        }
        for (Actuador a : actuadores) {
            if (a.getID().equals("bulb")) {
                bombilla = a;
                break;
            }
        }

        if (sensorLuz == null || sensorPir == null || bombilla == null) return;

        boolean hayPresencia = sensorPir.getValor() == 1.0;
        boolean pocaLuz = sensorLuz.getValor() < 300.0;

        if (hayPresencia && pocaLuz) {
            bombilla.ejecutarAccion("ON");
        } else {
            bombilla.ejecutarAccion("OFF");
        }
    }

    @Override
    public String getNombre() {
        return "R2. Iluminacion Automatica";
    }

    @Override
    public String getDescripcion() {
        return "Presencia + Luz < 300 lux -> Bombilla ON | Sin presencia o luz alta -> OFF";
    }
}
