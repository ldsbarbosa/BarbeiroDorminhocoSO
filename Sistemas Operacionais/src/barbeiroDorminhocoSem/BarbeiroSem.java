package barbeiroDorminhocoSem;

import java.util.Random;

/* THE BARBER THREAD */
public class BarbeiroSem extends Thread {
	public BarbeiroSem() {}
	public void run() {
		while(true) {  // runs in an infinite loop
			try {
				BarbeiroDorminhocoSem.clientes.acquire();
				BarbeiroDorminhocoSem.acessoAosAssentos.release();
				BarbeiroDorminhocoSem.numeroDeAssentosLivres++;
				BarbeiroDorminhocoSem.barbeiro.release();
				BarbeiroDorminhocoSem.acessoAosAssentos.release();
				this.cutHair();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	public void cutHair(){
		Random aleatorio = new Random();
		System.out.println("B: O barbeiro está cortando o cabelo.");
		try {
			sleep((long) (4000 + (2000*aleatorio.nextGaussian())));
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}
	}
}
