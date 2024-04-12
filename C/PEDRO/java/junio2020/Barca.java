import java.util.concurrent.Semaphore;
import java.util.concurrent.*;

public class Barca {

	private boolean barcaLlena = false;
	private boolean viajeRealizado = false;
	private boolean todosBajados = true;
	private int orilla = 1;
	private int numPasajeros = 0;

	/*
	 * El Pasajero id quiere darse una vuelta en la barca desde la orilla pos
	 */
	public  synchronized void subir(int id,int pos) throws InterruptedException{
		while( barcaLlena || !todosBajados || orilla != pos ){
			wait();
		}
		++numPasajeros;
		if(numPasajeros == 3){
			barcaLlena = true;
			notifyAll();
		}
		System.out.println("el pasajero " + id + " de orilla " + pos + " se sube a la barca en orilla " + orilla + ". Pasajeros: " + numPasajeros);
	}	
	
	/*
	 * Cuando el viaje ha terminado, el Pasajero que esta en la barca se baja
	 */
	public  synchronized int bajar(int id) throws InterruptedException{
		while(!viajeRealizado){
			wait();
		}
		--numPasajeros;
		System.out.println("Baja pasajero " + id + ". Pasajeros: " + numPasajeros);
		if(numPasajeros == 0){
			barcaLlena = false;
			todosBajados = true;
			notifyAll();
		}
		return orilla;
	}
	/*
	 * El Capitan espera hasta que se suben 3 pasajeros para comenzar el viaje
	 */
	public  synchronized void esperoSuban() throws InterruptedException{
		while(!barcaLlena){
			wait();
		}
		System.out.println("Viaje empieza");
		todosBajados = false;
	}
	/*
	 * El Capitan indica a los pasajeros que el viaje ha terminado y tienen que bajarse
	 */
	public synchronized  void finViaje() throws InterruptedException{
		System.out.println("Viaje acaba");
		orilla = (orilla+1)%2;
		viajeRealizado = true;
		//barcaLlena = false;
		notifyAll();
	}

}
