package tpe;

public class Tarea implements Comparable<Tarea>{

    private String id_tarea;
    private String nombre_tarea;
    private Integer tiempo_ejecucion;
    private Boolean es_critica;
    private Integer nivel_prioridad;

    public Tarea(String id_tarea, String nombre_tarea, Integer tiempo_ejecucion, Boolean es_critica, Integer nivel_prioridad) {
        this.id_tarea = id_tarea;
        this.nombre_tarea = nombre_tarea;
        this.tiempo_ejecucion = tiempo_ejecucion;
        this.es_critica = es_critica;
        this.nivel_prioridad = nivel_prioridad;
    }

    public String getId() {
        return id_tarea;
    }

    public String getNombre() {
        return nombre_tarea;
    }

    public Integer getTiempoEjecucion() {
        return tiempo_ejecucion;
    }

    public Boolean isCritica() {
        return es_critica;
    }

    public Integer getPrioridad() {
        return nivel_prioridad;
    }

    @Override
    public String toString() {
        return id_tarea + " " + tiempo_ejecucion + " " + isCritica();
    }
    @Override
    public int compareTo(Tarea otraTarea) {
        // Ordenamos por prioridad de mayor a menor
        return Integer.compare(otraTarea.getTiempoEjecucion(), this.getTiempoEjecucion());
    }
}
