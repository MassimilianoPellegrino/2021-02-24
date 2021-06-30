package it.polito.tdp.PremierLeague.model;

public class Arco {
	
	int idp1;
	int idp2;
	double w;
	public Arco(int idp1, int idp2, double w) {
		super();
		this.idp1 = idp1;
		this.idp2 = idp2;
		this.w = w;
	}
	public int getIdp1() {
		return idp1;
	}
	public int getIdp2() {
		return idp2;
	}
	public double getW() {
		return w;
	}
	
	

}
