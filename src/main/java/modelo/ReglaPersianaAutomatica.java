package modelo;

import java.util.List;

/*
 * Regla 3 - Persiana automatica (patron Strategy)
 * Esta regla la he creado yo para la persiana nueva.
 *
 * Si hay mucha luz (>800 lux) cierra la persiana
 * Si hay poca (<200 lux) la abre para aprovechar la luz natural
 * En caso intermedio la deja a medias
 */
public class ReglaPersianaAutomatica implements Regla {

    @Override
    public void aplicar(List<Sensor> sensores, List<Actuador> actuadores) {

        Sensor sensorLuz = null;
        Actuador persiana = null;

        for (Sensor s : sensores) {
            if (s.getID().equals("light")) {
                sensorLuz = s;
                break;
            }
        }
        for (Actuador a : actuadores) {
            if (a.getID().equals("blind")) {
                persiana = a;
                break;
            }
        }

        if (sensorLuz == null || persiana == null) return;

        double luz = sensorLuz.getValor();

        if (luz > 800.0) {
            persiana.ejecutarAccion("CERRAR");
        } else if (luz < 200.0) {
            persiana.ejecutarAccion("ABRIR");
        } else {
            persiana.ejecutarAccion("MEDIA");
        }
    }

    @Override
    public String getNombre() {
        return "R3. Persiana Automatica";
    }

    @Override
    public String getDescripcion() {
        return "Luz > 800 lux -> CERRAR | Luz < 200 lux -> ABRIR | Otro -> MEDIA";
    }
}
