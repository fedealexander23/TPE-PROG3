package tpe;

import utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    HashMap<String, Tarea> tareas;

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        reader.readTasks(pathTareas);
        reader.readProcessors(pathProcesadores);
        tareas = new HashMap<>(reader.getTareas());
    }

    /*
     * Expresar la complejidad temporal del servicio 1.
     */

    // Servicio 1: Obtener toda la información de la tarea asociada a un ID dado
    public Tarea servicio1(String ID) {
        return tareas.get(ID);
    }

    // Servicio 2: Obtener el listado de tareas críticas o no críticas según el parámetro esCritica
    public ArrayList<Tarea> servicio2(boolean esCritica) {
        ArrayList<Tarea> tareasFiltradas = new ArrayList<>();
        for (Tarea tarea : tareas.values()) {
            if (tarea.isCritica() == esCritica) {
                tareasFiltradas.add(tarea);
            }
        }
        return tareasFiltradas;
    }

    // Servicio 3: Obtener todas las tareas entre dos niveles de prioridad dados
    public ArrayList<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        ArrayList<Tarea> tareasEnRango = new ArrayList<>();
        for (Tarea tarea : tareas.values()) {
            int prioridad = tarea.getPrioridad();
            if (prioridad >= prioridadInferior && prioridad <= prioridadSuperior) {
                tareasEnRango.add(tarea);
            }
        }
        return tareasEnRango;
    }

}