package it.polito.tdp.bar.model;

public class Tavolo implements Comparable<Tavolo> {
	
	int id;
	int numeroPosti;
	boolean occupato;
	
	
	public Tavolo(int id, int numeroPosti, boolean occupato) {
		this.id=id;
		this.numeroPosti = numeroPosti;
		this.occupato = occupato;
	}


	public int getNumeroPosti() {
		return numeroPosti;
	}


	public void setNumeroPosti(int numeroPosti) {
		this.numeroPosti = numeroPosti;
	}


	public boolean isOccupato() {
		return occupato;
	}


	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}


	@Override
	public int compareTo(Tavolo other) {
		// Con questo vorrei ordinare i tavoli in ordine di grandezza
		return this.numeroPosti-other.getNumeroPosti();
	}


	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	
	
	
	
}
