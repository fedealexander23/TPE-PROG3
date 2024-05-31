package tpe;

import java.time.LocalDate;

public class Procesadores {
    private String id_procesador;
    private String codigo_procesador;
    private Boolean esta_refrigerado;
    private Integer ano_funcionamiento;

    public Procesadores(String id_procesador, String codigo_procesador, Boolean esta_refrigerado, Integer ano_funcionamiento) {
        this.id_procesador = id_procesador;
        this.codigo_procesador = codigo_procesador;
        this.esta_refrigerado = esta_refrigerado;
        this.ano_funcionamiento = ano_funcionamiento;
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

    @Override
    public String toString() {
        return "Procesadores{" +
                "id_procesador='" + id_procesador + '\'' +
                ", codigo_procesador='" + codigo_procesador + '\'' +
                ", esta_refrigerado=" + esta_refrigerado +
                ", ano_funcionamiento=" + ano_funcionamiento +
                '}';
    }
}
