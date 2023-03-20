package barbeiroDorminhocoSem;

import java.util.Random;

/* THREAD DO CLIENTE */
class ClienteSem extends Thread {
	/* Criamos o identificador, que é um número de identificação exclusivo para cada cliente
 		e um booleano nãoCortado que é usado no loop de espera do cliente */
	int identificador;
	boolean naoCortou=true;
	public ClienteSem(int i) {
		identificador = i;
	}

	public void run() {   
		while (naoCortou) {
			try {
				BarbeiroDorminhocoSem.acessoAosAssentos.acquire();
				if (BarbeiroDorminhocoSem.numeroDeAssentosLivres > 0) {
					System.out.println("C: Cliente " + this.identificador + " acaba de sentar.");
					BarbeiroDorminhocoSem.numeroDeAssentosLivres--;
					BarbeiroDorminhocoSem.clientes.release();
					BarbeiroDorminhocoSem.acessoAosAssentos.release();
					try {
						BarbeiroDorminhocoSem.barbeiro.acquire();
						naoCortou = false;
						this.get_haircut();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}   
				else  {
					System.out.println("C: Não há assentos livres. Cliente " + this.identificador + " saiu da barbearia.");
					BarbeiroDorminhocoSem.acessoAosAssentos.release();
					naoCortou=false; // O cabelo segue não cortado, porém o cliente sairá pois não há onde sentar.
				}
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void get_haircut(){
		Random aleatorio = new Random();
		System.out.println("C: Cliente " + this.identificador + " está tendo seu cabelo cortado.");
		try {
			sleep((long) (5000 + (1000*aleatorio.nextGaussian())));
			System.out.println("C: Cliente " + this.identificador + " recebeu o corte de cabelo e sai da barbearia!");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}