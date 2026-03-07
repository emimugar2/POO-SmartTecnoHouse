package modelo;

// Bombilla inteligente: solo puede estar ON o OFF
public class ActuadorBombilla extends Actuador {

    public ActuadorBombilla() {
        super("bulb", "Bombilla Inteligente");
    }

    @Override
    public void ejecutarAccion(String accion) {
        String acc = accion.toUpperCase();
        // comprobamos que la accion sea valida
        for (String posible : getAccionesPosibles()) {
            if (posible.equals(acc)) {
                this.estadoActual = acc;
                return;
            }
        }
        throw new IllegalArgumentException(
            "Accion no valida para Bombilla: " + accion + ". Usa ON o OFF");
    }

    @Override
    public String[] getAccionesPosibles() {
        return new String[]{"ON", "OFF"};
    }
}
