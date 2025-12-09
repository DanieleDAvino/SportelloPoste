import java.util.ArrayList;
/**
 * Classe che rappresenta la risorsa condivisa fra i due thread
 * da gestire con metodi "synchronized"
 * e con l'uso di wait() e notify()
 * @author frida
 * @version 1.0
 */
public class ListaClienti {
    /*variabili d'istanza*/
    private ArrayList<Integer> listaNumeri;
    private int ultimoArrivo;
    private int ultimoServito;
    private final int numeroMassimo = 20;
    private final int capacitaMassima = 5;
    /**
     * constructor
     * settaggio delle variabili di istanza
     */
    public ListaClienti() {
        listaNumeri = new ArrayList<Integer>();
        ultimoArrivo = 0;
        ultimoServito = 0;
    }

    /*synchronized parola chiave che gestisce il meccanismo del lock, ovvero
    * 1) impedisce ad un altro thread l'esecuzione di tale
    * metodo, se un precedente thread lo sta già eseguendo
    * 2) senza di lui wait e notify non possono essere usati
    * si genera l'eccezione : IllegalMonitorStateException,*/

    /**
     * metodo eseguito da un thread della Classe Sportello
     * si chiede se è in coda un altro cliente dopo l'ultimo che ha servito
     * se c'è lo server (incrementa di uno ultimoServito)
     * else resta di attesa che arrivi...(notify)
     * @return Integer ultimoServito synchronized
     */
    public synchronized Integer rimuoviCliente(String nome) throws InterruptedException{
        while (ultimoServito >= ultimoArrivo) {
            System.out.println("non ci sono arrivi dopo l'ultimo servito");
            wait();
        }
        ultimoServito++;
        System.out.println("Servito Cliente Numero \t " + ultimoServito+
                " dallo sportello "+ nome);
        return ultimoServito;
    /*while (listaNumeri.isEmpty()){
        System.out.println("non ci sono arrivi dopo l'ultimo servito");
        wait();
    }
        ultimoServito++;
        return ultimoServito;*/
    }



    /**
     * metodo eseguito da un thread della Classe GestoreArrivi
     * che produce un nuovo int aggiungendo 1 all'ultimoArrivo
     * e inserisce tale nuovo numero / ticket nella lista numeri
     * @return Integer: ultimoArrivo o null se gli arrivi saturano
     */
    public synchronized Integer addCliente() throws InterruptedException {
        while ((ultimoArrivo - ultimoServito) >= capacitaMassima) {
            System.out.println("Posta piena, cliente in attesa di entrare...");
            wait();
        }
        if (ultimoArrivo < numeroMassimo) {
            ultimoArrivo++;
            listaNumeri.add(ultimoArrivo);
            notifyAll();
            return ultimoArrivo;
        }
        return null;
    }
}
