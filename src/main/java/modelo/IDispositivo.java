package modelo;

// Interfaz base que tienen que implementar todos los dispositivos (sensores y actuadores)
public interface IDispositivo {

    String getID();        // id unico, por ejemplo "temp" o "bulb"
    String getNombre();    // nombre para mostrar en la interfaz
    String getEstadoActual(); // texto con el estado actual del dispositivo
}
