package it.polito.tdp.PremierLeague.model;

public class AzioneSaliente implements Comparable<AzioneSaliente> {
	
	public enum Tipologia{
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	};
	
	private int indice;
	private Tipologia tipo;

	public AzioneSaliente(int indice, Tipologia tipo) {
		super();
		this.indice = indice;
		this.tipo = tipo;
	}

	public Tipologia getTipo() {
		return tipo;
	}
	
	public int getIndice() {
		return indice;
	}

	@Override
	public int compareTo(AzioneSaliente o) {
		// TODO Auto-generated method stub
		return o.getIndice()-this.getIndice();
	}

	@Override
	public String toString() {
		return indice+" - "+tipo;
	}
	
	

}
