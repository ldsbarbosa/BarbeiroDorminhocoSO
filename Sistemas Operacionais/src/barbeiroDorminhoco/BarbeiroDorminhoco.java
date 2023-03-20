/**
 * Fonte: https://github.com/shivam-moray/Sleeping-Barber-Problem-Java
 * 
 */

package barbeiroDorminhoco;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BarbeiroDorminhoco {
	public static void main (String a[]) throws InterruptedException {
		// Criando variáveis
		int numeroDeBarbeiros = 1, identificadorDoCliente = 1,
			numeroDeClientes = 100, numeroDeCadeiras;
		
		//Instanciando objetos
		Random aleatorio = new Random();
		Scanner leitor = new Scanner(System.in);
		ExecutorService executor = Executors.newFixedThreadPool(12); // ExecutorService é como se fosse a Thread, ou o Processo.
		
		/*
		 * ###############
		 * ###############
		 */
		
		// System.out.println("Qual é o número de barbeiros?");
    	// numeroDeBarbeiros = leitor.nextInt();
    	// Via de regra, é sempre um barbeiro
		
    	System.out.println("Qual é o número de cadeiras na sala de espera?");
    	numeroDeCadeiras = leitor.nextInt();
    	
    	System.out.println("Qual é o número de clientes?");
    	numeroDeClientes = leitor.nextInt();
		
    	Barbearia barbearia = new Barbearia(numeroDeBarbeiros, numeroDeCadeiras);
    	
        System.out.println("\nBarbearia abriu com " + numeroDeBarbeiros + " barbeiro(s)\n");
        
        long tempoDeComeco  = System.currentTimeMillis();
        
        // Instanciando Barbeiros como Threads para representarem Processos que devem cuidar dos Clientes.
        for(int i = 1; i <= numeroDeBarbeiros; i++) {
        	Barbeiro barbeiro = new Barbeiro(barbearia, i);	
        	Thread threadBarbeiro = new Thread(barbeiro);
            executor.execute(threadBarbeiro);
        }
        
        // Instanciando Clientes como Threads para representarem Processos que disputam Barbeiros.
        for(int i = 0; i < numeroDeClientes; i++) {
            Cliente cliente = new Cliente(barbearia);
            cliente.setNoTempo(new Date());
            Thread threadCliente = new Thread(cliente);
            cliente.setIdDoCliente(identificadorDoCliente++);
            executor.execute(threadCliente);
            try {    	
            	double valor = aleatorio.nextGaussian() * 2000 + 2000;
            	int atrasoEmMilisegundos = Math.abs((int) Math.round(valor));
            	Thread.sleep(atrasoEmMilisegundos);
            }
            catch(InterruptedException iex) {
                iex.printStackTrace();
            }
        }
        executor.shutdown();
        executor.awaitTermination(60, SECONDS); // Pode mudar o primeiro argumento conforme a necessidade
        long tempoDecorrido = System.currentTimeMillis() - tempoDeComeco; //Calcular o termino do programa, o tempo decorrido desde o início até o fim
        
        System.out.println("\nBarbearia fechou!");
        System.out.println("\nTempo total decorrido em segundos para atender "+numeroDeClientes+" clientes para "
        		+numeroDeBarbeiros+" barbeiros com "+numeroDeCadeiras+" cadeiras na sala de espera é: "+TimeUnit.MILLISECONDS.toSeconds(tempoDecorrido));
        System.out.println("\nNúmero total de clientes: "+numeroDeClientes+
        		"\nNúmero total de clientes atendidos: "+barbearia.getCortesDeCabelosTotais()
        		+"\nNúmero total de clientes perdidos: "+barbearia.getClientesPerdidos());
        leitor.close();
    }
}