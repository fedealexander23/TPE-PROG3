package tpe;

import utils.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    HashMap<String, Tarea> tareas;

    //COMPLEJIDAD O(N) YA QUE O(N) + O(N);
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        reader.readTasks(pathTareas);
        reader.readProcessors(pathProcesadores);
        tareas = new HashMap<>(reader.getTareas());
    }


    //COMPLEJIDAD O(1);
    public Tarea servicio1(String ID) {
        return tareas.get(ID);
    }

    //COMPLEJIDAD O(N) EN DONDE "N" VA A SER EL NUMERO TOTAL DE TAREAS;
    public LinkedList<Tarea> servicio2(boolean esCritica) {
        LinkedList<Tarea> tareasFiltradas = new LinkedList<>();
        for (Tarea tarea : tareas.values()) {
            if (tarea.isCritica() == esCritica) {
                tareasFiltradas.add(tarea);
            }
        }
        return tareasFiltradas;
    }

    //COMPLEJIDAD O(N) EN DONDE "N" VA A SER EL NUMERO TOTAL DE TAREAS;
    public LinkedList<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        LinkedList<Tarea> tareasEnRango = new LinkedList<>();
        for (Tarea tarea : tareas.values()) {
            int prioridad = tarea.getPrioridad();
            if (prioridad >= prioridadInferior && prioridad <= prioridadSuperior) {
                tareasEnRango.add(tarea);
            }
        }
        return tareasEnRango;
    }

}