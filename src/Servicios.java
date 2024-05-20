import utils.CSVReader;
import java.util.ArrayList;


/**
 * NO modificar la interfaz de esta clase ni sus métodos públicos.
 * Sólo se podrá adaptar el nombre de la clase "Tarea" según sus decisiones
 * de implementación.
 */
public class Servicios {

    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas){
        CSVReader reader = new CSVReader();
        reader.readProcessors(pathProcesadores);
        reader.readTasks(pathTareas);
    }

    /*
     * Expresar la complejidad temporal del servicio 1.
     */
    public Tarea servicio1(String ID) {
        return new Tarea();
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     */
    public ArrayList<Tarea> servicio2(boolean esCritica) {
        return new ArrayList<>();
    }

    /*
     * Expresar la complejidad temporal del servicio 3.
     */
    public ArrayList<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return new ArrayList<>();
    }

}