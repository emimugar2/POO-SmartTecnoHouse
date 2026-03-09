package modelo;

import java.util.List;

/*
 * Regla 1 - Ventilacion confortable (patron Strategy)
 *
 * Logica:
 *   - Si temp > 28 grados -> ventilador a HIGH
 *   - Si temp entre 24 y 28 -> ventilador a LOW
 *   - Si temp < 24 -> ventilador OFF
 */
public class ReglaVentilacionConfortable implements Regla {

    @Override
    public void aplicar(List<Sensor> sensores, List<Actuador> actuadores) {

        // Buscar el sensor de temperatura y el ventilador
        Sensor sensorTemp = null;
        Actuador ventilador = null;

        for (Sensor s : sensores) {
            if (s.getID().equals("temp")) {
                sensorTemp = s;
                break;
            }
        }
        for (Actuador a : actuadores) {
            if (a.getID().equals("fan")) {
                ventilador = a;
                break;
            }
        }

        // si no encontramos alguno no hacemos nada
        if (sensorTemp == null || ventilador == null) return;

        double temp = sensorTemp.getValor();

        if (temp > 28.0) {
            ventilador.ejecutarAccion("HIGH");
        } else if (temp >= 24.0) {
            ventilador.ejecutarAccion("LOW");
        } else {
            ventilador.ejecutarAccion("OFF");
        }
    }

    @Override
    public String getNombre() {
        return "R1. Ventilacion Confortable";
    }

    @Override
    public String getDescripcion() {
        return "Temp > 28C -> Ventilador HIGH | 24-28C -> LOW | < 24C -> OFF";
    }
}
