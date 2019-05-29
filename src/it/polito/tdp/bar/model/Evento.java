package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Evento implements Comparable<Evento> {

	public enum TipoEvento{
		ARRIVO_GRUPPO_CLIENTI,
		TAVOLO_LIBERATO //i clienti lasciano un tavolo
	}
	
	
	private TipoEvento tipo;
	private Duration time;
	private int num_persone;
	private Duration durata;
	private float tolleranza;
	private int id;
	
	public Evento(TipoEvento tipo, Duration time, int num_persone, Duration durata, float tolleranza) {
		this.tipo = tipo;
		this.time = time;
		this.num_persone = num_persone;
		this.durata = durata;
		this.tolleranza = tolleranza;
	}

	@Override
	public int compareTo(Evento other) {
		// TODO Auto-generated method stub
		return this.time.compareTo(other.time);
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public int getNum_persone() {
		return num_persone;
	}

	public void setNum_persone(int num_persone) {
		this.num_persone = num_persone;
	}

	public Duration getDurata() {
		return durata;
	}

	public void setDurata(Duration durata) {
		this.durata = durata;
	}

	public float getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}

	@Override
	public String toString() {
		return "Evento [tipo=" + tipo + ", time=" + time + ", num_persone=" + num_persone + ", durata=" + durata
				+ ", tolleranza=" + tolleranza + "]";
	}

	public void setId(int id) {
		// Questo e' un riferimento al tavolo assegnaato al gruppo di clienti
		this.id=id;
	}

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	
	
	
	}