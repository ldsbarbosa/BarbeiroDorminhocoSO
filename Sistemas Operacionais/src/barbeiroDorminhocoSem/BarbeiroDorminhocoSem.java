/**
 * Fonte: https://www.cs.helsinki.fi/u/kerola/rio/ohjeet/Java/semaphores/s06e-huhtamaki-merikanto-nevalainen/SleepingBarber.java
 */
package barbeiroDorminhocoSem;

import java.util.Random;
import java.util.concurrent.*;

public class BarbeiroDorminhocoSem extends Thread {

	/* Cria-se os semaforos. Primeiro n�o h� clientes e
	 o barbeiro est� dormindo, ent�o chamamos o construtor com o par�metro
	 0 criando assim sem�foros com zero permiss�es iniciais.
	 Semaphore(1) constr�i um sem�foro bin�rio(mutex), conforme desejado. */

	public static Semaphore clientes = new Semaphore(0);
	public static Semaphore barbeiro = new Semaphore(0);
	public static Semaphore acessoAosAssentos = new Semaphore(1);

	public static final int CADEIRAS = 5; /* denotamos que o n�mero de cadeiras nesta barbearia � 5. */

	/* criamos o n�mero inteiro numeroDeAssentosLivres para que os clientes
   pode sentar-se em um assento livre ou sair da barbearia, se houver
   n�o h� assentos dispon�veis */

	public static int numeroDeAssentosLivres = CADEIRAS;

	public static void main(String args[]) {
		BarbeiroDorminhocoSem barbearia = new BarbeiroDorminhocoSem();
		barbearia.start();  // Come�o da simula��o
		
	}
	public void run(){
		BarbeiroSem barbeiro = new BarbeiroSem();
		barbeiro.start();

		/* Este m�todo ir� criar novos clientes por um tempo */
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
