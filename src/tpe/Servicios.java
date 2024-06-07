package tpe;

import utils.CSVReader;

import java.util.Collections;
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
    private int cantEstados;


    //COMPLEJIDAD O(N) YA QUE O(M+N) = O(N);
    public Servicios(String pathProcesadores, String pathTareas) {
        CSVReader reader = new CSVReader();
        reader.readTasks(pathTareas);
        reader.readProcessors(pathProcesadores);
        tareas = new HashMap<>(reader.getTareas());
        procesadores = new HashMap<>(reader.getProcesadores());
        this.cantEstados = 0;
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
        int tiempoProc = 0;

        LinkedList<Tarea> tareasDisponibles = new LinkedList<>(tareas.values());

        asignarTareasRecursivo(mejorAsignacion, asignacionActual, tareasDisponibles, tiempoRefrigerado, maxCriticas);
        int menorTiempoFinal = calcularTiempoFinal(mejorAsignacion);

        System.out.println("BACKTRACKING");
        Iterator itAsignacion = mejorAsignacion.values().iterator();
        for (String proc: mejorAsignacion.keySet()) {
            System.out.println(proc);
            if (itAsignacion.hasNext()){
                System.out.println(itAsignacion.next().toString());

            }
            System.out.println("Tiempo de procesador " + proc + ": " + this.calcularTiempoProcesador(mejorAsignacion.get(proc)));
            System.out.println();
        }
        System.out.println("Tiempo maximo de ejecucion: " + menorTiempoFinal);
        System.out.println("Cantidad de estados obtenidos: " + cantEstados);

    }

    private void asignarTareasRecursivo(HashMap<String, LinkedList<Tarea>> mejorAsignacion,
                                        HashMap<String, LinkedList<Tarea>> asignacionActual,
                                        LinkedList<Tarea> tareasDisponibles, int tiempoRefrigerado,
                                        int maxCritica) {
        cantEstados++;

        if (tareasDisponibles.isEmpty()) { // Si no hay más tareas
            mejorAsignacion.clear();
            // Asignamos manualmente nuestro HashMap asignacionActual a mejorAsignacion
            // para guarda los valores de forma independiente, evitando cualquier posible
            // interferencia entre las dos estructuras de datos.
            for (HashMap.Entry<String, LinkedList<Tarea>> entry : asignacionActual.entrySet()) {
                LinkedList<Tarea> tareasClonadas = new LinkedList<>(entry.getValue());
                mejorAsignacion.put(entry.getKey(), tareasClonadas);
            }
        } else {
            for (Procesador procesador : procesadores.values()) {
                // Obtenenemos el ID del procesador
                String idProcesador = procesador.getId_procesador();

                // agarramos la tarea, la asignamos a una variable y la borrramos de la lista de tareasDisponibles
                Tarea tareaAsignada = tareasDisponibles.poll();

                if (tareaAsignada != null) {
                    // Obtenemos la lista de tareas asignadas al procesador o creamos una nueva lista si no existe
                    LinkedList<Tarea> tareasAsignadas = asignacionActual.get(idProcesador);

                    if (tareasAsignadas == null) {
                        tareasAsignadas = new LinkedList<>();
                        asignacionActual.put(idProcesador, tareasAsignadas);
                    }

                    // Verificamos si se puede agregar la tarea al procesador en base a las restricciones
                    if (procesador.puedoAgregarTarea(tareasAsignadas, tareaAsignada,
                            tiempoRefrigerado, maxCritica)) {

                        // Agregamos la tarea a la lista de tareas asignadas al procesador
                        tareasAsignadas.add(tareaAsignada);

                        // PODA: verificamos si el tiempo de la asignacion actual es menor
                        // que el tiempo de la mejor asignacion
                        if (mejorAsignacion.isEmpty() || calcularTiempoFinal(asignacionActual)
                                < calcularTiempoFinal(mejorAsignacion)) {
                            cantEstados ++;
                            asignarTareasRecursivo(mejorAsignacion, asignacionActual, tareasDisponibles, tiempoRefrigerado, maxCritica);
                        }

                        tareasAsignadas.removeLast();
                    }

                    // devolvemos la tarea a la lista
                    tareasDisponibles.addFirst(tareaAsignada);
                }
            }
        }
    }

    private int calcularTiempoFinal(HashMap<String, LinkedList<Tarea>> asignacion) {
        int tiempoFinal = 0;

        for (LinkedList<Tarea> tareasProcesador : asignacion.values()) {
            // calculamos el tiempo de cada procesador del HashMap solucion
            int tiempoProcesador = tareasProcesador.stream().mapToInt(Tarea::getTiempoEjecucion).sum();
            // comparamos los tiempos de los procesadores y nos quedamos con el mas alto
            tiempoFinal = Math.max(tiempoFinal, tiempoProcesador);
        }
        return tiempoFinal;
    }

    public void asignarTareasGreedy(int tiempoRefrigerado, int maxCriticas){

        HashMap<String, LinkedList<Tarea>> resultado = new HashMap<>();
        LinkedList<Tarea> tareasDisponibles = new LinkedList<>(tareas.values());
        Collections.sort(tareasDisponibles);
        cantEstados = 0;

        for (Tarea tarea: tareasDisponibles) {
            if (tarea != null) {
                Procesador mejorProcesador = null;

                for (Procesador procesador: procesadores.values()) {
                    // verificamos si el procesador puede asignar la tarea en base a las restricciones
                    if (procesador.puedoAgregarTarea(resultado.get(procesador.getId_procesador()), tarea, tiempoRefrigerado, maxCriticas)){
                        int tiempoActualProc = calcularTiempoProcesador(resultado.get(procesador.getId_procesador()));
                        // verificamos si el tiempo del actual procesador es mejor que el tiempo del mejor procesador
                        if (mejorProcesador == null || tiempoActualProc <
                                calcularTiempoProcesador(resultado.get(mejorProcesador.getId_procesador()))){
                            cantEstados++;
                            mejorProcesador = procesador;
                        }
                    }
                }

                // Obtenemos la lista de tareas asignadas al procesador o creamos una nueva lista si no existe
                if (mejorProcesador != null){
                    LinkedList<Tarea> tareasAsignadas = resultado.get(mejorProcesador.getId_procesador());

                    if (tareasAsignadas == null) {
                        tareasAsignadas = new LinkedList<>();
                        resultado.put(mejorProcesador.getId_procesador(), tareasAsignadas);
                    }
                    tareasAsignadas.add(tarea);
                }
            }
        }

        int menorTiempoFinal = calcularTiempoFinal(resultado);
        System.out.println("GREEDY");

        Iterator itAsignacion = resultado.values().iterator();
        for (Procesador proc: procesadores.values()) {
            System.out.println(proc.getId_procesador());
            if (itAsignacion.hasNext()){
                System.out.println(itAsignacion.next().toString());
            }
            System.out.println("Tiempo de procesador " + proc.getId_procesador() + ": " +this.calcularTiempoProcesador(resultado.get(proc.getId_procesador())));
            System.out.println();
        }

        System.out.println("Tiempo maximo de ejecucion: " + menorTiempoFinal);
        System.out.println("Cantidad de estados obtenidos: " + cantEstados);
    }

    private int calcularTiempoProcesador(LinkedList<Tarea> listaTarea) {
        if (listaTarea == null){ return 0; }
        else {
            return listaTarea.stream().mapToInt(Tarea::getTiempoEjecucion).sum();
        }
    }


}