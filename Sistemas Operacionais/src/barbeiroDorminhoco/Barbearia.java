package barbeiroDorminhoco;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Barbearia {
	private final AtomicInteger cortesDeCabelosTotais = new AtomicInteger(0);
	private final AtomicInteger clientesPerdidos = new AtomicInteger(0);
	int numeroCadeira, numeroDeBarbeiros, barbeirosDisponiveis;
	List<Cliente> listaDeClientes;
	Random r = new Random();
	
	public Barbearia(int numeroDeBarbeiros, int numeroDeCadeiras){
		this.numeroCadeira = numeroDeCadeiras;
		listaDeClientes = new LinkedList<Cliente>();
		this.numeroDeBarbeiros = numeroDeBarbeiros;
		this.barbeirosDisponiveis = numeroDeBarbeiros;
	}
	
	public AtomicInteger getCortesDeCabelosTotais() {
		cortesDeCabelosTotais.get();
		return cortesDeCabelosTotais;
	}
	
	public AtomicInteger getClientesPerdidos() {
		clientesPerdidos.get();
		return clientesPerdidos;
	}
	
	public void cortarCabelo(int idDoBarbeiro){
		Cliente cliente;
		synchronized (listaDeClientes) {
			while(listaDeClientes.size()==0) {
				System.out.println("\nBarbeiro "+idDoBarbeiro+" est� aguardando pelo cliente e dorme em sua cadeira.");
				try {
					listaDeClientes.wait();
				}
				catch(InterruptedException iex) {
					iex.printStackTrace();
				}
			}
			cliente = (Cliente)((LinkedList<?>)listaDeClientes).poll();
			System.out.println("Cliente "+cliente.getIdDoCliente()+" acha o barbeiro dormindo e acorda o barbeiro "+idDoBarbeiro);
		}
		int atrasoEmMilisegundos=0;
		try {
			barbeirosDisponiveis--;
			System.out.println("Barbeiro "+idDoBarbeiro+" cortando o cabelo de "+
					cliente.getIdDoCliente()+ " ent�o, cliente dorme");
			
			// Tempo para cortar o cabelo
			double val = r.nextGaussian() * 2000 + 4000;
			atrasoEmMilisegundos = Math.abs((int) Math.round(val));
			Thread.sleep(atrasoEmMilisegundos); 
			
			System.out.println("\nA atividade de cortar o cabelo do "+
					cliente.getIdDoCliente()+" pelo barbeiro " + 
					idDoBarbeiro +" decorreu em "+ atrasoEmMilisegundos + " millisegundos e foi completada.");

			cortesDeCabelosTotais.incrementAndGet();
			if(listaDeClientes.size()>0) {									
				System.out.println("Barbeiro "+idDoBarbeiro+" acorda o cliente, que est� na sala de espera");		
			}

			barbeirosDisponiveis++;
		}
		catch(InterruptedException iex) {
			iex.printStackTrace();
		}
	}
	
	public void adicionar(Cliente cliente) {
		System.out.println("\nCliente "+cliente.getIdDoCliente()+" entra pela porta da frente na barbearia �s "+cliente.getInTime());
		synchronized (listaDeClientes) {
			if(listaDeClientes.size() == numeroCadeira) {
				System.out.println("\nN�o h� cadeiras dispon�veis para o cliente "+cliente.getIdDoCliente()+", ent�o o cliente deixa a barbearia.");
				clientesPerdidos.incrementAndGet();
				return;
			} else if (barbeirosDisponiveis > 0) {
				((LinkedList<Cliente>)listaDeClientes).offer(cliente);
				listaDeClientes.notify();
			} else {
				((LinkedList<Cliente>)listaDeClientes).offer(cliente);
				System.out.println("Todos os barbeiros est�o ocupados, ent�o o cliente "+cliente.getIdDoCliente()+"pega uma cadeira na sala de espera");
				if(listaDeClientes.size()==1) {
					listaDeClientes.notify();
				}
			}
		}
	}
	
}
