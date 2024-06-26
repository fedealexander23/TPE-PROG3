package tpe;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Servicios servicios = new Servicios(
                "./src/datasets/Procesadores.csv",
                "./src/datasets/Tareas.csv");

        // Ejemplo de uso de los servicios
        Tarea tarea = servicios.servicio1("T1");
        if (tarea != null) {
            System.out.println("Información de la Tarea T1:");
            System.out.println("ID: " + tarea.getId());
            System.out.println("Nombre: " + tarea.getNombre());
            System.out.println("Tiempo de Ejecución: " + tarea.getTiempoEjecucion());
            System.out.println("Es Crítica: " + tarea.isCritica());
            System.out.println("Prioridad: " + tarea.getPrioridad());
        } else {
            System.out.println("La tarea con ID T1 no fue encontrada.");
        }

        // Ejemplo de uso de servicio2
        List<Tarea> tareasCriticas = servicios.servicio2(true);
        System.out.println("\nTareas Críticas:");
        for (Tarea t : tareasCriticas) {
            System.out.println(t.getId() + " - " + t.getNombre());
        }

        // Ejemplo de uso de servicio3
        // Sin contar los limites inferior y superior
        List<Tarea> tareasEnRango = servicios.servicio3(50, 70);
        System.out.println("\nTareas con Prioridad entre 50 y 70:");
        for (Tarea t : tareasEnRango) {
            System.out.println(t.getId() + " - " + t.getNombre() + " - " + t.getPrioridad());
        }

        System.out.println();

        servicios.asignarTareasBacktracking(1,2);
        System.out.println();
        servicios.asignarTareasGreedy(1,2);
    }
}