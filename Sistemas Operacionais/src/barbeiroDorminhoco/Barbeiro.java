package barbeiroDorminhoco;

public class Barbeiro implements Runnable {
    Barbearia barbearia;
    int idDoBarbeiro;
    public Barbeiro(Barbearia barbearia, int idDoBarbeiro) {
        this.barbearia = barbearia;
        this.idDoBarbeiro = idDoBarbeiro;
    }
    public void run() {
        while(true) {
            barbearia.cortarCabelo(idDoBarbeiro);
        }
    }
}
