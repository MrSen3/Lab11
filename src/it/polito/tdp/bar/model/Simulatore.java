package it.polito.tdp.bar.model;

import java.time.*;
import java.util.*;

import it.polito.tdp.bar.model.Evento.TipoEvento;

public class Simulatore {

	//Coda
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	
	//Stato del mondo
	private List<Tavolo> listaTavoli = new ArrayList<>();
	
	//Parametri di soluzione
	private Duration tempo = Duration.ofMinutes(0);
	private Duration intervalloArrivoCliente;
	private int num_persone;
	private Duration tempoAlTavolo; //tra 60 e 120 minuti
	private float tolleranza;
	private final int NUM_EVENTI;
	
	//Statistiche raccolte
	private int numero_totale_clienti;
	private int numero_clienti_soddisfatti;
	private int numero_clienti_insoddisfatti;
	
	//Variabili interne
	Random random = new Random();
	
	public Simulatore() {
		NUM_EVENTI=2000;
		//Creazione dei tavoli
		for(int i=0; i<=14; i++) {
			if(i<=4)
				listaTavoli.add(new Tavolo(i, 4, false));
			if(i>=5 && i<=8) 
				listaTavoli.add(new Tavolo(i, 6, false));
			if(i>=9 && i<=12) 
				listaTavoli.add(new Tavolo(i, 8, false));
			if(i>=13 && i<=14)
				listaTavoli.add(new Tavolo(i, 10, false));
			}
		Collections.sort(listaTavoli); //In ordine di grandezza
		
		
	}



	public void init() {
		// TODO Auto-generated method stub
		numero_totale_clienti=0;
		numero_clienti_soddisfatti=0;
		numero_clienti_insoddisfatti=0;
		
		//Inizializzo la coda con tempo=0
		queue.add(new Evento(TipoEvento.ARRIVO_GRUPPO_CLIENTI, tempo, ((random.nextInt(10))+1), Duration.ofMinutes(60 + (random.nextInt(61))), random.nextFloat()));
		
		for(int i=1; i<NUM_EVENTI; i++) {
			num_persone=((random.nextInt(10))+1);
			intervalloArrivoCliente = Duration.ofMinutes((random.nextInt(10))+1);
			tempoAlTavolo = Duration.ofMinutes(60 + (random.nextInt(61)));
			tolleranza = random.nextFloat();
			
			tempo=tempo.plus(intervalloArrivoCliente);
			Evento ev=new Evento(TipoEvento.ARRIVO_GRUPPO_CLIENTI, tempo, num_persone, tempoAlTavolo, tolleranza);
			queue.add(ev);
			System.out.println(i+" "+ev+"\n");
		}
		
	}



	public void run() {
		// TODO Auto-generated method stub
		
		while(!queue.isEmpty()) {
			Evento ev = queue.poll();
			
			switch(ev.getTipo()) {
			
			case ARRIVO_GRUPPO_CLIENTI:
				numero_totale_clienti+=ev.getNum_persone();
				boolean tavoloAssegnato=false;
				//Devo vedere se e' disponibile un tavolo
				//con un numeroPosti>=di ev.getNumPersone
				//Un altro vincolo e' che ev.getnumpersone>i/2
				for(Tavolo t: listaTavoli) {
					if(tavoloAssegnato==false && t.isOccupato()==false &&  t.getNumeroPosti()>=ev.getNum_persone() && ev.getNum_persone()>=t.getNumeroPosti()/2) {
						//Se il tavolo e' libero, ha abbastanza posti e non ne ha troppi liberi allora faccio sedere le persone
						t.setOccupato(true);
						tavoloAssegnato=true;
						//Assegno l'id del tavolo all'evento
						ev.setId(t.getId());
						//Adesso aggiungo alla coda l'evento futuro in cui le persone lasceranno libero il tavolo
						queue.add(new Evento(TipoEvento.TAVOLO_LIBERATO, (ev.getTime().plus(ev.getDurata())), ev.getNum_persone(), Duration.ZERO, 1));
						
					}
				}
				
				//Se non e' stato assegnato il tavolo al gruppo di persone significa che non era disponibile
				//nessun tavolo con i requisiti desiderati
				//In tal caso bisogna vedere se: a) il gruppo decide di andarsene-->insoddisfatti++
											//   b)il gruppo si accomoda al 
				if(tavoloAssegnato==false) {
					float probabilita=random.nextFloat();
					
					if(tolleranza>=probabilita) {
					//Allora si fermano al bancone e sono soddisfatti
						numero_clienti_soddisfatti+=ev.getNum_persone();
					
					}
					else if(tolleranza<probabilita) {
						numero_clienti_insoddisfatti+=ev.getNum_persone();
					}
				}
				
				break;
				
			case TAVOLO_LIBERATO:
				//rendo il tavolo libero
				listaTavoli.get(ev.getId()).setOccupato(false);
				//aumento il numero di clienti soddisfatti
				numero_clienti_soddisfatti+=ev.getNum_persone();
				
				break;
			}
		}
		
		System.out.println("Numero totale di clienti: "+numero_totale_clienti+"\nNumero di clienti soddisfatti: "+numero_clienti_soddisfatti+"\nNumero di clienti insoddisfatti: "+numero_clienti_insoddisfatti);
		
	}

}
