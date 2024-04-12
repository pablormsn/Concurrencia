package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {
    private Semaphore mutex = new Semaphore(1);
    private int numMediciones = 0;
    private Semaphore esperoPorSensores = new Semaphore(0);
    private Semaphore esperoPorTrabajadores = new Semaphore(0);

    public Mediciones() {

    }

    /**
     * El sensor id deja su medición y espera hasta que el trabajador
     * ha terminado sus tareas
     * 
     * @param id
     * @throws InterruptedException
     */
    public void nuevaMedicion(int id) throws InterruptedException {
        mutex.acquire();
        ++numMediciones;
        System.out.println("Sensor " + id + " deja sus mediciones.");
        if(numMediciones == 3){
            esperoPorSensores.release();
        }
        mutex.release();
        

    }

    /***
     * El trabajador espera hasta que están las tres mediciones
     * 
     * @throws InterruptedException
     */
    public void leerMediciones() throws InterruptedException{
        esperoPorTrabajadores.acquire();
        System.out.println("El trabajador tiene sus mediciones...y empieza sus tareas");

    }

    /**
     * El trabajador indica que ha terminado sus tareas
     */
    public void finTareas() {
        System.out.println("El trabajador ha terminado sus tareas");
        esperoPorTrabajadores.release();
    }

}
