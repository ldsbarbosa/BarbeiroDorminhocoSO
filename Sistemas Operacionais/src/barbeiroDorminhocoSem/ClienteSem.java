package barbeiroDorminhocoSem;

import java.util.Random;

/* THREAD DO CLIENTE */
class ClienteSem extends Thread {
	/* Criamos o identificador, que � um n�mero de identifica��o exclusivo para cada cliente
 		e um booleano n�oCortado que � usado no loop de espera do cliente */
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
					System.out.println("C: N�o h� assentos livres. Cliente " + this.identificador + " saiu da barbearia.");
					BarbeiroDorminhocoSem.acessoAosAssentos.release();
					naoCortou=false; // O cabelo segue n�o cortado, por�m o cliente sair� pois n�o h� onde sentar.
				}
			}
			catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void get_haircut(){
		Random aleatorio = new Random();
		System.out.println("C: Cliente " + this.identificador + " est� tendo seu cabelo cortado.");
		try {
			sleep((long) (5000 + (1000*aleatorio.nextGaussian())));
			System.out.println("C: Cliente " + this.identificador + " recebeu o corte de cabelo e sai da barbearia!");
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}