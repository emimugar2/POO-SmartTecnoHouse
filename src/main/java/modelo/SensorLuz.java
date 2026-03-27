package modelo;

import java.util.Random;

// Sensor de luminosidad en lux (0 a 1000)
public class SensorLuz extends Sensor {

    private Random rand = new Random();

    public SensorLuz() {
        super("light", "Sensor de Luz", "lux");
        this.valor = 500.0;
    }

    @Override
    public void actualizarValor() {
        // varia +-100 lux
        double variacion = (rand.nextDouble() * 200) - 100;
        this.valor = Math.round((this.valor + variacion) * 10.0) / 10.0;
        if (this.valor < 0) this.valor = 0;
        if (this.valor > 1000) this.valor = 1000;
    }
}
