package modelo;

/*
 * Persiana motorizada (nuevo actuador que he añadido yo).
 * Puede estar ABRIR (abierta), CERRAR (cerrada) o MEDIA (a medias).
 * Demuestra que se pueden añadir actuadores nuevos facilmente.
 */
public class ActuadorPersiana extends Actuador {

    public ActuadorPersiana() {
        super("blind", "Persiana Motorizada");
        this.estadoActual = "CERRAR"; // empieza cerrada
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
            "Accion no valida para Persiana: " + accion + ". Usa ABRIR, CERRAR o MEDIA");
    }

    @Override
    public String[] getAccionesPosibles() {
        return new String[]{"ABRIR", "CERRAR", "MEDIA"};
    }
}
