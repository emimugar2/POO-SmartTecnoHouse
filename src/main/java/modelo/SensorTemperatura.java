package modelo;

import java.util.Random;

// Sensor de temperatura: genera valores entre 15 y 40 grados
public class SensorTemperatura extends Sensor {

    private Random rand = new Random();

    public SensorTemperatura() {
        super("temp", "Sensor de Temperatura", "\u00B0C");
        this.valor = 22.0; // valor inicial razonable
    }

    @Override
    public void actualizarValor() {
        // varia +-1.5 grados respecto al valor actual
        double variacion = (rand.nextDouble() * 3) - 1.5;
        this.valor = Math.round((this.valor + variacion) * 10.0) / 10.0;

        // que no se salga del rango
        if (this.valor < 15.0) this.valor = 15.0;
        if (this.valor > 40.0) this.valor = 40.0;
    }
}

