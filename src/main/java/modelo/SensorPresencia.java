package modelo;

import java.util.Random;

// Sensor PIR de presencia. Devuelve 1.0 si detecta y 0.0 si no
public class SensorPresencia extends Sensor {

    private Random rand = new Random();

    public SensorPresencia() {
        super("pir", "Sensor de Presencia", "");
        this.valor = 0.0;
    }

    @Override
    public void actualizarValor() {
        // un 40% de probabilidad de que haya alguien
        this.valor = rand.nextDouble() < 0.4 ? 1.0 : 0.0;
    }

    @Override
    public String getEstadoActual() {
        // en vez de mostrar 1.0 o 0.0 mostramos texto
        return valor == 1.0 ? "Detectada" : "No detectada";
    }
}
