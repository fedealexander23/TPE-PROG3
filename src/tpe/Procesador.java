package tpe;

import java.util.LinkedList;

public class Procesador {
    private String id_procesador;
    private String codigo_procesador;
    private Boolean esta_refrigerado;
    private Integer ano_funcionamiento;
    private LinkedList<Tarea> tareas;


    public Procesador(String id_procesador, String codigo_procesador, Boolean esta_refrigerado, Integer ano_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.ano_funcionamiento = ano_funcionamiento;
        this.tareas = new LinkedList<>();
    }

    public String getId_procesador() {
        return id_procesador;
    }

    public String getCodigo_procesador() {
        return codigo_procesador;
    }

    public Boolean getEsta_refrigerado() {
        return esta_refrigerado;
    }

    public Integer getAno_funcionamiento() {
        return ano_funcionamiento;
    }

    public LinkedList<Tarea> getTareas(){
        return new LinkedList<>(tareas);
    }

    public int getTiempo(){
        int cont = 0;
        for (Tarea t: tareas) {
            cont += t.getTiempoEjecucion();
        }
        return cont;
    }

    public boolean puedoAgregarTarea(LinkedList<Tarea> listaTareas,
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

        // Verificamos si el procesador no supera la cantidad de tareas criticas
        if (tareaAsignada.isCritica() && contCritica >= maxCritica) {
            return false;
        }

        // Verificamos si el procesador es no refrigerado y si esta dentro del parametro necesario
        if (!this.getEsta_refrigerado() && (tiempoTotal + tareaAsignada.getTiempoEjecucion() > tiempoRefrigerado)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Procesador{" +
                "id_procesador='" + id_procesador + '\'' +
                ", codigo_procesador='" + codigo_procesador + '\'' +
                ", esta_refrigerado=" + esta_refrigerado +
                ", ano_funcionamiento=" + ano_funcionamiento +
                ", tares=" + tareas +
                '}';
    }
}
