package modelo;

// Ventilador con varias velocidades: OFF, ON, LOW, HIGH
public class ActuadorVentilador extends Actuador {

    public ActuadorVentilador() {
        super("fan", "Ventilador Inteligente");
    }

    @Override
    public void ejecutarAccion(String accion) {
        String acc = accion.toUpperCase();
        for (String posible : getAccionesPosibles()) {
            if (posible.equals(acc)) {
                this.estadoActual = acc;
                return;
            }
        }
        throw new IllegalArgumentException(
            "Accion no valida para Ventilador: " + accion);
    }

    @Override
    public String[] getAccionesPosibles() {
        return new String[]{"ON", "OFF", "LOW", "HIGH"};
    }
}
