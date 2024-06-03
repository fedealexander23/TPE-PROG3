package tpe;

import utils.CSVReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    private HashMap<String, Tarea> tareas;
    private HashMap<String, Procesador> procesadores;


    //COMPLEJIDAD O(N) YA QUE O(M+N) = O(N);
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        reader.readTasks(pathTareas);
        reader.readProcessors(pathProcesadores);
        tareas = new HashMap<>(reader.getTareas());
        procesadores = new HashMap<>(reader.getProcesadores());
    }


    //COMPLEJIDAD O(1);
    public Tarea servicio1(String ID) {
        return tareas.get(ID);
    }

    public Procesador servicio1p(String ID) {
        return procesadores.get(ID);
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

    public void asignarTareasBacktracking(int tiempoRefrigerado, int maxCriticas) {
        HashMap<String, LinkedList<Tarea>> mejorAsignacion = new HashMap<>();
        HashMap<String, LinkedList<Tarea>> asignacionActual = new HashMap<>();

        LinkedList<Tarea> tareasDisponibles = new LinkedList<>(tareas.values());

        asignarTareasRecursivo(mejorAsignacion, asignacionActual, tareasDisponibles, tiempoRefrigerado, maxCriticas);
        int menorTiempoFinal = calcularTiempoFinal(mejorAsignacion);
        System.out.println(menorTiempoFinal);

        Iterator itAsignacion = mejorAsignacion.values().iterator();
        for (String proc: mejorAsignacion.keySet()) {
            System.out.println(proc);
            if (itAsignacion.hasNext()){
                System.out.println(itAsignacion.next().toString());
            }
        }

    }

    private void asignarTareasRecursivo(HashMap<String, LinkedList<Tarea>> mejorAsignacion,
                                        HashMap<String, LinkedList<Tarea>> asignacionActual,
                                        LinkedList<Tarea> tareasDisponibles, int tiempoRefrigerado,
                                        int maxCritica) {

        if (tareasDisponibles.isEmpty()) { // Si no hay más tareas
            int tiempoFinal = calcularTiempoFinal(asignacionActual); // Calculo el tiempo
            if (mejorAsignacion.isEmpty() || tiempoFinal < calcularTiempoFinal(mejorAsignacion)) {
                mejorAsignacion.clear();

                // Asignamos manualmente nuestro HashMap asignacionActual a mejorAsignacion
                // para guarda los valores de forma independiente, evitando cualquier posible
                // interferencia entre las dos estructuras de datos.

                for (HashMap.Entry<String, LinkedList<Tarea>> entry : asignacionActual.entrySet()) {
                    LinkedList<Tarea> tareasClonadas = new LinkedList<>(entry.getValue());
                    mejorAsignacion.put(entry.getKey(), tareasClonadas);
                }
            }
        } else {
            for (Procesador procesador : procesadores.values()) {
                // Obtener el ID del procesador
                String idProcesador = procesador.getId_procesador();

                // AGARRAMOS LA TAREA Y LA ELIMINAMOS DE LA LISTA DE TAREAS DISPONIBLES
                Tarea tareaAsignada = tareasDisponibles.poll();

                if (tareaAsignada != null) {
                    // Obtener la lista de tareas asignadas al procesador o crear una nueva lista si no existe
                    LinkedList<Tarea> tareasAsignadas = asignacionActual.get(idProcesador);

                    if (tareasAsignadas == null) {
                        tareasAsignadas = new LinkedList<>();
                        asignacionActual.put(idProcesador, tareasAsignadas);
                    }

                    // Verificar si se puede agregar la tarea al procesador
                    if (puedoAgregarTarea(tareasAsignadas, procesador, tareaAsignada,
                            tiempoRefrigerado, maxCritica)) {
                        // Agregar la tarea a la lista de tareas asignadas al procesador
                        tareasAsignadas.add(tareaAsignada);

                        // Llamada recursiva
                        asignarTareasRecursivo(mejorAsignacion, asignacionActual, tareasDisponibles, tiempoRefrigerado, maxCritica);

                        // Deshacer la asignación
                        tareasAsignadas.removeLast();
                    }

                    // Volver a añadir la tarea al inicio de la lista
                    tareasDisponibles.addFirst(tareaAsignada);
                }
            }
        }
    }

    private int calcularTiempoFinal(HashMap<String, LinkedList<Tarea>> asignacion) {
        int tiempoFinal = 0;
        for (LinkedList<Tarea> tareasProcesador : asignacion.values()) {
            int tiempoProcesador = tareasProcesador.stream().mapToInt(Tarea::getTiempoEjecucion).sum();
            tiempoFinal = Math.max(tiempoFinal, tiempoProcesador);
        }
        return tiempoFinal;
    }

    private boolean puedoAgregarTarea(LinkedList<Tarea> listaTareas, Procesador procesador,
                                      Tarea tareaAsignada, int tiempoRefrigerado, int maxCritica) {
        if (listaTareas == null) {
            listaTareas = new LinkedList<>();
        }

        int contCritica = 0;
        int tiempoTotal = 0;

        for (Tarea tarea : listaTareas) {
            tiempoTotal += tarea.getTiempoEjecucion();
            if (tarea.isCritica()) {
                contCritica++;
            }
        }

        // Verificar si el procesador puede manejar una tarea crítica adicional
        if (tareaAsignada.isCritica() && contCritica >= maxCritica) {
            return false;
        }

        // Verificar si el procesador no refrigerado puede manejar el tiempo adicional
        if (!procesador.getEsta_refrigerado() && (tiempoTotal + tareaAsignada.getTiempoEjecucion() > tiempoRefrigerado)) {
            return false;
        }

        return true;
    }


}