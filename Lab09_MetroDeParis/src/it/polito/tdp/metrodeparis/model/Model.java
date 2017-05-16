package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	private Map<Integer,Fermata> fermate = new HashMap<>();
	private Map<Integer,Linea> linee = new HashMap<>() ;
	private WeightedGraph<Fermata,DefaultWeightedEdge> grafo = new WeightedMultigraph<>(DefaultWeightedEdge.class);
	
	public Model(){
		MetroDAO dao = new MetroDAO();
		dao.creaGrafo(fermate, linee, grafo);
	}

	public Collection<Fermata> getFermate() {
		return fermate.values();
	}

	public List<Fermata> getRaggiungibili(Fermata f) {
		BreadthFirstIterator<Fermata,DefaultWeightedEdge> bfi = new BreadthFirstIterator<>(grafo);
		List<Fermata> raggiungibili = new ArrayList<Fermata>();
		while(bfi.hasNext())
			raggiungibili.add((Fermata) bfi.next());
		return raggiungibili;
	}
	
	public String calcolaPercorso(Fermata p,Fermata a){
		DijkstraShortestPath<Fermata,DefaultWeightedEdge> dsp = new DijkstraShortestPath<>(grafo,p,a);
		dsp.findPathBetween(grafo, p, a);
		String res = "" ;
		for(DefaultWeightedEdge e : dsp.getPathEdgeList())
			res+=e.toString()+"\n";
		return res ;
	}
	
	
	

}
