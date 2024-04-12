package aseosJustos;

import java.util.concurrent.Semaphore;

public class Aseos {
	int numClientes = 0;
	boolean enLimpieza = false;
	Semaphore mutex = new Semaphore(1, true);
	Semaphore limpiando = new Semaphore(1, true); 
	Semaphore BloquearClientes = new Semaphore(1, true);


	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o
	 * está esperando para poder limpiar los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		if(enLimpieza == true){
			BloquearClientes.acquire();
		}
		mutex.acquire();
		++numClientes;
		if(numClientes == 1){
			limpiando.acquire();
		}
		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: " + numClientes);
		mutex.release();

	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();
		--numClientes;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: " + numClientes);
		if(numClientes == 0){
			limpiando.release();
		}
		mutex.release();

	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que
	 * no
	 * haya ningún cliente.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		BloquearClientes.acquire();
		enLimpieza = true;
		System.out.println("			El equipo de limpieza quiere entrar");
		limpiando.acquire();
		System.out.println("El equipo de limpieza está trabajando.");
		

	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public void saleEquipoLimpieza() throws InterruptedException {
		System.out.println("El equipo de limpieza ha terminado.");
		BloquearClientes.release();
		enLimpieza = false;
		limpiando.release(); 
	}
}