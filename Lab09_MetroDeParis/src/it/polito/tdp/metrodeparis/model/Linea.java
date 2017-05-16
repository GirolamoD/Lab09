package it.polito.tdp.metrodeparis.model;

public class Linea {
	
	private int idLinea ;
	private String nome ;
	private double velocita ;
	private double intervallo ;
	
	public Linea(int idLinea, String nome, double velocita, double intervallo) {
		super();
		this.idLinea = idLinea;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
	}
		
	public int getIdLinea(){
		return this.idLinea ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLinea;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Linea))
			return false;
		Linea other = (Linea) obj;
		if (idLinea != other.idLinea)
			return false;
		return true;
	}
	
	
	
	

}
