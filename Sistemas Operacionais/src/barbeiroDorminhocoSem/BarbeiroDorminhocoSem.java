/**
 * Fonte: https://www.cs.helsinki.fi/u/kerola/rio/ohjeet/Java/semaphores/s06e-huhtamaki-merikanto-nevalainen/SleepingBarber.java
 */
package barbeiroDorminhocoSem;

import java.util.Random;
import java.util.concurrent.*;

public class BarbeiroDorminhocoSem extends Thread {

	/* Cria-se os semaforos. Primeiro não há clientes e
	 o barbeiro está dormindo, então chamamos o construtor com o parâmetro
	 0 criando assim semáforos com zero permissões iniciais.
	 Semaphore(1) constrói um semáforo binário(mutex), conforme desejado. */

	public static Semaphore clientes = new Semaphore(0);
	public static Semaphore barbeiro = new Semaphore(0);
	public static Semaphore acessoAosAssentos = new Semaphore(1);

	public static final int CADEIRAS = 5; /* denotamos que o número de cadeiras nesta barbearia é 5. */

	/* criamos o número inteiro numeroDeAssentosLivres para que os clientes
   pode sentar-se em um assento livre ou sair da barbearia, se houver
   não há assentos disponíveis */

	public static int numeroDeAssentosLivres = CADEIRAS;

	public static void main(String args[]) {
		BarbeiroDorminhocoSem barbearia = new BarbeiroDorminhocoSem();
		barbearia.start();  // Começo da simulação
		
	}
	public void run(){
		BarbeiroSem barbeiro = new BarbeiroSem();
		barbeiro.start();

		/* Este método irá criar novos clientes por um tempo */
		Random aleatorio = new Random();
		for (int i=1; i<16; i++) {
			ClienteSem cliente = new ClienteSem(i);
			cliente.start();
			try {
				sleep((long) (2000 + (500*aleatorio.nextGaussian())));
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			};
		}
	} 
}
