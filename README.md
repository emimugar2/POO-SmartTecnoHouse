# POO-SmartTecnoHouse

**Sistema de control domótico IoT** para la empresa Smart TecnoHouse SA.

Proyecto de la asignatura Programación Orientada a Objetos. Se trata de una aplicación Java con interfaz gráfica (Swing) que permite monitorizar y controlar los dispositivos de una vivienda inteligente.

## Arquitectura

El proyecto sigue el patrón **Modelo-Vista-Controlador (MVC)**:

- `src/main/java/modelo/` → Clases de dominio (sensores, actuadores, reglas). No importan nada de Swing.
- `src/main/java/vista/` → Interfaz gráfica con Java Swing.
- `src/main/java/controlador/` → Lógica de control, conecta modelo y vista.

## Patrones de diseño utilizados

- **Strategy**: Para las reglas de automatización (cada regla es un objeto intercambiable).
- **Singleton**: Para la clase principal `SmartTecnoHouse` y el servicio de logging.
- **MVC**: Para toda la estructura de la aplicación.

## Dispositivos

| Tipo | Dispositivos |
|------|-------------|
| Sensores | Temperatura, Luz, Presencia, **Humedad** (nuevo) |
| Actuadores | Bombilla, Ventilador, **Persiana Motorizada** (nuevo) |

## Cómo compilar y ejecutar

```bash
javac -d out -sourcepath src/main/java src/main/java/modelo/*.java src/main/java/vista/*.java src/main/java/controlador/*.java src/main/java/Main.java

java -cp out Main
```

## Otros ficheros

- `datos/estado.json` → Fichero de persistencia con el estado guardado
- `logs/actuators.log` → Registro de acciones de los actuadores
- `docs/` → Documentación Javadoc

## Autor

Emilio Martínez Varela
