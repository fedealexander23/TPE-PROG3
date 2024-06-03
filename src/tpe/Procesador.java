package tpe;

import java.util.LinkedList;

public class Procesador {
    private String id_procesador;
    private String codigo_procesador;
    private Boolean esta_refrigerado;
    private Integer ano_funcionamiento;
    private LinkedList<Tarea> tares;


    public Procesador(String id_procesador, String codigo_procesador, Boolean esta_refrigerado, Integer ano_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.ano_funcionamiento = ano_funcionamiento;
        this.tares = new LinkedList<>();
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

    public LinkedList<Tarea> getTares(){
        return new LinkedList<>(tares);
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

        // Verificar si el procesador puede manejar una tarea crítica adicional
        if (tareaAsignada.isCritica() && contCritica >= maxCritica) {
            return false;
        }

        // Verificar si el procesador no refrigerado puede manejar el tiempo adicional
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
                ", tares=" + tares +
                '}';
    }
}
