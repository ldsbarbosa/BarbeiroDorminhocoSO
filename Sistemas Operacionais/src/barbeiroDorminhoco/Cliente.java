package barbeiroDorminhoco;

import java.util.Date;

public class Cliente implements Runnable {
    int idDoCliente;
    Date noTempo;
    Barbearia barbearia;
    public Cliente(Barbearia barbearia) {
        this.barbearia = barbearia;
    }
    public int getIdDoCliente() {
        return idDoCliente;
    }
    public Date getInTime() {
        return noTempo;
    }
    public void setIdDoCliente(int idDoCliente) {
        this.idDoCliente = idDoCliente;
    }
    public void setNoTempo(Date noTempo) {
        this.noTempo = noTempo;
    }
    public void run() {
        irCortarOCabelo();
    }
    private synchronized void irCortarOCabelo() {
        barbearia.adicionar(this);
    }
}
