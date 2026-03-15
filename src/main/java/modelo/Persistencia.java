package modelo;

import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/*
 * Clase para guardar y cargar el estado del sistema en un fichero JSON.
 * Lo hago "a mano" con StringBuilder porque no queria meter librerias externas,
 * aunque se podria usar Gson o similar. Para lo que necesitamos funciona bien.
 */
public class Persistencia {

    private static final String FICHERO = "datos/estado.json";

    // Guarda sensores, actuadores y reglas activas en el JSON
    public static void guardarEstado(List<Sensor> sensores, List<Actuador> actuadores,
                                      Map<String, Boolean> reglasActivas) {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        // sensores
        sb.append("  \"sensores\": {\n");
        for (int i = 0; i < sensores.size(); i++) {
            Sensor s = sensores.get(i);
            sb.append(String.format("    \"%s\": %.1f", s.getID(), s.getValor()));
            if (i < sensores.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  },\n");

        // actuadores
        sb.append("  \"actuadores\": {\n");
        for (int i = 0; i < actuadores.size(); i++) {
            Actuador a = actuadores.get(i);
            sb.append(String.format("    \"%s\": \"%s\"", a.getID(), a.getEstadoActual()));
            if (i < actuadores.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  },\n");

        // reglas
        sb.append("  \"reglasActivas\": {\n");
        int cont = 0;
        for (Map.Entry<String, Boolean> entry : reglasActivas.entrySet()) {
            sb.append(String.format("    \"%s\": %s", entry.getKey(), entry.getValue()));
            cont++;
            if (cont < reglasActivas.size()) sb.append(",");
            sb.append("\n");
        }
        sb.append("  }\n");
        sb.append("}\n");

        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO))) {
            pw.print(sb.toString());
        } catch (IOException e) {
            System.err.println("Error al guardar: " + e.getMessage());
        }
    }

    // Carga el estado desde el JSON. Devuelve false si no existe el fichero
    public static boolean cargarEstado(List<Sensor> sensores, List<Actuador> actuadores,
                                        Map<String, Boolean> reglasActivas) {

        File f = new File(FICHERO);
        if (!f.exists()) return false;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            // leer todo el fichero de golpe
            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea.trim());
            }
            String json = contenido.toString();

            // restaurar sensores
            Map<String, String> mapSensores = extraerSeccion(json, "sensores");
            for (Sensor s : sensores) {
                String val = mapSensores.get(s.getID());
                if (val != null) {
                    s.setValor(Double.parseDouble(val));
                }
            }

            // restaurar actuadores
            Map<String, String> mapActuadores = extraerSeccion(json, "actuadores");
            for (Actuador a : actuadores) {
                String estado = mapActuadores.get(a.getID());
                if (estado != null) {
                    a.setEstadoActual(estado.replace("\"", ""));
                }
            }

            // restaurar reglas
            Map<String, String> mapReglas = extraerSeccion(json, "reglasActivas");
            for (Map.Entry<String, String> entry : mapReglas.entrySet()) {
                reglasActivas.put(entry.getKey(), Boolean.parseBoolean(entry.getValue()));
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error al cargar: " + e.getMessage());
            return false;
        }
    }

    // Metodo auxiliar para parsear una seccion del JSON
    // No es el parser mas robusto del mundo pero para nuestro formato funciona
    private static Map<String, String> extraerSeccion(String json, String seccion) {
        Map<String, String> mapa = new HashMap<>();

        String clave = "\"" + seccion + "\":";
        int inicio = json.indexOf(clave);
        if (inicio == -1) return mapa;

        inicio = json.indexOf("{", inicio) + 1;
        int fin = json.indexOf("}", inicio);
        if (fin == -1) return mapa;

        String bloque = json.substring(inicio, fin);
        String[] pares = bloque.split(",");

        for (String par : pares) {
            par = par.trim();
            if (par.isEmpty()) continue;
            String[] kv = par.split(":", 2);
            if (kv.length == 2) {
                String k = kv[0].trim().replace("\"", "");
                String v = kv[1].trim();
                mapa.put(k, v);
            }
        }
        return mapa;
    }
}
