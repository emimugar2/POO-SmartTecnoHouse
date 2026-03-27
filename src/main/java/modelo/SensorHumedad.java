package modelo;

import java.util.Random;

/*
 * Sensor de humedad relativa (nuevo dispositivo que he añadido).
 * Sirve para demostrar que la jerarquia es extensible
 * sin tocar las clases que ya existian (principio abierto/cerrado).
 */
public class SensorHumedad extends Sensor {

    private Random rand = new Random();

    public SensorHumedad() {
        super("humidity", "Sensor de Humedad", "%HR");
        this.valor = 50.0;
    }

    @Override
    public void actualizarValor() {
        double variacion = (rand.nextDouble() * 10) - 5;
        this.valor = Math.round((this.valor + variacion) * 10.0) / 10.0;
        if (this.valor < 20.0) this.valor = 20.0;
        if (this.valor > 90.0) this.valor = 90.0;
    }
}
